package net.mine_diver.aethermp.blocks;

import net.minecraft.src.BlockTrap;
import net.minecraft.src.Entity;
import net.minecraft.src.World;

public class BlockTrapMp extends BlockTrap
{

    public BlockTrapMp(int i)
    {
        super(i);
    }
    
    @Override
    public void onEntityWalking(World world, int i, int j, int k, Entity entity)
    {
    	if (!world.multiplayerWorld)
	        super.onEntityWalking(world, i, j, k, entity);
    }
}
