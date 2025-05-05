package com.islington.util;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordUtil {

    private static final int SALT_LENGTH_BYTE = 16;
    private static final int HASH_ITERATIONS = 65536;
    private static final int KEY_LENGTH = 256; // bits
    private static final String HASH_ALGORITHM = "PBKDF2WithHmacSHA256";

    // Generate a random salt
    public static byte[] getRandomSalt() {
        byte[] salt = new byte[SALT_LENGTH_BYTE];
        new SecureRandom().nextBytes(salt);
        return salt;
    }

    // Hash password with a salt using PBKDF2
    public static String hashPassword(String password) {
        try {
            // Generate a salt
            byte[] salt = getRandomSalt();
            // Derive the hash using PBKDF2
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, HASH_ITERATIONS, KEY_LENGTH);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(HASH_ALGORITHM);
            byte[] hash = factory.generateSecret(spec).getEncoded();

            // Combine salt and hash and encode in Base64 for storage
            byte[] hashWithSalt = new byte[salt.length + hash.length];
            System.arraycopy(salt, 0, hashWithSalt, 0, salt.length);
            System.arraycopy(hash, 0, hashWithSalt, salt.length, hash.length);

            return Base64.getEncoder().encodeToString(hashWithSalt); // Base64 encoding for storage
        } catch (Exception ex) {
            Logger.getLogger(PasswordUtil.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static boolean verifyPassword(String password, String storedHash) {
        try {
            byte[] hashWithSalt = Base64.getDecoder().decode(storedHash);  // Decode stored hash
            byte[] salt = new byte[SALT_LENGTH_BYTE];
            System.arraycopy(hashWithSalt, 0, salt, 0, salt.length);

            byte[] storedHashBytes = new byte[hashWithSalt.length - salt.length];
            System.arraycopy(hashWithSalt, salt.length, storedHashBytes, 0, storedHashBytes.length);

            // Hash the input password with the same salt
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, HASH_ITERATIONS, KEY_LENGTH);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(HASH_ALGORITHM);
            byte[] hash = factory.generateSecret(spec).getEncoded();

            // Compare the stored hash and the newly generated hash
            for (int i = 0; i < storedHashBytes.length; i++) {
                if (storedHashBytes[i] != hash[i]) {
                    return false;
                }
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
