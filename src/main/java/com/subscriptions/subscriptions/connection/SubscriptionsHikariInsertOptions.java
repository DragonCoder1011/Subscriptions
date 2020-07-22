package com.subscriptions.subscriptions.connection;

import com.subscriptions.subscriptions.enums.PrepareStatements;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SubscriptionsHikariInsertOptions {

    private static SubscriptionsHikariInsertOptions instance = null;

    public static synchronized SubscriptionsHikariInsertOptions getInstance() {
        if (instance == null) instance = new SubscriptionsHikariInsertOptions();
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
        }
    }

    public void setPlatinumPlayer(Player player) {
        try (Connection connection = SubscriptionsHikari.getInstance().getDataSource().getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(PrepareStatements.GETPLATINUMDATA.getStatement())) {
                ps.setString(1, player.getName());
                if (!this.hasSubscription(player.getName(), "PLATINUM")) {
                    try (PreparedStatement insert = connection.prepareStatement(PrepareStatements.SETPLATINUMDATA.getStatement())) {
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
        }
    }
}
