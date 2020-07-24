package net.mine_diver.aethermp.api.player;

import net.mine_diver.aethermp.api.entities.IAetherBoss;

public interface CanFightBoss extends PlayerApplicableInterface {
	
	void setCurrentBoss(IAetherBoss boss);
	
	IAetherBoss getCurrentBoss();
}
