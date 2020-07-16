package com.subscriptions.subscriptions.connection;

import com.subscriptions.main.Subscriptions;
import com.subscriptions.string.StringUtils;
import com.subscriptions.subscriptions.enums.PrepareStatements;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HikariInsertOptions {

    private static HikariInsertOptions instance = null;

    public static synchronized HikariInsertOptions getInstance() {
        if (instance == null) instance = new HikariInsertOptions();
        return instance;
    }

    public boolean hasSubscription(String playerName, String name) {
        try (Connection connection = SubscriptionsHikari.getInstance().getDataSource().getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + name + " WHERE NAME=?")) {
                ps.setString(1, playerName);
                ResultSet r = ps.executeQuery();
                if (r.next()) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            Bukkit.getConsoleSender().sendMessage(StringUtils.format("&c***If you're getting this message, warning database hasn't been pinged in"
                    + "a while! Ignoring it!***"));
            ex.printStackTrace();
        }

        return false;
    }

    public void setSilverPlayer(Player player) {
        try (Connection connection = SubscriptionsHikari.getInstance().getDataSource().getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(PrepareStatements.GETSILVERDATA.getStatement())) {
                ps.setString(1, player.getName());
                if (!this.hasSubscription(player.getName(), "SILVER")) {
                    try (PreparedStatement insert = connection.prepareStatement(PrepareStatements.SETSILVERDATA.getStatement())) {
                        insert.setString(1, player.getName());
                        insert.setInt(2, 0);
                        insert.executeUpdate();
                        System.out.print("All data has been inserted");
                    }
                } else {
                    System.out.println("");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Bukkit.getConsoleSender().sendMessage(StringUtils.format("&c***If you're getting this message, warning database hasn't been pinged in"
                    + "a while! Ignoring it!***"));
        }
    }

    public void setGoldPlayer(Player player) {
        try (Connection connection = SubscriptionsHikari.getInstance().getDataSource().getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(PrepareStatements.GETGOLDDATA.getStatement())) {
                ps.setString(1, player.getName());
                if (!this.hasSubscription(player.getName(), "GOLD")) {
                    try (PreparedStatement insert = connection.prepareStatement(PrepareStatements.SETGOLDDATA.getStatement())) {
                        insert.setString(1, player.getName());
                        insert.setInt(2, 0);
                        insert.executeUpdate();
                        System.out.print("All data has been inserted");
                    }
                } else {
                    System.out.println("");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Bukkit.getConsoleSender().sendMessage(StringUtils.format("&c***If you're getting this message, warning database hasn't been pinged in"
                    + "a while! Ignoring it!***"));
        }
    }

    public void setPlatinumPlayer(Player player) {
        try (Connection connection = SubscriptionsHikari.getInstance().getDataSource().getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(PrepareStatements.GETPLATINUMDATA.getStatement())) {
                ps.setString(1, player.getName());
                if (!this.hasSubscription(player.getName(), "PLATINUM")) {
                    try (PreparedStatement insert =connection.prepareStatement(PrepareStatements.SETPLATINUMDATA.getStatement())) {
                        insert.setString(1, player.getName());
                        insert.setInt(2, 0);
                        insert.executeUpdate();
                        System.out.print("All data has been inserted");
                    }
                } else {
                    System.out.println("");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Bukkit.getConsoleSender().sendMessage(StringUtils.format("&c***If you're getting this message, warning database hasn't been pinged in"
                    + "a while! Ignoring it!***"));
        }
    }
}
