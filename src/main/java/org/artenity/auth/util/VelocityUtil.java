package org.artenity.auth.util;

import org.bukkit.entity.Player;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class VelocityUtil {
    public static boolean isVelocityForwarded(Player player) {
        UUID offlineUUID = UUID.nameUUIDFromBytes(("OfflinePlayer:" + player.getName()).getBytes(StandardCharsets.UTF_8));
        return !player.getUniqueId().equals(offlineUUID);
    }
}