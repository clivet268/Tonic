package com.Clivet268.Tonic.Util;

import com.Clivet268.Tonic.Blocks.BlockBubbler;
import com.Clivet268.Tonic.Items.ItemBase;
import com.Clivet268.Tonic.Items.TonicItem;
import com.Clivet268.Tonic.Tonic;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class RegistryHandler {
    public static final List<Block> BLOCKS = new ArrayList<Block>();
    public static final List<Item> ITEMS = new ArrayList<Item>();


    //Blocks
    public static final Block BUBBLER = new BlockBubbler();

    //Items
    public static final TonicItem TONIC = new TonicItem();

    //Block Items
    public static final ItemBlock BUBBLER_ITEM = new ItemBlock(BUBBLER);

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(ITEMS.toArray(new Item[0]));
    }
    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(BLOCKS.toArray(new Block[0]));
    }
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onModelRegistryEvent(ModelRegistryEvent event) {
        for(Item item : ITEMS)
        {
            if (item instanceof IHasModel){
                ((IHasModel)item).registerModels();
            }
        }for(Block block : BLOCKS)
        {
            if (block instanceof IHasModel){
                ((IHasModel)block).registerModels();
            }
        }

    }


}
