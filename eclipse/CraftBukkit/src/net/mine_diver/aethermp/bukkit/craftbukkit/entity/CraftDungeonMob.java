package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Monster;

import net.mine_diver.aethermp.entities.EntityDungeonMob;

public class CraftDungeonMob extends CraftCreatureAether implements Monster {

	public CraftDungeonMob(CraftServer server, EntityDungeonMob entity) {
		super(server, entity);
	}
	
	@Override
	public String toString() {
		return "CraftDungeonMob";
	}
}
