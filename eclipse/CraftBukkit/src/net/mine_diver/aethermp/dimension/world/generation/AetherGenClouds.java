package net.mine_diver.aethermp.dimension.world.generation;

import java.util.Random;

import net.minecraft.server.World;
import net.minecraft.server.WorldGenerator;

public class AetherGenClouds extends WorldGenerator {

    public AetherGenClouds(int i, int j, int k, boolean flag) {
        cloudBlockId = i;
        meta = j;
        numberOfBlocks = k;
        flat = flag;
    }
    
    @Override
    public boolean a(World world, Random random, int i, int j, int k) {
        org.bukkit.World worldBukkit = world.getWorld();
        int l = i;
        int i1 = j;
        int j1 = k;
        int k1 = random.nextInt(3) - 1;
        int l1 = random.nextInt(3) - 1;
        for(int i2 = 0; i2 < numberOfBlocks; i2++) {
            l += (random.nextInt(3) - 1) + k1;
            if(random.nextBoolean() && !flat || flat && random.nextInt(10) == 0)
                i1 += random.nextInt(3) - 1;
            j1 += (random.nextInt(3) - 1) + l1;
            for(int j2 = l; j2 < l + random.nextInt(4) + 3 * (flat ? 3 : 1); j2++)
                for(int k2 = i1; k2 < i1 + random.nextInt(1) + 2; k2++)
                    for(int l2 = j1; l2 < j1 + random.nextInt(4) + 3 * (flat ? 3 : 1); l2++)
                        if(world.getTypeId(j2, k2, l2) == 0 && Math.abs(j2 - l) + Math.abs(k2 - i1) + Math.abs(l2 - j1) < 4 * (flat ? 3 : 1) + random.nextInt(2))
                            worldBukkit.getBlockAt(j2, k2, l2).setTypeIdAndData(cloudBlockId, (byte)meta, true);

        }
        return true;
    }

    private int cloudBlockId;
    private int meta;
    private int numberOfBlocks;
    private boolean flat;
}
