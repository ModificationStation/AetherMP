package net.mine_diver.aethermp.blocks.tileentities;

import java.util.ArrayList;
import java.util.List;

import net.mine_diver.aethermp.blocks.BlockEnchanter;
import net.mine_diver.aethermp.blocks.BlockManager;
import net.mine_diver.aethermp.items.ItemManager;
import net.mine_diver.aethermp.util.Enchantment;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.IInventory;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.NBTTagList;
import net.minecraft.server.TileEntity;

public class TileEntityEnchanter extends TileEntity implements IInventory {

    public TileEntityEnchanter() {
        enchanterItemStacks = new ItemStack[3];
        enchantProgress = 0;
        enchantPowerRemaining = 0;
        enchantTimeForItem = 0;
        firstTick = true;
    }
    
    @Override
    public ItemStack[] getContents() {
    	return this.enchanterItemStacks;
    }
    
    @Override
    public int getSize() {
        return enchanterItemStacks.length;
    }
    
    @Override
    public ItemStack getItem(int i) {
        return enchanterItemStacks[i];
    }
    
    @Override
    public ItemStack splitStack(int i, int j) {
        if(enchanterItemStacks[i] != null) {
            if(enchanterItemStacks[i].count <= j) {
                ItemStack itemstack = enchanterItemStacks[i];
                enchanterItemStacks[i] = null;
                return itemstack;
            }
            ItemStack itemstack1 = enchanterItemStacks[i].a(j);
            if(enchanterItemStacks[i].count == 0)
                enchanterItemStacks[i] = null;
            return itemstack1;
        } else
            return null;
    }
    
    @Override
    public void setItem(int i, ItemStack itemstack) {
        enchanterItemStacks[i] = itemstack;
        if(itemstack != null && itemstack.count > getMaxStackSize())
            itemstack.count = getMaxStackSize();
    }
    
    @Override
    public String getName() {
        return "Enchanter";
    }
    
    @Override
    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        NBTTagList nbttaglist = nbttagcompound.l("Items");
        enchanterItemStacks = new ItemStack[getSize()];
        for(int i = 0; i < nbttaglist.c(); i++) {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.a(i);
            byte byte0 = nbttagcompound1.c("Slot");
            if(byte0 >= 0 && byte0 < enchanterItemStacks.length)
                enchanterItemStacks[byte0] = new ItemStack(nbttagcompound1);
        }

