package net.mine_diver.aethermp.dimension.world.generation;

import java.util.Random;

import net.minecraft.server.World;
import net.minecraft.server.WorldGenerator;

public class AetherGenQuicksoil extends WorldGenerator {

    public AetherGenQuicksoil(int i) {
        minableBlockId = i;
    }
    
    @Override
    public boolean a(World world, Random random, int i, int j, int k) {
        org.bukkit.World worldBukkit = world.getWorld();
        for(int l = i - 3; l < i + 4; l++)
            for(int i1 = k - 3; i1 < k + 4; i1++)
                if(world.getTypeId(l, j, i1) == 0 && (l - i) * (l - i) + (i1 - k) * (i1 - k) < 12)
                    worldBukkit.getBlockAt(l, j, i1).setTypeId(minableBlockId, false);
        return true;
    }

    private int minableBlockId;
}
