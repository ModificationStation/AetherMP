package net.mine_diver.aethermp.dimension.world.generation;

import java.util.Random;

import net.mine_diver.aethermp.blocks.BlockManager;
import net.minecraft.server.World;
import net.minecraft.server.WorldGenerator;

public class AetherGenGoldenOak extends WorldGenerator {

    public boolean branch(World world, Random random, int i, int j, int k, int l) {
        org.bukkit.World worldBukkit = world.getWorld();
        int i1 = random.nextInt(3) - 1;
        int j1 = l;
        int k1 = random.nextInt(3) - 1;
        for(int l1 = 0; l1 < random.nextInt(2) + 1; l1++) {
            i += i1;
            j += j1;
            k += k1;
            if(world.getTypeId(i, j, k) == BlockManager.GoldenOakLeaves.id)
                worldBukkit.getBlockAt(i, j, k).setTypeIdAndData(BlockManager.Log.id, (byte)2 , false);
        }

        return true;
    }
    
    @Override
    public boolean a(World world, Random random, int i, int j, int k) {
        if(world.getTypeId(i, j - 1, k) != BlockManager.Grass.id && world.getTypeId(i, j - 1, k) != BlockManager.Dirt.id)
            return false;
        org.bukkit.World worldBukkit = world.getWorld();
        int l = random.nextInt(5) + 6;
        for(int i1 = i - 3; i1 < i + 4; i1++)
            for(int k1 = j + 5; k1 < j + 12; k1++)
                for(int l1 = k - 3; l1 < k + 4; l1++)
                    if((i1 - i) * (i1 - i) + (k1 - j - 8) * (k1 - j - 8) + (l1 - k) * (l1 - k) < 12 + random.nextInt(5) && world.getTypeId(i1, k1, l1) == 0)
                        worldBukkit.getBlockAt(i1, k1, l1).setTypeId(BlockManager.GoldenOakLeaves.id, false);

        for(int j1 = 0; j1 < l; j1++) {
            if(j1 > 4 && random.nextInt(3) > 0)
                branch(world, random, i, j + j1, k, j1 / 4 - 1);
            worldBukkit.getBlockAt(i, j + j1, k).setTypeIdAndData(BlockManager.Log.id, (byte)2, false);
        }

        return true;
    }
}
