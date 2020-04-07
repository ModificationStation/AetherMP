package net.mine_diver.aethermp.network;

import net.mine_diver.aethermp.entities.EntityCloudParachuteMp;
import net.mine_diver.aethermp.entities.EntityManager;
import net.mine_diver.aethermp.gui.GuiManager;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.ModLoader;
import net.minecraft.src.Packet230ModLoader;
import net.minecraft.src.mod_AetherMp;

public class PacketManager {
	
	public static void handlePacket(Packet230ModLoader packet) {
		if (packet.packetType == 1)
            ModLoader.getMinecraftInstance().theWorld.playSoundEffect(packet.dataFloat[0], packet.dataFloat[1], packet.dataFloat[2], packet.dataString[0], packet.dataFloat[3], packet.dataFloat[4]);
		else if (packet.packetType == 4) {
	        GuiScreen guiscreen = GuiManager.handleGuiWithMeta(packet.dataInt[1], packet.dataInt[3]);
	        if(guiscreen != null) {
	            ModLoader.OpenGUI(ModLoader.getMinecraftInstance().thePlayer, guiscreen);
	            ModLoader.getMinecraftInstance().thePlayer.craftingInventory.windowId = packet.dataInt[0];
	        }
		} else if (mod_AetherMp.utilizeUnstablePackets) {
			//TODO: Find an alternative way to send sounds from Zephyrs
	        if (packet.packetType == 0)
	        	ModLoader.getMinecraftInstance().theWorld.playSoundAtEntity(EntityManager.getEntityByID(packet.dataInt[0]), packet.dataString[0], packet.dataFloat[0], packet.dataFloat[1]);
	        
	        //TODO: Utilize Packet10Flying instead of a custom packet (requires server update)
	        else if (packet.packetType == 2) {
	        	ModLoader.getMinecraftInstance().thePlayer.motionX = packet.dataFloat[0];
	        	ModLoader.getMinecraftInstance().thePlayer.motionY = packet.dataFloat[1];
	        	ModLoader.getMinecraftInstance().thePlayer.motionZ = packet.dataFloat[2];
	        }
	        
	        //TODO: Make EntityCloudParachute smoke for itself
	        else if (packet.packetType == 3)
	            EntityCloudParachuteMp.doCloudSmoke(ModLoader.getMinecraftInstance().thePlayer.worldObj, (EntityLiving)EntityManager.getEntityByID(packet.dataInt[0]), false);
        }
	}
}
