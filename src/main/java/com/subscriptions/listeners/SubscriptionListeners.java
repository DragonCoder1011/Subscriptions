package com.subscriptions.listeners;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.subscriptions.builder.ItemBuilder;
import com.subscriptions.config.ConfigManager;
import com.subscriptions.main.Subscriptions;
import com.subscriptions.string.StringUtils;
import com.subscriptions.subscriptions.api.SubscriptionsShopAPI;
import com.subscriptions.threads.SubThreads;
import com.subscriptions.vault.EconomyUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiConsumer;

public class SubscriptionListeners implements Listener {

    private final String prefix = StringUtils.format("&c&lKitPvP &8» ");
    private final List<String> silverList = Collections.synchronizedList(Lists.newArrayList());
    private final List<String> goldList = Collections.synchronizedList(Lists.newArrayList());
    private final List<String> platinumList = Collections.synchronizedList(Lists.newArrayList());


    @EventHandler
    public void onClick(InventoryClickEvent e) {

        Player player = (Player) e.getWhoClicked();

        if (e.getInventory().getTitle().equalsIgnoreCase(StringUtils.format("&cSubscriptions Menu"))) {
            e.setCancelled(true);
        }

        if (ItemBuilder.hasDisplayName(e.getCurrentItem())) {
            if (e.getInventory().getTitle().equalsIgnoreCase(StringUtils.format("&cSubscriptions Menu"))) {
                String item = e.getCurrentItem().getItemMeta().getDisplayName();
                if (item.contains(StringUtils.format("&eSubscription"))) {
                    e.setCancelled(true);
                }

                if (item.equalsIgnoreCase(StringUtils.format("&7Silver &eSubscription"))) {
                    SubThreads.globalThread.execute(() -> {
                        SubscriptionsShopAPI.getInstance().giveSilver(player);
                    });
                }

                if (item.equalsIgnoreCase(StringUtils.format("&6Gold &eSubscription"))) {
                    SubThreads.globalThread.execute(() -> {
                        SubscriptionsShopAPI.getInstance().giveGold(player);
                    });
                }

                if (item.equalsIgnoreCase(StringUtils.format("&8Platinum &eSubscription"))) {
                    SubThreads.globalThread.execute(() -> {
                        SubscriptionsShopAPI.getInstance().givePlatinum(player);
                    });
                }
            }
        }
    }

