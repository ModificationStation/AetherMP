package net.mine_diver.aethermp.gui;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import net.minecraft.server.Container;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.ICrafting;
import net.minecraft.server.IInventory;
import net.minecraft.server.ModLoader;
import net.minecraft.server.ModLoaderMp;
import net.minecraft.server.Packet230ModLoader;
import net.minecraft.server.mod_AetherMp;

public class GuiManager {
	
	public static void OpenGUIWithMeta(EntityHuman entityhuman, int i, IInventory iinventory, Container container, int meta) {
		if (entityhuman instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer) entityhuman;
	        int j;
			try {
				((Method) ModLoader.getPrivateValue(ModLoader.class, null, "method_getNextWindowId")).invoke(entityplayer, new Object[0]);
				j = ((Field) ModLoader.getPrivateValue(ModLoader.class, null, "field_currentWindowId")).getInt(entityplayer);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
	        Packet230ModLoader packet = new Packet230ModLoader();
	        packet.packetType = 3;
	        packet.dataInt = new int[] {j, i, iinventory.getSize(), meta};
	        packet.dataString = new String[] {iinventory.getName()};
	        ModLoaderMp.SendPacketTo(ModLoaderMp.GetModInstance(mod_AetherMp.class), entityplayer, packet);
	        entityplayer.activeContainer = container;
	        entityplayer.activeContainer.windowId = j;
	        entityplayer.activeContainer.a((ICrafting) entityplayer);
		}
	}
}
