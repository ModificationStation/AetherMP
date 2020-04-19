package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;

import net.mine_diver.aethermp.bukkit.entity.FlamingArrow;
import net.mine_diver.aethermp.entities.EntityFlamingArrow;

public class CraftFlamingArrow extends AbstractProjectileAether implements FlamingArrow {

	public CraftFlamingArrow(CraftServer server, EntityFlamingArrow entity) {
		super(server, entity);
	}

	@Override
	public LivingEntity getShooter() {
		return (LivingEntity) ((EntityFlamingArrow)getHandle()).owner.getBukkitEntity();
	}

	@Override
	public void setShooter(LivingEntity entity) {
		((EntityFlamingArrow)getHandle()).owner = ((CraftLivingEntity)entity).getHandle();
	}
	
	@Override
	public String toString() {
		return "CraftFlamingArrow";
	}
}
