package net.mine_diver.aethermp.gui;

import net.minecraft.src.BaseModMp;
import net.minecraft.src.GuiEnchanter;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiTreasureChest;
import net.minecraft.src.ModLoader;
import net.minecraft.src.ModLoaderMp;
import net.minecraft.src.TileEntityChest;
import net.minecraft.src.TileEntityEnchanter;
import net.minecraft.src.mod_AetherMp;

public class GuiManager {
	
	public static void registerGuis(BaseModMp mod) {
		ModLoaderMp.RegisterGUI(mod, mod_AetherMp.idGuiEnchanter);
	}
	
	public static GuiScreen handleGui(int ID){
    	if (ID == mod_AetherMp.idGuiEnchanter)
    		return new GuiEnchanter(ModLoader.getMinecraftInstance().thePlayer.inventory, new TileEntityEnchanter());
    	else
    		return null;
	}
	
	public static GuiScreen handleGuiWithMeta(int ID, int meta) {
		if (ID == mod_AetherMp.idGuiTreasureChest)
    		return new GuiTreasureChest(ModLoader.getMinecraftInstance().thePlayer.inventory, new TileEntityChest(), meta);
		else
			return null;
	}
}
