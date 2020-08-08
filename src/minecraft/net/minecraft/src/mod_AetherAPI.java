package net.minecraft.src;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import net.mine_diver.aetherapi.Core;

public class mod_AetherAPI extends BaseModMp {
	
	public mod_AetherAPI() {
		try {
			info.load(getClass().getResourceAsStream("/" + CORE.getClass().getPackage().getName().replace(".", "/") + "/mod.info"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public String Name() {
		return info.getProperty("name");
	}
	
	public String Description() {
		return info.getProperty("description");
	}
	
	@Override
	public String Version() {
		return info.getProperty("version");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void ModsLoaded() {
		for (BaseMod mod : (List<BaseMod>) ModLoader.getLoadedMods())
			if (mod.getClass().equals(mod_Aether.class)) {
				aetherInstance = mod;
				break;
			}
		CORE.init();
	}
	
	@Override
	public void AddRenderer(@SuppressWarnings("rawtypes") Map map) {
		CORE.postInit(aetherInstance);
	}

	public static final Core CORE = new Core();
	private final Properties info = new Properties();
	private BaseMod aetherInstance;
	
public static class PackageAccess {
		
		public static class Block {
			
			public static void setHardness(net.minecraft.src.Block block, float hardness) {
				block.setHardness(hardness);
			}
			
			public static float getResistance(net.minecraft.src.Block block) {
				return block.blockResistance;
			}
			
			public static void setResistance(net.minecraft.src.Block block, float resistance) {
				block.blockResistance = resistance;
			}
			
			public static void setStepSound(net.minecraft.src.Block block, StepSound stepsound) {
				block.setStepSound(stepsound);
			}
		}
		
		public static class Item {
			
			public static int getIconIndex(net.minecraft.src.Item item) {
				return item.iconIndex;
			}
		}
	}
}
