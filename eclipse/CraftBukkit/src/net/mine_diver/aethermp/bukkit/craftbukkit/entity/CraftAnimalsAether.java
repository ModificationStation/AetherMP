package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftAnimals;
import org.bukkit.craftbukkit.entity.CraftEntity;

import net.minecraft.server.Entity;
import net.minecraft.server.EntityAnimal;

public class CraftAnimalsAether extends CraftAnimals {

	public CraftAnimalsAether(CraftServer server, EntityAnimal entity) {
		super(server, entity);
	}
	
	public static CraftEntity getEntity(CraftServer server, Entity entity) {
		return CraftEntityAether.getEntity(server, entity);
	}
}
