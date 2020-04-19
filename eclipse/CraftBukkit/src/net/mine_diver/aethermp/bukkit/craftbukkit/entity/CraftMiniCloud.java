package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;

import net.mine_diver.aethermp.bukkit.entity.MiniCloud;
import net.mine_diver.aethermp.entities.EntityMiniCloud;

public class CraftMiniCloud extends CraftFlyingAether implements MiniCloud {

	public CraftMiniCloud(CraftServer server, EntityMiniCloud entity) {
		super(server, entity);
	}

	@Override
	public int getShotTimer() {
		return ((EntityMiniCloud)getHandle()).shotTimer;
	}

	@Override
	public boolean gotPlayer() {
		return ((EntityMiniCloud)getHandle()).gotPlayer;
	}

	@Override
	public boolean isOnLeftSide() {
		return ((EntityMiniCloud)getHandle()).toLeft;
	}

	@Override
	public LivingEntity getOwner() {
		return (LivingEntity) ((EntityMiniCloud)getHandle()).dude.getBukkitEntity();
	}

	@Override
	public double getOwnerX() {
		return ((EntityMiniCloud)getHandle()).targetX;
	}

	@Override
	public double getOwnerY() {
		return ((EntityMiniCloud)getHandle()).targetY;
	}

	@Override
	public double getOwnerZ() {
		return ((EntityMiniCloud)getHandle()).targetZ;
	}

	@Override
	public void setShotTimer(int shotTimer) {
		((EntityMiniCloud)getHandle()).shotTimer = shotTimer;
	}

	@Override
	public void setHasPlayer(boolean gotPlayer) {
		((EntityMiniCloud)getHandle()).gotPlayer = gotPlayer;
	}

	@Override
	public void setIsOnLeftSide(boolean toLeft) {
		((EntityMiniCloud)getHandle()).toLeft = toLeft;
	}

	@Override
	public void setOwner(LivingEntity dude) {
		((EntityMiniCloud)getHandle()).dude = ((CraftLivingEntity)dude).getHandle();
	}

	@Override
	public void setOwnerX(double targetX) {
		((EntityMiniCloud)getHandle()).targetX = targetX;
	}

	@Override
	public void setOwnerY(double targetY) {
		((EntityMiniCloud)getHandle()).targetY = targetY;
	}

	@Override
	public void setOwnerZ(double targetZ) {
		((EntityMiniCloud)getHandle()).targetZ = targetZ;
	}
	
	@Override
	public String toString() {
		return "CraftMiniCloud";
	}
}
