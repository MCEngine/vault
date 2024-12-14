package io.github.mcengine.api;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Manages vault inventories keyed by password, for demonstration.
 * In a real plugin, you'd store them in a more robust system (file/DB).
 */
public class MCEngineVaultApiInventoryManager {
    private final JavaPlugin plugin;
    private final Map<String, Inventory> vaultsByPassword = new HashMap<>();

    public MCEngineVaultApiInventoryManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Creates a vault inventory of given size and populates the "head item" in slot 0
     * with the specified password stored via NBT (PersistentDataContainer).
     */
    public Inventory createVault(int size, String headId, String password) {
        Inventory vault = Bukkit.createInventory(null, size, ChatColor.BLUE + "Vault - Locked");

        // Create a head item from HeadDatabase or fallback
        ItemStack headItem = MCEngineVaultApiHeadUtil.getHeadItem(headId);
        // Store the password in the head’s NBT
        headItem = MCEngineVaultApiHeadUtil.storePasswordInItem(headItem, plugin, password);

        vault.setItem(0, headItem);

        // Keep a reference by password so we can open it later
        vaultsByPassword.put(password, vault);

        return vault;
    }

    /**
     * Opens the vault for the player if it exists.
     */
    public void openVault(Player player, String password) {
        Inventory vault = vaultsByPassword.get(password);
        if (vault == null) {
            player.sendMessage(ChatColor.RED + "Incorrect password or vault not found.");
            return;
        }
        player.openInventory(vault);
    }
}