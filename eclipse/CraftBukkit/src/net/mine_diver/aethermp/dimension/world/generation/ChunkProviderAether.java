package net.mine_diver.aethermp.dimension.world.generation;

import java.util.Random;

import net.mine_diver.aethermp.blocks.BlockManager;
import net.minecraft.server.BiomeBase;
import net.minecraft.server.Block;
import net.minecraft.server.BlockSand;
import net.minecraft.server.Chunk;
import net.minecraft.server.IChunkProvider;
import net.minecraft.server.IProgressUpdate;
import net.minecraft.server.MapGenBase;
import net.minecraft.server.MapGenCaves;
import net.minecraft.server.NoiseGeneratorOctaves;
import net.minecraft.server.World;
import net.minecraft.server.WorldGenLakes;
import net.minecraft.server.WorldGenerator;

public class ChunkProviderAether implements IChunkProvider {

    public ChunkProviderAether(World world, long l) {
        field_28079_r = new double[256];
        field_28078_s = new double[256];
        field_28077_t = new double[256];
        mapGenCaves = new MapGenCaves();
        field_28088_i = new int[32][32];
        worldObj = world;
        random = new Random(l);
        noiseGenerator1 = new NoiseGeneratorOctaves(random, 16);
        noiseGenerator2 = new NoiseGeneratorOctaves(random, 16);
        noiseGenerator3 = new NoiseGeneratorOctaves(random, 8);
        noiseGenerator4 = new NoiseGeneratorOctaves(random, 4);
        noiseGenerator5 = new NoiseGeneratorOctaves(random, 4);
        noiseGenerator6 = new NoiseGeneratorOctaves(random, 10);
        noiseGenerator7 = new NoiseGeneratorOctaves(random, 16);
        noiseGenerator8 = new NoiseGeneratorOctaves(random, 8);
    }
    
    public void func_28071_a(int i, int j, byte abyte0[], BiomeBase abiomebase[], double ad[]) {
        byte byte0 = 2;
        int k = byte0 + 1;
        byte byte1 = 33;
        int l = byte0 + 1;
        field_28080_q = func_28073_a(field_28080_q, i * byte0, 0, j * byte0, k, byte1, l);
        for(int i1 = 0; i1 < byte0; i1++)
            for(int j1 = 0; j1 < byte0; j1++)
                for(int k1 = 0; k1 < 32; k1++) {
                    double d = 0.25D;
                    double d1 = field_28080_q[((i1 + 0) * l + (j1 + 0)) * byte1 + (k1 + 0)];
                    double d2 = field_28080_q[((i1 + 0) * l + (j1 + 1)) * byte1 + (k1 + 0)];
                    double d3 = field_28080_q[((i1 + 1) * l + (j1 + 0)) * byte1 + (k1 + 0)];
                    double d4 = field_28080_q[((i1 + 1) * l + (j1 + 1)) * byte1 + (k1 + 0)];
                    double d5 = (field_28080_q[((i1 + 0) * l + (j1 + 0)) * byte1 + (k1 + 1)] - d1) * d;
                    double d6 = (field_28080_q[((i1 + 0) * l + (j1 + 1)) * byte1 + (k1 + 1)] - d2) * d;
                    double d7 = (field_28080_q[((i1 + 1) * l + (j1 + 0)) * byte1 + (k1 + 1)] - d3) * d;
                    double d8 = (field_28080_q[((i1 + 1) * l + (j1 + 1)) * byte1 + (k1 + 1)] - d4) * d;
                    for(int l1 = 0; l1 < 4; l1++) {
                        double d9 = 0.125D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;
                        for(int i2 = 0; i2 < 8; i2++) {
                            int j2 = i2 + i1 * 8 << 11 | 0 + j1 * 8 << 7 | k1 * 4 + l1;
                            char c = '\200';
                            double d14 = 0.125D;
                            double d15 = d10;
                            double d16 = (d11 - d10) * d14;
                            for(int k2 = 0; k2 < 8; k2++) {
                                int l2 = 0;
                                if(d15 > 0.0D)
                                    l2 = BlockManager.Holystone.id;
                                abyte0[j2] = (byte) l2;
                                j2 += c;
                                d15 += d16;
                            }

                            d10 += d12;
                            d11 += d13;
                        }

                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }
                }
    }

