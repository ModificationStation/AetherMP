package net.mine_diver.aetherapi.impl.proxy;

import java.lang.reflect.Field;
import java.util.Random;

import net.mine_diver.aetherapi.api.event.dimension.world.generation.AetherPopulator;
import net.minecraft.src.AetherBlocks;
import net.minecraft.src.AetherGenClouds;
import net.minecraft.src.AetherGenFlowers;
import net.minecraft.src.AetherGenLiquids;
import net.minecraft.src.AetherGenMinable;
import net.minecraft.src.AetherGenQuicksoil;
import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.BlockSand;
import net.minecraft.src.ChunkProviderAether;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenLakes;
import net.minecraft.src.WorldGenerator;

public class ChunkProviderAetherProxy extends ChunkProviderAether {

	public ChunkProviderAetherProxy(World world, long l) {
		super(world, l);
	}
	
	public void populate(IChunkProvider ichunkprovider, int i, int j) {
		try {
			populateThrows(ichunkprovider, i, j);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void populateThrows(IChunkProvider ichunkprovider, int i, int j) throws IllegalArgumentException, IllegalAccessException {
		BlockSand.fallInstantly = true;
        int k = i * 16;
        int l = j * 16;
        BiomeGenBase biomegenbase = ((World) worldObjField.get(this)).getWorldChunkManager().getBiomeGenAt(k + 16, l + 16);
        ((Random) randomField.get(this)).setSeed(((World) worldObjField.get(this)).getRandomSeed());
        long l1 = (((Random) randomField.get(this)).nextLong() / 2L) * 2L + 1L;
        long l2 = (((Random) randomField.get(this)).nextLong() / 2L) * 2L + 1L;
        ((Random) randomField.get(this)).setSeed((long)i * l1 + (long)j * l2 ^ ((World) worldObjField.get(this)).getRandomSeed());
        double d = 0.25D;
        if (gumCount < 800)
            gumCount++;
        else if (((Random) randomField.get(this)).nextInt(32) == 0) {
            boolean flag = false;
            int x = k + ((Random) randomField.get(this)).nextInt(16) + 8;
            int y = ((Random) randomField.get(this)).nextInt(64) + 32;
            int z = l + ((Random) randomField.get(this)).nextInt(16) + 8;
            flag = (new AetherGenGumdropProxy()).generate((World) worldObjField.get(this), (Random) randomField.get(this), x, y, z);
            if (flag)
                gumCount = 0;
        }
        if (((Random) randomField.get(this)).nextInt(3) == 0) {
            int x = k + ((Random) randomField.get(this)).nextInt(16) + 8;
            int y = ((Random) randomField.get(this)).nextInt(128);
            int z = l + ((Random) randomField.get(this)).nextInt(16) + 8;
            (new WorldGenLakes(Block.waterStill.blockID)).generate((World) worldObjField.get(this), (Random) randomField.get(this), x, y, z);
        }
        for (int n = 0; n < 20; n++) {
            int x = k + ((Random) randomField.get(this)).nextInt(16);
            int y = ((Random) randomField.get(this)).nextInt(128);
            int z = l + ((Random) randomField.get(this)).nextInt(16);
            (new AetherGenMinable(AetherBlocks.Dirt.blockID, 32)).generate((World) worldObjField.get(this), (Random) randomField.get(this), x, y, z);
        }

        for (int n = 0; n < 2; n++) {
            int x = k + ((Random) randomField.get(this)).nextInt(16) + 8;
            int y = ((Random) randomField.get(this)).nextInt(128);
            int z = l + ((Random) randomField.get(this)).nextInt(16) + 8;
            (new AetherGenFlowers(AetherBlocks.WhiteFlower.blockID)).generate((World) worldObjField.get(this), (Random) randomField.get(this), x, y, z);
        }

        for (int n = 0; n < 2; n++)
            if (((Random) randomField.get(this)).nextInt(2) == 0) {
                int x = k + ((Random) randomField.get(this)).nextInt(16) + 8;
                int y = ((Random) randomField.get(this)).nextInt(128);
                int z = l + ((Random) randomField.get(this)).nextInt(16) + 8;
                (new AetherGenFlowers(AetherBlocks.PurpleFlower.blockID)).generate((World) worldObjField.get(this), (Random) randomField.get(this), x, y, z);
            }

        for (int n = 0; n < 10; n++) {
            int x = k + ((Random) randomField.get(this)).nextInt(16);
            int y = ((Random) randomField.get(this)).nextInt(128);
            int z = l + ((Random) randomField.get(this)).nextInt(16);
            (new AetherGenMinable(AetherBlocks.Icestone.blockID, 32)).generate((World) worldObjField.get(this), (Random) randomField.get(this), x, y, z);
        }

        for (int n = 0; n < 20; n++) {
            int x = k + ((Random) randomField.get(this)).nextInt(16);
            int y = ((Random) randomField.get(this)).nextInt(128);
            int z = l + ((Random) randomField.get(this)).nextInt(16);
            (new AetherGenMinable(AetherBlocks.AmbrosiumOre.blockID, 16)).generate((World) worldObjField.get(this), (Random) randomField.get(this), x, y, z);
        }

        for (int n = 0; n < 15; n++) {
            int x = k + ((Random) randomField.get(this)).nextInt(16);
            int y = ((Random) randomField.get(this)).nextInt(64);
            int z = l + ((Random) randomField.get(this)).nextInt(16);
            (new AetherGenMinable(AetherBlocks.ZaniteOre.blockID, 8)).generate((World) worldObjField.get(this), (Random) randomField.get(this), x, y, z);
        }

        for (int n = 0; n < 8; n++) {
            int x = k + ((Random) randomField.get(this)).nextInt(16);
            int y = ((Random) randomField.get(this)).nextInt(32);
            int z = l + ((Random) randomField.get(this)).nextInt(16);
            (new AetherGenMinable(AetherBlocks.GravititeOre.blockID, 7)).generate((World) worldObjField.get(this), (Random) randomField.get(this), x, y, z);
        }

        if (((Random) randomField.get(this)).nextInt(50) == 0) {
            int x = k + ((Random) randomField.get(this)).nextInt(16);
            int y = ((Random) randomField.get(this)).nextInt(32) + 96;
            int z = l + ((Random) randomField.get(this)).nextInt(16);
            (new AetherGenClouds(AetherBlocks.Aercloud.blockID, 2, 4, false)).generate((World) worldObjField.get(this), (Random) randomField.get(this), x, y, z);
        }
        if (((Random) randomField.get(this)).nextInt(13) == 0) {
            int x = k + ((Random) randomField.get(this)).nextInt(16);
            int y = ((Random) randomField.get(this)).nextInt(64) + 32;
            int z = l + ((Random) randomField.get(this)).nextInt(16);
            (new AetherGenClouds(AetherBlocks.Aercloud.blockID, 1, 8, false)).generate((World) worldObjField.get(this), (Random) randomField.get(this), x, y, z);
        }
        if (((Random) randomField.get(this)).nextInt(7) == 0) {
            int x = k + ((Random) randomField.get(this)).nextInt(16);
            int y = ((Random) randomField.get(this)).nextInt(64) + 32;
            int z = l + ((Random) randomField.get(this)).nextInt(16);
            (new AetherGenClouds(AetherBlocks.Aercloud.blockID, 0, 16, false)).generate((World) worldObjField.get(this), (Random) randomField.get(this), x, y, z);
        }
        if (((Random) randomField.get(this)).nextInt(50) == 0) {
            int x = k + ((Random) randomField.get(this)).nextInt(16);
            int y = ((Random) randomField.get(this)).nextInt(32);
            int z = l + ((Random) randomField.get(this)).nextInt(16);
            (new AetherGenClouds(AetherBlocks.Aercloud.blockID, 0, 64, true)).generate((World) worldObjField.get(this), (Random) randomField.get(this), x, y, z);
        }
        for (int qq = 0; qq < 2; qq++) {
            int x = k + ((Random) randomField.get(this)).nextInt(16);
            int y = 32 + ((Random) randomField.get(this)).nextInt(64);
            int z = l + ((Random) randomField.get(this)).nextInt(16);
            (new AetherGenDungeonBronzeProxy(AetherBlocks.LockedDungeonStone.blockID, AetherBlocks.LockedLightDungeonStone.blockID, AetherBlocks.DungeonStone.blockID, AetherBlocks.LightDungeonStone.blockID, AetherBlocks.Holystone.blockID, 2, AetherBlocks.Holystone.blockID, 0, 16, true)).generate((World) worldObjField.get(this), (Random) randomField.get(this), x, y, z);
        }

        if (((Random) randomField.get(this)).nextInt(500) == 0) {
            int x = k + ((Random) randomField.get(this)).nextInt(16);
            int y = ((Random) randomField.get(this)).nextInt(32) + 64;
            int z = l + ((Random) randomField.get(this)).nextInt(16);
            (new AetherGenDungeonSilverProxy(AetherBlocks.LockedDungeonStone.blockID, AetherBlocks.LockedLightDungeonStone.blockID, AetherBlocks.DungeonStone.blockID, AetherBlocks.LightDungeonStone.blockID, AetherBlocks.Holystone.blockID, 2, AetherBlocks.Holystone.blockID, 0, AetherBlocks.Pillar.blockID)).generate((World) worldObjField.get(this), (Random) randomField.get(this), x, y, z);
        }
        if (((Random) randomField.get(this)).nextInt(5) == 0)
            for (int x = k; x < k + 16; x++)
                for (int z = l; z < l + 16; z++)
                    for (int n = 0; n < 48; n++)
                        if (((World) worldObjField.get(this)).getBlockId(x, n, z) == 0 && ((World) worldObjField.get(this)).getBlockId(x, n + 1, z) == AetherBlocks.Grass.blockID && ((World) worldObjField.get(this)).getBlockId(x, n + 2, z) == 0) {
                            (new AetherGenQuicksoil(AetherBlocks.Quicksoil.blockID)).generate((World) worldObjField.get(this), (Random) randomField.get(this), x, n, z);
                            n = 128;
                        }
        d = 0.5D;
        int k4 = (int)((noiseGenerator8.func_806_a((double)k * d, (double)l * d) / 8D + ((Random) randomField.get(this)).nextDouble() * 4D + 4D) / 3D);
        int l7 = 0;
        if (((Random) randomField.get(this)).nextInt(10) == 0)
            l7++;
        if (biomegenbase == BiomeGenBase.forest)
            l7 += k4 + 5;
        if (biomegenbase == BiomeGenBase.rainforest)
            l7 += k4 + 5;
        if (biomegenbase == BiomeGenBase.seasonalForest)
            l7 += k4 + 2;
        if (biomegenbase == BiomeGenBase.taiga)
            l7 += k4 + 5;
        if (biomegenbase == BiomeGenBase.desert)
            l7 -= 20;
        if (biomegenbase == BiomeGenBase.tundra)
            l7 -= 20;
        if (biomegenbase == BiomeGenBase.plains)
            l7 -= 20;
        l7 += k4;
        for (int i11 = 0; i11 < l7; i11++) {
            int k15 = k + ((Random) randomField.get(this)).nextInt(16) + 8;
            int j18 = l + ((Random) randomField.get(this)).nextInt(16) + 8;
            WorldGenerator worldgenerator = biomegenbase.getRandomWorldGenForTrees((Random) randomField.get(this));
            worldgenerator.func_517_a(1.0D, 1.0D, 1.0D);
            worldgenerator.generate((World) worldObjField.get(this), (Random) randomField.get(this), k15, ((World) worldObjField.get(this)).getHeightValue(k15, j18), j18);
        }

        for (int k17 = 0; k17 < 50; k17++) {
            int j20 = k + ((Random) randomField.get(this)).nextInt(16) + 8;
            int l21 = ((Random) randomField.get(this)).nextInt(((Random) randomField.get(this)).nextInt(120) + 8);
            int l22 = l + ((Random) randomField.get(this)).nextInt(16) + 8;
            (new AetherGenLiquids(Block.waterMoving.blockID)).generate((World) worldObjField.get(this), (Random) randomField.get(this), j20, l21, l22);
        }

        BlockSand.fallInstantly = false;
        
        Random rnd = new Random(((World) worldObjField.get(this)).getRandomSeed());
        long xSeed = (rnd.nextLong() / 2L) * 2L + 1L;
        long zSeed = (rnd.nextLong() / 2L) * 2L + 1L;
        rnd.setSeed((long)i * xSeed + (long)j * zSeed ^ ((World) worldObjField.get(this)).getRandomSeed());
		AetherPopulator.EVENT.getInvoker().GenerateAether((World) worldObjField.get(this), rnd, i << 4, j << 4);
	}
	
	private static final Field worldObjField;
	private static final Field randomField;
	static {
		try {
			worldObjField = ChunkProviderAether.class.getDeclaredField("worldObj");
			worldObjField.setAccessible(true);
			randomField = ChunkProviderAether.class.getDeclaredField("random");
			randomField.setAccessible(true);
		} catch (NoSuchFieldException | SecurityException e) {
			throw new RuntimeException(e);
		}
	}
}
