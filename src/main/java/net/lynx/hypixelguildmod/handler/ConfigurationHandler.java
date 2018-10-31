package net.lynx.hypixelguildmod.handler;

import net.lynx.hypixelguildmod.Utility;
import net.lynx.hypixelguildmod.api.HypixelAPI;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

/**
 * This Class is used to store the cached api
 * and guild ID to execute commands faster
 */
public class ConfigurationHandler {
    private static Configuration configuration;

    public static void init(File configFile) {
        if (configuration == null) {
            configuration = new Configuration(configFile);
            loadConfiguration();
        }
    }

    private static void loadConfiguration() {
        HypixelAPI.KEY = getAPIKey();
        HypixelAPI.guildID = getGuildID();
        saveConfig();
    }

    public static String getAPIKey(){
        return configuration.getString("Api-Key", "Purge Guild Mod", "", "This holds the Api key for Hypixel");
    }

    public static String getGuildID(){
        return configuration.getString("Guild-ID", "Purge Guild Mod", "", "This holds the identify of the guild");
    }

    public static void setApiKey(String uuid) {
        configuration.get("Purge Guild Mod", "Api-Key", "").set(uuid);
        saveConfig();
        HypixelAPI.KEY = getAPIKey();
    }

    public static void setGuildID(String uuid) {
        configuration.get("Purge Guild Mod", "Guild-ID", "").set(uuid);
        saveConfig();
        loadConfiguration();
    }

    private static void saveConfig() {
        if (configuration.hasChanged()) configuration.save();
    }

    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equalsIgnoreCase(Utility.MODID)) {
            loadConfiguration();
        }
    }
}
