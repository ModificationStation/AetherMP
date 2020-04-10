package net.mine_diver.aethermp.inventory;

import net.mine_diver.aethermp.blocks.tileentities.TileEntityFreezer;
import net.minecraft.server.Container;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.ICrafting;
import net.minecraft.server.InventoryPlayer;
import net.minecraft.server.ItemStack;
import net.minecraft.server.Slot;
import net.minecraft.server.SlotResult2;

public class ContainerFreezer extends Container {

    public ContainerFreezer(InventoryPlayer inventoryplayer, TileEntityFreezer tileentityfreezer) {
        cookTime = 0;
        burnTime = 0;
        itemBurnTime = 0;
        freezer = tileentityfreezer;
        a(new Slot(tileentityfreezer, 0, 56, 17));
        a(new Slot(tileentityfreezer, 1, 56, 53));
        a(new SlotResult2(inventoryplayer.d, tileentityfreezer, 2, 116, 35));
        for(int i = 0; i < 3; i++)
            for(int k = 0; k < 9; k++)
                a(new Slot(inventoryplayer, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
        for(int j = 0; j < 9; j++)
            a(new Slot(inventoryplayer, j, 8 + j * 18, 142));
    }
    
    @Override
    protected void a(ItemStack itemstack1, int k, int l, boolean flag1) {}
    
    @Override
    public void a() {
        super.a();
        for(int i = 0; i < listeners.size(); i++) {
            ICrafting icrafting = (ICrafting)listeners.get(i);
            if(cookTime != freezer.frozenTimeForItem)
                icrafting.a(this, 0, freezer.frozenTimeForItem);
            if(burnTime != freezer.frozenProgress)
                icrafting.a(this, 1, freezer.frozenProgress);
            if(itemBurnTime != freezer.frozenPowerRemaining)
                icrafting.a(this, 2, freezer.frozenPowerRemaining);
        }

        cookTime = freezer.frozenTimeForItem;
        burnTime = freezer.frozenProgress;
        itemBurnTime = freezer.frozenPowerRemaining;
    }
    
    @Override
    public void a(final ICrafting crafting) {
        super.a(crafting);
        crafting.a(this, 0, freezer.frozenTimeForItem);
        crafting.a(this, 1, freezer.frozenProgress);
        crafting.a(this, 2, freezer.frozenPowerRemaining);
    }
    
    @Override
    public boolean b(EntityHuman entityhuman) {
        return freezer.a_(entityhuman);
    }
    
    @Override
    public ItemStack a(int i) {
        ItemStack itemstack = null;
        Slot slot = (Slot) e.get(i);
        if(slot != null && slot.b()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.cloneItemStack();
            if(i == 2)
                a(itemstack1, 3, 39, true);
            else if(i >= 3 && i < 30)
                a(itemstack1, 30, 39, false);
            else if(i >= 30 && i < 39)
                a(itemstack1, 3, 30, false);
            else
                a(itemstack1, 3, 39, false);
            if(itemstack1.count == 0)
                slot.c(null);
            else
                slot.c();
            if(itemstack1.count != itemstack.count)
                slot.a(itemstack1);
            else
                return null;
        }
        return itemstack;
    }

    private TileEntityFreezer freezer;
    private int cookTime;
    private int burnTime;
    private int itemBurnTime;
}
