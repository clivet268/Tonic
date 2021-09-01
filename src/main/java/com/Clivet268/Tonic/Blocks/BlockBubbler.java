package com.Clivet268.Tonic.Blocks;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

import com.Clivet268.Tonic.TileEntities.TileEntityBubbler;
import com.Clivet268.Tonic.Tonic;
import com.Clivet268.Tonic.Util.IHasModel;
import com.Clivet268.Tonic.Util.RegistryHandler;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockBubbler extends BlockContainer
{
    public static final PropertyInteger BOTTLE_IN_TYPE = PropertyInteger.create("bottle_in_type", 0, 3);

    public BlockBubbler()
    {

        super(Material.IRON);
        String name = "bubbler";
        setUnlocalizedName(name);
        setRegistryName(name);
        RegistryHandler.BLOCKS.add(this);
        RegistryHandler.ITEMS.add(new ItemBlock(this).setRegistryName(name).setCreativeTab(CreativeTabs.MISC));

        this.setDefaultState(this.blockState.getBaseState().withProperty(BOTTLE_IN_TYPE, 0));
    }

    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityBubbler();
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (worldIn.isRemote)
        {
            return true;
        }
        else
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityBubbler)
            {
                playerIn.displayGUIChest((TileEntityBubbler)tileentity);
                //playerIn.addStat(StatList.BREWINGSTAND_INTERACTION);
            }

            return true;
        }
    }

    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        if (stack.hasDisplayName())
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityBubbler)
            {
                ((TileEntityBubbler)tileentity).setName(stack.getDisplayName());
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        double d0 = (double)((float)pos.getX() + 0.4F + rand.nextFloat() * 0.2F);
        double d1 = (double)((float)pos.getY() + 0.7F + rand.nextFloat() * 0.3F);
        double d2 = (double)((float)pos.getZ() + 0.4F + rand.nextFloat() * 0.2F);
        worldIn.spawnParticle(EnumParticleTypes.WATER_BUBBLE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        TileEntity tileentity = worldIn.getTileEntity(pos);

        if (tileentity instanceof TileEntityBubbler)
        {
            InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityBubbler)tileentity);
        }

        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return RegistryHandler.BUBBLER_ITEM;
    }
    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(RegistryHandler.BUBBLER);
    }
    @Override
    public boolean hasComparatorInputOverride(IBlockState state)
    {
        return true;
    }
    @Override
    public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos)
    {
        return Container.calcRedstone(worldIn.getTileEntity(pos));
    }
    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.SOLID;
    }


    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, BOTTLE_IN_TYPE);
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
    @Override
    public int getMetaFromState(IBlockState state){
        return 0;
    }
}
