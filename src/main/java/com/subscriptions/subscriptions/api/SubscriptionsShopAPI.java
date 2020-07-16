package com.subscriptions.subscriptions.api;

import com.subscriptions.config.ConfigManager;
import com.subscriptions.string.StringUtils;
import com.subscriptions.subscriptions.connection.HikariInsertOptions;
import com.subscriptions.vault.EconomyUtil;
import com.subscriptions.vault.PermissionsUtil;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SubscriptionsShopAPI {

    private static SubscriptionsShopAPI instance = null;
    private final String prefix = StringUtils.format("&c&lKitPvP &8» ");
    private final int silverPrice = ConfigManager.getConfigManager().getInt("silver-price");
    private final int goldPrice = ConfigManager.getConfigManager().getInt("gold-price");
    private final int platinumPrice = ConfigManager.getConfigManager().getInt("platinum-price");

    public static synchronized SubscriptionsShopAPI getInstance() {
        if (instance == null) instance = new SubscriptionsShopAPI();
        return instance;
    }

    public boolean hasSilver(Player player) {
        return HikariInsertOptions.getInstance().hasSubscription(player.getName(), "SILVER");
    }

    public boolean hasGold(Player player) {
        return HikariInsertOptions.getInstance().hasSubscription(player.getName(), "GOLD");
    }

    public boolean hasPlatinum(Player player) {
        return HikariInsertOptions.getInstance().hasSubscription(player.getName(), "PLATINUM");
    }

    public void giveSilver(Player player) {
        if (hasSilver(player)) return;

        if (EconomyUtil.getBalance(player) >= silverPrice) {
            EconomyUtil.takeMoney(player, silverPrice);
            HikariInsertOptions.getInstance().setSilverPlayer(player);
            PermissionsUtil.givePermission(player, "essentials.kits.viking");
            player.sendMessage(prefix + StringUtils.format("&aYou have successfully bought the &7Silver &eSubscription&a!"));
            if (player.getOpenInventory() != null) {
                player.closeInventory();
            }
            player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
        } else {
            if (EconomyUtil.getBalance(player) < silverPrice) {
                player.sendMessage(prefix + StringUtils.format("&cYou don't have enough to buy the &7Silver &eSubscription&c!"));
                if (player.getOpenInventory() != null) {
                    player.closeInventory();
                }
                player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
            }
        }
    }

    public void giveGold(Player player) {
        if (hasGold(player)) return;
        if (EconomyUtil.getBalance(player) >= goldPrice) {
            EconomyUtil.takeMoney(player, goldPrice);
            HikariInsertOptions.getInstance().setGoldPlayer(player);
            PermissionsUtil.givePermission(player, "essentials.kits.pyro");
            player.sendMessage(prefix + StringUtils.format("&aYou have successfully bought the &6Gold &eSubscription&a!"));
            if (player.getOpenInventory() != null) {
                player.closeInventory();
            }
            player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
        } else {
            if (EconomyUtil.getBalance(player) < goldPrice) {
                player.sendMessage(prefix + StringUtils.format("&cYou don't have enough to buy the &6Gold &eSubscription&c!"));
                if (player.getOpenInventory() != null) {
                    player.closeInventory();
                }
                player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
            }
        }
    }

    public void givePlatinum(Player player) {
        if (hasPlatinum(player)) return;

        if (EconomyUtil.getBalance(player) >= platinumPrice) {
            EconomyUtil.takeMoney(player, platinumPrice);
            HikariInsertOptions.getInstance().setPlatinumPlayer(player);
            PermissionsUtil.givePermission(player, "essentials.kits.tank");
            player.sendMessage(prefix + StringUtils.format("&aYou have successfully bought the &8Platinum &eSubscription&a!"));
            if (player.getOpenInventory() != null) {
                player.closeInventory();
            }
            player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
        } else {
            if (EconomyUtil.getBalance(player) < platinumPrice) {
                player.sendMessage(prefix + StringUtils.format("&cYou don't have enough to buy the &7Platinum &eSubscription&c!"));
                if (player.getOpenInventory() != null) {
                    player.closeInventory();
                }
                player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
            }
        }
    }
}
