package com.subscriptions.subscriptions.api;

import com.subscriptions.config.ConfigManager;
import com.subscriptions.file.PlayerFile;
import com.subscriptions.string.StringUtils;
import com.subscriptions.subscriptions.connection.SubscriptionsHikariInsertOptions;
import com.subscriptions.vault.EconomyUtil;
import com.subscriptions.vault.PermissionsUtil;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SubscriptionsShopAPI {

    private static SubscriptionsShopAPI instance = null;
    private final String prefix = StringUtils.format("&c&lKitPvP &8» ");
    private final int silverPrice = ConfigManager.getConfigManager().getInt("silver-price");
    private final int goldPrice = ConfigManager.getConfigManager().getInt("gold-price");
    private final int platinumPrice = ConfigManager.getConfigManager().getInt("platinum-price");
    private final List<String> silver = Collections.synchronizedList(new ArrayList<>());
    private final List<String> gold = Collections.synchronizedList(new ArrayList<>());
    private final List<String> platinum = Collections.synchronizedList(new ArrayList<>());

    public static synchronized SubscriptionsShopAPI getInstance() {
        if (instance == null) instance = new SubscriptionsShopAPI();
        return instance;
    }

    public boolean hasSilver(Player player) {
        return SubscriptionsHikariInsertOptions.getInstance().hasSubscription(player.getName(), "SILVER");
    }

    public boolean hasGold(Player player) {
        return SubscriptionsHikariInsertOptions.getInstance().hasSubscription(player.getName(), "GOLD");
    }

    public boolean hasPlatinum(Player player) {
        return SubscriptionsHikariInsertOptions.getInstance().hasSubscription(player.getName(), "PLATINUM");
    }

    public void giveSilver(Player player) {
        if (hasSilver(player)) return;

        if (EconomyUtil.getBalance(player) >= silverPrice) {
            EconomyUtil.takeMoney(player, silverPrice);
            SubscriptionsHikariInsertOptions.getInstance().setSilverPlayer(player);
            PermissionsUtil.givePermission(player, "essentials.kits.viking");
            player.sendMessage(prefix + StringUtils.format("&aYou have successfully bought the &7Silver &eSubscription&a!"));
            silver.add(player.getName());
            set(silver, "Silver");
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
            SubscriptionsHikariInsertOptions.getInstance().setGoldPlayer(player);
            PermissionsUtil.givePermission(player, "essentials.kits.pyro");
            player.sendMessage(prefix + StringUtils.format("&aYou have successfully bought the &6Gold &eSubscription&a!"));
            gold.add(player.getName());
            set(gold, "Gold");
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
            SubscriptionsHikariInsertOptions.getInstance().setPlatinumPlayer(player);
            PermissionsUtil.givePermission(player, "essentials.kits.tank");
            player.sendMessage(prefix + StringUtils.format("&aYou have successfully bought the &8Platinum &eSubscription&a!"));
            platinum.add(player.getName());
            set(platinum, "Platinum");
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

    private synchronized void set(List<String> list, String path) {
        PlayerFile.getFileConfiguration().set(path, list);
        PlayerFile.saveFile();
    }
}
