package org.artenity.auth.command;

import org.artenity.auth.util.CaptchaUtil;
import org.bukkit.entity.Player;

public class CaptchaCommand {
    public boolean execute(Player player, String[] args) {
        String captcha = CaptchaUtil.generate();
        player.sendMessage("§eYour captcha: §l" + captcha);
        return true;
    }
}
