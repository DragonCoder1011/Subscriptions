package com.subscriptions.commands;

import com.subscriptions.inventories.subscriptions.SubscriptionsPerksMenu;
import com.subscriptions.threads.SubThreads;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

public class PerkCommands extends BukkitCommand {


    public PerkCommands(String name) {
        super(name);
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        SubThreads.globalThread.execute(() -> {
            if (!(sender instanceof Player)) {
                System.out.println("Only players can use this command!");
                return;
            }

            Player player = (Player) sender;
            SubscriptionsPerksMenu.getInstance().init(player);
            player.playSound(player.getLocation(), Sound.CHEST_OPEN, 1, 1);
        });

        return true;
    }
}

