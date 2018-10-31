package net.lynx.hypixelguildmod;

import net.lynx.hypixelguildmod.api.HypixelAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

import java.util.HashMap;

import static net.lynx.hypixelguildmod.Utility.displayMessage;

/**
 * This class handles the command
 * and executes it correctly
 */
public class PurgeCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "purge";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "Usage is /purge (number of days) example : /purge 7";
    }

    @Override
    public void processCommand(ICommandSender ics, String[] args) {
        if(!GuildMod.isHypixel){
            displayMessage("This command is only for hypixel!", true);
            return;
        }
        String string = args[0];
        if(string.isEmpty() || verifyInt(string)){
            getCommandUsage(ics);
            return;
        }
        new Thread(() -> {
            int days = Integer.parseInt(string);
            HypixelAPI hypixelAPI = new HypixelAPI();
            if(hypixelAPI.checkKey()){
                displayMessage("You need to gen an API key before using this mod, Type in /api new", true);
                return;
            }
            HashMap<String, Long> inactives = hypixelAPI.getPlayerBasedOnLastLoginDays(days);
            if(inactives.isEmpty()){
                displayMessage("There is no inactive members in the guild!", false);
                return;
            }
            displayMessage("Trying to purge " + inactives.size() + " members now", true);
            for (String uuid : inactives.keySet()) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Minecraft.getMinecraft()
                            .thePlayer
                            .sendChatMessage("/g kick " + uuid + " inactive for " + days + " days");

            }
        }).start();
    }

    private static boolean verifyInt(String string){
        try {
            Integer.parseInt(string);
            return true;
        } catch (Exception ignored){
            return false;
        }
    }/**/

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }
}
