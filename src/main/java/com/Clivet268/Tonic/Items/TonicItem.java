package com.Clivet268.Tonic.Items;

import com.Clivet268.Tonic.Tonic;
import com.Clivet268.Tonic.Util.IHasModel;
import com.Clivet268.Tonic.Util.RegistryHandler;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class TonicItem extends Item implements IHasModel
{
    private int tonicMode;
    @Nullable
    private PotionType vanillaEffect;
    public TonicItem()
    {
        String name = "tonic";
        setUnlocalizedName(name);
        setRegistryName(name);
        this.setMaxStackSize(1);
        this.setTonicMode(0);
        this.setVanillaEffect(null);
        this.setCreativeTab(CreativeTabs.BREWING);
        RegistryHandler.ITEMS.add(this);
    }

    public int getTonicMode() {
        return tonicMode;
    }

    public void setTonicMode(int tonicMode) {
        this.tonicMode = tonicMode;
    }

    @Nullable
    public PotionType getVanillaEffect() {
        return vanillaEffect;
    }

    public void setVanillaEffect(@Nullable PotionType vanillaEffect) {
        this.vanillaEffect = vanillaEffect;
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
    {
        System.out.println(this.getTonicMode());
        if(this.getTonicMode() == 0) {
            EntityPlayer entityplayer = entityLiving instanceof EntityPlayer ? (EntityPlayer) entityLiving : null;

            if (entityplayer == null || !entityplayer.capabilities.isCreativeMode) {
                stack.shrink(1);
            }

            if (entityplayer instanceof EntityPlayerMP) {
                CriteriaTriggers.CONSUME_ITEM.trigger((EntityPlayerMP) entityplayer, stack);
            }

            if (!worldIn.isRemote) {
                for (PotionEffect potioneffect : PotionUtils.getEffectsFromStack(stack)) {
                    if (potioneffect.getPotion().isInstant()) {
                        potioneffect.getPotion().affectEntity(entityplayer, entityplayer, entityLiving, potioneffect.getAmplifier(), 1.0D);
                    } else {
                        entityLiving.addPotionEffect(new PotionEffect(potioneffect));
                    }
                }
            }

            if (entityplayer != null) {
                entityplayer.addStat(StatList.getObjectUseStats(this));
            }

            if (entityplayer == null || !entityplayer.capabilities.isCreativeMode) {
                if (stack.isEmpty()) {
                    return new ItemStack(Items.GLASS_BOTTLE);
                }

                if (entityplayer != null) {
                    entityplayer.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
                }
            }
        }
        return stack;
    }

    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 32;
    }

    public EnumAction getItemUseAction(ItemStack stack)
    {
        if(this.getTonicMode() == 0) {
            return EnumAction.DRINK;
        }
        return EnumAction.NONE;
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        System.out.println(this.getTonicMode());
        if (this.getTonicMode() == 0) {
            playerIn.setActiveHand(handIn);
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
        }
        else {
            ItemStack itemstack = playerIn.getHeldItem(handIn);
            ItemStack itemstack1 = playerIn.capabilities.isCreativeMode ? itemstack.copy() : itemstack.splitStack(1);
            worldIn.playSound((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_SPLASH_POTION_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

            if (!worldIn.isRemote)
            {
                EntityPotion entitypotion = new EntityPotion(worldIn, playerIn, itemstack1);
                entitypotion.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, -20.0F, 0.5F, 1.0F);
                worldIn.spawnEntity(entitypotion);
            }

            playerIn.addStat(StatList.getObjectUseStats(this));
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
        }
    }

    public String getItemStackDisplayName(ItemStack stack)
    {
        return I18n.translateToLocal(PotionUtils.getPotionFromItem(stack).getNamePrefixed("potion.effect."));
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        PotionUtils.addPotionTooltip(stack, tooltip, 1.0F);
    }

    /*@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
        return super.hasEffect(stack) || !PotionUtils.getEffectsFromStack(stack).isEmpty();
    }

     */
    @Override
    public void registerModels()
    {
        Tonic.proxy.registerItemRenderer(this, 0, "inventory");
    }

}

