package net.mine_diver.aethermp.inventory;

import net.mine_diver.aethermp.blocks.tileentities.TileEntityEnchanter;
import net.minecraft.server.Container;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.ICrafting;
import net.minecraft.server.InventoryPlayer;
import net.minecraft.server.ItemStack;
import net.minecraft.server.Slot;
import net.minecraft.server.SlotResult2;

public class ContainerEnchanter extends Container {

    public ContainerEnchanter(InventoryPlayer inventoryplayer, TileEntityEnchanter tileentityenchanter) {
        cookTime = 0;
        burnTime = 0;
        itemBurnTime = 0;
        enchanter = tileentityenchanter;
        a(new Slot(tileentityenchanter, 0, 56, 17));
        a(new Slot(tileentityenchanter, 1, 56, 53));
        a(new SlotResult2(inventoryplayer.d, tileentityenchanter, 2, 116, 35));
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
            if (cookTime != enchanter.enchantTimeForItem)
                icrafting.a(this, 0, enchanter.enchantTimeForItem);
            if (burnTime != enchanter.enchantProgress)
                icrafting.a(this, 1, enchanter.enchantProgress);
            if (itemBurnTime != enchanter.enchantPowerRemaining)
                icrafting.a(this, 2, enchanter.enchantPowerRemaining);
        }

        cookTime = enchanter.enchantTimeForItem;
        burnTime = enchanter.enchantProgress;
        itemBurnTime = enchanter.enchantPowerRemaining;
    }
    
    @Override
    public void a(final ICrafting crafting) {
        super.a(crafting);
        crafting.a(this, 0, enchanter.enchantTimeForItem);
        crafting.a(this, 1, enchanter.enchantProgress);
        crafting.a(this, 2, enchanter.enchantPowerRemaining);
    }
    
    @Override
    public boolean b(EntityHuman entityhuman) {
        return enchanter.a_(entityhuman);
    }
    
    @Override
    public ItemStack a(int i) {
        ItemStack itemstack = null;
        Slot slot = (Slot)e.get(i);
        if(slot != null && slot.b()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.cloneItemStack();
            if(i == 2)
                a(itemstack1, 3, 39, true);
            else
            if(i >= 3 && i < 30)
                a(itemstack1, 30, 39, false);
            else
            if(i >= 30 && i < 39)
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

    private TileEntityEnchanter enchanter;
    private int cookTime;
    private int burnTime;
    private int itemBurnTime;
}
