package com.stockmarket.sproject.application.util;

import java.security.SecureRandom;

public class BasicKeyGenerator implements IKeyGenerator {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int KEY_LENGTH = 16;
    private static final int GROUP_SIZE = 4;
    private static final char SEPARATOR = '-';

    public static String GenerateKey() {
        SecureRandom random = new SecureRandom();
        StringBuilder keyBuilder = new StringBuilder(KEY_LENGTH + 3); // Including separators

        for (int i = 0; i < KEY_LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            keyBuilder.append(randomChar);

            if ((i + 1) % GROUP_SIZE == 0 && i != KEY_LENGTH - 1) {
                keyBuilder.append(SEPARATOR);
            }
        }

        return keyBuilder.toString();
    }

    @Override
    public String getKey() {
        return GenerateKey();
    }

}
