package net.mine_diver.aethermp.api.player;

import net.minecraft.server.ItemStack;

public interface HasMoreArmor extends PlayerApplicableInterface {
	
	ItemStack getMoreArmorEquipment(int i);
	int getMoreArmorAmount();
}
