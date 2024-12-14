package io.github.mcengine.api;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

/**
 * Utility methods for working with heads and storing an encrypted password in NBT.
 */
public class MCEngineVaultApiHeadUtil {
    /**
     * Retrieve a head item from HeadDatabase plugin; fallback to a plain PLAYER_HEAD if unavailable.
     */
    public static ItemStack getHeadItem(String headId) {
        // Check if the HeadDatabase plugin is enabled
        if (!Bukkit.getPluginManager().isPluginEnabled("HeadDatabase")) {
            return new ItemStack(Material.PLAYER_HEAD); // fallback
        }

        // Attempt to get the HeadDatabaseAPI
        HeadDatabaseAPI headDatabaseAPI = (HeadDatabaseAPI) Bukkit.getPluginManager().getPlugin("HeadDatabase");
        if (headDatabaseAPI == null) {
            return new ItemStack(Material.PLAYER_HEAD); // fallback
        }

        // Attempt to get the head by ID
        ItemStack head = headDatabaseAPI.getItemHead(headId);
        if (head == null) {
            head = new ItemStack(Material.PLAYER_HEAD); // fallback
        }
        return head;
    }

    /**
     * Encrypt the password, then store it in the head's NBT using PersistentDataContainer.
     */
    public static ItemStack storePasswordInItem(ItemStack item, Plugin plugin, String password) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;

        String encrypted = MCEngineVaultApiEncryptionUtil.encrypt(password);
        if (encrypted == null) {
            // If encryption fails for some reason, fallback to plaintext (not recommended)
            encrypted = password;
        }

        NamespacedKey key = new NamespacedKey(plugin, "vault_password");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, encrypted);
        item.setItemMeta(meta);

        return item;
    }

    /**
     * Retrieve the encrypted password from an item, decrypt it, and return plaintext.
     */
    public static String getPasswordFromItem(ItemStack item, Plugin plugin) {
        if (item == null || !item.hasItemMeta()) return null;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;

        NamespacedKey key = new NamespacedKey(plugin, "vault_password");
        String encrypted = meta.getPersistentDataContainer().get(key, PersistentDataType.STRING);
        if (encrypted == null) return null;

        return MCEngineVaultApiEncryptionUtil.decrypt(encrypted);
    }
}