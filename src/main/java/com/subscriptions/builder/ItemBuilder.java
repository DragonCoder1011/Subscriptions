package com.subscriptions.builder;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class ItemBuilder {

    public static boolean hasDisplayName(ItemStack currentitem) {
        if (currentitem == null) {
            return false;
        } else if (!currentitem.hasItemMeta()) {
            return false;
        } else {
            return currentitem.getItemMeta().hasDisplayName();
        }
    }

    public static String format(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static class Builder {
        private Material material;
        private int amount;
        private byte id;
        private String name;
        private List<String> lore;

        public static ItemBuilder.Builder getInstance() {
            return new ItemBuilder.Builder();
        }

        public ItemBuilder.Builder itemType(Material material) {
            this.material = material;
            return this;
        }

        public ItemBuilder.Builder itemAmount(int amount) {
            this.amount = amount;
            return this;
        }

        public ItemBuilder.Builder itemID(byte id) {
            this.id = id;
            return this;
        }

        public ItemBuilder.Builder itemName(String name) {
            this.name = name;
            return this;
        }

        public ItemBuilder.Builder itemLore(String... lores) {
            this.lore = Arrays.asList(lores);
            return this;
        }

        public ItemStack build() {
            ItemStack item = new ItemStack(this.material, this.amount);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ItemBuilder.format(this.name));
            meta.setLore(this.lore);
            item.setItemMeta(meta);
            return item;
        }

        public ItemStack buildWithID() {
            ItemStack item = new ItemStack(this.material, this.amount, (short) this.id);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ItemBuilder.format(this.name));
            meta.setLore(this.lore);
            item.setItemMeta(meta);
            return item;
        }
    }

    private ItemBuilder() {

    }

}
