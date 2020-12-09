package net.mine_diver.aethermp.bukkit.entity;

import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

public interface AetherBoss extends Monster {
	
	public int getBossHP();

    public int getBossMaxHP();

    public boolean isCurrentBoss(Player player);

    public int getBossEntityID();

    public String getBossTitle();
    
    public void stopFight();
}
