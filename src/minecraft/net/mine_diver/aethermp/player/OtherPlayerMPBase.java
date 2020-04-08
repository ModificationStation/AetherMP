package net.mine_diver.aethermp.player;

import net.minecraft.src.EntityOtherPlayerMP;

public class OtherPlayerMPBase {
	
	public OtherPlayerMPBase(EntityOtherPlayerMP entityotherplayermp) {
		player = entityotherplayermp;
	}
	
	public boolean outfitWithItem(int slot, int ID, int meta) {
		return false;
	}
	
	public EntityOtherPlayerMP player;
}
