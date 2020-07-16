package com.subscriptions.config;

import com.subscriptions.main.Subscriptions;

import java.util.List;

public class ConfigManager {

    private static ConfigManager configManager;

    public static synchronized ConfigManager getConfigManager() {
        if (configManager == null) {
            configManager = new ConfigManager();
        }
        return configManager;
    }
    public String getString(String path) {
        return Subscriptions.plugin.getConfig().getString(path);
    }

    public int getInt(String path) {
        return Subscriptions.plugin.getConfig().getInt(path);
    }

    public double getDouble(String path) {
        return Subscriptions.plugin.getConfig().getDouble(path);
    }

    public boolean getBoolean(String path) {
        return Subscriptions.plugin.getConfig().getBoolean(path);
    }

    public List<String> getStringList(String path) {
        return Subscriptions.plugin.getConfig().getStringList(path);
    }

}
