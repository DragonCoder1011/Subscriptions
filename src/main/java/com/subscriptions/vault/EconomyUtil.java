package com.subscriptions.vault;

import com.subscriptions.main.Subscriptions;
import org.bukkit.entity.Player;

public class EconomyUtil {

    public static synchronized void giveMoney(Player player, double amount) {
        Subscriptions.economy.depositPlayer(player, amount);
    }

    public static synchronized void takeMoney(Player player, int amount) {
        Subscriptions.economy.withdrawPlayer(player, amount);
    }

    public static synchronized double getBalance(Player player) {
        return Subscriptions.economy.getBalance(player);
    }
}
