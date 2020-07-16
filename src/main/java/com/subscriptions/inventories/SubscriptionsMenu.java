package com.subscriptions.inventories;

import com.subscriptions.builder.ItemBuilder;
import com.subscriptions.config.ConfigManager;
import com.subscriptions.main.Subscriptions;
import com.subscriptions.string.StringUtils;
import com.subscriptions.subscriptions.api.SubscriptionsShopAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class SubscriptionsMenu implements InventoryInterface {

    private static SubscriptionsMenu instance = null;
    private Inventory inventory;
    private Player player;

    public static SubscriptionsMenu getInstance() {
        if (instance == null) instance = new SubscriptionsMenu();
        return instance;
    }

    public SubscriptionsMenu init(Player player) {
        this.player = player;
        register();
        return this;
    }

    @Override
    public void create() {
        inventory = Bukkit.createInventory(null, 9, StringUtils.format("&cSubscriptions Menu"));
    }

    @Override
    public void add() {
        if (!SubscriptionsShopAPI.getInstance().hasSilver(player)) {
            inventory.setItem(1, ItemBuilder.Builder.getInstance().itemType(Material.NAME_TAG).itemAmount(1).itemName(StringUtils.format(
                    "&7Silver &eSubscription")).itemLore(
                    StringUtils.format("&c&lFeatures:"),
                    StringUtils.format("&7Access To Kit &eViking"),
                    StringUtils.format("&7Gain &ex0.25 &aMoney &7Perk Kill"),
                    StringUtils.format("&e2 &7Extra &6Gold &7On Drop"),
                    StringUtils.format("      "),
                    StringUtils.format("&c&lOther Info"),
                    StringUtils.format("&7Cost: &6" + ConfigManager.getConfigManager().getInt("silver-price")),
                    StringUtils.format("&7Bought: &cFalse")).build());
        } else {

            if (SubscriptionsShopAPI.getInstance().hasSilver(player)) {
                inventory.setItem(1, ItemBuilder.Builder.getInstance().itemType(Material.NAME_TAG).itemAmount(1).itemName(StringUtils.format(
                        "&7Silver &eSubscription")).itemLore(
                        StringUtils.format("&c&lFeatures:"),
                        StringUtils.format("&7Access To Kit &eViking"),
                        StringUtils.format("&7Gain &ex0.25 &aMoney &7Perk Kill"),
                        StringUtils.format("&e2 &7Extra &6Gold &7On Drop"),
                        StringUtils.format("      "),
                        StringUtils.format("&c&lOther Info"),
                        StringUtils.format("&7Cost: &6" + ConfigManager.getConfigManager().getInt("silver-price")),
                        StringUtils.format("&7Bought: &aTrue")).build());
            }
        }


        if (!SubscriptionsShopAPI.getInstance().hasGold(player)) {
            inventory.setItem(4, ItemBuilder.Builder.getInstance().itemType(Material.NAME_TAG).itemAmount(1).itemName(StringUtils.format(
                    "&6Gold &eSubscription")).itemLore(
                    StringUtils.format("&c&lFeatures:"),
                    StringUtils.format("&7Access To Kit &ePyro"),
                    StringUtils.format("&7Gain &ex0.50 &aMoney &7Per Kill"),
                    StringUtils.format("&e4 &7Extra &6Gold &7On Drop"),
                    StringUtils.format("      "),
                    StringUtils.format("&c&lOther Info"),
                    StringUtils.format("&7Cost: &6" + ConfigManager.getConfigManager().getInt("gold-price")),
                    StringUtils.format("&7Bought: &cFalse")).build());
        } else {

            if (SubscriptionsShopAPI.getInstance().hasGold(player)) {
                inventory.setItem(4, ItemBuilder.Builder.getInstance().itemType(Material.NAME_TAG).itemAmount(1).itemName(StringUtils.format(
                        "&6Gold &eSubscription")).itemLore(
                        StringUtils.format("&c&lFeatures:"),
                        StringUtils.format("&7Access To Kit &ePyro"),
                        StringUtils.format("&7Gain &ex0.50 &aMoney &7Per Kill"),
                        StringUtils.format("&e4 &7Extra &6Gold &7On Drop"),
                        StringUtils.format("      "),
                        StringUtils.format("&c&lOther Info"),
                        StringUtils.format("&7Cost: &6" + ConfigManager.getConfigManager().getInt("gold-price")),
                        StringUtils.format("&7Bought: &aTrue")).build());
            }
        }

        if (!SubscriptionsShopAPI.getInstance().hasPlatinum(player)) {
            inventory.setItem(7, ItemBuilder.Builder.getInstance().itemType(Material.NAME_TAG).itemAmount(1).itemName(StringUtils.format(
                    "&8Platinum &eSubscription")).itemLore(
                    StringUtils.format("&c&lFeatures:"),
                    StringUtils.format("&7Access To Kit &eTank"),
                    StringUtils.format("&7Gain &ex0.75 &aMoney &7Per Kill"),
                    StringUtils.format("&e6 &7Extra &6Gold &7On Drop"),
                    StringUtils.format("      "),
                    StringUtils.format("&c&lOther Info"),
                    StringUtils.format("&7Cost: &6" + ConfigManager.getConfigManager().getInt("platinum-price")),
                    StringUtils.format("&7Bought: &cFalse")).build());
        } else {

            if (SubscriptionsShopAPI.getInstance().hasPlatinum(player)) {
                inventory.setItem(7, ItemBuilder.Builder.getInstance().itemType(Material.NAME_TAG).itemAmount(1).itemName(StringUtils.format(
                        "&8Platinum &eSubscription")).itemLore(
                        StringUtils.format("&c&lFeatures:"),
                        StringUtils.format("&7Access To Kit &eTank"),
                        StringUtils.format("&7Gain &ex0.75 &aMoney &7Per Kill"),
                        StringUtils.format("&e6 &7Extra &6Gold &7On Drop"),
                        StringUtils.format("      "),
                        StringUtils.format("&c&lOther Info"),
                        StringUtils.format("&7Cost: &6" + ConfigManager.getConfigManager().getInt("platinum-price")),
                        StringUtils.format("&7Bought: &aTrue")).build());
            }
        }
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
