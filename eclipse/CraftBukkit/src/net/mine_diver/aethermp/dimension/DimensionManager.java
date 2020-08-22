package net.mine_diver.aethermp.dimension;

import org.bukkit.World.Environment;

import net.mine_diver.aethermp.dimension.biomes.BiomeAether;
import net.mine_diver.aethermp.dimension.world.PortalTravelAgentAether;
import net.mine_diver.aethermp.dimension.world.WorldProviderAether;
import net.minecraft.server.BiomeBase;
import net.minecraft.server.DimensionBase;
import net.minecraft.server.World;
import net.minecraft.server.mod_AetherMp;

public class DimensionManager {
	
	public static void registerDimensions() {
		for (DimensionInfo dimension : dimensions) {
			DimensionBase dimensionInst = dimension.getDimensionBase();
			dimensionInst.name = dimension.getName();
		}
	}
	
	public static Environment getCurrentDimension(World world) {
        return world.getWorld().getEnvironment();
    }
	
	public static DimensionBase Aether = new DimensionBase(mod_AetherMp.idDimensionAether, WorldProviderAether.class, PortalTravelAgentAether.class);
	
	public static final DimensionInfo[] dimensions = new DimensionInfo[] {
			new DimensionInfo(Aether).setName(mod_AetherMp.nameDimensionAether).setBiomes(new BiomeBase[] {new BiomeAether()})
	};
}