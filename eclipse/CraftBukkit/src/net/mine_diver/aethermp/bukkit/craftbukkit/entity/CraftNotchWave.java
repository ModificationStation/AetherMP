package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;

import net.mine_diver.aethermp.bukkit.entity.NotchWave;
import net.mine_diver.aethermp.entities.EntityNotchWave;

public class CraftNotchWave extends AbstractProjectileAether implements NotchWave {

	public CraftNotchWave(CraftServer server, EntityNotchWave entity) {
		super(server, entity);
	}

	@Override
	public LivingEntity getShooter() {
		return (LivingEntity) ((EntityNotchWave)getHandle()).getThrower().getBukkitEntity();
	}

	@Override
	public void setShooter(LivingEntity entity) {
		((EntityNotchWave)getHandle()).setThrower(((CraftLivingEntity)entity).getHandle());
	}
	
	@Override
	public String toString() {
		return "CraftNotchWave";
	}
}
