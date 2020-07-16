package com.subscriptions.commands;

import com.subscriptions.inventories.SubscriptionsMenu;
import com.subscriptions.main.Subscriptions;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

public class SubscriptionsMenuCommand extends BukkitCommand {

    public SubscriptionsMenuCommand(String name) {
        super(name);
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(Subscriptions.plugin, () -> {
            if (!(sender instanceof Player)) {
                System.out.println("Only players can use this command!");
                return;
            }

            Player player = (Player) sender;
            SubscriptionsMenu.getInstance().init(player);
            player.playSound(player.getLocation(), Sound.CHEST_OPEN, 1, 1);
        }, 1);

        return true;
    }
}
