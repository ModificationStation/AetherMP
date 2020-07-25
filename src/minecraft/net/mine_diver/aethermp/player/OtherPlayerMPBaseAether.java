package net.mine_diver.aethermp.player;

import net.minecraft.src.EntityOtherPlayerMP;
import net.minecraft.src.ItemStack;

public class OtherPlayerMPBaseAether extends OtherPlayerMPBase {
	
	public OtherPlayerMPBaseAether(EntityOtherPlayerMP entityotherplayermp) {
		super(entityotherplayermp);
	}

	@Override
	public boolean outfitWithItem(int slot, int ID, int meta) {
		if (slot > 4) {
			inv[slot - 5] = new ItemStack(ID, 1, meta);
			return true;
		} else
			return false;
	}
	
	public ItemStack[] inv = new ItemStack[4];
}
