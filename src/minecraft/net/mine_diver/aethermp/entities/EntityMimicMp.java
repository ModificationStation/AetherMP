package net.mine_diver.aethermp.entities;

import net.minecraft.src.EntityMimic;
import net.minecraft.src.MathHelper;
import net.minecraft.src.World;

public class EntityMimicMp extends EntityMimic {

	public EntityMimicMp(World world) {
		super(world);
		legsDirection = 1.0F;
	}
	
	@Override
	public void onUpdate()
    {
        super.onUpdate();
        if (worldObj.multiplayerWorld && (MathHelper.abs((float) (posX - prevPosX)) > 0.001D || MathHelper.abs((float) (posZ - prevPosZ)) > 0.001D)) {
        	legs += legsDirection * 0.2F;
            if(legs > 1.0F)
            {
                legsDirection = -1F;
            }
            if(legs < -1F)
            {
                legsDirection = 1.0F;
            }
        }
    }

	private float legsDirection;
}
