package net.mine_diver.aethermp.bukkit.entity;

import org.bukkit.entity.Animals;
import org.bukkit.material.Colorable;

public interface Sheepuff extends Animals, Colorable {
	
    boolean isSheared();
    
    void setSheared(boolean sheared);
    
    boolean isPuffed();
    
    void setPuffed(boolean puffed);
}
