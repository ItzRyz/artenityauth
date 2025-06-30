public class RegisterCommand {
    private final AuthService authService = new AuthService();

    public boolean execute(Player player, String[] args) {
        if (args.length != 1) {
            player.sendMessage("§cUsage: /register <password>");
            return true;
        }
        boolean success = authService.register(player, args[0]);
        player.sendMessage(success ? "§aRegistered!" : "§cAlready registered.");
        return true;
    }
}
