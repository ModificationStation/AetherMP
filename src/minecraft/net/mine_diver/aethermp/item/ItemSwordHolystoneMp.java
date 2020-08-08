package net.mine_diver.aethermp.item;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ItemSwordHolystone;

public class ItemSwordHolystoneMp extends ItemSwordHolystone {

	public ItemSwordHolystoneMp(int itemID, EnumToolMaterial mat) {
        super(itemID, mat);
    }
	
	@Override
	public boolean hitEntity(ItemStack itemstack, EntityLiving entityliving, EntityLiving entityliving1) {
		if (entityliving.worldObj.multiplayerWorld) {
			itemstack.damageItem(1, entityliving1);
			return true;
		} else
			return super.hitEntity(itemstack, entityliving, entityliving1);
	}
}
