package org.artenity.auth.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHandler implements CommandExecutor {
    private final LoginCommand login = new LoginCommand();
    private final RegisterCommand register = new RegisterCommand();
    private final CaptchaCommand captcha = new CaptchaCommand();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        switch (command.getName().toLowerCase()) {
            case "login": return login.execute((Player) sender, args);
            case "register": return register.execute((Player) sender, args);
            case "captcha": return captcha.execute((Player) sender, args);
            default: return false;
        }
    }
}
