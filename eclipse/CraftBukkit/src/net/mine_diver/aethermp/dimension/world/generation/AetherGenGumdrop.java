package net.mine_diver.aethermp.dimension.world.generation;

import java.util.Random;

import net.mine_diver.aethermp.blocks.BlockManager;
import net.minecraft.server.Block;
import net.minecraft.server.MathHelper;
import net.minecraft.server.World;
import net.minecraft.server.WorldGenFlowers;
import net.minecraft.server.WorldGenLakes;
import net.minecraft.server.WorldGenerator;

public class AetherGenGumdrop extends WorldGenerator {
	
	@Override
    public boolean a(World world, Random random, int i, int j, int k) {
        return func_100009_a(world, random, i, j, k, 24);
    }

    public boolean func_100009_a(World world, Random random, int i, int j, int k, int l) {
        org.bukkit.World worldBukkit = world.getWorld();
        if(j - l <= 0)
            j = l + 1;
        if(j + l >= 116)
            j = 116 - l - 1;
        int i1 = 0;
        int j1 = MathHelper.floor((double)l * 0.875D);
        for(int k1 = -j1; k1 <= j1; k1++)
            for(int l1 = l; l1 >= -j1; l1--)
                for(int k2 = -j1; k2 <= j1; k2++)
                    if(!BlockManager.isGood(world.getTypeId(k1 + i, l1 + j, k2 + k), world.getData(k1 + i, l1 + j, k2 + k)) && ++i1 > l / 2)
                        return false;

        float f = 0.8F;
        for(int i2 = -l; i2 <= l; i2++)
            for(int l2 = l; l2 >= -l; l2--)
                for(int i3 = -l; i3 <= l; i3++) {
                    int k3 = MathHelper.floor(((double)i2 * (1.0D + (double)l2 / ((double)l * 10D))) / (double)f);
                    int i4 = l2;
                    if((double)l2 > (double)l * 0.625D) {
                        i4 = MathHelper.floor((double)i4 * 1.375D);
                        i4 -= MathHelper.floor((double)l * 0.25D);
                    } else if((double)l2 < (double)l * -0.625D) {
                        i4 = MathHelper.floor((double)i4 * 1.3500000238418579D);
                        i4 += MathHelper.floor((double)l * 0.25D);
                    }
                    int k4 = MathHelper.floor(((double)i3 * (1.0D + (double)l2 / ((double)l * 10D))) / (double)f);
                    if(Math.sqrt(k3 * k3 + i4 * i4 + k4 * k4) > (double)l)
                        continue;
                    if(BlockManager.isGood(world.getTypeId(i2 + i, l2 + j + 1, i3 + k), world.getData(i2 + i, l2 + j + 1, i3 + k)) && (double)l2 > (double)MathHelper.floor((double)l / 5D)) {
                        org.bukkit.block.Block blockGrass = worldBukkit.getBlockAt(i2 + i, l2 + j, i3 + k);
                        org.bukkit.block.Block blockDirt = worldBukkit.getBlockAt(i2 + i, (l2 + j) - 1, i3 + k);
                        org.bukkit.block.Block blockRandomDirt = worldBukkit.getBlockAt(i2 + i, (l2 + j) - (1 + random.nextInt(2)), i3 + k);
                        blockGrass.setTypeId(BlockManager.Grass.id, false);
                        blockDirt.setTypeId(BlockManager.Dirt.id, false);
                        blockRandomDirt.setTypeId(BlockManager.Dirt.id, false);
                        if(l2 < l / 2)
                            continue;
                        int j5 = random.nextInt(48);
                        if(j5 < 2) {
                            (new AetherGenGoldenOak()).a(world, random, i2 + i, l2 + j + 1, i3 + k);
                            continue;
                        }
                        if(j5 == 3) {
                            if(random.nextInt(2) == 0)
                                (new WorldGenLakes(Block.STATIONARY_WATER.id)).a(world, random, (i2 + i + random.nextInt(3)) - random.nextInt(3), l2 + j, (i3 + k + random.nextInt(3)) - random.nextInt(3));
                            continue;
                        }
                        if(j5 != 4)
                            continue;
                        if(random.nextInt(2) == 0)
                            (new WorldGenFlowers(Block.YELLOW_FLOWER.id)).a(world, random, (i2 + i + random.nextInt(3)) - random.nextInt(3), l2 + j + 1, (i3 + k + random.nextInt(3)) - random.nextInt(3));
                        else
                            (new WorldGenFlowers(Block.RED_ROSE.id)).a(world, random, (i2 + i + random.nextInt(3)) - random.nextInt(3), l2 + j + 1, (i3 + k + random.nextInt(3)) - random.nextInt(3));
                        continue;
                    }
                    if(BlockManager.isGood(world.getTypeId(i2 + i, l2 + j, i3 + k), world.getData(i2 + i, l2 + j, i3 + k))) {
                        org.bukkit.block.Block blockFiller = worldBukkit.getBlockAt(i2 + i, l2 + j, i3 + k);
                        blockFiller.setTypeIdAndData(BlockManager.Holystone.id, (byte)0, false);
                    }
                }

        int j2 = 8 + random.nextInt(5);
        float f1 = 0.01745329F;
        for(int j3 = 0; j3 < j2; j3++) {
            float f2 = random.nextFloat() * 360F;
            float f3 = (random.nextFloat() * 0.125F + 0.7F) * (float)l;
            int l4 = i + MathHelper.floor(Math.cos(f1 * f2) * (double)f3);
            int k5 = j - MathHelper.floor((double)l * (double)random.nextFloat() * 0.29999999999999999D);
            int i6 = k + MathHelper.floor(-Math.sin(f1 * f2) * (double)f3);
            func_100008_b(world, random, l4, k5, i6, MathHelper.floor((double)l / 3D), true);
        }
        new AetherGenDungeon().generate(world, random, i, j, k, j1);
        int l3 = MathHelper.floor((double)l * 0.75D);
        for(int j4 = 0; j4 < l3; j4++) {
            int i5 = (i + random.nextInt(l)) - random.nextInt(l);
            int l5 = (j + random.nextInt(l)) - random.nextInt(l);
            int j6 = (k + random.nextInt(l)) - random.nextInt(l);
            (new AetherGenGumdropCaves(0, 24 + l3 / 3)).a(world, random, i5, l5, j6);
        }

        return true;
    }

