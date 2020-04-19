package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;

import net.mine_diver.aethermp.bukkit.entity.Mimic;
import net.mine_diver.aethermp.entities.EntityMimic;

public class CraftMimic extends CraftDungeonMob implements Mimic {

	public CraftMimic(CraftServer server, EntityMimic entity) {
		super(server, entity);
	}
	
	@Override
	public String toString() {
		return "CraftMimic";
	}
}
