package io.github.mcengine.api;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public interface MCEngineVaultApiInterface {
    /**
     * Creates a vault with a given size and a head item.
     * 
     * @param size   number of slots (must be multiple of 9, typically 9 to 54 in a standard chest GUI)
     * @param headId head identifier (coming from HeadDatabase or your own system)
     * @param password password to lock this vault
     * @return The newly created Vault inventory
     */
    Inventory createVault(int size, String headId, String password);

    /**
     * Opens a vault for a player using the specified password.
     * 
     * @param player   the player who wants to open it
     * @param password the lock password
     */
    void openVault(Player player, String password);
}