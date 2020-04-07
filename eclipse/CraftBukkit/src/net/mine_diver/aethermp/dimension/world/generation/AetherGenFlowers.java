package net.mine_diver.aethermp.dimension.world.generation;

import java.util.Random;

import net.mine_diver.aethermp.blocks.BlockAetherFlower;
import net.minecraft.server.Block;
import net.minecraft.server.World;
import net.minecraft.server.WorldGenerator;
public class AetherGenFlowers extends WorldGenerator {

    public AetherGenFlowers(int i) {
        plantBlockId = i;
    }
    
    @Override
    public boolean a(World world, Random random, int i, int j, int k) {
        org.bukkit.World worldBukkit = world.getWorld();
        for(int l = 0; l < 64; l++) {
            int i1 = (i + random.nextInt(8)) - random.nextInt(8);
            int j1 = (j + random.nextInt(4)) - random.nextInt(4);
            int k1 = (k + random.nextInt(8)) - random.nextInt(8);
            if(world.isEmpty(i1, j1, k1) && ((BlockAetherFlower) Block.byId[plantBlockId]).f(world, i1, j1, k1))
                worldBukkit.getBlockAt(i1, j1, k1).setTypeId(plantBlockId, false);
        }

        return true;
    }

    private int plantBlockId;
}
