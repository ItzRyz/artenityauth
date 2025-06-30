public class SessionManager {
    private final Map<UUID, Long> sessions = new HashMap<>();
    private final long sessionDuration = 30 * 60 * 1000L; // 30 minutes

    public void saveSession(Player player) {
        sessions.put(player.getUniqueId(), System.currentTimeMillis());
    }

    public boolean isSessionValid(Player player) {
        Long lastLogin = sessions.get(player.getUniqueId());
        return lastLogin != null && (System.currentTimeMillis() - lastLogin) <= sessionDuration;
    }

    public void invalidate(Player player) {
        sessions.remove(player.getUniqueId());
    }
}
