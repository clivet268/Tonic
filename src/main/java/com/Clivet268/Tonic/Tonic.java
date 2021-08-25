package com.Clivet268.Tonic;

import com.Clivet268.Tonic.Proxy.CommonProxy;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = Tonic.MODID, name = Tonic.NAME, version = Tonic.VERSION)
public class Tonic
{
    @Mod.Instance
    public static Tonic instance;
    public static final String MODID = "tonic";
    public static final String NAME = "Tonic";
    public static final String VERSION = "1.0";
    public static final String MC_VERSION = "[1.12.2]";
    public static final String CLIENT_PROXY_CLASS = "com.Clivet268.Tonic.Proxy.ClientProxy";
    public static final String COMMON_PROXY_CLASS = "com.Clivet268.Tonic.Proxy.ClientProxy";

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
        logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }
    @EventHandler
    public void postInit(FMLPostInitializationEvent event){

    }
    @EventHandler
    public void serverInit(FMLServerStartingEvent event){

    }

    @SidedProxy(clientSide = CLIENT_PROXY_CLASS, serverSide = COMMON_PROXY_CLASS)
    public static CommonProxy proxy;


}
