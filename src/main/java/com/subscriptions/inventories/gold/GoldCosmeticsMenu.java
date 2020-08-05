package com.subscriptions.inventories.gold;

import com.subscriptions.builder.ItemBuilder;
import com.subscriptions.inventories.handlers.InventoryInterface;
import com.subscriptions.string.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class GoldCosmeticsMenu implements InventoryInterface {

    private static GoldCosmeticsMenu instance = null;
    private Inventory inventory;
    private Player player;

    public static GoldCosmeticsMenu getInstance() {
        if (instance == null) instance = new GoldCosmeticsMenu();
        return instance;
    }

    public GoldCosmeticsMenu init(Player player) {
        this.player = player;
        register();
        return this;
    }

    @Override
    public void create() {
        inventory = Bukkit.createInventory(null, 27, StringUtils.format("&6Gold Perks"));
    }

    @Override
    public void add() {
        inventory.setItem(10, ItemBuilder.Builder.getInstance().itemType(Material.FIREWORK).itemName(StringUtils.format("&fSpark &eTrail")
        ).itemAmount(1).build());

        inventory.setItem(12, ItemBuilder.Builder.getInstance().itemType(Material.BLAZE_POWDER).itemName(StringUtils.format("&6Flame &eTrail")
        ).itemAmount(1).build());

        inventory.setItem(14, ItemBuilder.Builder.getInstance().itemType(Material.SNOW_BALL).itemName(StringUtils.format("&fSnow &eTrail")
        ).itemAmount(1).build());

        inventory.setItem(16, ItemBuilder.Builder.getInstance().itemType(Material.WATER_BUCKET).itemName(StringUtils.format("&bWater &eTrail")
        ).itemAmount(1).build());
    }

    @Override
    public void open() {
        player.openInventory(inventory);
    }

    @Override
    public void register() {
        create();
        add();
        open();
    }
}