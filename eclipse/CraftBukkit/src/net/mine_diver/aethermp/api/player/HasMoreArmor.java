package net.mine_diver.aethermp.api.player;

import net.minecraft.server.ItemStack;

public interface HasMoreArmor {
	
	ItemStack getMoreArmorEquipment(int i);
	int getMoreArmorAmount();
}
