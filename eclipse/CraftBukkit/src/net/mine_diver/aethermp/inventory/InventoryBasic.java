// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 
// Source File Name:   SourceFile

package net.mine_diver.aethermp.inventory;

import java.util.List;

import net.minecraft.server.EntityHuman;
import net.minecraft.server.IInventory;
import net.minecraft.server.ItemStack;

public class InventoryBasic implements IInventory {

    public InventoryBasic(String s, int i) {
        inventoryTitle = s;
        slotsCount = i;
        inventoryContents = new ItemStack[i];
    }
    
    @Override
    public ItemStack[] getContents() {
        return this.inventoryContents;
    }
    
    @Override
    public ItemStack getItem(int i) {
        return inventoryContents[i];
    }
    
    @Override
    public ItemStack splitStack(int i, int j) {
        if(inventoryContents[i] != null) {
            if(inventoryContents[i].count <= j) {
                ItemStack itemstack = inventoryContents[i];
                inventoryContents[i] = null;
                update();
                return itemstack;
            }
            ItemStack itemstack1 = inventoryContents[i].a(j);
            if(inventoryContents[i].count == 0)
                inventoryContents[i] = null;
            update();
            return itemstack1;
        } else
            return null;
    }
    
    @Override
    public void setItem(int i, ItemStack itemstack) {
        inventoryContents[i] = itemstack;
        if(itemstack != null && itemstack.count > getMaxStackSize())
            itemstack.count = getMaxStackSize();
        update();
    }
    
    @Override
    public int getSize() {
        return slotsCount;
    }
    
    @Override
    public String getName() {
        return inventoryTitle;
    }
    
    @Override
    public int getMaxStackSize() {
        return 64;
    }
    
    @Override
    public void update() {
        if(changeListeners != null)
            for(int i = 0; i < changeListeners.size(); i++)
                ((IInvBasic) changeListeners.get(i)).onInventoryChanged(this);
    }

    @Override
    public boolean a_(EntityHuman entityhuman) {
        return true;
    }

    private String inventoryTitle;
    private int slotsCount;
    private ItemStack inventoryContents[];
    private List<IInvBasic> changeListeners;
}
