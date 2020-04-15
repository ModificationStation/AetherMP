package net.mine_diver.aethermp.network;

import net.minecraft.server.Entity;
import net.minecraft.server.ModLoader;
import net.minecraft.server.ModLoaderMp;
import net.minecraft.server.Packet230ModLoader;
import net.minecraft.server.WorldServer;
import net.minecraft.server.mod_AetherMp;

public class PacketManager {
	
	public static void makeSound(Entity entity, String sound, float volume, float pitch){
        Packet230ModLoader packet = new Packet230ModLoader();
        packet.dataInt = new int[]{entity.id};
        packet.dataString = new String[]{sound};
        packet.dataFloat = new float[]{volume, pitch};
        packet.packetType = 0;
        packet.modId = ModLoaderMp.GetModInstance(mod_AetherMp.class).getId();
        ModLoader.getMinecraftServerInstance().getTracker(((WorldServer) entity.world).dimension).a(entity, packet);
    }
	
    public static void makeSound(float x, float y, float z, String sound, float volume, float pitch, int i) {
        Packet230ModLoader packet = new Packet230ModLoader();
        packet.dataString = new String[]{sound};
        packet.dataFloat = new float[]{x, y, z, volume, pitch};
        packet.packetType = 1;
        packet.modId = ModLoaderMp.GetModInstance(mod_AetherMp.class).getId();
        ModLoader.getMinecraftServerInstance().serverConfigurationManager.sendPacketNearby(x, y, z, ModLoader.getMinecraftServerInstance().propertyManager.getInt("view-distance", 10) * 16, i, packet);
    }
}
