package net.mine_diver.aethermp.inventory;

import net.mine_diver.aethermp.blocks.tileentities.TileEntityEnchanter;
import net.minecraft.server.Container;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.ICrafting;
import net.minecraft.server.InventoryPlayer;
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
    public boolean b(EntityHuman entityhuman) {
        return enchanter.a_(entityhuman);
    }

    private TileEntityEnchanter enchanter;
    private int cookTime;
    private int burnTime;
    private int itemBurnTime;
}