    @EventHandler
    public void onDrops(PlayerDeathEvent e) {
        Player killer = e.getEntity().getKiller();
        Player killed = e.getEntity();
        if (killer == null) return;
        if (killer == killed) return;
        if (killed.getWorld().getName().equalsIgnoreCase("pvpworld") && killer.getWorld().getName().equalsIgnoreCase("pvpworld") ||
                killed.getWorld().getName().equalsIgnoreCase("void") && killer.getWorld().getName().equalsIgnoreCase("void")) {
            SubThreads.globalThread.execute(() -> {
                if (SubscriptionsShopAPI.getInstance().hasSilver(killer) && SubscriptionsShopAPI.getInstance().hasGold(killer)
                        && SubscriptionsShopAPI.getInstance().hasPlatinum(killer)) {
                    EconomyUtil.giveMoney(killer, 15.00);
                    killer.sendMessage(prefix + StringUtils.format(
                            "&7Here's an extra &6$15.00 &7from your &7Silver, &6Gold&7, And &8Platinum &eSubscription&7!"));

                } else if (SubscriptionsShopAPI.getInstance().hasSilver(killer) && SubscriptionsShopAPI.getInstance().hasGold(killer)) {
                    EconomyUtil.giveMoney(killer, 7.50);
                    killer.sendMessage(prefix + StringUtils.format(
                            "&7Here's an extra &6$7.50 &7from your &7Silver, And &6Gold &eSubscription&7!"));
                } else if (SubscriptionsShopAPI.getInstance().hasSilver(killer) && SubscriptionsShopAPI.getInstance().hasPlatinum(killer)) {
                    EconomyUtil.giveMoney(killer, 10.00);
                    killer.sendMessage(prefix + StringUtils.format(
                            "&7Here's an extra &6$7.50 &7from your &7Silver, And &8Platinum &eSubscription&7!"));

                } else if (SubscriptionsShopAPI.getInstance().hasGold(killer) && SubscriptionsShopAPI.getInstance().hasPlatinum(killer)) {
                    EconomyUtil.giveMoney(killer, 12.50);
                    killer.sendMessage(prefix + StringUtils.format(
                            "&7Here's an extra &6$12.50 &7from your &6Gold&7, And &8Platinum &eSubscription&7!"));
                } else if (SubscriptionsShopAPI.getInstance().hasSilver(killer)) {
                    EconomyUtil.giveMoney(killer, 2.50);
                    killer.sendMessage(prefix + StringUtils.format(
                            "&7Here's an extra &6$2.50 &7from your &7Silver &eSubscription&7!"));
                } else if (SubscriptionsShopAPI.getInstance().hasGold(killer)) {
                    EconomyUtil.giveMoney(killer, 5.00);
                    killer.sendMessage(prefix + StringUtils.format(
                            "&7Here's an extra &6$5.00 &7from your &6Gold &eSubscription&7!"));
                } else if (SubscriptionsShopAPI.getInstance().hasPlatinum(killer)) {
                    EconomyUtil.giveMoney(killer, 7.50);
                    killer.sendMessage(prefix + StringUtils.format(
                            "&7Here's an extra &6$7.50 &7from your &8Platinum &eSubscription&7!"));
                }
            });
        }

        double chance = ThreadLocalRandom.current().nextDouble(0, 1);
        double goldRate = ConfigManager.getConfigManager().getDouble("gold-drop-rate");
        if (chance <= goldRate) {
            ItemStack silver = ItemBuilder.Builder.getInstance().itemType(Material.GOLD_INGOT).itemAmount(2).
                    itemName(StringUtils.format("&6&lGolden Artifact")).itemLore(
                    StringUtils.format("&eUse these to trade into the Gold Shop!")).build();
            ItemStack gold = ItemBuilder.Builder.getInstance().itemType(Material.GOLD_INGOT).itemAmount(4).
                    itemName(StringUtils.format("&6&lGolden Artifact")).itemLore(
                    StringUtils.format("&eUse these to trade into the Gold Shop!")).build();
            ItemStack platinum = ItemBuilder.Builder.getInstance().itemType(Material.GOLD_INGOT).itemAmount(6).
                    itemName(StringUtils.format("&6&lGolden Artifact")).itemLore(
                    StringUtils.format("&eUse these to trade into the Gold Shop!")).build();

            CompletableFuture.runAsync(() -> {

                if (SubscriptionsShopAPI.getInstance().hasSilver(killer)) {
                    silverList.add(killer.getName());
                    killer.sendMessage(prefix + StringUtils.format("&e+2 &6&lGolden Artifacts &7have dropped!"));

                }

                if (SubscriptionsShopAPI.getInstance().hasGold(killer)) {
                    goldList.add(killer.getName());
                    killer.sendMessage(prefix + StringUtils.format("&e+4 &6&lGolden Artifacts &7have dropped!"));

                }

                if (SubscriptionsShopAPI.getInstance().hasPlatinum(killer)) {
                    platinumList.add(killer.getName());
                    killer.sendMessage(prefix + StringUtils.format("&e+6 &6&lGolden Artifacts &7have dropped!"));
                }
            }).whenCompleteAsync((aVoid, throwable) -> Bukkit.getScheduler().runTaskLater(Subscriptions.plugin, () -> {

                if (silverList.contains(killer.getName())) {
                    killer.getWorld().dropItem(killer.getLocation(), silver);
                    silverList.remove(killer.getName());
                }

                if (goldList.contains(killer.getName())) {
                    killer.getWorld().dropItem(killer.getLocation(), gold);
                    goldList.remove(killer.getName());
                }

                if (platinumList.contains(killer.getName())) {
                    killer.getWorld().dropItem(killer.getLocation(), platinum);
                    platinumList.remove(killer.getName());
                }
            }, 20));
        }
    }

    @EventHandler
    public void removeListDataOnQuit(PlayerQuitEvent e) {
        String name = e.getPlayer().getName();
        if (silverList.contains(name) || goldList.contains(name) || platinumList.contains(name)) {
            silverList.remove(name);
            goldList.remove(name);
            platinumList.remove(name);
        }
    }
}