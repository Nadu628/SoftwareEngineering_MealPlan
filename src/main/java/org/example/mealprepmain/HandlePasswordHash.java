package org.example.mealprepmain;

import org.mindrot.jbcrypt.BCrypt;

public class HandlePasswordHash {
    private static final int BCRYPT_WORK_FACTOR = 12;

    public static String hashPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        return BCrypt.hashpw(password, BCrypt.gensalt(BCRYPT_WORK_FACTOR));
    }

    public static boolean checkPassword(String password, String hashedPassword) {
        if (password == null || password.isEmpty() || hashedPassword == null || hashedPassword.isEmpty()) {
            return false;
        }
        try {
            return BCrypt.checkpw(password, hashedPassword);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}