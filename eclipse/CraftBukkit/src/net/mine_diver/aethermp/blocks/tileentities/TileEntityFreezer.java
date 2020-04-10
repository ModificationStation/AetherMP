package net.mine_diver.aethermp.blocks.tileentities;

import java.util.ArrayList;
import java.util.List;

import net.mine_diver.aethermp.blocks.BlockEnchanter;
import net.mine_diver.aethermp.blocks.BlockFreezer;
import net.mine_diver.aethermp.blocks.BlockManager;
import net.mine_diver.aethermp.items.ItemManager;
import net.mine_diver.aethermp.util.Frozen;
import net.minecraft.server.Block;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.IInventory;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.NBTTagList;
import net.minecraft.server.TileEntity;

public class TileEntityFreezer extends TileEntity implements IInventory {

    public TileEntityFreezer() {
        frozenItemStacks = new ItemStack[3];
        frozenProgress = 0;
        frozenPowerRemaining = 0;
        frozenTimeForItem = 0;
        firstTick = true;
    }
    
    @Override
    public ItemStack[] getContents() {
    	return this.frozenItemStacks;
    }
    
    @Override
    public int getSize() {
        return frozenItemStacks.length;
    }
    
    @Override
    public ItemStack getItem(int i) {
        return frozenItemStacks[i];
    }
    
    @Override
    public ItemStack splitStack(int i, int j) {
        if(frozenItemStacks[i] != null) {
            if(frozenItemStacks[i].count <= j) {
                ItemStack itemstack = frozenItemStacks[i];
                frozenItemStacks[i] = null;
                return itemstack;
            }
            ItemStack itemstack1 = frozenItemStacks[i].a(j);
            if(frozenItemStacks[i].count == 0)
                frozenItemStacks[i] = null;
            return itemstack1;
        } else
            return null;
    }
    
    @Override
    public void setItem(int i, ItemStack itemstack) {
        frozenItemStacks[i] = itemstack;
        if(itemstack != null && itemstack.count > getMaxStackSize())
            itemstack.count = getMaxStackSize();
    }
    
    @Override
    public String getName() {
        return "Freezer";
    }
    
