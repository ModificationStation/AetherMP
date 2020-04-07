package net.mine_diver.aethermp.dimension;

import net.minecraft.server.BiomeBase;
import net.minecraft.server.DimensionBase;

public class DimensionInfo {
	
	DimensionInfo(DimensionBase dimensionBase) {
		this.dimensionBase = dimensionBase;
	}
	
	DimensionInfo setName(String name) {
		this.name = name;
		return this;
	}
	
	DimensionInfo setBiomes(BiomeBase[] biomes) {
		this.biomes = biomes;
		return this;
	}
	
	public DimensionBase getDimensionBase() {
		return dimensionBase;
	}
	
	public String getName() {
		return name;
	}
	
	public BiomeBase[] getBiomes() {
		return biomes;
	}
	
	private final DimensionBase dimensionBase;
	private String name;
	private BiomeBase[] biomes;
}
