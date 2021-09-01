package com.Clivet268.Tonic.Items;

import com.Clivet268.Tonic.Tonic;
import com.Clivet268.Tonic.Util.IHasModel;
import com.Clivet268.Tonic.Util.RegistryHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item implements IHasModel {

        public ItemBase(String name)
        {
            setUnlocalizedName(name);
            setRegistryName(name);
            setCreativeTab(CreativeTabs.MATERIALS);
            RegistryHandler.ITEMS.add(this);
        }

        @Override
        public void registerModels()
        {
            Tonic.proxy.registerItemRenderer(this, 0, "inventory");
        }

}
