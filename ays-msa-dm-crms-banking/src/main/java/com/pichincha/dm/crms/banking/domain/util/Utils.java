package com.pichincha.dm.crms.banking.domain.util;

import java.security.SecureRandom;

public class Utils {
    private Utils() {
        // Private constructor to prevent instantiation
    }

    public static String generateClientId() {
        int number = (int)(Math.random() * 10000); // genera un número entre 0 y 9999
        return String.format("C%04d", number);     // lo convierte a formato C0000, C1234, etc.
    }

    public static String generarNumeroCuentaSeguro() {
        SecureRandom random = new SecureRandom();
        // "19" + 10 dígitos aleatorios
        StringBuilder sb = new StringBuilder("19");
        for (int i = 0; i < 10; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    

}
