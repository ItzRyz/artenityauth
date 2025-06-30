package org.artenity.auth.util;

import org.bukkit.entity.Player;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class PremiumChecker {
    public static boolean isPremium(Player player) {
        // Very basic check: online-mode UUID != offline-mode UUID
        UUID onlineUUID = UUID.nameUUIDFromBytes(("OfflinePlayer:" + player.getName()).getBytes(StandardCharsets.UTF_8));
        return !onlineUUID.equals(player.getUniqueId());
    }
}
