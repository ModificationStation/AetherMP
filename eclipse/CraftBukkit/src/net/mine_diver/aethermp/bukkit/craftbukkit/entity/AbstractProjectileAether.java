package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.AbstractProjectile;
import org.bukkit.craftbukkit.entity.CraftEntity;

import net.minecraft.server.Entity;

public abstract class AbstractProjectileAether extends AbstractProjectile {

	public AbstractProjectileAether(CraftServer server, Entity entity) {
		super(server, entity);
	}
	
	public static CraftEntity getEntity(CraftServer server, Entity entity) {
		return CraftEntityAether.getEntity(server, entity);
	}
}
