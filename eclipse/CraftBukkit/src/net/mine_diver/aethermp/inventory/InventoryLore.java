package net.mine_diver.aethermp.inventory;

import net.mine_diver.aethermp.items.ItemManager;
import net.mine_diver.aethermp.util.Achievements;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.ItemStack;

public class InventoryLore extends InventoryBasic {

	public InventoryLore(String s, int i, EntityHuman entityhuman) {
		super(s, i);
		human = entityhuman;
	}
	
	@Override
	public void setItem(int i, ItemStack itemstack) {
		super.setItem(i, itemstack);
		if (itemstack != null && human instanceof EntityPlayer) {
			Achievements.giveAchievement(Achievements.lore, (EntityPlayer) human);
			if (itemstack.id == ItemManager.LoreBook.id)
				Achievements.giveAchievement(Achievements.loreception, (EntityPlayer) human);
		}
	}
	
	public EntityHuman human;
}
