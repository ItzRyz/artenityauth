package org.artenity.auth.util;

import java.util.Random;

public class CaptchaUtil {
    private static final String CHARS = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";

    public static String generate() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            sb.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        return sb.toString();
    }
}
