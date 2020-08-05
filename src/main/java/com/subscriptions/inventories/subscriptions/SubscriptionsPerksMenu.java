package com.subscriptions.inventories.subscriptions;

import com.subscriptions.builder.ItemBuilder;
import com.subscriptions.inventories.handlers.InventoryInterface;
import com.subscriptions.string.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class SubscriptionsPerksMenu implements InventoryInterface {

    private static SubscriptionsPerksMenu instance = null;
    private Inventory inventory;
    private Player player;

    public static SubscriptionsPerksMenu getInstance() {
        if (instance == null) instance = new SubscriptionsPerksMenu();
        return instance;
    }

    public SubscriptionsPerksMenu init(Player player) {
        this.player = player;
        register();
        return this;
    }

    @Override
    public void create() {
        inventory = Bukkit.createInventory(null, 36, StringUtils.format("&7Subscription Perks Menu"));
    }

    @Override
    public void add() {
        inventory.setItem(10, ItemBuilder.Builder.getInstance().itemType(Material.CHEST).itemName(StringUtils.format("&7Silver &eSubscription &6Perks")
        ).itemAmount(1).itemLore(
                StringUtils.format("&c&lTrails:"),
                StringUtils.format("&7● &cHeart"),
                StringUtils.format("&7● &dMagic"),
                StringUtils.format("&7● &fCloud"),
                StringUtils.format("&7● &dEnder"),
                StringUtils.format("&c&lMore Perks To Come")).build());

        inventory.setItem(13, ItemBuilder.Builder.getInstance().itemType(Material.CHEST).itemName(StringUtils.format("&6Gold &eSubscription &6Perks")
        ).itemAmount(1).itemLore(
                StringUtils.format("&c&lTrails:"),
                StringUtils.format("&7● &fSpark"),
                StringUtils.format("&7● &6Flame"),
                StringUtils.format("&7● &fSnow"),
                StringUtils.format("&7● &bWater"),
                StringUtils.format("&c&lMore Perks To Come")).build());

        inventory.setItem(16, ItemBuilder.Builder.getInstance().itemType(Material.CHEST).itemName(StringUtils.format("&8Platinum &eSubscription &6Perks")
        ).itemAmount(1).itemLore(
                StringUtils.format("&c&lTrails:"),
                StringUtils.format("&7● Smoke"),
                StringUtils.format("&7● &aSlime"),
                StringUtils.format("&7● &fGlyph"),
                StringUtils.format("&7● &cMusic"),
                StringUtils.format("&7● &aEmerald"),
                StringUtils.format("&c&lMore Perks To Come")).build());

        inventory.setItem(31, ItemBuilder.Builder.getInstance().itemType(Material.BARRIER).itemName(StringUtils.format("&cReset Particle"))
                .itemAmount(1).itemLore(StringUtils.format("&7Click To Reset Your Trail!")).build());
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
