package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.LivingEntity;

import net.mine_diver.aethermp.bukkit.entity.Zephyr;
import net.mine_diver.aethermp.entities.EntityZephyr;

public class CraftZephyr extends CraftFlyingAether implements Zephyr {

	public CraftZephyr(CraftServer server, EntityZephyr entity) {
		super(server, entity);
	}

	@Override
	public LivingEntity getTarget() {
		return (LivingEntity) ((EntityZephyr)getHandle()).getTargetedEntity().getBukkitEntity();
	}

	@Override
	public void setTarget(LivingEntity entity) {
		((EntityZephyr)getHandle()).setTargetedEntity(((CraftEntity)entity).getHandle());
	}
	
	@Override
	public String toString() {
		return "CraftZephyr";
	}
}
