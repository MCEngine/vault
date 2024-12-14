package io.github.mcengine.api;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class MCEngineVaultApiEncryptionUtil {
    // A 16-byte (128-bit) key for AES. Hard-coded for simplicity.
    // In practice, store & manage keys more securely (config file, environment variable, etc.).
    private static final String SECRET_KEY = System.getenv("MCENGINEVAULT_SECRET_KEY"); // Must be exactly 16 chars for AES-128

    /**
     * Encrypts the given plaintext using AES and returns a Base64-encoded string.
     */
    public static String encrypt(String plaintext) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Decrypts the given Base64-encoded string (which was AES-encrypted).
     */
    public static String decrypt(String encryptedBase64) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedBase64);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}