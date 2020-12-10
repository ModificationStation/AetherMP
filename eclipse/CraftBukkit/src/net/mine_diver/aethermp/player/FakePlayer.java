package net.mine_diver.aethermp.player;

import net.mine_diver.aethermp.entities.FakeEntityPlayer;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.ItemInWorldManager;
import net.minecraft.server.ModLoader;
import net.minecraft.server.World;
import net.minecraft.server.WorldServer;

public class FakePlayer {

	private FakePlayer() {
		
	}
	
	public static EntityPlayer get(World world) {
		if (fakePlayer == null)
			fakePlayer = new FakeEntityPlayer(ModLoader.getMinecraftServerInstance(), world, "[AetherMP]", new ItemInWorldManager((WorldServer) world));
		if (fakePlayer.world != world) {
			fakePlayer.world = world;
			fakePlayer.itemInWorldManager = new ItemInWorldManager((WorldServer) world);
			fakePlayer.itemInWorldManager.player = fakePlayer;
		}
		return fakePlayer;
	}
	
	private static EntityPlayer fakePlayer;
}
