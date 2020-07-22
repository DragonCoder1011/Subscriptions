package com.subscriptions.listeners;

import com.subscriptions.builder.ItemBuilder;
import com.subscriptions.inventories.gold.GoldCosmeticsMenu;
import com.subscriptions.inventories.platinum.PlatinumCosmeticsMenu;
import com.subscriptions.inventories.silver.SilverCosmeticsMenu;
import com.subscriptions.string.StringUtils;
import com.subscriptions.subscriptions.api.TrailsAPI;
import com.subscriptions.subscriptions.api.SubscriptionsShopAPI;
import com.subscriptions.subscriptions.connection.CosmeticsHikariInsertOptions;
import com.subscriptions.subscriptions.enums.PrepareStatements;
import com.subscriptions.threads.SubThreads;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public class CosmeticListeners implements Listener {

    private final String prefix = StringUtils.format("&c&lKitPvP &8» ");

    @EventHandler
    public void setData(PlayerLoginEvent e) {
        Player player = e.getPlayer();
        SubThreads.globalThread.execute(() -> {
            CosmeticsHikariInsertOptions.getInstance().setParticleData(player.getName());
        });
    }

    @EventHandler
    public void openCosmeticsMenu(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (e.getInventory().getTitle().equalsIgnoreCase(StringUtils.format("&7Subscription Perks Menu"))) {
            e.setCancelled(true);
        }
        if (e.getInventory().getTitle().equalsIgnoreCase(StringUtils.format("&7Subscription Perks Menu"))) {
            if (ItemBuilder.hasDisplayName(e.getCurrentItem())) {
                String item = e.getCurrentItem().getItemMeta().getDisplayName();
                if (item.contains(StringUtils.format("&eSubscriptions"))) {
                    e.setCancelled(true);
                }

                if (item.equalsIgnoreCase(StringUtils.format("&7Silver &eSubscription &6Perks"))) {
                    if (!SubscriptionsShopAPI.getInstance().hasSilver(player)) {
                        player.sendMessage(StringUtils.format(prefix + "&cYou don't have the &7Silver &eSubscription&7!"));
                        player.closeInventory();
                        player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
                    } else {
                        if (SubscriptionsShopAPI.getInstance().hasSilver(player)) {
                            SilverCosmeticsMenu.getInstance().init(player);
                            player.playSound(player.getLocation(), Sound.CHEST_OPEN, 10, 29);
                        }
                    }
                }

                if (item.equalsIgnoreCase(StringUtils.format("&6Gold &eSubscription &6Perks"))) {
                    if (!SubscriptionsShopAPI.getInstance().hasGold(player)) {
                        player.sendMessage(StringUtils.format(prefix + "&cYou don't have the &6Gold &eSubscription&7!"));
                        player.closeInventory();
                        player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
                    } else {
                        if (SubscriptionsShopAPI.getInstance().hasGold(player)) {
                            GoldCosmeticsMenu.getInstance().init(player);
                            player.playSound(player.getLocation(), Sound.CHEST_OPEN, 10, 29);
                        }
                    }
                }

                if (item.equalsIgnoreCase(StringUtils.format("&8Platinum &eSubscription &6Perks"))) {
                    if (!SubscriptionsShopAPI.getInstance().hasPlatinum(player)) {
                        player.sendMessage(StringUtils.format(prefix + "&cYou don't have the &8Platinum &eSubscription&7!"));
                        player.closeInventory();
                        player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
                    } else {
                        if (SubscriptionsShopAPI.getInstance().hasPlatinum(player)) {
                            PlatinumCosmeticsMenu.getInstance().init(player);
                            player.playSound(player.getLocation(), Sound.CHEST_OPEN, 10, 29);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void silverCosmetics(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (e.getInventory().getTitle().equalsIgnoreCase(StringUtils.format("&7Silver Perks"))) {
            e.setCancelled(true);
        }
        if (e.getInventory().getTitle().equalsIgnoreCase(StringUtils.format("&7Silver Perks"))) {
            if (ItemBuilder.hasDisplayName(e.getCurrentItem())) {
                String item = e.getCurrentItem().getItemMeta().getDisplayName();
                if (item.contains(StringUtils.format("&eTrail"))) {
                    e.setCancelled(true);
                }

                SubThreads.globalThread.execute(() -> {
                    TrailsAPI api = TrailsAPI.getInstance();
                    api.chooseParticle(e.getCurrentItem(), "&cHeart &eTrail", player,
                            PrepareStatements.HEARTID.getStatement(), PrepareStatements.SETHEART.getStatement(), PrepareStatements.UPDATEHEART.getStatement());

                    api.chooseParticle(e.getCurrentItem(), "&dMagic &eTrail", player,
                            PrepareStatements.MAGICID.getStatement(), PrepareStatements.SETMAGIC.getStatement(), PrepareStatements.UPDATEMAGIC.getStatement());

                    api.chooseParticle(e.getCurrentItem(), "&fCloud &eTrail", player,
                            PrepareStatements.CLOUDID.getStatement(), PrepareStatements.SETCLOUD.getStatement(), PrepareStatements.UPDATECLOUD.getStatement());

                    api.chooseParticle(e.getCurrentItem(), "&dEnder &eTrail", player,
                            PrepareStatements.EMERALDID.getStatement(), PrepareStatements.SETENDER.getStatement(), PrepareStatements.UPDATEENDER.getStatement());
                });
            }
        }
    }

    @EventHandler
    public void goldCosmetics(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (e.getInventory().getTitle().equalsIgnoreCase(StringUtils.format("&6Gold Perks"))) {
            e.setCancelled(true);
        }
        if (e.getInventory().getTitle().equalsIgnoreCase(StringUtils.format("&6Gold Perks"))) {
            if (ItemBuilder.hasDisplayName(e.getCurrentItem())) {
                String item = e.getCurrentItem().getItemMeta().getDisplayName();
                if (item.contains(StringUtils.format("&eTrail"))) {
                    e.setCancelled(true);
                }

                SubThreads.globalThread.execute(() -> {
                    TrailsAPI api = TrailsAPI.getInstance();
                    api.chooseParticle(e.getCurrentItem(), "&fSpark &eTrail", player,
                            PrepareStatements.SPARKID.getStatement(), PrepareStatements.SETSPARK.getStatement(), PrepareStatements.UPDATESPARK.getStatement());

                    api.chooseParticle(e.getCurrentItem(), "&6Flame &eTrail", player,
                            PrepareStatements.FLAMEID.getStatement(), PrepareStatements.SETFLAME.getStatement(), PrepareStatements.UPDATEFLAME.getStatement());

                    api.chooseParticle(e.getCurrentItem(), "&fSnow &eTrail", player,
                            PrepareStatements.SNOWID.getStatement(), PrepareStatements.SETSNOW.getStatement(), PrepareStatements.UPDATESNOW.getStatement());

                    api.chooseParticle(e.getCurrentItem(), "&bWater &eTrail", player,
                            PrepareStatements.WATERID.getStatement(), PrepareStatements.SETWATER.getStatement(), PrepareStatements.UPDATEWATER.getStatement());
                });
            }
        }
    }

    @EventHandler
    public void platinumCosmetics(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (e.getInventory().getTitle().equalsIgnoreCase(StringUtils.format("&8Platinum Perks"))) {
            e.setCancelled(true);
        }
        if (e.getInventory().getTitle().equalsIgnoreCase(StringUtils.format("&8Platinum Perks"))) {
            if (ItemBuilder.hasDisplayName(e.getCurrentItem())) {
                String item = e.getCurrentItem().getItemMeta().getDisplayName();
                if (item.contains(StringUtils.format("&eTrail"))) {
                    e.setCancelled(true);
                }

                SubThreads.globalThread.execute(() -> {
                    TrailsAPI api = TrailsAPI.getInstance();
                    api.chooseParticle(e.getCurrentItem(), "&7Smoke &eTrail", player,
                            PrepareStatements.SMOKEID.getStatement(), PrepareStatements.SETSMOKE.getStatement(), PrepareStatements.UPDATESMOKE.getStatement());

                    api.chooseParticle(e.getCurrentItem(), "&aSlime &eTrail", player,
                            PrepareStatements.SLIMEID.getStatement(), PrepareStatements.SETSLIME.getStatement(), PrepareStatements.UPDATESLIME.getStatement());

                    api.chooseParticle(e.getCurrentItem(), "&fGlyph &eTrail", player,
                            PrepareStatements.ENCHANTEDID.getStatement(), PrepareStatements.SETENCHANTED.getStatement(), PrepareStatements.UPDATEENCHANTED.getStatement());

                    api.chooseParticle(e.getCurrentItem(), "&cMusic &eTrail", player,
                            PrepareStatements.MUSICID.getStatement(), PrepareStatements.SETMUSIC.getStatement(), PrepareStatements.UPDATEMUSIC.getStatement());

                    api.chooseParticle(e.getCurrentItem(), "&aEmerald &eTrail", player,
                            PrepareStatements.EMERALDID.getStatement(), PrepareStatements.SETEMERALD.getStatement(), PrepareStatements.UPDATEEMERALD.getStatement());
                });
            }
        }
    }
}
