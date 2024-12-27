package io.github.mcengine.util;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.logging.Logger;

public class MCEngineVaultUtil {

    private final Logger logger;
    private final FileConfiguration config;

    private String cipher;
    private int iterationCount;
    private int keySize;
    private String secretKey;
    private String salt;

    public MCEngineVaultUtil(Plugin plugin) {
        this.logger = plugin.getLogger();
        this.config = plugin.getConfig();
        loadConfiguration();
    }

    private void loadConfiguration() {
        // Retrieve and validate configuration values
        cipher = config.getString("settings.cipher", "AES/CBC/PKCS5Padding");
        iterationCount = config.getInt("settings.iteration_count", 65536);
        keySize = config.getInt("settings.key_size", 256);
        secretKey = config.getString("settings.secret_key", "defaultSecretKey");
        salt = config.getString("settings.salt", "defaultSalt");

        // Log loaded configuration
        logger.info("Configuration loaded:");
        logger.info("Cipher: " + cipher);
        logger.info("Iteration Count: " + iterationCount);
        logger.info("Key Size: " + keySize);
    }

    // Getters for configuration values
    public String getCipher() {
        return cipher;
    }

    public int getIterationCount() {
        return iterationCount;
    }

    public int getKeySize() {
        return keySize;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getSalt() {
        return salt;
    }
}
