package net.mine_diver.aethermp.items;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.ItemPigSlayer;
import net.minecraft.src.ItemStack;

public class ItemPigSlayerMp extends ItemPigSlayer {

	public ItemPigSlayerMp(int i) {
		super(i);
	}
	
	@Override
	public boolean hitEntity(ItemStack itemstack, EntityLiving entityliving, EntityLiving entityliving1) {
        if (entityliving.worldObj.multiplayerWorld)
        	return true;
        return super.hitEntity(itemstack, entityliving, entityliving1);
    }
}