    public void replaceBlocksForBiome(int i, int j, byte abyte0[], BiomeBase abiomebase[]) {
        double d = 0.03125D;
        field_28079_r = noiseGenerator4.a(field_28079_r, i * 16, j * 16, 0.0D, 16, 16, 1, d, d, 1.0D);
        field_28078_s = noiseGenerator4.a(field_28078_s, i * 16, 109.0134D, j * 16, 16, 1, 16, d, 1.0D, d);
        field_28077_t = noiseGenerator5.a(field_28077_t, i * 16, j * 16, 0.0D, 16, 16, 1, d * 2D, d * 2D, d * 2D);
        for(int k = 0; k < 16; k++)
            for(int l = 0; l < 16; l++) {
                int i1 = (int)(field_28077_t[k + l * 16] / 3D + 3D + random.nextDouble() * 0.25D);
                int j1 = -1;
                topAetherBlock = (byte)BlockManager.Grass.id;
                fillerAetherBlock = (byte)BlockManager.Dirt.id;
                byte byte0 = topAetherBlock;
                byte byte1 = fillerAetherBlock;
                byte byte2 = (byte)BlockManager.Holystone.id;
                if(byte0 < 0)
                    byte0 += 0;
                if(byte1 < 0)
                    byte1 += 0;
                if(byte2 < 0)
                    byte2 += 0;
                for(int k1 = 127; k1 >= 0; k1--) {
                    int l1 = (l * 16 + k) * 128 + k1;
                    byte byte3 = abyte0[l1];
                    if(byte3 == 0) {
                        j1 = -1;
                        continue;
                    }
                    if(byte3 != byte2)
                        continue;
                    if(j1 == -1) {
                        if(i1 <= 0) {
                            byte0 = 0;
                            byte1 = byte2;
                        }
                        j1 = i1;
                        if(k1 >= 0)
                            abyte0[l1] = byte0;
                        else
                            abyte0[l1] = byte1;
                        continue;
                    }
                    if(j1 > 0) {
                        j1--;
                        abyte0[l1] = byte1;
                    }
                }
            }

    }
    
    @Override
    public Chunk getChunkAt(int i, int j) {
        return getOrCreateChunk(i, j);
    }
    
    @Override
    public Chunk getOrCreateChunk(int i, int j) {
        random.setSeed((long)i * 0x4f9939f508L + (long)j * 0x1ef1565bd5L);
        byte abyte0[] = new byte[32768];
        Chunk chunk = new Chunk(worldObj, abyte0, i, j);
        field_28075_v = worldObj.getWorldChunkManager().a(field_28075_v, i * 16, j * 16, 16, 16);
        double ad[] = worldObj.getWorldChunkManager().temperature;
        func_28071_a(i, j, abyte0, field_28075_v, ad);
        replaceBlocksForBiome(i, j, abyte0, field_28075_v);
        mapGenCaves.a(this, worldObj, i, j, abyte0);
        chunk.initLighting();
        return chunk;
    }

