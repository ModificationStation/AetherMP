package net.mine_diver.aethermp.network;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import net.mine_diver.aethermp.entity.EntityCloudParachuteMp;
import net.mine_diver.aethermp.entity.EntityManager;
import net.mine_diver.aethermp.gui.GuiManager;
import net.mine_diver.aethermp.util.AchievementHandler;
import net.mine_diver.aethermp.util.AetherPoisonMp;
import net.minecraft.src.Achievement;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityAetherLightning;
import net.minecraft.src.EntityLightningBolt;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityProjectileBase;
import net.minecraft.src.EntitySlider;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.IAetherBoss;
import net.minecraft.src.ModLoader;
import net.minecraft.src.Packet230ModLoader;
import net.minecraft.src.StatList;
import net.minecraft.src.WorldClient;
import net.minecraft.src.mod_Aether;

public class PacketManager {
	
	public static void handlePacket(Packet230ModLoader packet) {
		handlers.get(packet.packetType).accept(packet);
	}
	
	public static final Map<Integer, Consumer<Packet230ModLoader>> handlers = new HashMap<>();
	static {
		handlers.put(0, (packet) -> ModLoader.getMinecraftInstance().theWorld.playSoundAtEntity(EntityManager.getEntityByID(packet.dataInt[0]), packet.dataString[0], packet.dataFloat[0], packet.dataFloat[1]));
		handlers.put(1, (packet) -> ModLoader.getMinecraftInstance().theWorld.playSoundEffect(packet.dataFloat[0], packet.dataFloat[1], packet.dataFloat[2], packet.dataString[0], packet.dataFloat[3], packet.dataFloat[4]));
        handlers.put(2, (packet) -> EntityCloudParachuteMp.doCloudSmoke(ModLoader.getMinecraftInstance().thePlayer.worldObj, (EntityLiving)EntityManager.getEntityByID(packet.dataInt[0]), false));
        handlers.put(3, (packet) -> {
        	GuiScreen guiscreen = GuiManager.handleGuiWithMeta(packet.dataInt[1], packet.dataInt[3]);
            if(guiscreen != null) {
                ModLoader.OpenGUI(ModLoader.getMinecraftInstance().thePlayer, guiscreen);
                ModLoader.getMinecraftInstance().thePlayer.craftingInventory.windowId = packet.dataInt[0];
            }
		});
        handlers.put(4, (packet) -> AchievementHandler.handleAchievement((Achievement) StatList.func_27361_a(packet.dataInt[0]), false));
        handlers.put(5, (packet) -> {
        	WorldClient worldClient = (WorldClient) ModLoader.getMinecraftInstance().theWorld;
    		double d = (double)packet.dataInt[1] / 32D;
            double d1 = (double)packet.dataInt[2] / 32D;
            double d2 = (double)packet.dataInt[3] / 32D;
            EntityLightningBolt entitylightningbolt = null;
            if(packet.dataInt[4] == 1)
                entitylightningbolt = new EntityAetherLightning(worldClient, d, d1, d2);
            if(entitylightningbolt != null) {
                entitylightningbolt.serverPosX = packet.dataInt[1];
                entitylightningbolt.serverPosY = packet.dataInt[2];
                entitylightningbolt.serverPosZ = packet.dataInt[3];
                entitylightningbolt.rotationYaw = 0.0F;
                entitylightningbolt.rotationPitch = 0.0F;
                entitylightningbolt.entityId = packet.dataInt[0];
                worldClient.addWeatherEffect(entitylightningbolt);
            }
		});
        handlers.put(6, (packet) -> mod_Aether.getPlayer().maxHealth = packet.dataInt[0]);
        handlers.put(7, (packet) -> AetherPoisonMp.afflictPoison());
        handlers.put(8, (packet) -> AetherPoisonMp.curePoison());
        handlers.put(9, (packet) -> {
        	Entity entity = EntityManager.getEntityByID(packet.dataInt[0]);
        	if (entity instanceof EntityProjectileBase)
        		((EntityProjectileBase)entity).setArrowHeading(packet.dataFloat[0], packet.dataFloat[1], packet.dataFloat[2], packet.dataFloat[3], packet.dataFloat[4]);
        });
        handlers.put(10, (packet) -> mod_Aether.currentBoss = packet.dataInt[0] == 0 ? null : (IAetherBoss) EntityManager.getEntityByID(packet.dataInt[1]));
        handlers.put(11, (packet) -> {
        	if (ModLoader.getMinecraftInstance().gameSettings.fancyGraphics)
        		((EntitySlider) EntityManager.getEntityByID(packet.dataInt[0])).addSquirrelButts(packet.dataInt[1], packet.dataInt[2], packet.dataInt[3]);
        });
        handlers.put(12, (packet) -> ModLoader.getMinecraftInstance().effectRenderer.addBlockDestroyEffects(packet.dataInt[0], packet.dataInt[1], packet.dataInt[2], packet.dataInt[3], packet.dataInt[4]));
	}
}
