package net.mine_diver.aethermp.items;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemCloudStaff;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemCloudStaffMp extends ItemCloudStaff {

	public ItemCloudStaffMp(int i) {
		super(i);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        if (world.multiplayerWorld)
        	return itemstack;
        return super.onItemRightClick(itemstack, world, entityplayer);
    }
}
