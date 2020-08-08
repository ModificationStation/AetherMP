package net.mine_diver.aethermp.block;

import net.minecraft.src.BlockChestMimic;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;

public class BlockChestMimicMp extends BlockChestMimic {

	protected BlockChestMimicMp(int i) {
		super(i);
	}
	
	@Override
	public void onBlockRemoval(World world, int i, int j, int k) {
        if (!world.multiplayerWorld)
        	super.onBlockRemoval(world, i, j, k);
    }
	
	@Override
    public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer) {
		if (!world.multiplayerWorld)
			world.setBlockWithNotify(i, j, k, 0);
        return true;
    }
}
