package net.mine_diver.aethermp.bukkit.entity;

import org.bukkit.entity.Flying;

public interface FiroBall extends Flying {
	
	boolean isFrosty();
	
	boolean isSmacked();
	
	boolean isFromCloud();
	
	int getTicksAlive();
	
	void setFrosty(boolean frosty);
	
	void setSmacked(boolean smacked);
	
	void setFromCloud(boolean fromCloud);
	
	void setTicksAlive(int life);
}
