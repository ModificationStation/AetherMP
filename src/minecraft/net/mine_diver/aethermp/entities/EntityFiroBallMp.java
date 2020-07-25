package net.mine_diver.aethermp.entities;

import net.minecraft.src.EntityFiroBall;
import net.minecraft.src.ISpawnable;
import net.minecraft.src.Packet230ModLoader;
import net.minecraft.src.World;

public class EntityFiroBallMp extends EntityFiroBall implements ISpawnable {

	public EntityFiroBallMp(World world) {
		super(world);
		isMultiplayerEntity = true;
	}

	@Override
	public void spawn(Packet230ModLoader packet) {
		entityId = packet.dataInt[0];
		boolean flag = packet.dataInt[1] == 1;
		boolean flag2 = packet.dataInt[2] == 1;
        setPositionAndRotation(packet.dataFloat[0], packet.dataFloat[1], packet.dataFloat[2], rotationYaw, rotationPitch);
        serverPosX = (int) (posX * 32);
        serverPosY = (int) (posY * 32);
        serverPosZ = (int) (posZ * 32);
        smotionX = packet.dataFloat[3];
        smotionY = packet.dataFloat[4];
        smotionZ = packet.dataFloat[5];
        if(flag) {
            frosty = true;
            texture = "/aether/mobs/iceyball.png";
        }
        fromCloud = flag2;
	}
}
