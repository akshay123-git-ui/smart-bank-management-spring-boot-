package com.smartbank.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Wraps jBCrypt so the rest of the app never touches raw hashing calls
 * directly - same pattern you used in the Complaint Tracker project.
 */
public class PasswordUtil {

    public static String hash(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }

    public static boolean matches(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
