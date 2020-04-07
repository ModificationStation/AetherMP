package net.mine_diver.aethermp.util;

import java.util.Random;

import net.mine_diver.aethermp.blocks.BlockAetherPortal;
import net.mine_diver.aethermp.blocks.BlockManager;
import net.mine_diver.aethermp.dimension.DimensionManager;
import net.minecraft.server.Block;
import net.minecraft.server.IInterceptBlockSet;
import net.minecraft.server.Loc;
import net.minecraft.server.World;
import net.minecraft.server.mod_AetherMp;

public class BlockPlacementHandler implements IInterceptBlockSet {

	@Override
	public boolean canIntercept(World arg0, Loc arg1, int arg2) {
		return (DimensionManager.getCurrentDimension(arg0) == mod_AetherMp.idDimensionAether &&
				(arg2 == Block.TORCH.id ||
	            arg2 == Block.FIRE.id ||
	            arg2 == Block.NETHERRACK.id ||
	            arg2 == Block.SOUL_SAND.id ||
	            arg2 == Block.LAVA.id ||
	            arg2 == Block.STATIONARY_LAVA.id ||
	            arg2 == Block.PORTAL.id ||
	            arg2 == Block.BED.id)) ||
				(DimensionManager.getCurrentDimension(arg0) < 0 &&
				(arg2 == BlockManager.Portal.id ||
	            arg2 == BlockManager.Dirt.id ||
	            arg2 == BlockManager.Grass.id ||
	            arg2 == BlockManager.Aercloud.id ||
	            arg2 == BlockManager.AmbrosiumOre.id ||
	            arg2 == BlockManager.AmbrosiumTorch.id ||
	            arg2 == BlockManager.DungeonStone.id ||
	            arg2 == BlockManager.EnchantedGravitite.id ||
	            arg2 == BlockManager.GoldenOakLeaves.id ||
	            arg2 == BlockManager.GoldenOakSapling.id ||
	            arg2 == BlockManager.GravititeOre.id ||
	            arg2 == BlockManager.Holystone.id ||
	            arg2 == BlockManager.Icestone.id ||
	            arg2 == BlockManager.LightDungeonStone.id ||
	            arg2 == BlockManager.LockedDungeonStone.id ||
	            arg2 == BlockManager.LockedLightDungeonStone.id ||
	            arg2 == BlockManager.ChestMimic.id ||
	            arg2 == BlockManager.Pillar.id ||
	            arg2 == BlockManager.Quicksoil.id ||
	            arg2 == BlockManager.SkyrootLeaves.id ||
	            arg2 == BlockManager.Plank.id ||
	            arg2 == BlockManager.SkyrootSapling.id ||
	            arg2 == BlockManager.Log.id ||
	            arg2 == BlockManager.Trap.id ||
	            arg2 == BlockManager.TreasureChest.id ||
	            arg2 == BlockManager.ZaniteOre.id)) ||
				arg2 == Block.WATER.id ||
				arg2 == Block.STATIONARY_WATER.id;
	}

	@Override
	public int intercept(World arg0, Loc arg1, int arg2) {
		/*if(arg2 == Block.blockBed.id)
            return BlockManager.Bed.id;*/
        if(arg2 == Block.WATER.id || arg2 == Block.STATIONARY_WATER.id) {
            if(arg0.getTypeId(arg1.x(), arg1.y() - 1, arg1.z()) == Block.GLOWSTONE.id && ((BlockAetherPortal) BlockManager.Portal).a_(arg0, arg1.x(), arg1.y(), arg1.z()))
                return BlockManager.Portal.id;
            if(DimensionManager.getCurrentDimension(arg0) != -1)
                return arg2;
        }
        Random rand = new Random();
        for(int n = 0; n < 10; n++)
            arg0.a("smoke", arg1.x + 0.5D + rand.nextGaussian() * 0.10000000000000001D, arg1.y + 0.5D + rand.nextGaussian() * 0.10000000000000001D, arg1.z + 0.5D + rand.nextGaussian() * 0.01D, rand.nextGaussian() * 0.01D, rand.nextGaussian() * 0.01D, rand.nextGaussian() * 0.10000000000000001D);

        if (arg2 == Block.LAVA.id || arg2 == Block.STATIONARY_LAVA.id)
            return BlockManager.Aerogel.id;
        if (DimensionManager.getCurrentDimension(arg0) < 0 && (arg2 == Block.WATER.id || arg2 == Block.STATIONARY_WATER.id))
            return Block.COBBLESTONE.id;
        else
            return 0;
	}
	
}
