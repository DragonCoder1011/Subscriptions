package com.subscriptions.commands;

import com.subscriptions.config.ConfigManager;
import com.subscriptions.file.PlayerFile;
import com.subscriptions.string.StringUtils;
import com.subscriptions.subscriptions.api.SubscriptionsShopAPI;
import com.subscriptions.subscriptions.connection.SubscriptionsHikariInsertOptions;
import com.subscriptions.threads.SubThreads;
import com.subscriptions.vault.PermissionsUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdminCommands extends BukkitCommand {

    private final String prefix = StringUtils.format("&c&lKitPvP &8» ");
    private final List<String> silver = Collections.synchronizedList(new ArrayList<>());
    private final List<String> gold = Collections.synchronizedList(new ArrayList<>());
    private final List<String> platinum =Collections.synchronizedList(new ArrayList<>());

    public AdminCommands(String name) {
        super(name);
        this.setPermission("subscriptions.admin");
        this.setPermissionMessage(prefix + StringUtils.format("&cYou don't have enough permissions to use this command!"));
        this.setUsage(prefix + StringUtils.format("&e/subsa give <player> <silver/gold/platinum> \n" +
                "&e/subsa list"));
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        SubThreads.globalThread.execute(() -> {
            if (!(sender.hasPermission(getPermission()))) {
                sender.sendMessage(getPermissionMessage());
                return;
            }

            if (args.length < 1) {
                sender.sendMessage(getUsage());
                return;
            }

            if (args[0].equalsIgnoreCase("give")) {
                if (args.length < 3) {
                    sender.sendMessage(getUsage());
                    return;
                }

                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    sender.sendMessage(prefix + StringUtils.format("&cThat player doesn't exist!"));
                    return;
                }

                if (args[2].equalsIgnoreCase("silver")) {
                    if (SubscriptionsShopAPI.getInstance().hasSilver(target)) {
                        sender.sendMessage(prefix + StringUtils.format("&7That player already has that subscription!"));
                        return;
                    }

                    SubscriptionsHikariInsertOptions.getInstance().setSilverPlayer(target);
                    silver.add(target.getName());
                    set(silver, "Silver");
                    PermissionsUtil.givePermission(target, "essentials.kits.viking");
                    target.sendMessage(prefix + StringUtils.format("&aYou were successfully given the &7Silver &eSubscription&a!"));
                    sender.sendMessage(prefix + StringUtils.format("&aYou have successfully given this player &7Silver &eSubscription&a!"));
                }

                if (args[2].equalsIgnoreCase("gold")) {
                    if (SubscriptionsShopAPI.getInstance().hasGold(target)) {
                        sender.sendMessage(prefix + StringUtils.format("&7That player already has that subscription!"));
                        return;
                    }

                    SubscriptionsHikariInsertOptions.getInstance().setGoldPlayer(target);
                    gold.add(target.getName());
                    set(gold, "Gold");
                    PermissionsUtil.givePermission(target, "essentials.kits.pyro");
                    target.sendMessage(prefix + StringUtils.format("&aYou were successfully given the &6Gold &eSubscription&a!"));
                    sender.sendMessage(prefix + StringUtils.format("&aYou have successfully given this player &6Gold &eSubscription&a!"));
                }

                if (args[2].equalsIgnoreCase("platinum")) {
                    if (SubscriptionsShopAPI.getInstance().hasPlatinum(target)) {
                        sender.sendMessage(prefix + StringUtils.format("&7That player already has that subscription!"));
                        return;
                    }

                    SubscriptionsHikariInsertOptions.getInstance().setPlatinumPlayer(target);
                    platinum.add(target.getName());
                    set(platinum, "Platinum");
                    PermissionsUtil.givePermission(target, "essentials.kits.tank");
                    target.sendMessage(prefix + StringUtils.format("&aYou were successfully given the &8Platinum &eSubscription&a!"));
                    sender.sendMessage(prefix + StringUtils.format("&aYou have successfully given this player &8Patinum &eSubscription&a!"));
                }
            }
        });

        return true;
    }

    private synchronized void set(List<String> list, String path) {
        PlayerFile.getFileConfiguration().set(path, list);
        PlayerFile.saveFile();
    }
}
