package net.mine_diver.aethermp.gui;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.src.BaseModMp;
import net.minecraft.src.GuiEnchanter;
import net.minecraft.src.GuiFreezer;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiTreasureChest;
import net.minecraft.src.ModLoader;
import net.minecraft.src.ModLoaderMp;
import net.minecraft.src.TileEntityChest;
import net.minecraft.src.TileEntityEnchanter;
import net.minecraft.src.TileEntityFreezer;
import net.minecraft.src.mod_AetherMp;

public class GuiManager {
	
	public static void registerGuis(BaseModMp mod) {
		for (int gui : guis.keySet())
			ModLoaderMp.RegisterGUI(mod, gui);
	}
	
	public static GuiScreen handleGui(int ID) {
		return guis.get(ID).getGui();
	}
	
	public static GuiScreen handleGuiWithMeta(int ID, int meta) {
		return guisMeta.get(ID).getGuiWithMeta(meta);
	}
	
	public static final Map<Integer, GuiInstance> guis = new HashMap<Integer, GuiInstance>();
	public static final Map<Integer, GuiMetaInstance> guisMeta = new HashMap<Integer, GuiMetaInstance>();
	
	static {
		guis.put(mod_AetherMp.idGuiEnchanter, () -> {return new GuiEnchanter(ModLoader.getMinecraftInstance().thePlayer.inventory, new TileEntityEnchanter());});
		guisMeta.put(mod_AetherMp.idGuiTreasureChest, (meta) -> {return new GuiTreasureChest(ModLoader.getMinecraftInstance().thePlayer.inventory, new TileEntityChest(), meta);});
		guis.put(mod_AetherMp.idGuiFreezer, () -> {return new GuiFreezer(ModLoader.getMinecraftInstance().thePlayer.inventory, new TileEntityFreezer());});
	}
}
