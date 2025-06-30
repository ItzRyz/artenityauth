package org.artenity.auth.database;

import org.artenity.auth.Main;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.*;
import java.util.UUID;

public class DatabaseManager {
    private Connection connection;

    public void init() {
        FileConfiguration cfg = Main.getInstance().getConfig();
        String url = "jdbc:mysql://" + cfg.getString("mysql.host") + ":" +
                cfg.getInt("mysql.port") + "/" + cfg.getString("mysql.database");
        try {
            connection = DriverManager.getConnection(url, cfg.getString("mysql.user"), cfg.getString("mysql.password"));
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS users (uuid VARCHAR(36), password TEXT, PRIMARY KEY(uuid));");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isUserRegistered(UUID uuid) {
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE uuid=?")) {
            ps.setString(1, uuid.toString());
            return ps.executeQuery().next();
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean insertUser(UUID uuid, String hash) {
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO users (uuid, password) VALUES (?, ?)")) {
            ps.setString(1, uuid.toString());
            ps.setString(2, hash);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public String getPasswordHash(UUID uuid) {
        try (PreparedStatement ps = connection.prepareStatement("SELECT password FROM users WHERE uuid=?")) {
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString("password");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void close() {
        try {
            if (connection != null) connection.close();
        } catch (SQLException ignored) {}
    }
}
