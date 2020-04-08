package net.mine_diver.aethermp.inventory;

import net.mine_diver.aethermp.items.ItemMoreArmor;
import net.minecraft.server.IInventory;
import net.minecraft.server.ItemStack;
import net.minecraft.server.Slot;

class SlotMoreArmor extends Slot {

    SlotMoreArmor(ContainerAether containerplayer, IInventory iinventory, int i, int j, int k, int l) {
        super(iinventory, i, j, k);
        inventory = containerplayer;
        armorType = l;
    }
    
    @Override
    public int d() {
        return 1;
    }
    
    @Override
    public boolean isAllowed(ItemStack itemstack) {
        if(itemstack.getItem() instanceof ItemMoreArmor)
            return ((ItemMoreArmor) itemstack.getItem()).isTypeValid(armorType);
        else
            return false;
    }

    final int armorType;
    final ContainerAether inventory;
}
