package org.artenity.auth.command;

import org.artenity.auth.Main;
import org.artenity.auth.service.AuthService;
import org.artenity.auth.util.VelocityUtil;
import org.bukkit.entity.Player;

public class RegisterCommand {
    private final AuthService authService = new AuthService();

    public boolean execute(Player player, String[] args) {
        if (args.length != 1) {
            player.sendMessage("§cUsage: /register <password>");
            return true;
        }

        // Check Velocity UUID forwarding
        if (!VelocityUtil.isVelocityForwarded(player)) {
            player.kickPlayer("§cYour UUID is not premium! Velocity forwarding misconfigured.");
            return true;
        }

        boolean success = authService.register(player, args[0]);

        if (success) {
            // ✅ Correct way to access sessionManager
            Main.getInstance().getSessionManager().saveSession(player);
            Main.getInstance().getJoinQuitListener().markLoggedIn(player);

            player.sendMessage("§aRegistration complete! You are now logged in.");
        } else {
            player.sendMessage("§cYou're already registered.");
        }

        return true;
    }
}
