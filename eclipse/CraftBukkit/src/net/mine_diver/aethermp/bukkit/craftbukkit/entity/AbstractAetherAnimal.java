package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;

import net.mine_diver.aethermp.entities.EntityAetherAnimal;

public abstract class AbstractAetherAnimal extends CraftAnimalsAether {

	public AbstractAetherAnimal(CraftServer server, EntityAetherAnimal entity) {
		super(server, entity);
	}
}
