package net.lynx.hypixelguildmod;

import net.lynx.hypixelguildmod.handler.ConfigurationHandler;
import net.lynx.hypixelguildmod.listener.ChatListener;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

@Mod(modid = Utility.MODID, version = Utility.VERSION)
public class GuildMod {
    static boolean isHypixel = false;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new ChatListener());
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ConfigurationHandler.init(event.getSuggestedConfigurationFile());
    }

    @Mod.EventHandler
    public void init(FMLPreInitializationEvent event) {
        System.out.println("MOD IS WORKING");
        ClientCommandHandler.instance.registerCommand(new PurgeCommand());
    }

    @SubscribeEvent
    public void playerLoggedIn(final FMLNetworkEvent.ClientConnectedToServerEvent event) {
        isHypixel = event.manager.getRemoteAddress().toString().toLowerCase().contains("hypixel.net");
    }

    @SubscribeEvent
    public void playerLoggedOut(final FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        isHypixel = false;
    }
}
