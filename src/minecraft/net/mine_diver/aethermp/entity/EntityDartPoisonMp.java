package net.mine_diver.aethermp.entity;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityDartPoison;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ISpawnable;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Packet230ModLoader;
import net.minecraft.src.World;

public class EntityDartPoisonMp extends EntityDartPoison implements ISpawnable {

	public EntityDartPoisonMp(World world) {
		super(world);
	}

	@Override
	public void spawn(Packet230ModLoader packet) {
		entityId = packet.dataInt[0];
		Entity entity = EntityManager.getEntityByID(packet.dataInt[1]);
		if (entity instanceof EntityLiving)
			shooter = (EntityLiving) entity;
		shotByPlayer = shooter instanceof EntityPlayer;
		if (shooter != null)
			setLocationAndAngles(shooter.posX, shooter.posY + (double)shooter.getEyeHeight(), shooter.posZ, shooter.rotationYaw, shooter.rotationPitch);
		else
			setLocationAndAngles(packet.dataFloat[0], posY = packet.dataFloat[1], packet.dataFloat[2], packet.dataFloat[3], packet.dataFloat[4]);
		posX -= MathHelper.cos((rotationYaw / 180F) * 3.141593F) * 0.16F;
        posY -= 0.10000000149011612D;
        posZ -= MathHelper.sin((rotationYaw / 180F) * 3.141593F) * 0.16F;
        setPositionAndRotation(posX, posY, posZ, rotationYaw, rotationPitch);
        serverPosX = (int) (posX * 32);
		serverPosY = (int) (posY * 32);
		serverPosZ = (int) (posZ * 32);
        motionX = -MathHelper.sin((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F);
        motionZ = MathHelper.cos((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F);
        motionY = -MathHelper.sin((rotationPitch / 180F) * 3.141593F);
        setArrowHeading(motionX, motionY, motionZ, speed, precision);
	}
}
