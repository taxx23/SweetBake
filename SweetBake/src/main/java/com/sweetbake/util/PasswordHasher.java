package com.sweetbake.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasher {

    public static void main(String[] args) {
        String[] passwords = {"Admin@123", "Baker@123", "Customer@123"};

        for (String password : passwords) {
            String hash = BCrypt.hashpw(password, BCrypt.gensalt());
            System.out.println(password + " -> " + hash);
        }
    }
}