        enchantProgress = nbttagcompound.d("BurnTime");
        enchantTimeForItem = nbttagcompound.d("CookTime");
        this.enchantTimeForItem = getItemBurnTime(this.enchanterItemStacks[1]);
    }
    
    @Override
    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("BurnTime", (short)enchantProgress);
        nbttagcompound.a("CookTime", (short)enchantTimeForItem);
        NBTTagList nbttaglist = new NBTTagList();
        for(int i = 0; i < enchanterItemStacks.length; i++)
            if(enchanterItemStacks[i] != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.a("Slot", (byte)i);
                enchanterItemStacks[i].a(nbttagcompound1);
                nbttaglist.a(nbttagcompound1);
            }

        nbttagcompound.a("Items", nbttaglist);
    }
    
    @Override
    public int getMaxStackSize() {
        return 64;
    }
    
    public boolean isBurning() {
        return enchantPowerRemaining > 0;
    }
    
    @Override
    public void g_() {
    	if (firstTick) {
    		BlockEnchanter.updateEnchanterBlockState(false, world, x, y, z);
    		firstTick = false;
    	}
        boolean flag = enchantPowerRemaining > 0;
        boolean flag1 = false;
        if(enchantPowerRemaining > 0) {
            enchantPowerRemaining--;
            if(currentEnchantment != null)
                enchantProgress++;
        }
        if(currentEnchantment != null && (enchanterItemStacks[0] == null || enchanterItemStacks[0].id != currentEnchantment.enchantFrom.id)) {
            currentEnchantment = null;
            enchantProgress = 0;
        }
        if(currentEnchantment != null && enchantProgress >= currentEnchantment.enchantPowerNeeded) {
            if(enchanterItemStacks[2] == null)
                setItem(2, new ItemStack(currentEnchantment.enchantTo.getItem(), 1, currentEnchantment.enchantTo.getData()));
            else
                setItem(2, new ItemStack(currentEnchantment.enchantTo.getItem(), getItem(2).count + 1, currentEnchantment.enchantTo.getData()));
            splitStack(0, 1);
            enchantProgress = 0;
            currentEnchantment = null;
            enchantTimeForItem = 0;
        }
        if(enchantPowerRemaining <= 0 && currentEnchantment != null && getItem(1) != null && getItem(1).id == ItemManager.AmbrosiumShard.id) {
            enchantPowerRemaining += 500;
            splitStack(1, 1);
        }
        if(currentEnchantment == null) {
            ItemStack itemstack = getItem(0);
            for(int i = 0; i < enchantments.size(); i++) {
                if(itemstack == null || enchantments.get(i) == null || itemstack.id != ((Enchantment)enchantments.get(i)).enchantFrom.id)
                    continue;
                if(enchanterItemStacks[2] == null) {
                    currentEnchantment = (Enchantment)enchantments.get(i);
                    enchantTimeForItem = currentEnchantment.enchantPowerNeeded;
                    continue;
                }
                if(enchanterItemStacks[2].id == ((Enchantment)enchantments.get(i)).enchantTo.id && ((Enchantment)enchantments.get(i)).enchantTo.getItem().getMaxStackSize() > enchanterItemStacks[2].count) {
                    currentEnchantment = (Enchantment)enchantments.get(i);
                    enchantTimeForItem = currentEnchantment.enchantPowerNeeded;
                }
            }

        }
        if(flag != (enchantPowerRemaining > 0)) {
            flag1 = true;
            BlockEnchanter.updateEnchanterBlockState(enchantPowerRemaining > 0, world, x, y, z);
        }
        if(flag1)
            update();
    }
    
    @Override
    public boolean a_(EntityHuman entityhuman) {
        if(world.getTileEntity(x, y, z) != this)
            return false;
        else
            return entityhuman.e((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D) <= 64D;
    }
    
    private int getItemBurnTime(ItemStack itemstack) {
    	if (itemstack == null)
    		return 0;
    	Enchantment searchingFor;
    	int power = 0;
    	for(int i = 0; i < enchantments.size(); i++)
    		if(itemstack.id == ((Enchantment) enchantments.get(i)).enchantFrom.id) {
    			searchingFor = (Enchantment) enchantments.get(i);
    			power = searchingFor.enchantPowerNeeded;
    		}
    	return power;
    }
    
    public static void addEnchantment(ItemStack itemstack, ItemStack itemstack1, int i) {
        enchantments.add(new Enchantment(itemstack, itemstack1, i));
    }

    private static List<Enchantment> enchantments = new ArrayList<Enchantment>();
    private ItemStack enchanterItemStacks[];
    public int enchantProgress;
    public int enchantPowerRemaining;
    public int enchantTimeForItem;
    private Enchantment currentEnchantment;
    private boolean firstTick;

    static  {
        addEnchantment(new ItemStack(BlockManager.GravititeOre, 1), new ItemStack(BlockManager.EnchantedGravitite, 1), 1000);
        addEnchantment(new ItemStack(ItemManager.PickSkyroot, 1), new ItemStack(ItemManager.PickSkyroot, 1), 250);
        addEnchantment(new ItemStack(ItemManager.SwordSkyroot, 1), new ItemStack(ItemManager.SwordSkyroot, 1), 250);
        addEnchantment(new ItemStack(ItemManager.ShovelSkyroot, 1), new ItemStack(ItemManager.ShovelSkyroot, 1), 200);
        addEnchantment(new ItemStack(ItemManager.AxeSkyroot, 1), new ItemStack(ItemManager.AxeSkyroot, 1), 200);
        addEnchantment(new ItemStack(ItemManager.PickHolystone, 1), new ItemStack(ItemManager.PickHolystone, 1), 600);
        addEnchantment(new ItemStack(ItemManager.SwordHolystone, 1), new ItemStack(ItemManager.SwordHolystone, 1), 600);
        addEnchantment(new ItemStack(ItemManager.ShovelHolystone, 1), new ItemStack(ItemManager.ShovelHolystone, 1), 500);
        addEnchantment(new ItemStack(ItemManager.AxeHolystone, 1), new ItemStack(ItemManager.AxeHolystone, 1), 500);
        addEnchantment(new ItemStack(ItemManager.PickZanite, 1), new ItemStack(ItemManager.PickZanite, 1), 2500);
        addEnchantment(new ItemStack(ItemManager.SwordZanite, 1), new ItemStack(ItemManager.SwordZanite, 1), 2500);
        addEnchantment(new ItemStack(ItemManager.ShovelZanite, 1), new ItemStack(ItemManager.ShovelZanite, 1), 2000);
        addEnchantment(new ItemStack(ItemManager.AxeZanite, 1), new ItemStack(ItemManager.AxeZanite, 1), 2000);
        addEnchantment(new ItemStack(ItemManager.PickGravitite, 1), new ItemStack(ItemManager.PickGravitite, 1), 6000);
        addEnchantment(new ItemStack(ItemManager.SwordGravitite, 1), new ItemStack(ItemManager.SwordGravitite, 1), 6000);
        addEnchantment(new ItemStack(ItemManager.ShovelGravitite, 1), new ItemStack(ItemManager.ShovelGravitite, 1), 5000);
        addEnchantment(new ItemStack(ItemManager.AxeGravitite, 1), new ItemStack(ItemManager.AxeGravitite, 1), 5000);
        //addEnchantment(new ItemStack(ItemManager.Dart, 1, 0), new ItemStack(ItemManager.Dart, 1, 2), 250);
        addEnchantment(new ItemStack(ItemManager.Bucket, 1, 2), new ItemStack(ItemManager.Bucket, 1, 3), 1000);
        addEnchantment(new ItemStack(Item.GOLD_RECORD, 1), new ItemStack(ItemManager.BlueMusicDisk, 1), 2500);
        addEnchantment(new ItemStack(Item.GREEN_RECORD, 1), new ItemStack(ItemManager.BlueMusicDisk, 1), 2500);
        addEnchantment(new ItemStack(Item.LEATHER_HELMET, 1), new ItemStack(Item.LEATHER_HELMET, 1), 400);
        addEnchantment(new ItemStack(Item.LEATHER_CHESTPLATE, 1), new ItemStack(Item.LEATHER_CHESTPLATE, 1), 500);
        addEnchantment(new ItemStack(Item.LEATHER_LEGGINGS, 1), new ItemStack(Item.LEATHER_LEGGINGS, 1), 500);
        addEnchantment(new ItemStack(Item.LEATHER_BOOTS, 1), new ItemStack(Item.LEATHER_BOOTS, 1), 400);
        addEnchantment(new ItemStack(Item.WOOD_PICKAXE, 1), new ItemStack(Item.WOOD_PICKAXE, 1), 500);
        addEnchantment(new ItemStack(Item.WOOD_SPADE, 1), new ItemStack(Item.WOOD_SPADE, 1), 400);
        addEnchantment(new ItemStack(Item.WOOD_SWORD, 1), new ItemStack(Item.WOOD_SWORD, 1), 500);
        addEnchantment(new ItemStack(Item.WOOD_AXE, 1), new ItemStack(Item.WOOD_AXE, 1), 400);
        addEnchantment(new ItemStack(Item.WOOD_HOE, 1), new ItemStack(Item.WOOD_HOE, 1), 300);
        addEnchantment(new ItemStack(Item.STONE_PICKAXE, 1), new ItemStack(Item.STONE_PICKAXE, 1), 1000);
        addEnchantment(new ItemStack(Item.STONE_SPADE, 1), new ItemStack(Item.STONE_SPADE, 1), 750);
        addEnchantment(new ItemStack(Item.STONE_SWORD, 1), new ItemStack(Item.STONE_SWORD, 1), 1000);
        addEnchantment(new ItemStack(Item.STONE_AXE, 1), new ItemStack(Item.STONE_AXE, 1), 750);
        addEnchantment(new ItemStack(Item.STONE_HOE, 1), new ItemStack(Item.STONE_HOE, 1), 750);
        addEnchantment(new ItemStack(Item.IRON_HELMET, 1), new ItemStack(Item.IRON_HELMET, 1), 1500);
        addEnchantment(new ItemStack(Item.IRON_CHESTPLATE, 1), new ItemStack(Item.IRON_CHESTPLATE, 1), 2000);
        addEnchantment(new ItemStack(Item.IRON_LEGGINGS, 1), new ItemStack(Item.IRON_LEGGINGS, 1), 2000);
        addEnchantment(new ItemStack(Item.IRON_BOOTS, 1), new ItemStack(Item.IRON_BOOTS, 1), 1500);
        addEnchantment(new ItemStack(Item.IRON_PICKAXE, 1), new ItemStack(Item.IRON_PICKAXE, 1), 2500);
        addEnchantment(new ItemStack(Item.IRON_SPADE, 1), new ItemStack(Item.IRON_SPADE, 1), 2000);
        addEnchantment(new ItemStack(Item.IRON_SWORD, 1), new ItemStack(Item.IRON_SWORD, 1), 2500);
        addEnchantment(new ItemStack(Item.IRON_AXE, 1), new ItemStack(Item.IRON_AXE, 1), 1500);
        addEnchantment(new ItemStack(Item.IRON_HOE, 1), new ItemStack(Item.IRON_HOE, 1), 1500);
        addEnchantment(new ItemStack(Item.GOLD_HELMET, 1), new ItemStack(Item.GOLD_HELMET, 1), 1000);
        addEnchantment(new ItemStack(Item.GOLD_CHESTPLATE, 1), new ItemStack(Item.GOLD_CHESTPLATE, 1), 1200);
        addEnchantment(new ItemStack(Item.GOLD_LEGGINGS, 1), new ItemStack(Item.GOLD_LEGGINGS, 1), 1200);
        addEnchantment(new ItemStack(Item.GOLD_BOOTS, 1), new ItemStack(Item.GOLD_BOOTS, 1), 1000);
        addEnchantment(new ItemStack(Item.GOLD_PICKAXE, 1), new ItemStack(Item.GOLD_PICKAXE, 1), 1500);
        addEnchantment(new ItemStack(Item.GOLD_SPADE, 1), new ItemStack(Item.GOLD_SPADE, 1), 1000);
        addEnchantment(new ItemStack(Item.GOLD_SWORD, 1), new ItemStack(Item.GOLD_SWORD, 1), 1500);
        addEnchantment(new ItemStack(Item.GOLD_AXE, 1), new ItemStack(Item.GOLD_AXE, 1), 1000);
        addEnchantment(new ItemStack(Item.GOLD_HOE, 1), new ItemStack(Item.GOLD_HOE, 1), 1000);
        addEnchantment(new ItemStack(Item.DIAMOND_HELMET, 1), new ItemStack(Item.DIAMOND_HELMET, 1), 5000);
        addEnchantment(new ItemStack(Item.DIAMOND_CHESTPLATE, 1), new ItemStack(Item.DIAMOND_CHESTPLATE, 1), 6000);
        addEnchantment(new ItemStack(Item.DIAMOND_LEGGINGS, 1), new ItemStack(Item.DIAMOND_LEGGINGS, 1), 6000);
        addEnchantment(new ItemStack(Item.DIAMOND_BOOTS, 1), new ItemStack(Item.DIAMOND_BOOTS, 1), 5000);
        addEnchantment(new ItemStack(Item.DIAMOND_PICKAXE, 1), new ItemStack(Item.DIAMOND_PICKAXE, 1), 7000);
        addEnchantment(new ItemStack(Item.DIAMOND_SPADE, 1), new ItemStack(Item.DIAMOND_SPADE, 1), 6000);
        addEnchantment(new ItemStack(Item.DIAMOND_SWORD, 1), new ItemStack(Item.DIAMOND_SWORD, 1), 6500);
        addEnchantment(new ItemStack(Item.DIAMOND_AXE, 1), new ItemStack(Item.DIAMOND_AXE, 1), 6000);
        addEnchantment(new ItemStack(Item.DIAMOND_HOE, 1), new ItemStack(Item.DIAMOND_HOE, 1), 6000);
        addEnchantment(new ItemStack(Item.FISHING_ROD, 1), new ItemStack(Item.FISHING_ROD, 1), 500);
        addEnchantment(new ItemStack(BlockManager.Quicksoil, 1), new ItemStack(BlockManager.QuicksoilGlass, 1), 250);
        addEnchantment(new ItemStack(BlockManager.Holystone, 1), new ItemStack(ItemManager.HealingStone, 1), 750);
        addEnchantment(new ItemStack(ItemManager.GravititeHelmet, 1), new ItemStack(ItemManager.GravititeHelmet, 1), 12000);
        addEnchantment(new ItemStack(ItemManager.GravititeBodyplate, 1), new ItemStack(ItemManager.GravititeBodyplate, 1), 20000);
        addEnchantment(new ItemStack(ItemManager.GravititePlatelegs, 1), new ItemStack(ItemManager.GravititePlatelegs, 1), 15000);
        addEnchantment(new ItemStack(ItemManager.GravititeBoots, 1), new ItemStack(ItemManager.GravititeBoots, 1), 12000);
        //addEnchantment(new ItemStack(ItemManager.GravititeGlove, 1), new ItemStack(ItemManager.GravititeGlove, 1), 10000);
        addEnchantment(new ItemStack(ItemManager.ZaniteHelmet, 1), new ItemStack(ItemManager.ZaniteHelmet, 1), 6000);
        addEnchantment(new ItemStack(ItemManager.ZaniteChestplate, 1), new ItemStack(ItemManager.ZaniteChestplate, 1), 10000);
        addEnchantment(new ItemStack(ItemManager.ZaniteLeggings, 1), new ItemStack(ItemManager.ZaniteLeggings, 1), 8000);
        addEnchantment(new ItemStack(ItemManager.ZaniteBoots, 1), new ItemStack(ItemManager.ZaniteBoots, 1), 5000);
        /*addEnchantment(new ItemStack(ItemManager.ZaniteGlove, 1), new ItemStack(ItemManager.ZaniteGlove, 1), 4000);
        addEnchantment(new ItemStack(ItemManager.ZaniteRing, 1), new ItemStack(ItemManager.ZaniteRing, 1), 2000);
        addEnchantment(new ItemStack(ItemManager.ZanitePendant, 1), new ItemStack(ItemManager.ZanitePendant, 1), 2000);
        addEnchantment(new ItemStack(ItemManager.LeatherGlove, 1), new ItemStack(ItemManager.LeatherGlove, 1), 300);
        addEnchantment(new ItemStack(ItemManager.IronGlove, 1), new ItemStack(ItemManager.IronGlove, 1), 1200);
        addEnchantment(new ItemStack(ItemManager.GoldGlove, 1), new ItemStack(ItemManager.GoldGlove, 1), 800);
        addEnchantment(new ItemStack(ItemManager.DiamondGlove, 1), new ItemStack(ItemManager.DiamondGlove, 1), 4000);
        addEnchantment(new ItemStack(ItemManager.DartShooter, 1, 0), new ItemStack(ItemManager.DartShooter, 1, 2), 2000);*/
    }
}
