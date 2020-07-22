package com.subscriptions.inventories.silver;

import com.subscriptions.builder.ItemBuilder;
import com.subscriptions.inventories.handlers.InventoryInterface;
import com.subscriptions.string.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class SilverCosmeticsMenu implements InventoryInterface {

    private static SilverCosmeticsMenu instance = null;
    private Inventory inventory;
    private Player player;

    public static SilverCosmeticsMenu getInstance() {
        if (instance == null) instance = new SilverCosmeticsMenu();
        return instance;
    }

    public SilverCosmeticsMenu init(Player player) {
        this.player = player;
        register();
        return this;
    }

    @Override
    public void create() {
        inventory = Bukkit.createInventory(null, 27, StringUtils.format("&7Silver Perks"));
    }

    @Override
    public void add() {
        inventory.setItem(10, ItemBuilder.Builder.getInstance().itemType(Material.APPLE).itemName(StringUtils.format("&cHeart &eTrail")
        ).itemAmount(1).build());

        inventory.setItem(12, ItemBuilder.Builder.getInstance().itemType(Material.STICK).itemName(StringUtils.format("&dMagic &eTrail")
        ).itemAmount(1).build());

        inventory.setItem(14, ItemBuilder.Builder.getInstance().itemType(Material.WEB).itemName(StringUtils.format("&fCloud &eTrail")
        ).itemAmount(1).build());

        inventory.setItem(16, ItemBuilder.Builder.getInstance().itemType(Material.EYE_OF_ENDER).itemName(StringUtils.format("&dEnder &eTrail")
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
