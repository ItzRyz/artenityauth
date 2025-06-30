package org.artenity.auth.command;

import org.artenity.auth.Main;
import org.artenity.auth.service.AuthService;
import org.artenity.auth.util.VelocityUtil;
import org.bukkit.entity.Player;

public class LoginCommand {

    private final AuthService authService = new AuthService();

    public boolean execute(Player player, String[] args) {
        // Require exactly one argument
        if (args.length != 1) {
            player.sendMessage("§cUsage: /login <password>");
            return true;
        }

        // Ensure Velocity forwarding is properly configured
        if (!VelocityUtil.isVelocityForwarded(player)) {
            player.kickPlayer("§cYour UUID is not premium! Velocity forwarding misconfigured.");
            return true;
        }

        // Attempt login
        boolean success = authService.login(player, args[0]);

        if (success) {
            // Save session and mark login status
            Main.getInstance().getSessionManager().saveSession(player);
            Main.getInstance().getJoinQuitListener().markLoggedIn(player);

            player.sendMessage("§aLogin successful! Welcome back.");
        } else {
            player.sendMessage("§cLogin failed. Invalid password.");
        }

        return true;
    }
}
