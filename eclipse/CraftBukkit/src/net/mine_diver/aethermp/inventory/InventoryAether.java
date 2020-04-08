package net.mine_diver.aethermp.inventory;

import net.mine_diver.aethermp.items.ItemMoreArmor;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.IInventory;
import net.minecraft.server.ItemStack;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.NBTTagList;

public class InventoryAether implements IInventory {

    public InventoryAether(EntityHuman entityhuman) {
        player = entityhuman;
        slots = new ItemStack[8];
    }
    
    @Override
    public ItemStack[] getContents() {
        return this.slots;
    }

    public void readFromNBT(NBTTagList nbttaglist) {
        slots = new ItemStack[8];
        for(int i = 0; i < nbttaglist.c(); i++) {
            NBTTagCompound nbttagcompound = (NBTTagCompound) nbttaglist.a(i);
            int j = nbttagcompound.c("Slot") & 0xff;
            ItemStack itemstack = new ItemStack(nbttagcompound);
            if(j > 8 || !(itemstack.getItem() instanceof ItemMoreArmor)) {
                readOldFile(nbttaglist);
                return;
            }
            if(itemstack.getItem() != null && j < slots.length)
                slots[j] = itemstack;
        }

    }

    public void readOldFile(NBTTagList nbttaglist) {
        for(int i = 0; i < nbttaglist.c(); i++) {
            NBTTagCompound nbttagcompound = (NBTTagCompound) nbttaglist.a(i);
            int j = nbttagcompound.c("Slot") & 0xff;
            ItemStack itemstack = new ItemStack(nbttagcompound);
            if(itemstack.getItem() != null && j >= 104 && j < 112)
                slots[j - 104] = itemstack;
        }

    }

    public NBTTagList writeToNBT(NBTTagList nbttaglist) {
        for(int j = 0; j < slots.length; j++)
            if(slots[j] != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.a("Slot", (byte)j);
                slots[j].a(nbttagcompound1);
                nbttaglist.a(nbttagcompound1);
            }
        return nbttaglist;
    }
    
    @Override
    public ItemStack getItem(int i) {
        return slots[i];
    }
    
    @Override
    public ItemStack splitStack(int i, int j) {
        if(slots[i] != null) {
            if(slots[i].count <= j) {
                ItemStack itemstack = slots[i];
                slots[i] = null;
                update();
                return itemstack;
            }
            ItemStack itemstack1 = slots[i].a(j);
            if(slots[i].count == 0)
                slots[i] = null;
            update();
            return itemstack1;
        } else
            return null;
    }
    
    @Override
    public void setItem(int i, ItemStack itemstack) {
        slots[i] = itemstack;
        if(itemstack != null && itemstack.count > getMaxStackSize())
            itemstack.count = getMaxStackSize();
        update();
    }
    
    @Override
    public int getSize() {
        return 8;
    }
    
    @Override
    public String getName() {
        return "Aether Slots";
    }
    
    @Override
    public int getMaxStackSize() {
        return 1;
    }
    
    @Override
    public void update() {}
    
    @Override
    public boolean a_(EntityHuman entityhuman) {
        return true;
    }

    public int getTotalArmorValue() {
        int i = 0;
        int j = 0;
        int k = 0;
        for(int l = 0; l < slots.length; l++)
            if(slots[l] != null && (slots[l].getItem() instanceof ItemMoreArmor)) {
                int i1 = slots[l].i();
                int j1 = slots[l].g();
                int k1 = i1 - j1;
                j += k1;
                k += i1;
                int l1 = ((ItemMoreArmor) slots[l].getItem()).damageReduceAmount;
                i += l1;
            }
        if(k == 0)
            return 0;
        else
            return ((i - 1) * j) / k + 1;
    }

    public void damageArmor(int i) {
        for(int j = 0; j < slots.length; j++) {
            if(slots[j] == null || !(slots[j].getItem() instanceof ItemMoreArmor))
                continue;
            slots[j].damage(i, player);
            if(slots[j].count == 0) {
                slots[j].a(player);
                slots[j] = null;
            }
        }

    }

    public void dropAllItems() {
        for(int j = 0; j < slots.length; j++)
            if(slots[j] != null) {
                player.a(slots[j], true);
                slots[j] = null;
            }
    }

    public ItemStack slots[];
    public EntityHuman player;
}
