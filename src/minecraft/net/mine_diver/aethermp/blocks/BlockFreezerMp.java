package net.mine_diver.aethermp.blocks;

import java.util.Random;

import net.minecraft.src.BlockFreezer;
import net.minecraft.src.TileEntityFreezer;
import net.minecraft.src.World;

public class BlockFreezerMp extends BlockFreezer {

	protected BlockFreezerMp(int blockID) {
		super(blockID);
	}
	
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
		if (world.multiplayerWorld) {
	        TileEntityFreezer tileentity = (TileEntityFreezer)world.getBlockTileEntity(i, j, k);
	        if(tileentity.getBlockMetadata() == 1) {
	            float f = (float)i + 0.5F;
	            float f1 = (float)j + 1.0F + (random.nextFloat() * 6F) / 16F;
	            float f2 = (float)k + 0.5F;
	            world.spawnParticle("smoke", f, f1, f2, 0.0D, 0.0D, 0.0D);
	            world.spawnParticle("largesmoke", f, f1, f2, 0.0D, 0.0D, 0.0D);
	            world.spawnParticle("snowshovel", f, f1, f2, 0.0D, 0.0D, 0.0D);
	            world.spawnParticle("snowshovel", f, f1, f2, 0.0D, 0.0D, 0.0D);
	        }
        } else {
        	super.randomDisplayTick(world, i, j, k, random);
        }
    }
}
