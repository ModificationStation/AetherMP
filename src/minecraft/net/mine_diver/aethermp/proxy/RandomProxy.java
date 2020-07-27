package net.mine_diver.aethermp.proxy;

import java.util.Random;

import net.minecraft.src.ModLoader;

public class RandomProxy extends Random {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8363739975700685186L;
	
	public RandomProxy(long seed) {
		super(seed);
	}
	
	@Override
	public int nextInt(int bound) {
		if (ModLoader.getMinecraftInstance().theWorld.multiplayerWorld)
			return 1;
		else
			return super.nextInt(bound);
	}
}
