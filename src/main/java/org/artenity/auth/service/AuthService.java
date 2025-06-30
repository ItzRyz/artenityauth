public class AuthService {
    private final DatabaseManager db = AuthPlugin.getInstance().getDatabaseManager();

    public boolean register(Player player, String password) {
        if (db.isUserRegistered(player.getUniqueId())) return false;
        String hash = PasswordUtils.hash(password);
        return db.insertUser(player.getUniqueId(), hash);
    }

    public boolean login(Player player, String password) {
        String hash = db.getPasswordHash(player.getUniqueId());
        return PasswordUtils.verify(password, hash);
    }
}
