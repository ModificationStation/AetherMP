package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;

import net.mine_diver.aethermp.entities.EntityPoisonNeedle;

public class CraftPoisonNeedle extends AbstractProjectileBase {

	public CraftPoisonNeedle(CraftServer server, EntityPoisonNeedle entity) {
		super(server, entity);
	}
	
	@Override
	public String toString() {
		return "CraftPoisonNeedle";
	}
}