    public boolean func_100008_b(World world, Random random, int i, int j, int k, int l, boolean flag) {
        org.bukkit.World worldBukkit = world.getWorld();
        if(j - l <= 0)
            j = l + 1;
        if(j + l >= 127)
            j = 127 - l - 1;
        float f = 1.0F;
        for(int i1 = -l; i1 <= l; i1++)
            for(int k1 = l; k1 >= -l; k1--)
                for(int i2 = -l; i2 <= l; i2++) {
                    int k2 = MathHelper.floor((double)i1 / (double)f);
                    int i3 = k1;
                    if ((double)k1 > (double)l * 0.625D) {
                        i3 = MathHelper.floor((double)i3 * 1.375D);
                        i3 -= MathHelper.floor((double)l * 0.25D);
                    } else if ((double)k1 < (double)l * -0.625D) {
                        i3 = MathHelper.floor((double)i3 * 1.3500000238418579D);
                        i3 += MathHelper.floor((double)l * 0.25D);
                    }
                    int k3 = MathHelper.floor((double)i2 / (double)f);
                    if (Math.sqrt(k2 * k2 + i3 * i3 + k3 * k3) > (double)l)
                        continue;
                    if (BlockManager.isGood(world.getTypeId(i1 + i, k1 + j + 1, i2 + k), world.getData(i1 + i, k1 + j + 1, i2 + k)) && (double)k1 > (double)MathHelper.floor((double)l / 5D)) {
                        org.bukkit.block.Block blockGrass = worldBukkit.getBlockAt(i1 + i, k1 + j, i2 + k);
                        org.bukkit.block.Block blockDirt = worldBukkit.getBlockAt(i1 + i, (k1 + j) - 1, i2 + k);
                        org.bukkit.block.Block blockRandomDirt = worldBukkit.getBlockAt(i1 + i, (k1 + j) - (1 + random.nextInt(2)), i2 + k);
                        blockGrass.setTypeId(BlockManager.Grass.id, false);
                        blockDirt.setTypeId(BlockManager.Dirt.id, false);
                        blockRandomDirt.setTypeId(BlockManager.Dirt.id, false);
                        if(k1 < l / 2)
                            continue;
                        int l3 = random.nextInt(64);
                        if(l3 == 0) {
                            (new AetherGenGoldenOak()).a(world, random, i1 + i, k1 + j + 1, i2 + k);
                            continue;
                        }
                        if(l3 == 5 && random.nextInt(3) == 0)
                            (new WorldGenLakes(Block.STATIONARY_WATER.id)).a(world, random, (i1 + i + random.nextInt(3)) - random.nextInt(3), k1 + j, (i2 + k + random.nextInt(3)) - random.nextInt(3));
                        continue;
                    }
                    if(BlockManager.isGood(world.getTypeId(i1 + i, k1 + j, i2 + k), world.getData(i1 + i, k1 + j, i2 + k))) {
                        org.bukkit.block.Block blockFiller = worldBukkit.getBlockAt(i1 + i, k1 + j, i2 + k);
                        blockFiller.setTypeIdAndData(BlockManager.Holystone.id, (byte)0, false);
                    }
                }
        
        if(!flag) {
            int j1 = MathHelper.floor((double)l * 1.25D);
            for(int l1 = 0; l1 < j1; l1++) {
                int j2 = (i + random.nextInt(l)) - random.nextInt(l);
                int l2 = (j + random.nextInt(l)) - random.nextInt(l);
                int j3 = (k + random.nextInt(l)) - random.nextInt(l);
                (new AetherGenGumdropCaves(0, 16 + j1 / 3)).a(world, random, j2, l2, j3);
            }

        }
        return true;
    }
}
