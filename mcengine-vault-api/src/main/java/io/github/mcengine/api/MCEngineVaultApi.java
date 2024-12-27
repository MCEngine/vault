package io.github.mcengine.api;

public class MCEngineVaultApi {
    private final String password;

    public MCEngineVaultApi(Plugin plugin, String password) {
        this.password = password;
    }

    public void createVault(String password, int size) {}

    public String getPassword() {
        return password;
    }
}