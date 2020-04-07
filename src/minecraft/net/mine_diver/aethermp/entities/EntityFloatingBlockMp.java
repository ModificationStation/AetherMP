package net.mine_diver.aethermp.entities;

import net.minecraft.src.EntityFloatingBlock;
import net.minecraft.src.World;

public class EntityFloatingBlockMp extends EntityFloatingBlock {
	
	public EntityFloatingBlockMp(World world, double d, double d1, double d2)
    {
        super(world, d, d1, d2, 0, 0);
    }
	
	@Override
    public void outfitWithItem(int i, int id, int meta){
    	blockID = id;
    	metadata = meta;
    }
}
