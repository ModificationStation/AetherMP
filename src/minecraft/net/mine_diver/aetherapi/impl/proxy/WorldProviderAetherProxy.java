package net.mine_diver.aetherapi.impl.proxy;

import net.minecraft.src.IChunkProvider;
import net.minecraft.src.WorldProviderAether;

public class WorldProviderAetherProxy extends WorldProviderAether {
	
	public IChunkProvider getChunkProvider() {
        return new ChunkProviderAetherProxy(worldObj, worldObj.getRandomSeed());
    }
}
