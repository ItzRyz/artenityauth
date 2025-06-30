package org.artenity.auth.listener;

import org.artenity.auth.Main;
import org.artenity.auth.service.AuthService;
import org.artenity.auth.session.SessionManager;
import org.artenity.auth.util.PremiumChecker;
import org.artenity.auth.util.VelocityUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class JoinQuitListener implements Listener {
    private final Set<UUID> awaitingLogin = new HashSet<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!VelocityUtil.isVelocityForwarded(player)) {
            player.kickPlayer("§cYour server is not properly configured for Velocity UUID forwarding.");
            return;
        }

        UUID uuid = player.getUniqueId();
        AuthService auth = new AuthService();
        SessionManager sessionManager = Main.getInstance().getSessionManager();

        if (PremiumChecker.isPremium(player)) {
            player.sendMessage("§aPremium user detected. Auto-login.");
            sessionManager.saveSession(player);
            return;
        }

        if (sessionManager.isSessionValid(player)) {
            player.sendMessage("§aSession valid. Auto-login.");
            return;
        }

        player.sendMessage("§ePlease login with /login or /register.");
        awaitingLogin.add(uuid);

        // Kick after timeout if not logged in
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            if (awaitingLogin.contains(uuid)) {
                player.kickPlayer("§cLogin timed out.");
            }
        }, 60 * 20); // 60 seconds * 20 ticks
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        awaitingLogin.remove(event.getPlayer().getUniqueId());
        Main.getInstance().getSessionManager().invalidate(event.getPlayer());
    }

    public void markLoggedIn(Player player) {
        awaitingLogin.remove(player.getUniqueId());
        Main.getInstance().getSessionManager().saveSession(player);
    }
}
