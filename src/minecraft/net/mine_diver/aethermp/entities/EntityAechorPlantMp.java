package net.mine_diver.aethermp.entities;

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
