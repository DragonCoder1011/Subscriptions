package com.subscriptions.listeners;

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
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class SubscriptionListeners implements Listener {

    private final String prefix = StringUtils.format("&c&lKitPvP &8» ");
    private final Map<String, ItemStack> silverMap = Maps.newConcurrentMap();
    private final Map<String, ItemStack> goldMap = Maps.newConcurrentMap();
    private final Map<String, ItemStack> platinumMap = Maps.newConcurrentMap();

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
                    Bukkit.getScheduler().runTaskLaterAsynchronously(Subscriptions.plugin, () -> {
                        SubscriptionsShopAPI.getInstance().giveSilver(player);
                    }, 1);
                }

                if (item.equalsIgnoreCase(StringUtils.format("&6Gold &eSubscription"))) {
                    Bukkit.getScheduler().runTaskLaterAsynchronously(Subscriptions.plugin, () -> {
                        SubscriptionsShopAPI.getInstance().giveGold(player);
                    }, 1);
                }

                if (item.equalsIgnoreCase(StringUtils.format("&8Platinum &eSubscription"))) {
                    Bukkit.getScheduler().runTaskLaterAsynchronously(Subscriptions.plugin, () -> {
                        SubscriptionsShopAPI.getInstance().givePlatinum(player);
                    }, 1);
                }
            }
        }
    }

    @EventHandler
    public void onMoney(PlayerDeathEvent e) {
        Player killer = e.getEntity().getKiller();
        Player killed = e.getEntity();
        if (killed.getWorld().getName().equalsIgnoreCase("pvpworld") && killer.getWorld().getName().equalsIgnoreCase("pvpworld") ||
                killed.getWorld().getName().equalsIgnoreCase("void") && killer.getWorld().getName().equalsIgnoreCase("void")) {
            SubThreads.globalThread.execute(() -> {
                if (SubscriptionsShopAPI.getInstance().hasSilver(killer)) {
                    EconomyUtil.giveMoney(killer, 2.50);
                    killer.sendMessage(prefix + StringUtils.format("&7Here's an extra &e2.50 &7from your Silver &eSubscription&7!"));
                }

                if (SubscriptionsShopAPI.getInstance().hasGold(killer)) {
                    EconomyUtil.giveMoney(killer, 5.00);
                    killer.sendMessage(prefix + StringUtils.format("&7Here's an extra &e5.00 &7from your &6Gold &eSubscription&7!"));
                }

                if (SubscriptionsShopAPI.getInstance().hasPlatinum(killer)) {
                    EconomyUtil.giveMoney(killer, 7.50);
                    killer.sendMessage(prefix + StringUtils.format("&7Here's an extra &e7.50 &7from your &8Platinum &eSubscription&7!"));
                }
            });
        }

        double chance = ThreadLocalRandom.current().nextDouble(0, 1);
        double goldRate = ConfigManager.getConfigManager().getDouble("gold-drop-rate");
        if (chance <= goldRate) {
            SubThreads.globalThread.execute(() -> {
                ItemStack silver = ItemBuilder.Builder.getInstance().itemType(Material.GOLD_INGOT).itemAmount(2).
                        itemName(StringUtils.format("&6&lGolden Artifact")).itemLore(
                        StringUtils.format("&eUse these to trade into the Gold Shop!")).build();
                ItemStack gold = ItemBuilder.Builder.getInstance().itemType(Material.GOLD_INGOT).itemAmount(4).
                        itemName(StringUtils.format("&6&lGolden Artifact")).itemLore(
                        StringUtils.format("&eUse these to trade into the Gold Shop!")).build();
                ItemStack platinum = ItemBuilder.Builder.getInstance().itemType(Material.GOLD_INGOT).itemAmount(6).
                        itemName(StringUtils.format("&6&lGolden Artifact")).itemLore(
                        StringUtils.format("&eUse these to trade into the Gold Shop!")).build();

                if (SubscriptionsShopAPI.getInstance().hasSilver(killer)) {
                    silverMap.put(killer.getName(), silver);
                }

                if (SubscriptionsShopAPI.getInstance().hasGold(killer)) {
                    goldMap.put(killer.getName(), gold);
                }

                if (SubscriptionsShopAPI.getInstance().hasPlatinum(killer)) {
                    platinumMap.put(killer.getName(), platinum);
                }
            });

            if (!silverMap.containsKey(killer.getName()) || !goldMap.containsKey(killer.getName()) || !platinumMap.containsKey(killer.getName())) {
                return;
            }

            killer.getWorld().dropItem(killer.getLocation(), silverMap.get(killer.getName()));
            silverMap.remove(killer.getName());

            killer.getWorld().dropItem(killer.getLocation(), goldMap.get(killer.getName()));
            goldMap.remove(killer.getName());

            killer.getWorld().dropItem(killer.getLocation(), platinumMap.get(killer.getName()));
            platinumMap.remove(killer.getName());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        String name = p.getName();
        if (silverMap.containsKey(name) || goldMap.containsKey(name) || platinumMap.containsKey(name)) {
            silverMap.remove(name);
            goldMap.remove(name);
            platinumMap.remove(name);
        }
    }
}
