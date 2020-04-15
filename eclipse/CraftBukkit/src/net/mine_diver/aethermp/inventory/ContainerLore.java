package net.mine_diver.aethermp.inventory;

import net.minecraft.server.Container;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.IInventory;
import net.minecraft.server.InventoryPlayer;
import net.minecraft.server.ItemStack;
import net.minecraft.server.Slot;

public class ContainerLore extends Container {

    public ContainerLore(InventoryPlayer inventoryplayer) {
        loreSlot = new InventoryLore("Lore Item", 1, inventoryplayer.d);
        a(new Slot(loreSlot, 0, 82, 66));
        for(int i1 = 0; i1 < 3; i1++)
            for(int l1 = 0; l1 < 9; l1++)
                a(new Slot(inventoryplayer, l1 + i1 * 9 + 9, 48 + l1 * 18, 113 + i1 * 18));
        for(int j1 = 0; j1 < 9; j1++)
            a(new Slot(inventoryplayer, j1, 48 + j1 * 18, 171));
    }
    
    @Override
    protected void a(ItemStack itemstack1, int k, int l, boolean flag1) {}
    
    @Override
    public void a(EntityHuman entityhuman) {
        super.a(entityhuman);
        ItemStack itemstack = loreSlot.getItem(0);
        if(itemstack != null)
            entityhuman.b(itemstack);
    }
    
    @Override
    public boolean b(EntityHuman entityplayer) {
        return true;
    }
    
    @Override
    public ItemStack a(int i) {
        ItemStack itemstack = null;
        Slot slot = (Slot) e.get(i);
        if(slot != null && slot.b()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.cloneItemStack();
            if(i == 0)
                a(itemstack1, 10, 46, true);
            else if(i >= 10 && i < 37)
                a(itemstack1, 37, 46, false);
            else if(i >= 37 && i < 46)
                a(itemstack1, 10, 37, false);
            else
                a(itemstack1, 10, 46, false);
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

    public IInventory loreSlot;
}
