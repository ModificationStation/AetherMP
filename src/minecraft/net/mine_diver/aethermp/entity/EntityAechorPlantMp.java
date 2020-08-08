package net.mine_diver.aethermp.entity;

import net.minecraft.src.EntityAechorPlant;
import net.minecraft.src.World;

public class EntityAechorPlantMp extends EntityAechorPlant {

	public EntityAechorPlantMp(World world1) {
		super(world1);
	}
	
	@Override
	public void shootTarget() {
        if (!worldObj.multiplayerWorld)
        	super.shootTarget();
    }
}
