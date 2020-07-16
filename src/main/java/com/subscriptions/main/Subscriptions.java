package com.subscriptions.main;

import com.subscriptions.commands.AdminCommands;
import com.subscriptions.commands.SubscriptionsMenuCommand;
import com.subscriptions.listeners.SubscriptionListeners;
import com.subscriptions.subscriptions.connection.SubscriptionsHikari;
import com.subscriptions.threads.SubThreads;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public class Subscriptions extends JavaPlugin {

    public static Plugin plugin;
    public static Economy economy;
    public static Permission permission;

    public void onEnable() {
        plugin = this;
        registerCommands();
        registerListeners();
        registerConfig();
        setupEconomy();
        setUpPermission();
        SubscriptionsHikari.getInstance().connectToDB();
    }

    public void onDisable() {
        SubThreads.globalThread.shutdown();
        SubscriptionsHikari.getInstance().disconnect();
    }

    private void registerCommands() {
        addCommand("subsa", new AdminCommands("subsa"));
        addCommand("subscriptions", new SubscriptionsMenuCommand("subscriptions"));
    }

    private void registerListeners() {
        addListener(new SubscriptionListeners());
    }

    private void registerConfig() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
    }

    private void addCommand(String cmd, BukkitCommand bc) {
        try {
            Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
            commandMap.register(cmd, bc);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    private void addListener(Listener... listener) {
        for (Listener listeners : listener) {
            Bukkit.getPluginManager().registerEvents(listeners, this);
        }
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = this.getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider != null) {
            economy = (Economy) economyProvider.getProvider();
        }

        return economy != null;
    }

    private boolean setUpPermission() {
        RegisteredServiceProvider<Permission> permProvider = this.getServer().getServicesManager().getRegistration(Permission.class);
        if (permProvider != null) {
            permission = (Permission) permProvider.getProvider();
        }

        return permission != null;
    }
}
