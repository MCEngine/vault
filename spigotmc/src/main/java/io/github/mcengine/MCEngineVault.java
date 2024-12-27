package io.github.mcengine;

import io.github.mcengine.util.ConfigUtil;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class MCEngineVault extends JavaPlugin {

    private ConfigUtil configUtil;

    @Override
    public void onEnable() {
        Logger logger = getLogger();
        logger.info("MCEngineVault is starting...");

        // Save default config if it doesn't exist
        saveDefaultConfig();

        // Initialize configuration utility
        configUtil = new ConfigUtil(this);

        // Access configuration values through ConfigUtil
        logger.info("Cipher used: " + configUtil.getCipher());
        logger.info("Secret key: " + configUtil.getSecretKey());

        logger.info("MCEngineVault started successfully.");
    }

    @Override
    public void onDisable() {
        getLogger().info("MCEngineVault is shutting down...");
    }
}
