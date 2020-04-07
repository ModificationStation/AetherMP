package net.minecraft.src;

import java.util.List;
import java.util.Map;

import net.mine_diver.aethermp.Core;
import net.mine_diver.aethermp.info;
import net.minecraft.client.Minecraft;

public class mod_AetherMp extends BaseModMp {
	
	public String Name() {
		return info.NAME;
	}
	
	public String Description() {
		return info.DESCRIPTION;
	}
	
	@Override
	public String Version() {
		return info.VERSION;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void ModsLoaded() {
		for (BaseMod mod : (List<BaseMod>) ModLoader.getLoadedMods())
			if (mod.getClass().equals(mod_Aether.class)) {
				aetherInstance = mod;
				break;
			}
		CORE.postInit(this, aetherInstance);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void AddRenderer(@SuppressWarnings("rawtypes") Map map) {
		CORE.addRenderer(map);
	}
	
	@Override
	public boolean OnTickInGUI(Minecraft minecraft, GuiScreen guiscreen) {
		return CORE.onTickInGUI(minecraft, guiscreen);
	}
	
	@Override
	public boolean OnTickInGame(Minecraft minecraft) {
		return CORE.onTickInGame(minecraft, aetherInstance);
	}
	
	@Override
	public GuiScreen HandleGUI(int ID) {
		return CORE.handleGui(ID);
	}
	
	@Override
	public void HandlePacket(Packet230ModLoader packet) {
		CORE.handlePacket(packet);
    }
	
	public static final Core CORE = new Core();
	private BaseMod aetherInstance;
	
	@MLProp(info = "Receive packets from old server code with additional events. Enhances gameplay experience, yet kicks from server may occur.")
	public static boolean utilizeUnstablePackets = false;
	
	@MLProp
	public static int
	idGuiEnchanter = 80,
	idGuiTreasureChest = 81,
	idEntityCloudParachute = 101,
	idEntityFloatingBlock = 80,
	idEntityMimic = 81,
	idEntitySentry = 89,
	idEntitySheepuff = 86,
	idEntityZephyr = 85,
	idEntityZephyrSnowball = 100;
	
	public static class PackageAccess {
		
		public static class World {
			
			public static ISaveHandler getSaveHandler(net.minecraft.src.World world) {
				return world.saveHandler;
			}
		}
		
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
		
		public static class Entity {

			public static void setFallDistance(net.minecraft.src.Entity entity, float fallDistance) {
				entity.fallDistance = fallDistance;
			}
		}
	}
}
