package net.mine_diver.aethermp.bukkit.entity;

import org.bukkit.entity.Monster;

public interface Sentry extends Monster {
	
	boolean isOn();
	
	public void shutdown();
}
