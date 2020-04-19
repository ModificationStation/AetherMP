package net.mine_diver.aethermp.bukkit.entity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public interface CloudParachute extends Entity {
	
	boolean isGolden();
	
	LivingEntity getAttachedEntity();
}
