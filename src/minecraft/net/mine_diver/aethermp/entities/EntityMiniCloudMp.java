package net.mine_diver.aethermp.entities;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityMiniCloud;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ISpawnable;
import net.minecraft.src.Packet230ModLoader;
import net.minecraft.src.World;

public class EntityMiniCloudMp extends EntityMiniCloud implements ISpawnable {

	public EntityMiniCloudMp(World world) {
		super(world);
		isMultiplayerEntity = true;
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		updatePlayerActionState();
	}
	
	@Override
	public void updatePlayerActionState() {
        getTargetPos();
        if(atShoulder()) {
            motionX *= 0.65000000000000002D;
            motionY *= 0.65000000000000002D;
            motionZ *= 0.65000000000000002D;
            rotationYaw = dude.rotationYaw + (toLeft ? 1.0F : -1F);
            rotationPitch = dude.rotationPitch;
        } else
            approachTarget();
	}
	
	@Override
	public void setPosition(double x, double y, double z) {
		super.setPosition(x, y, z);
	}

	@Override
	public void spawn(Packet230ModLoader packet) {
		entityId = packet.dataInt[0];
		Entity entity = EntityManager.getEntityByID(packet.dataInt[1]);
		EntityPlayer ep = null;
		if (entity instanceof EntityPlayer)
			ep = (EntityPlayer) entity;
		boolean flag = packet.dataInt[2] == 1;
		posX = packet.dataFloat[0];
		posY = packet.dataFloat[1];
		posZ = packet.dataFloat[2];
		prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
		texture = "/aether/mobs/minicloud.png";
        setSize(0.5F, 0.45F);
        noClip = false;
        dude = ep;
        toLeft = flag;
        lifeSpan = 3600;
        getTargetPos();
        setPosition(targetX, targetY, targetZ);
        rotationPitch = dude.rotationPitch;
        rotationYaw = dude.rotationYaw;
        entityCollisionReduction = 1.75F;
        spawnExplosionParticle();
	}
}
