package net.mine_diver.aethermp;

import java.lang.reflect.Field;
import java.util.Map;
import sun.misc.Unsafe;
import net.mine_diver.aethermp.blocks.BlockManager;
import net.mine_diver.aethermp.entities.EntityManager;
import net.mine_diver.aethermp.gui.GuiManager;
import net.mine_diver.aethermp.network.PacketManager;
import net.mine_diver.aethermp.player.PlayerBaseAetherMp;
import net.mine_diver.aethermp.proxy.GuiIngameAetherMp;
import net.mine_diver.aethermp.render.RenderManager;
import net.minecraft.client.Minecraft;
import net.minecraft.src.AetherAchievements;
import net.minecraft.src.BaseMod;
import net.minecraft.src.BaseModMp;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.GuiMainMenu;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.ModLoader;
import net.minecraft.src.Packet230ModLoader;
import net.minecraft.src.PlayerAPI;
import net.minecraft.src.PlayerBaseAether;
import net.minecraft.src.Render;

public class Core {
	
	@SuppressWarnings("unchecked")
	public void postInit(BaseModMp mod, BaseMod aetherInstance) {
		ModLoader.SetInGameHook(aetherInstance, false, false);
		ModLoader.SetInGUIHook(mod, true, false);
		ModLoader.SetInGameHook(mod, true, false);
		AetherAchievements.enterAether.setIndependent();
		for (int i = 0; i < PlayerAPI.playerBaseClasses.size(); i++)
			if (PlayerAPI.playerBaseClasses.get(i).equals(PlayerBaseAether.class))
				PlayerAPI.playerBaseClasses.set(i, PlayerBaseAetherMp.class);
		GuiManager.registerGuis(mod);
		BlockManager.registerBlocks();
		EntityManager.registerEntities();
	}
	
	public void addRenderer(Map<Class<? extends Entity>, Render> entityRenderers) {
		RenderManager.registerRenderers(entityRenderers);
	}
	
	public boolean onTickInGUI(Minecraft minecraft, GuiScreen guiscreen) {
		if (guiscreen instanceof GuiMainMenu && !(minecraft.ingameGUI instanceof GuiIngameAetherMp))
			minecraft.ingameGUI = new GuiIngameAetherMp(minecraft);
		return true;
	}
	
	public boolean onTickInGame(Minecraft minecraft, BaseMod aetherInstance) {
		EntityPlayerSP player = minecraft.thePlayer;
		boolean postHook = false;
		double realY = 0;
		if (minecraft.theWorld.multiplayerWorld && player.posY < -2D) {
			postHook = true;
			realY = player.posY;
			player.posY = -2D;
		}
		boolean res = aetherInstance.OnTickInGame(minecraft);
		if (postHook)
			player.posY = realY;
		if (!(minecraft.ingameGUI instanceof GuiIngameAetherMp))
			minecraft.ingameGUI = new GuiIngameAetherMp(minecraft);
		return res;
	}
	
	
	
	public GuiScreen handleGui(int ID) {
		return GuiManager.handleGui(ID);
	}
	
	public void handlePacket(Packet230ModLoader packet) {
        PacketManager.handlePacket(packet);
	}
	
	public static final Unsafe unsafe;
	static {
		Unsafe instance = null;
		try {
			Field field = Unsafe.class.getDeclaredField("theUnsafe");
			field.setAccessible(true);
			instance = (Unsafe) field.get(null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		unsafe = instance;
	}
}
