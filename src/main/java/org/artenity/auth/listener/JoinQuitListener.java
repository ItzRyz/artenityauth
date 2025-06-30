public class JoinQuitListener implements Listener {
    private final Set<UUID> awaitingLogin = new HashSet<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        AuthService auth = new AuthService();
        SessionManager sessionManager = AuthPlugin.getInstance().getSessionManager();

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
        Bukkit.getScheduler().runTaskLater(AuthPlugin.getInstance(), () -> {
            if (awaitingLogin.contains(uuid)) {
                player.kickPlayer("§cLogin timed out.");
            }
        }, 60 * 20); // 60 seconds * 20 ticks
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        awaitingLogin.remove(event.getPlayer().getUniqueId());
        AuthPlugin.getInstance().getSessionManager().invalidate(event.getPlayer());
    }

    public void markLoggedIn(Player player) {
        awaitingLogin.remove(player.getUniqueId());
        AuthPlugin.getInstance().getSessionManager().saveSession(player);
    }
}
