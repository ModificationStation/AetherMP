package net.mine_diver.aethermp.item;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemLoreBook;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemLoreBookMp extends ItemLoreBook {

	public ItemLoreBookMp(int i) {
		super(i);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (world.multiplayerWorld)
	        return itemstack;
		return super.onItemRightClick(itemstack, world, entityplayer);
    }
}
