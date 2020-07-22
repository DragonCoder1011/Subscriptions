package com.subscriptions.subscriptions.api;

import com.subscriptions.string.StringUtils;
import com.subscriptions.subscriptions.connection.CosmeticsDataUtilMethods;
import com.subscriptions.subscriptions.enums.PrepareStatements;
import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TrailsAPI {

    private static TrailsAPI instance = null;

    public static TrailsAPI getInstance() {
        if (instance == null) instance = new TrailsAPI();
        return instance;
    }

    public void setParticle(String name, int amount, String id, String setter, String updater) {
        CosmeticsDataUtilMethods.getInstance().set(name, PrepareStatements.GETPARTICLEDATA.getStatement(), id, amount, setter, updater);
    }

    public void resetParticles(Player player) {
        CosmeticsDataUtilMethods.getInstance().set(player.getName(), PrepareStatements.GETPARTICLEDATA.getStatement(), PrepareStatements.HEARTID.getStatement()
                , 0, PrepareStatements.SETHEART.getStatement(), PrepareStatements.UPDATEHEART.getStatement());

        CosmeticsDataUtilMethods.getInstance().set(player.getName(), PrepareStatements.GETPARTICLEDATA.getStatement(), PrepareStatements.MAGICID.getStatement()
                , 0, PrepareStatements.SETMAGIC.getStatement(), PrepareStatements.UPDATEMAGIC.getStatement());

        CosmeticsDataUtilMethods.getInstance().set(player.getName(), PrepareStatements.GETPARTICLEDATA.getStatement(), PrepareStatements.CLOUDID.getStatement()
                , 0, PrepareStatements.SETCLOUD.getStatement(), PrepareStatements.UPDATECLOUD.getStatement());

        CosmeticsDataUtilMethods.getInstance().set(player.getName(), PrepareStatements.GETPARTICLEDATA.getStatement(), PrepareStatements.ENDERID.getStatement()
                , 0, PrepareStatements.SETENDER.getStatement(), PrepareStatements.UPDATEENDER.getStatement());

        CosmeticsDataUtilMethods.getInstance().set(player.getName(), PrepareStatements.GETPARTICLEDATA.getStatement(), PrepareStatements.SPARKID.getStatement()
                , 0, PrepareStatements.SETSPARK.getStatement(), PrepareStatements.UPDATESPARK.getStatement());

        CosmeticsDataUtilMethods.getInstance().set(player.getName(), PrepareStatements.GETPARTICLEDATA.getStatement(), PrepareStatements.FLAMEID.getStatement()
                , 0, PrepareStatements.SETFLAME.getStatement(), PrepareStatements.UPDATEFLAME.getStatement());

        CosmeticsDataUtilMethods.getInstance().set(player.getName(), PrepareStatements.GETPARTICLEDATA.getStatement(), PrepareStatements.SNOWID.getStatement()
                , 0, PrepareStatements.SETSNOW.getStatement(), PrepareStatements.UPDATESNOW.getStatement());

        CosmeticsDataUtilMethods.getInstance().set(player.getName(), PrepareStatements.GETPARTICLEDATA.getStatement(), PrepareStatements.WATERID.getStatement()
                , 0, PrepareStatements.SETWATER.getStatement(), PrepareStatements.UPDATEWATER.getStatement());

        CosmeticsDataUtilMethods.getInstance().set(player.getName(), PrepareStatements.GETPARTICLEDATA.getStatement(), PrepareStatements.HEARTID.getStatement()
                , 0, PrepareStatements.SETHEART.getStatement(), PrepareStatements.UPDATEHEART.getStatement());

        CosmeticsDataUtilMethods.getInstance().set(player.getName(), PrepareStatements.GETPARTICLEDATA.getStatement(), PrepareStatements.SMOKEID.getStatement()
                , 0, PrepareStatements.SETSMOKE.getStatement(), PrepareStatements.UPDATESMOKE.getStatement());

        CosmeticsDataUtilMethods.getInstance().set(player.getName(), PrepareStatements.GETPARTICLEDATA.getStatement(), PrepareStatements.SLIMEID.getStatement()
                , 0, PrepareStatements.SETSLIME.getStatement(), PrepareStatements.UPDATESLIME.getStatement());

        CosmeticsDataUtilMethods.getInstance().set(player.getName(), PrepareStatements.GETPARTICLEDATA.getStatement(), PrepareStatements.ENCHANTEDID.getStatement()
                , 0, PrepareStatements.SETENCHANTED.getStatement(), PrepareStatements.UPDATEENCHANTED.getStatement());

        CosmeticsDataUtilMethods.getInstance().set(player.getName(), PrepareStatements.GETPARTICLEDATA.getStatement(), PrepareStatements.MUSICID.getStatement()
                , 0, PrepareStatements.SETMUSIC.getStatement(), PrepareStatements.UPDATEMUSIC.getStatement());

        CosmeticsDataUtilMethods.getInstance().set(player.getName(), PrepareStatements.GETPARTICLEDATA.getStatement(), PrepareStatements.EMERALDID.getStatement()
                , 0, PrepareStatements.SETEMERALD.getStatement(), PrepareStatements.UPDATEEMERALD.getStatement());

    }

    public void chooseParticle(ItemStack item, String name, Player player, String id, String setter, String update) {
        if (item.getItemMeta().getDisplayName().equalsIgnoreCase(StringUtils.format(name))) {
            TrailsAPI.getInstance().resetParticles(player);
            TrailsAPI.getInstance().setParticle(player.getName(), 1, id, setter, update);
            player.closeInventory();
            player.sendMessage(StringUtils.format("&c&lKitPvP &8» &7You have successfully selected a &eTrail&7!"));
        }
    }

    public void playParticle(Player player, String resultID, Effect effect) {
        if (CosmeticsDataUtilMethods.getInstance().get(player.getName(), PrepareStatements.GETPARTICLEDATA.getStatement(), resultID) >= 1) {
            player.getLocation().getWorld().spigot().playEffect(player.getLocation(), effect, 0, 0,
                    0.4f, 0.4f, 0.4f, 0f, 3, 50);

        }
    }
}
