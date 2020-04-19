package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;

import net.mine_diver.aethermp.bukkit.entity.AetherLightning;
import net.mine_diver.aethermp.entities.EntityAetherLightning;

public class CraftAetherLightning extends CraftLightningStrikeAether implements AetherLightning {

	public CraftAetherLightning(CraftServer server, EntityAetherLightning entity) {
		super(server, entity);
	}
	
	@Override
	public String toString() {
		return "CraftAetherLightning";
	}
}
