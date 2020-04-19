package net.mine_diver.aethermp.bukkit.entity;

import org.bukkit.entity.Projectile;
import org.bukkit.util.Vector;

public interface ZephyrSnowball extends Projectile {
	
	void setDirection(final Vector vector);
    
    Vector getDirection();
}
