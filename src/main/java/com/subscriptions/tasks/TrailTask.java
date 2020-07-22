package com.subscriptions.tasks;

import com.subscriptions.subscriptions.api.TrailsAPI;
import com.subscriptions.subscriptions.enums.PrepareStatements;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.entity.Player;

public class TrailTask implements Runnable {

    @Override
    public void run() {
        for (Player all : Bukkit.getServer().getOnlinePlayers()) {
            TrailsAPI api = TrailsAPI.getInstance();
            api.playParticle(all, PrepareStatements.HEARTID.getStatement(), Effect.HEART);
            api.playParticle(all, PrepareStatements.MAGICID.getStatement(), Effect.WITCH_MAGIC);
            api.playParticle(all, PrepareStatements.CLOUDID.getStatement(), Effect.CLOUD);
            api.playParticle(all, PrepareStatements.ENDERID.getStatement(), Effect.ENDER_SIGNAL);
            api.playParticle(all, PrepareStatements.SPARKID.getStatement(), Effect.FIREWORKS_SPARK);
            api.playParticle(all, PrepareStatements.FLAMEID.getStatement(), Effect.FLAME);
            api.playParticle(all, PrepareStatements.SNOWID.getStatement(), Effect.SNOWBALL_BREAK);
            api.playParticle(all, PrepareStatements.WATERID.getStatement(), Effect.WATERDRIP);
            api.playParticle(all, PrepareStatements.SMOKEID.getStatement(), Effect.SMOKE);
            api.playParticle(all, PrepareStatements.SLIMEID.getStatement(), Effect.SLIME);
            api.playParticle(all, PrepareStatements.ENCHANTEDID.getStatement(), Effect.FLYING_GLYPH);
            api.playParticle(all, PrepareStatements.MUSICID.getStatement(), Effect.NOTE);
            api.playParticle(all, PrepareStatements.EMERALDID.getStatement(), Effect.HAPPY_VILLAGER);
        }
    }
}
