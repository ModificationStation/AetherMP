package net.mine_diver.aethermp.dimension.world;

import java.util.Arrays;

import net.mine_diver.aethermp.dimension.biomes.BiomeAether;
import net.minecraft.server.BiomeBase;
import net.minecraft.server.ChunkCoordIntPair;
import net.minecraft.server.WorldChunkManager;

public class WorldChunkManagerAether extends WorldChunkManager {

    public WorldChunkManagerAether(double d) {
        field_4201_e = BiomeAether.me;
        field_4200_f = d;
    }
    
    @Override
    public BiomeBase a(ChunkCoordIntPair chunkcoordintpair) {
        return field_4201_e;
    }
    
    @Override
    public BiomeBase getBiome(int i, int j) {
        return field_4201_e;
    }
    
    @Override
    public double[] a(double ad[], int i, int j, int k, int l) {
        if(ad == null || ad.length < k * l)
            ad = new double[k * l];
        Arrays.fill(ad, 0, k * l, field_4200_f);
        return ad;
    }
    
    @Override
    public BiomeBase[] getBiomeData(int i, int j, int k, int l) {
        d = a(d, i, j, k, l);
        return d;
    }
    
    @Override
    public BiomeBase[] a(BiomeBase abiomegenbase[], int i, int j, int k, int l) {
        if(abiomegenbase == null || abiomegenbase.length < k * l)
            abiomegenbase = new BiomeBase[k * l];
        if(temperature == null || temperature.length < k * l) {
            temperature = new double[k * l];
            rain = new double[k * l];
        }
        Arrays.fill(abiomegenbase, 0, k * l, field_4201_e);
        return abiomegenbase;
    }

    private BiomeBase field_4201_e;
    private double field_4200_f;
}