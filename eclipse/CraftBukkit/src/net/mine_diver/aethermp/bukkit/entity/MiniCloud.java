package net.mine_diver.aethermp.bukkit.entity;

import org.bukkit.entity.Flying;
import org.bukkit.entity.LivingEntity;

public interface MiniCloud extends Flying {
	
	int getShotTimer();
	
	boolean gotPlayer();
	
	boolean isOnLeftSide();
	
	LivingEntity getOwner();
	
	double getOwnerX();
	
	double getOwnerY();
	
	double getOwnerZ();
	
	void setShotTimer(int shotTimer);
	
	void setHasPlayer(boolean gotPlayer);
	
	void setIsOnLeftSide(boolean toLeft);
	
	void setOwner(LivingEntity dude);
	
	void setOwnerX(double targetX);
	
	void setOwnerY(double targetY);
	
	void setOwnerZ(double targetZ);
}
