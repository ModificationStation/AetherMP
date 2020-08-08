package net.mine_diver.aethermp.block;

import java.util.Random;

import net.minecraft.src.BlockEnchanter;
import net.minecraft.src.TileEntityEnchanter;
import net.minecraft.src.World;

public class BlockEnchanterMp extends BlockEnchanter {

	protected BlockEnchanterMp(int blockID) {
		super(blockID);
	}
	
	@Override
	public void randomDisplayTick(World world, int i, int j, int k, Random random)
    {
		if (world.multiplayerWorld) {
	        TileEntityEnchanter tileentityenchanter = (TileEntityEnchanter)world.getBlockTileEntity(i, j, k);
	    	if(tileentityenchanter.getBlockMetadata() == 1)
	        {
	    		float f = (float)i + 0.5F;
	    		float f1 = (float)j + 1.0F + (random.nextFloat() * 6F) / 16F;
	    		float f2 = (float)k + 0.5F;
	    		world.spawnParticle("smoke", f, f1, f2, 0.0D, 0.0D, 0.0D);
	    		world.spawnParticle("flame", f, f1, f2, 0.0D, 0.0D, 0.0D);
	        }
		} else
			super.randomDisplayTick(world, i, j, k, random);
    }
}
