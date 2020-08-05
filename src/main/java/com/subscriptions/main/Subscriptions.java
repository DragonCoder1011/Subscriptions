package com.subscriptions.main;

import com.subscriptions.commands.AdminCommands;
import com.subscriptions.commands.PerkCommands;
import com.subscriptions.commands.SubscriptionsMenuCommand;
import com.subscriptions.file.PlayerFile;
import com.subscriptions.listeners.CosmeticListeners;
import com.subscriptions.listeners.SubscriptionListeners;
import com.subscriptions.subscriptions.api.TrailsAPI;
import com.subscriptions.subscriptions.connection.SubscriptionsHikari;
import com.subscriptions.tasks.TrailTask;
import com.subscriptions.threads.SubThreads;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public class Subscriptions extends JavaPlugin {

    public static Plugin plugin;
    public static Economy economy;
    public static Permission permission;

    synchronized public void onEnable() {
        plugin = this;
        registerCommands();
        registerListeners();
        registerConfig();
        setupEconomy();
        setUpPermission();
        SubscriptionsHikari.getInstance().connectToDB();
        PlayerFile.setUpFile();
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new TrailTask(), 20, 10);
    }

    public void onDisable() {
        for (Player all : Bukkit.getOnlinePlayers()) {
            TrailsAPI.getInstance().resetParticles(all);
        }
        SubThreads.globalThread.shutdown();
    }

    private void registerCommands() {
        addCommand("subsa", new AdminCommands("subsa"));
        addCommand("subscriptions", new SubscriptionsMenuCommand("subscriptions"));
        addCommand("subperks", new PerkCommands("subperks"));
    }

    private void registerListeners() {
        addListener(new SubscriptionListeners(), new CosmeticListeners());
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
