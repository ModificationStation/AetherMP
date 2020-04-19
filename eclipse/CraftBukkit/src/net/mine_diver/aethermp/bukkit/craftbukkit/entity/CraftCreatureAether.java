package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftCreature;
import org.bukkit.craftbukkit.entity.CraftEntity;

import net.minecraft.server.Entity;
import net.minecraft.server.EntityCreature;

public class CraftCreatureAether extends CraftCreature {

	public CraftCreatureAether(CraftServer server, EntityCreature entity) {
		super(server, entity);
	}
	
	public static CraftEntity getEntity(CraftServer server, Entity entity) {
		return CraftEntityAether.getEntity(server, entity);
	}
}
