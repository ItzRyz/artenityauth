public class CaptchaCommand {
    public boolean execute(Player player, String[] args) {
        String captcha = CaptchaUtil.generate();
        player.sendMessage("§eYour captcha: §l" + captcha);
        return true;
    }
}
