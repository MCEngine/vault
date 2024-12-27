package io.github.mcengine.api.vault.encryption;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;
import java.util.Base64;

public class MCEngineVaultApiEncryption {
    private final String secretKeyAlgorithm;
    private final String cipherAlgorithm;
    private final int keySize;
    private final int iterationCount;
    private final byte[] salt;
    private final String password;

    public MCEngineVaultApiEncryption(String secretKeyAlgorithm, String cipherAlgorithm, int keySize, int iterationCount, String salt, String password) {
        this.secretKeyAlgorithm = secretKeyAlgorithm;
        this.cipherAlgorithm = cipherAlgorithm;
        this.keySize = keySize;
        this.iterationCount = iterationCount;
        this.salt = salt.getBytes();
        this.password = password;
    }

    public String encrypt(String data) throws Exception {
        SecretKeySpec secretKey = generateKey(password);
        Cipher cipher = Cipher.getInstance(cipherAlgorithm);
        byte[] iv = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
        byte[] encryptedData = cipher.doFinal(data.getBytes());

        byte[] combined = new byte[iv.length + encryptedData.length];
        System.arraycopy(iv, 0, combined, 0, iv.length);
        System.arraycopy(encryptedData, 0, combined, iv.length, encryptedData.length);

        return Base64.getEncoder().encodeToString(combined);
    }

    public String decrypt(String encryptedData) throws Exception {
        SecretKeySpec secretKey = generateKey(password);
        byte[] combined = Base64.getDecoder().decode(encryptedData);

        byte[] iv = new byte[16];
        System.arraycopy(combined, 0, iv, 0, iv.length);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        byte[] encryptedBytes = new byte[combined.length - iv.length];
        System.arraycopy(combined, iv.length, encryptedBytes, 0, encryptedBytes.length);

        Cipher cipher = Cipher.getInstance(cipherAlgorithm);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
        byte[] decryptedData = cipher.doFinal(encryptedBytes);

        return new String(decryptedData);
    }

    private SecretKeySpec generateKey(String password) throws Exception {
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterationCount, keySize);
        SecretKeyFactory factory = SecretKeyFactory.getInstance(secretKeyAlgorithm);
        SecretKey secretKey = factory.generateSecret(spec);
        return new SecretKeySpec(secretKey.getEncoded(), "AES");
    }
}
