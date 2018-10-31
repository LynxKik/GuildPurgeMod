package net.lynx.hypixelguildmod.listener;

import net.lynx.hypixelguildmod.handler.ConfigurationHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * This Class is used to listen for
 * when the API key command is being Executed
 */
public class ChatListener {

    @SubscribeEvent
    public void onReceivedMessage(ClientChatReceivedEvent event) {
        String message = event.message.getUnformattedText();
        if (message.startsWith("Your new API key is")) {
            ConfigurationHandler.setApiKey(message.substring(message.lastIndexOf(" ") + 1));
        }
    }
}
