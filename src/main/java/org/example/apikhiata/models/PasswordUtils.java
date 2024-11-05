package org.example.apikhiata.models;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {
    // Hash a senha
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // Verifica se a senha corresponde ao hash
    public static boolean checkPassword(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }
}