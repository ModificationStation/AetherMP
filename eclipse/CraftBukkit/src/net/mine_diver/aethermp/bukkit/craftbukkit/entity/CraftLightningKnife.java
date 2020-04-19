package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;

import net.mine_diver.aethermp.bukkit.entity.LightningKnife;
import net.mine_diver.aethermp.entities.EntityLightningKnife;

public class CraftLightningKnife extends AbstractProjectileAether implements LightningKnife {
	
	public CraftLightningKnife(CraftServer server, EntityLightningKnife entity) {
		super(server, entity);
	}

	@Override
	public LivingEntity getShooter() {
		return (LivingEntity) ((EntityLightningKnife)getHandle()).getThrower().getBukkitEntity();
	}

	@Override
	public void setShooter(LivingEntity entity) {
		((EntityLightningKnife)getHandle()).setThrower(((CraftLivingEntity)entity).getHandle());
	}
	
	@Override
	public String toString() {
		return "CraftLightningKnife";
	}
}
