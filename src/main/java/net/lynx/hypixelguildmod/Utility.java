package net.lynx.hypixelguildmod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.ChatComponentText;

/**
 * This is an Utils class
 * that holds constants and
 * Useful methods
 */
public class Utility {
    public static final net.minecraft.client.Minecraft minecraft = net.minecraft.client.Minecraft.getMinecraft();
    private static final EntityPlayerSP player = minecraft.thePlayer;
    public static final String HYPIXEL_API = "https://api.hypixel.net/";
    public static final String MODID = "Hypixel-Purge-Guild";
    static final String VERSION = "1.0";
    private static final java.util.UUID UUID = player.getUniqueID();
    public static final String getPlainUUID = UUID.toString().replace("-", "");

    static void displayMessage(String string, boolean error) {
        Minecraft.getMinecraft()
                .thePlayer
                .addChatComponentMessage(
                        //Alternate between Chat color Red and Green depending on boolean
                        new ChatComponentText(String.format("\u00a7%s%s", error ? "c" : "a", string)));

    }
}
