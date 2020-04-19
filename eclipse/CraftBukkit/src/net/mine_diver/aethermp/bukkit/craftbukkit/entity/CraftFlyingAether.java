package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.entity.CraftFlying;

import net.minecraft.server.Entity;
import net.minecraft.server.EntityFlying;

public class CraftFlyingAether extends CraftFlying {

	public CraftFlyingAether(CraftServer server, EntityFlying entity) {
		super(server, entity);
	}
	
	public static CraftEntity getEntity(CraftServer server, Entity entity) {
		return CraftEntityAether.getEntity(server, entity);
	}
}
