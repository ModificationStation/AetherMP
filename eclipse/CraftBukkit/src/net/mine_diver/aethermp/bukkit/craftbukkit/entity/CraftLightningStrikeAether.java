package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.entity.CraftLightningStrike;

import net.minecraft.server.Entity;
import net.minecraft.server.EntityWeatherStorm;

public class CraftLightningStrikeAether extends CraftLightningStrike {

	public CraftLightningStrikeAether(CraftServer server, EntityWeatherStorm entity) {
		super(server, entity);
	}
	
	public static CraftEntity getEntity(CraftServer server, Entity entity) {
		return CraftEntityAether.getEntity(server, entity);
	}
}