    private double[] func_28073_a(double ad[], int i, int j, int k, int l, int i1, int j1) {
        if(ad == null)
            ad = new double[l * i1 * j1];
        double d = 684.41200000000003D;
        double d1 = 684.41200000000003D;
        double ad1[] = worldObj.getWorldChunkManager().temperature;
        double ad2[] = worldObj.getWorldChunkManager().rain;
        field_28090_g = noiseGenerator6.a(field_28090_g, i, k, l, j1, 1.121D, 1.121D, 0.5D);
        field_28089_h = noiseGenerator7.a(field_28089_h, i, k, l, j1, 200D, 200D, 0.5D);
        d *= 2D;
        field_28093_d = noiseGenerator3.a(field_28093_d, i, j, k, l, i1, j1, d / 80D, d1 / 160D, d / 80D);
        field_28092_e = noiseGenerator1.a(field_28092_e, i, j, k, l, i1, j1, d, d1, d);
        field_28091_f = noiseGenerator2.a(field_28091_f, i, j, k, l, i1, j1, d, d1, d);
        int k1 = 0;
        int l1 = 0;
        int i2 = 16 / l;
        for(int j2 = 0; j2 < l; j2++) {
            int k2 = j2 * i2 + i2 / 2;
            for(int l2 = 0; l2 < j1; l2++) {
                int i3 = l2 * i2 + i2 / 2;
                double d2 = ad1[k2 * 16 + i3];
                double d3 = ad2[k2 * 16 + i3] * d2;
                double d4 = 1D - d3;
                d4 *= d4;
                d4 *= d4;
                d4 = 1D - d4;
                double d5 = (field_28090_g[l1] + 256D) / 512D;
                d5 *= d4;
                if(d5 > 1D)
                    d5 = 1D;
                double d6 = field_28089_h[l1] / 8000D;
                if(d6 < 0.0D)
                    d6 = -d6 * 0.29999999999999999D;
                d6 = d6 * 3D - 2D;
                if(d6 > 1D)
                    d6 = 1D;
                d6 /= 8D;
                d6 = 0D;
                if(d5 < 0D)
                    d5 = 0D;
                d5 += 0.5D;
                d6 = (d6 * (double) i1) / 16D;
                l1++;
                double d7 = (double) i1 / 2D;
                for(int j3 = 0; j3 < i1; j3++) {
                    double d8 = 0D;
                    double d9 = (((double)j3 - d7) * 8D) / d5;
                    if(d9 < 0D)
                        d9 *= -1D;
                    double d10 = field_28092_e[k1] / 512D;
                    double d11 = field_28091_f[k1] / 512D;
                    double d12 = (field_28093_d[k1] / 10D + 1D) / 2D;
                    if(d12 < 0.0D)
                        d8 = d10;
                    else if(d12 > 1.0D)
                        d8 = d11;
                    else
                        d8 = d10 + (d11 - d10) * d12;
                    d8 -= 8D;
                    int k3 = 32;
                    if(j3 > i1 - k3) {
                        double d13 = (float)(j3 - (i1 - k3)) / ((float)k3 - 1.0F);
                        d8 = d8 * (1D - d13) + -30D * d13;
                    }
                    k3 = 8;
                    if(j3 < k3) {
                        double d14 = (float)(k3 - j3) / ((float)k3 - 1.0F);
                        d8 = d8 * (1D - d14) + -30D * d14;
                    }
                    ad[k1] = d8;
                    k1++;
                }
            }
        }

        return ad;
    }
    
    @Override
    public boolean isChunkLoaded(int i, int j) {
        return true;
    }
    
