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
		double x = packet.dataFloat[0];
		double y = packet.dataFloat[1];
		double z = packet.dataFloat[2];
		boolean flag = packet.dataInt[1] == 1;
		boolean flag2 = packet.dataInt[2] == 1;
        setPositionAndRotation(x, y, z, rotationYaw, rotationPitch);
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
