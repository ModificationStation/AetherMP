package net.mine_diver.aethermp.dimension.world;

import net.mine_diver.aethermp.dimension.world.generation.ChunkProviderAether;
import net.minecraft.server.Block;
import net.minecraft.server.IChunkProvider;
import net.minecraft.server.WorldProvider;
import net.minecraft.server.mod_AetherMp;

public class WorldProviderAether extends WorldProvider {
	
	@Override
    public void a() {
        b = new WorldChunkManagerAether(1.0D);
        dimension = mod_AetherMp.idDimensionAether;
    }
    
    @Override
    public IChunkProvider getChunkProvider() {
        return new ChunkProviderAether(a, a.getSeed());
    }
    
    @Override
    public float a(long l, float f) {
        //boolean flag = ModLoader.getMinecraftInstance().statFileWriter.hasAchievementUnlocked(AetherAchievements.defeatGold);
        boolean flag = false;
        if (flag) {
            int i = (int)(l % 0x13880L);
            float f1 = ((float)i + f) / 120000F - 0.25F;
            if(i > 60000) {
                i -= 40000;
                f1 = ((float)i + f) / 20000F - 0.25F;
            }
            if(f1 < 0.0F)
                f1++;
            if(f1 > 1.0F)
                f1--;
            float f2 = f1;
            f1 = 1.0F - (float)((Math.cos((double)f1 * 3.1415926535897931D) + 1.0D) / 2D);
            f1 = f2 + (f1 - f2) / 3F;
            return f1;
        } else
            return 0.0F;
    }
    
    @Override
    public boolean canSpawn(int i, int j) {
        int k = a.a(i, j);
        if(k == 0)
            return false;
        else
            return Block.byId[k].material.isSolid();
    }
    
    @Override
    public boolean d() {
        return false;
    }
}
