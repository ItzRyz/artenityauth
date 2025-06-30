public class LoginCommand {
    private final AuthService authService = new AuthService();

    public boolean execute(Player player, String[] args) {
        if (args.length != 1) {
            player.sendMessage("§cUsage: /login <password>");
            return true;
        }

        boolean success = authService.login(player, args[0]);
        if (success) {
            AuthPlugin.getInstance().getJoinQuitListener().markLoggedIn(player);
            player.sendMessage("§aLogin successful!");
        } else {
            player.sendMessage("§cInvalid credentials.");
        }

        return true;
    }

}
