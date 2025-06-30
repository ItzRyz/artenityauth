package org.artenity.auth.service;

import org.artenity.auth.Main;
import org.artenity.auth.database.DatabaseManager;
import org.artenity.auth.util.PasswordUtil;
import org.bukkit.entity.Player;

public class AuthService {
    private final DatabaseManager db = Main.getInstance().getDatabaseManager();

    public boolean register(Player player, String password) {
        if (db.isUserRegistered(player.getUniqueId())) return false;
        String hash = PasswordUtil.hash(password);
        return db.insertUser(player.getUniqueId(), hash);
    }

    public boolean login(Player player, String password) {
        String hash = db.getPasswordHash(player.getUniqueId());
        return PasswordUtil.verify(password, hash);
    }
}
