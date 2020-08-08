package net.mine_diver.aetherapi.proxy;

import java.util.Random;

import net.minecraft.src.AetherBlocks;
import net.minecraft.src.AetherGenGoldenOak;
import net.minecraft.src.AetherGenGumdrop;
import net.minecraft.src.AetherGenGumdropCaves;
import net.minecraft.src.Block;
import net.minecraft.src.MathHelper;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenFlowers;
import net.minecraft.src.WorldGenLakes;

public class AetherGenGumdropProxy extends AetherGenGumdrop {
	
	public boolean func_100009_a(World world, Random random, int i, int j, int k, int l) {
        if(j - l <= 0)
            j = l + 1;
        if(j + l >= 116)
            j = 116 - l - 1;
        int i1 = 0;
        int j1 = MathHelper.floor_double((double)l * 0.875D);
        for(int k1 = -j1; k1 <= j1; k1++)
            for(int l1 = l; l1 >= -j1; l1--)
                for(int k2 = -j1; k2 <= j1; k2++)
                    if(!AetherBlocks.isGood(world.getBlockId(k1 + i, l1 + j, k2 + k), world.getBlockMetadata(k1 + i, l1 + j, k2 + k)) && ++i1 > l / 2)
                        return false;
        float f = 0.8F;
        for(int i2 = -l; i2 <= l; i2++)
            for(int l2 = l; l2 >= -l; l2--)
                for(int i3 = -l; i3 <= l; i3++) {
                    int k3 = MathHelper.floor_double(((double)i2 * (1.0D + (double)l2 / ((double)l * 10D))) / (double)f);
                    int i4 = l2;
                    if((double)l2 > (double)l * 0.625D) {
                        i4 = MathHelper.floor_double((double)i4 * 1.375D);
                        i4 -= MathHelper.floor_double((double)l * 0.25D);
                    } else if((double)l2 < (double)l * -0.625D) {
                        i4 = MathHelper.floor_double((double)i4 * 1.3500000238418579D);
                        i4 += MathHelper.floor_double((double)l * 0.25D);
                    }
                    int k4 = MathHelper.floor_double(((double)i3 * (1.0D + (double)l2 / ((double)l * 10D))) / (double)f);
                    if(Math.sqrt(k3 * k3 + i4 * i4 + k4 * k4) > (double)l)
                        continue;
                    if(AetherBlocks.isGood(world.getBlockId(i2 + i, l2 + j + 1, i3 + k), world.getBlockMetadata(i2 + i, l2 + j + 1, i3 + k)) && (double)l2 > (double)MathHelper.floor_double((double)l / 5D)) {
                        world.setBlock(i2 + i, l2 + j, i3 + k, AetherBlocks.Grass.blockID);
                        world.setBlock(i2 + i, (l2 + j) - 1, i3 + k, AetherBlocks.Dirt.blockID);
                        world.setBlock(i2 + i, (l2 + j) - (1 + random.nextInt(2)), i3 + k, AetherBlocks.Dirt.blockID);
                        if(l2 < l / 2)
                            continue;
                        int j5 = random.nextInt(48);
                        if(j5 < 2) {
                            (new AetherGenGoldenOak()).generate(world, random, i2 + i, l2 + j + 1, i3 + k);
                            continue;
                        }
                        if(j5 == 3) {
                            if(random.nextInt(2) == 0)
                                (new WorldGenLakes(Block.waterStill.blockID)).generate(world, random, (i2 + i + random.nextInt(3)) - random.nextInt(3), l2 + j, (i3 + k + random.nextInt(3)) - random.nextInt(3));
                            continue;
                        }
                        if(j5 != 4)
                            continue;
                        if(random.nextInt(2) == 0)
                            (new WorldGenFlowers(Block.plantYellow.blockID)).generate(world, random, (i2 + i + random.nextInt(3)) - random.nextInt(3), l2 + j + 1, (i3 + k + random.nextInt(3)) - random.nextInt(3));
                        else
                            (new WorldGenFlowers(Block.plantRed.blockID)).generate(world, random, (i2 + i + random.nextInt(3)) - random.nextInt(3), l2 + j + 1, (i3 + k + random.nextInt(3)) - random.nextInt(3));
                        continue;
                    }
                    if(AetherBlocks.isGood(world.getBlockId(i2 + i, l2 + j, i3 + k), world.getBlockMetadata(i2 + i, l2 + j, i3 + k)))
                        world.setBlockAndMetadata(i2 + i, l2 + j, i3 + k, AetherBlocks.Holystone.blockID, 0);
                }
        int j2 = 8 + random.nextInt(5);
        float f1 = 0.01745329F;
        for(int j3 = 0; j3 < j2; j3++) {
            float f2 = random.nextFloat() * 360F;
            float f3 = (random.nextFloat() * 0.125F + 0.7F) * (float)l;
            int l4 = i + MathHelper.floor_double(Math.cos(f1 * f2) * (double)f3);
            int k5 = j - MathHelper.floor_double((double)l * (double)random.nextFloat() * 0.29999999999999999D);
            int i6 = k + MathHelper.floor_double(-Math.sin(f1 * f2) * (double)f3);
            func_100008_b(world, random, l4, k5, i6, MathHelper.floor_double((double)l / 3D), true);
        }
        (new AetherGenDungeonProxy()).generate(world, random, i, j, k, j1);
        int l3 = MathHelper.floor_double((double)l * 0.75D);
        for(int j4 = 0; j4 < l3; j4++) {
            int i5 = (i + random.nextInt(l)) - random.nextInt(l);
            int l5 = (j + random.nextInt(l)) - random.nextInt(l);
            int j6 = (k + random.nextInt(l)) - random.nextInt(l);
            (new AetherGenGumdropCaves(0, 24 + l3 / 3)).generate(world, random, i5, l5, j6);
        }

        return true;
    }
}
