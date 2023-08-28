package com.stockmarket.sproject.application;

import java.security.SecureRandom;
import java.util.Optional;

import com.stockmarket.sproject.application.model.Account;

public class UtilMethods {
    public static String FormatDouble(double value) {
        return String.format("%.2f", value);
    }

    public class KeyGenerator {
        private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        private static final int KEY_LENGTH = 16;
        private static final int GROUP_SIZE = 4;
        private static final char SEPARATOR = '-';

        public static String generateKey() {
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
    }

}
