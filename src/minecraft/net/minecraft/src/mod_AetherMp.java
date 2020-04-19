package net.minecraft.src;

import java.util.List;
import java.util.Map;
import java.util.Random;

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
	public void KeyboardEvent(KeyBinding event) {
		CORE.keyboardEvent(event, aetherInstance);
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
	public static boolean utilizeUnstablePackets = true;
	
	@MLProp
	public static int
	idGuiEnchanter = 80,
	idGuiTreasureChest = 81,
	idGuiFreezer = 82,
	idGuiLore = 83,
	
	idEntityFloatingBlock = 80,
	idEntityMimic = 81,
	idEntityZephyr = 85,
	idEntitySheepuff = 86,
	idEntitySentry = 89,
	idEntityZephyrSnowball = 100,
	idEntityCloudParachute = 101,
	idEntityDartEnchanted = 102,
	idEntityDartGolden = 103,
	idEntityDartPoison = 104,
	idEntityAetherLightning = 105,
	idEntityLightningKnife = 106,
	idEntityNotchWave = 107,
	idEntityFlamingArrow = 108,
	idEntityMiniCloud = 109,
	idEntityFiroBall = 110;
	
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
			
			public static boolean getInWater(net.minecraft.src.Entity entity) {
				return entity.inWater;
			}
			
			public static Random getRand(net.minecraft.src.Entity entity) {
				return entity.rand;
			}
			
			public static void setEntityFlag(net.minecraft.src.Entity entity, int ID, boolean flag) {
				entity.setEntityFlag(ID, flag);
			}
		}
		
		public static class GuiConnecting {
			
			public static NetClientHandler setNetClientHandler(net.minecraft.src.GuiConnecting guiconnecting, NetClientHandler netclienthandler)
		    {
		        return net.minecraft.src.GuiConnecting.setNetClientHandler(guiconnecting, netclienthandler);
		    }

		    public static NetClientHandler getNetClientHandler(net.minecraft.src.GuiConnecting guiconnecting)
		    {
		        return net.minecraft.src.GuiConnecting.getNetClientHandler(guiconnecting);
		    }
		}
		
		public static class EntityLiving {
			
			public static boolean getIsJumping(net.minecraft.src.EntityLiving entityliving) {
				return entityliving.isJumping;
			}
		}
		
		public static class Item {
			
			public static int getIconIndex(net.minecraft.src.Item item) {
				return item.iconIndex;
			}
		}
	}
}
