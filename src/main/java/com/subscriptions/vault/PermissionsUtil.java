package com.subscriptions.vault;

import com.subscriptions.main.Subscriptions;
import org.bukkit.entity.Player;

public class PermissionsUtil {

    public static synchronized void givePermission(Player player, String permission) {
        Subscriptions.permission.playerAdd(null, player, permission);
    }
}
