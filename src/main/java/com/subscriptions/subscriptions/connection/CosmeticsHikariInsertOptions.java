package com.subscriptions.subscriptions.connection;

import com.subscriptions.subscriptions.enums.PrepareStatements;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CosmeticsHikariInsertOptions {

    private static CosmeticsHikariInsertOptions instance = null;

    public static synchronized CosmeticsHikariInsertOptions getInstance() {
        if (instance == null) instance = new CosmeticsHikariInsertOptions();
        return instance;
    }

    public boolean hasParticle(String playerName, String name) {
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

    public void setParticleData(String playerName) {
        try (Connection connection = SubscriptionsHikari.getInstance().getDataSource().getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(PrepareStatements.GETPARTICLEDATA.getStatement())) {
                ps.setString(1, playerName);
                if (!hasParticle(playerName, "PARTICLES")) {
                    try (PreparedStatement insert = connection.prepareStatement(PrepareStatements.SETPARTICLEDATA.getStatement())) {
                        insert.setString(1, playerName);
                        insert.setInt(2, 0);
                        insert.setInt(3, 0);
                        insert.setInt(4, 0);
                        insert.setInt(5, 0);
                        insert.setInt(6, 0);
                        insert.setInt(7, 0);
                        insert.setInt(8, 0);
                        insert.setInt(9, 0);
                        insert.setInt(10, 0);
                        insert.setInt(11, 0);
                        insert.setInt(12, 0);
                        insert.setInt(13, 0);
                        insert.setInt(14, 0);
                        insert.executeUpdate();
                        System.out.println("Particle data for this player has been inserted!");
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
