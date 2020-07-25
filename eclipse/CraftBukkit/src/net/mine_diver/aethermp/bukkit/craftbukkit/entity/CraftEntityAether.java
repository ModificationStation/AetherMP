package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftEntity;

import net.mine_diver.aethermp.entities.EntityAetherAnimal;
import net.mine_diver.aethermp.entities.EntityAetherLightning;
import net.mine_diver.aethermp.entities.EntityCloudParachute;
import net.mine_diver.aethermp.entities.EntityDartEnchanted;
import net.mine_diver.aethermp.entities.EntityDartGolden;
import net.mine_diver.aethermp.entities.EntityDartPoison;
import net.mine_diver.aethermp.entities.EntityDungeonMob;
import net.mine_diver.aethermp.entities.EntityFiroBall;
import net.mine_diver.aethermp.entities.EntityFlamingArrow;
import net.mine_diver.aethermp.entities.EntityFloatingBlock;
import net.mine_diver.aethermp.entities.EntityLightningKnife;
import net.mine_diver.aethermp.entities.EntityMimic;
import net.mine_diver.aethermp.entities.EntityMiniCloud;
import net.mine_diver.aethermp.entities.EntityPoisonNeedle;
import net.mine_diver.aethermp.entities.EntityProjectileBase;
import net.mine_diver.aethermp.entities.EntitySentry;
import net.mine_diver.aethermp.entities.EntitySheepuff;
import net.mine_diver.aethermp.entities.EntitySlider;
import net.mine_diver.aethermp.entities.EntityZephyr;
import net.mine_diver.aethermp.entities.EntityZephyrSnowball;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityAnimal;
import net.minecraft.server.EntityCreature;
import net.minecraft.server.EntityFlying;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntityWeather;
import net.minecraft.server.EntityWeatherStorm;

public class CraftEntityAether extends CraftEntity {
	
	public CraftEntityAether(CraftServer server, Entity entity) {
		super(server, entity);
	}

	public static CraftEntity getEntity(CraftServer server, Entity entity) {
		if (entity instanceof EntityLiving) {
			if (entity instanceof EntityCreature) {
				if (entity instanceof EntityDungeonMob) {
					if (entity instanceof EntitySentry)
						return new CraftSentry(server, (EntitySentry) entity);
					if (entity instanceof EntityMimic)
						return new CraftMimic(server, (EntityMimic) entity);
				} else if (entity instanceof EntityAnimal) {
					if (entity instanceof EntityAetherAnimal) {
						if (entity instanceof EntitySheepuff)
							return new CraftSheepuff(server, (EntitySheepuff) entity);
					}
				}
			} else if (entity instanceof EntityFlying) {
				if (entity instanceof EntityZephyr)
					return new CraftZephyr(server, (EntityZephyr) entity);
				if (entity instanceof EntityFiroBall)
					return new CraftFiroBall(server, (EntityFiroBall) entity);
				if (entity instanceof EntityMiniCloud)
					return new CraftMiniCloud(server, (EntityMiniCloud) entity);
				if (entity instanceof EntitySlider)
					return new CraftSlider(server, (EntitySlider) entity);
			}
		} else {
			if (entity instanceof EntityProjectileBase) {
				if (entity instanceof EntityDartEnchanted)
					return new CraftDartEnchanted(server, (EntityDartEnchanted) entity);
				if (entity instanceof EntityDartGolden)
					return new CraftDartGolden(server, (EntityDartGolden) entity);
				if (entity instanceof EntityDartPoison)
					return new CraftDartPoison(server, (EntityDartPoison) entity);
				if (entity instanceof EntityPoisonNeedle)
					return new CraftPoisonNeedle(server, (EntityPoisonNeedle) entity);
			} else if (entity instanceof EntityCloudParachute)
				return new CraftCloudParachute(server, (EntityCloudParachute) entity);
			else if (entity instanceof EntityFloatingBlock)
				return new CraftFloatingBlock(server, (EntityFloatingBlock) entity);
			else if (entity instanceof EntityZephyrSnowball)
				return new CraftZephyrSnowball(server, (EntityZephyrSnowball) entity);
			else if (entity instanceof EntityWeather) {
				if (entity instanceof EntityWeatherStorm)
					if (entity instanceof EntityAetherLightning)
						return new CraftAetherLightning(server, (EntityAetherLightning) entity);
			} else if (entity instanceof EntityLightningKnife)
				return new CraftLightningKnife(server, (EntityLightningKnife) entity);
			else if (entity instanceof EntityFlamingArrow)
				return new CraftFlamingArrow(server, (EntityFlamingArrow) entity);
					
		}
		return CraftEntity.getEntity(server, entity);
	}
}
