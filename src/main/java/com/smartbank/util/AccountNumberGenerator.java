package com.smartbank.util;

import java.util.concurrent.ThreadLocalRandom;

public class AccountNumberGenerator {

    /** 12-digit account number, e.g. 100482913567 */
    public static String generateAccountNumber() {
        StringBuilder sb = new StringBuilder("10");
        for (int i = 0; i < 10; i++) {
            sb.append(ThreadLocalRandom.current().nextInt(0, 10));
        }
        return sb.toString();
    }

    /** Simple UPI-style id derived from the user's email, e.g. ak123@smartbank */
    public static String generateUpiId(String emailLocalPart) {
        int suffix = ThreadLocalRandom.current().nextInt(100, 999);
        String cleaned = emailLocalPart.replaceAll("[^a-zA-Z0-9]", "");
        return cleaned + suffix + "@smartbank";
    }
}
