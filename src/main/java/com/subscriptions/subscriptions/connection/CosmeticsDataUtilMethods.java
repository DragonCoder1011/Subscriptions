package com.subscriptions.subscriptions.connection;

import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CosmeticsDataUtilMethods {

    private static CosmeticsDataUtilMethods instance = null;

    public static synchronized CosmeticsDataUtilMethods getInstance() {
        if (instance == null) instance = new CosmeticsDataUtilMethods();
        return instance;
    }

    public synchronized int get(String name, String prepareText, String resultSetID) {
        try (Connection connection = SubscriptionsHikari.getInstance().getDataSource().getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(prepareText)) {
                preparedStatement.setString(1, name);
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    return rs.getInt(resultSetID);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return -1;
    }

    public synchronized void set(String name, String getPrepareText, String resultSetID, int amount, String insert, String update) {
        if (get(name, getPrepareText, resultSetID) == -1) {
            try (Connection connection = SubscriptionsHikari.getInstance().getDataSource().getConnection()) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(insert)) {
                    preparedStatement.setString(1, name);
                    preparedStatement.setInt(2, amount);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            try (Connection connection = SubscriptionsHikari.getInstance().getDataSource().getConnection()) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(update)) {
                    preparedStatement.setInt(1, amount);
                    preparedStatement.setString(2, name);
                    preparedStatement.executeUpdate();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
