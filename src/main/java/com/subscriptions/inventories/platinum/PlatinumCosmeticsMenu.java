package com.subscriptions.inventories.platinum;

import com.subscriptions.builder.ItemBuilder;
import com.subscriptions.inventories.handlers.InventoryInterface;
import com.subscriptions.inventories.silver.SilverCosmeticsMenu;
import com.subscriptions.string.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class PlatinumCosmeticsMenu implements InventoryInterface {

    private static PlatinumCosmeticsMenu instance = null;
    private Inventory inventory;
    private Player player;

    public static PlatinumCosmeticsMenu getInstance() {
        if (instance == null) instance = new PlatinumCosmeticsMenu();
        return instance;
    }

    public PlatinumCosmeticsMenu init(Player player) {
        this.player = player;
        register();
        return this;
    }

    @Override
    public void create() {
        inventory = Bukkit.createInventory(null, 27, StringUtils.format("&8Platinum Perks"));
    }

    @Override
    public void add() {
        inventory.setItem(10, ItemBuilder.Builder.getInstance().itemType(Material.FLINT).itemName(StringUtils.format("&7Smoke &eTrail")
        ).itemAmount(1).build());

        inventory.setItem(11, ItemBuilder.Builder.getInstance().itemType(Material.SLIME_BALL).itemName(StringUtils.format("&aSlime &eTrail")
        ).itemAmount(1).build());

        inventory.setItem(13, ItemBuilder.Builder.getInstance().itemType(Material.ENCHANTMENT_TABLE).itemName(StringUtils.format("&fGlyph &eTrail")
        ).itemAmount(1).build());

        inventory.setItem(15, ItemBuilder.Builder.getInstance().itemType(Material.JUKEBOX).itemName(StringUtils.format("&cMusic &eTrail")
        ).itemAmount(1).build());

        inventory.setItem(16, ItemBuilder.Builder.getInstance().itemType(Material.EMERALD).itemName(StringUtils.format("&aEmerald &eTrail")
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
