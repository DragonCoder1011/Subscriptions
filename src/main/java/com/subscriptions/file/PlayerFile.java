package com.subscriptions.file;

import com.subscriptions.main.Subscriptions;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class PlayerFile {

    private static File file;

    private static FileConfiguration fileConfiguration;

    public static void setUpFile() {
        file = new File(Subscriptions.plugin.getDataFolder(), "subscriptionplayers.yml");
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
        saveFile();
    }

    public static void saveFile() {
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static FileConfiguration getFileConfiguration() {
        return fileConfiguration;
    }
}