    @Override
    public void getChunkAt(IChunkProvider ichunkprovider, int i, int j) {
        BlockSand.instaFall = true;
        int k = i * 16;
        int l = j * 16;
        BiomeBase biomebase = worldObj.getWorldChunkManager().getBiome(k + 16, l + 16);
        random.setSeed(worldObj.getSeed());
        long l1 = (random.nextLong() / 2L) * 2L + 1L;
        long l2 = (random.nextLong() / 2L) * 2L + 1L;
        random.setSeed((long)i * l1 + (long)j * l2 ^ worldObj.getSeed());
        double d = 0.25D;
        if(gumCount < 800)
            gumCount++;
        else if(random.nextInt(32) == 0) {
            boolean flag = false;
            int k5 = k + random.nextInt(16) + 8;
            int l9 = random.nextInt(64) + 32;
            int j14 = l + random.nextInt(16) + 8;
            flag = (new AetherGenGumdrop()).a(worldObj, random, k5, l9, j14);
            if(flag)
                gumCount = 0;
        }
        
        if(random.nextInt(3) == 0) {
            int i1 = k + random.nextInt(16) + 8;
            int l5 = random.nextInt(128);
            int i10 = l + random.nextInt(16) + 8;
            new WorldGenLakes(Block.STATIONARY_WATER.id).a(worldObj, random, i1, l5, i10);
        }
        
        for(int j1 = 0; j1 < 20; j1++) {
            int i6 = k + random.nextInt(16);
            int j10 = random.nextInt(128);
            int k14 = l + random.nextInt(16);
            new AetherGenMinable(BlockManager.Dirt.id, 32).a(worldObj, random, i6, j10, k14);
        }

        for(int k1 = 0; k1 < 2; k1++) {
            int j6 = k + random.nextInt(16) + 8;
            int k10 = random.nextInt(128);
            int l14 = l + random.nextInt(16) + 8;
            new AetherGenFlowers(BlockManager.WhiteFlower.id).a(worldObj, random, j6, k10, l14);
        }

        for(int i2 = 0; i2 < 2; i2++)
            if(random.nextInt(2) == 0) {
                int k6 = k + random.nextInt(16) + 8;
                int l10 = random.nextInt(128);
                int i15 = l + random.nextInt(16) + 8;
                new AetherGenFlowers(BlockManager.PurpleFlower.id).a(worldObj, random, k6, l10, i15);
            }

        for(int j2 = 0; j2 < 10; j2++) {
            int l6 = k + random.nextInt(16);
            int i11 = random.nextInt(128);
            int j15 = l + random.nextInt(16);
            new AetherGenMinable(BlockManager.Icestone.id, 32).a(worldObj, random, l6, i11, j15);
        }

        for(int k2 = 0; k2 < 20; k2++) {
            int i7 = k + random.nextInt(16);
            int j11 = random.nextInt(128);
            int k15 = l + random.nextInt(16);
            new AetherGenMinable(BlockManager.AmbrosiumOre.id, 16).a(worldObj, random, i7, j11, k15);
        }

        for(int i3 = 0; i3 < 15; i3++) {
            int j7 = k + random.nextInt(16);
            int k11 = random.nextInt(64);
            int l15 = l + random.nextInt(16);
            new AetherGenMinable(BlockManager.ZaniteOre.id, 8).a(worldObj, random, j7, k11, l15);
        }

        for(int j3 = 0; j3 < 8; j3++) {
            int k7 = k + random.nextInt(16);
            int l11 = random.nextInt(32);
            int i16 = l + random.nextInt(16);
            new AetherGenMinable(BlockManager.GravititeOre.id, 7).a(worldObj, random, k7, l11, i16);
        }

        if(random.nextInt(50) == 0) {
            int k3 = k + random.nextInt(16);
            int l7 = random.nextInt(32) + 96;
            int i12 = l + random.nextInt(16);
            new AetherGenClouds(BlockManager.Aercloud.id, 2, 4, false).a(worldObj, random, k3, l7, i12);
        }
        
        if(random.nextInt(13) == 0) {
            int l3 = k + random.nextInt(16);
            int i8 = random.nextInt(64) + 32;
            int j12 = l + random.nextInt(16);
            new AetherGenClouds(BlockManager.Aercloud.id, 1, 8, false).a(worldObj, random, l3, i8, j12);
        }
        
        if(random.nextInt(7) == 0) {
            int i4 = k + random.nextInt(16);
            int j8 = random.nextInt(64) + 32;
            int k12 = l + random.nextInt(16);
            new AetherGenClouds(BlockManager.Aercloud.id, 0, 16, false).a(worldObj, random, i4, j8, k12);
        }
        
        if(random.nextInt(50) == 0) {
            int j4 = k + random.nextInt(16);
            int k8 = random.nextInt(32);
            int l12 = l + random.nextInt(16);
            new AetherGenClouds(BlockManager.Aercloud.id, 0, 64, true).a(worldObj, random, j4, k8, l12);
        }
        
        for(int k4 = 0; k4 < 2; k4++) {
            int l8 = k + random.nextInt(16);
            int i13 = 32 + random.nextInt(64);
            int j16 = l + random.nextInt(16);
            new AetherGenDungeonBronze(BlockManager.LockedDungeonStone.id, BlockManager.LockedLightDungeonStone.id, BlockManager.DungeonStone.id, BlockManager.LightDungeonStone.id, BlockManager.Holystone.id, 2, BlockManager.Holystone.id, 0, 16).a(worldObj, random, l8, i13, j16);
        }
        
        if(random.nextInt(500) == 0) {
            int l4 = k + random.nextInt(16);
            int i9 = random.nextInt(32) + 64;
            int j13 = l + random.nextInt(16);
            new AetherGenDungeonSilver(BlockManager.LockedDungeonStone.id, BlockManager.LockedLightDungeonStone.id, BlockManager.DungeonStone.id, BlockManager.LightDungeonStone.id, BlockManager.Holystone.id, 2, BlockManager.Holystone.id, 0, BlockManager.Pillar.id).a(worldObj, random, l4, i9, j13);
        }
        
        if(random.nextInt(5) == 0)
            for(int i5 = k; i5 < k + 16; i5++)
                for(int j9 = l; j9 < l + 16; j9++)
                    for(int k13 = 0; k13 < 48; k13++)
                        if(worldObj.getTypeId(i5, k13, j9) == 0 && worldObj.getTypeId(i5, k13 + 1, j9) == BlockManager.Grass.id && worldObj.getTypeId(i5, k13 + 2, j9) == 0) {
                            new AetherGenQuicksoil(BlockManager.Quicksoil.id).a(worldObj, random, i5, k13, j9);
                            k13 = 128;
                        }
        
        d = 0.5;
        int j5 = (int)((noiseGenerator8.a((double) k * d, (double) l * d) / 8D + random.nextDouble() * 4 + 4) / 3);
        int k9 = 0;
        if(random.nextInt(10) == 0)
            k9++;
        if(biomebase == BiomeBase.FOREST)
            k9 += j5 + 5;
        if(biomebase == BiomeBase.RAINFOREST)
            k9 += j5 + 5;
        if(biomebase == BiomeBase.SEASONAL_FOREST)
            k9 += j5 + 2;
        if(biomebase == BiomeBase.TAIGA)
            k9 += j5 + 5;
        if(biomebase == BiomeBase.DESERT)
            k9 -= 20;
        if(biomebase == BiomeBase.TUNDRA)
            k9 -= 20;
        if(biomebase == BiomeBase.PLAINS)
            k9 -= 20;
        k9 += j5;
        
        for(int l13 = 0; l13 < k9; l13++) {
            int k16 = k + random.nextInt(16) + 8;
            int i17 = l + random.nextInt(16) + 8;
            WorldGenerator worldgenerator = biomebase.a(random);
            worldgenerator.a(1.0D, 1.0D, 1.0D);
            worldgenerator.a(worldObj, random, k16, worldObj.getHighestBlockYAt(k16, i17), i17);
        }

        for(int i14 = 0; i14 < 50; i14++) {
            int l16 = k + random.nextInt(16) + 8;
            int j17 = random.nextInt(random.nextInt(120) + 8);
            int k17 = l + random.nextInt(16) + 8;
            (new AetherGenLiquids(Block.WATER.id)).a(worldObj, random, l16, j17, k17);
        }

        BlockSand.instaFall = false;
    }
    
    @Override
    public boolean saveChunks(boolean flag, IProgressUpdate iprogressupdate) {
        return true;
    }
    
    @Override
    public boolean unloadChunks() {
        return false;
    }
    
    @Override
    public boolean canSave() {
        return true;
    }

    public static int gumCount;
    private Random random;
    private NoiseGeneratorOctaves noiseGenerator1;
    private NoiseGeneratorOctaves noiseGenerator2;
    private NoiseGeneratorOctaves noiseGenerator3;
    private NoiseGeneratorOctaves noiseGenerator4;
    private NoiseGeneratorOctaves noiseGenerator5;
    public NoiseGeneratorOctaves noiseGenerator6;
    public NoiseGeneratorOctaves noiseGenerator7;
    public NoiseGeneratorOctaves noiseGenerator8;
    private World worldObj;
    private double field_28080_q[];
    private double field_28079_r[];
    private double field_28078_s[];
    private double field_28077_t[];
    private MapGenBase mapGenCaves;
    private BiomeBase field_28075_v[];
    double field_28093_d[];
    double field_28092_e[];
    double field_28091_f[];
    double field_28090_g[];
    double field_28089_h[];
    int field_28088_i[][];
    public byte topAetherBlock;
    public byte fillerAetherBlock;
}