    @Override
    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        NBTTagList nbttaglist = nbttagcompound.l("Items");
        frozenItemStacks = new ItemStack[getSize()];
        for(int i = 0; i < nbttaglist.c(); i++) {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.a(i);
            byte byte0 = nbttagcompound1.c("Slot");
            if(byte0 >= 0 && byte0 < frozenItemStacks.length)
                frozenItemStacks[byte0] = new ItemStack(nbttagcompound1);
        }
        frozenProgress = nbttagcompound.d("BurnTime");
        frozenTimeForItem = nbttagcompound.d("CookTime");
    }
    
    @Override
    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("BurnTime", (short) frozenProgress);
        nbttagcompound.a("CookTime", (short) frozenTimeForItem);
        NBTTagList nbttaglist = new NBTTagList();
        for(int i = 0; i < frozenItemStacks.length; i++)
            if(frozenItemStacks[i] != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.a("Slot", (byte)i);
                frozenItemStacks[i].a(nbttagcompound1);
                nbttaglist.a(nbttagcompound1);
            }

        nbttagcompound.a("Items", nbttaglist);
    }
    
    @Override
    public int getMaxStackSize() {
        return 64;
    }
    
    @Override
    public void g_() {
    	if (firstTick) {
    		BlockEnchanter.updateEnchanterBlockState(false, world, x, y, z);
    		firstTick = false;
    	}
    	boolean flag = frozenPowerRemaining > 0;
        boolean flag1 = false;
        if(frozenPowerRemaining > 0) {
            frozenPowerRemaining--;
            if(currentFrozen != null)
                frozenProgress++;
        }
        if(currentFrozen != null && (frozenItemStacks[0] == null || frozenItemStacks[0].id != currentFrozen.frozenFrom.id)) {
            currentFrozen = null;
            frozenProgress = 0;
        }
        if(currentFrozen != null && frozenProgress >= currentFrozen.frozenPowerNeeded) {
            if(frozenItemStacks[2] == null)
                setItem(2, new ItemStack(currentFrozen.frozenTo.getItem(), 1, currentFrozen.frozenTo.getData()));
            else
                setItem(2, new ItemStack(currentFrozen.frozenTo.getItem(), getItem(2).count + 1, currentFrozen.frozenTo.getData()));
            if(getItem(0).id == Item.WATER_BUCKET.id || getItem(0).id == Item.LAVA_BUCKET.id)
                setItem(0, new ItemStack(Item.BUCKET));
            else
            if(getItem(0).id == ItemManager.Bucket.id)
                setItem(0, new ItemStack(ItemManager.Bucket));
            else
                splitStack(0, 1);
            frozenProgress = 0;
            currentFrozen = null;
            frozenTimeForItem = 0;
        }
        if(frozenPowerRemaining <= 0 && currentFrozen != null && getItem(1) != null && getItem(1).id == BlockManager.Icestone.id) {
            frozenPowerRemaining += 500;
            splitStack(1, 1);
        }
        if(currentFrozen == null) {
            ItemStack itemstack = getItem(0);
            for(int i = 0; i < frozen.size(); i++) {
                if(itemstack == null || frozen.get(i) == null || itemstack.id != ((Frozen)frozen.get(i)).frozenFrom.id || itemstack.getData() != ((Frozen)frozen.get(i)).frozenFrom.getData())
                    continue;
                if(frozenItemStacks[2] == null) {
                    currentFrozen = (Frozen)frozen.get(i);
                    frozenTimeForItem = currentFrozen.frozenPowerNeeded;
                    continue;
                }
                if(frozenItemStacks[2].id == ((Frozen)frozen.get(i)).frozenTo.id && ((Frozen)frozen.get(i)).frozenTo.getItem().getMaxStackSize() > frozenItemStacks[2].count) {
                    currentFrozen = (Frozen)frozen.get(i);
                    frozenTimeForItem = currentFrozen.frozenPowerNeeded;
                }
            }
        }
        if(flag != (frozenPowerRemaining > 0)) {
            flag1 = true;
            BlockFreezer.updateFreezerBlockState(frozenPowerRemaining > 0, world, x, y, z);
        }
        if(flag1)
            update();
    }
    
    @Override
    public boolean a_(EntityHuman entityhuman) {
        if(world.getTileEntity(x, y, z) != this)
            return false;
        else
            return entityhuman.e((double) x + 0.5D, (double) y + 0.5D, (double) z + 0.5D) <= 64D;
    }

    public static void addFrozen(ItemStack from, ItemStack to, int i) {
        frozen.add(new Frozen(from, to, i));
    }

    private static List<Frozen> frozen = new ArrayList<Frozen>();
    private ItemStack frozenItemStacks[];
    public int frozenProgress;
    public int frozenPowerRemaining;
    public int frozenTimeForItem;
    private Frozen currentFrozen;
    private boolean firstTick;

    static {
        addFrozen(new ItemStack(Item.WATER_BUCKET, 1), new ItemStack(Block.ICE, 5), 500);
        addFrozen(new ItemStack(ItemManager.Bucket, 1, 8), new ItemStack(Block.ICE, 5), 500);
        addFrozen(new ItemStack(Item.LAVA_BUCKET, 1), new ItemStack(Block.OBSIDIAN, 2), 500);
        addFrozen(new ItemStack(BlockManager.Aercloud, 1, 0), new ItemStack(BlockManager.Aercloud, 1, 1), 50);
        addFrozen(new ItemStack(ItemManager.GoldPendant, 1), new ItemStack(ItemManager.IcePendant, 1), 2500);
        addFrozen(new ItemStack(ItemManager.GoldRing, 1), new ItemStack(ItemManager.IceRing, 1), 1500);
        addFrozen(new ItemStack(ItemManager.IronRing, 1), new ItemStack(ItemManager.IceRing, 1), 1500);
        addFrozen(new ItemStack(ItemManager.IronPendant, 1), new ItemStack(ItemManager.IcePendant, 1), 2500);
    }
}
