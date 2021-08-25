package com.Clivet268.Tonic.Util;

import com.Clivet268.Tonic.Tonic;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class RegistryHandler {
    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(ItemInit.ITEMS.toArray(new Item[0]));
    }
    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event) {
        //event.getRegistry().registerAll(BlockInit.BLOCKS.toArray(new Block[0]));
    }
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onModelRegistryEvent(ModelRegistryEvent event) {
        for(Item item : ItemInit.ITEMS)
        {

            System.out.print("Model Registered: " + item.getUnlocalizedName());
            Tonic.proxy.registerItemRenderer(item, 0, "inventory");
            //ModelLoader.setCustomModelResourceLocation(item, 0, );
        }/*
        for(Block block : BlockInit.BLOCKS)
        {
            Tonic.proxy.registerItemRenderer(Item.getItemFromBlock(block), 0, "inventory");
        }
        */

    }
}