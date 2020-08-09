package net.mine_diver.aethermp.block;

import net.mine_diver.aetherapi.api.block.BlockType;
import net.minecraft.src.AetherBlocks;
import net.minecraft.src.mod_Aether;

public class BlockManager {
	
	public static void registerBlocks() {
		for (BlockType block : aetherBlocks)
			net.mine_diver.aetherapi.api.block.BlockManager.INSTANCE.overrideBlock(block);
	}
	
	private static final BlockType[] aetherBlocks;
	static {
		try {
			aetherBlocks = new BlockType[] {
					new BlockType(BlockChestMimicMp.class, AetherBlocks.class.getDeclaredField("ChestMimic"), mod_Aether.idBlockChestMimic),
					new BlockType(BlockTrapMp.class, AetherBlocks.class.getDeclaredField("Trap"), mod_Aether.idBlockTrap),
					new BlockType(BlockEnchanterMp.class, AetherBlocks.class.getDeclaredField("Enchanter"), mod_Aether.idBlockEnchanter),
					new BlockType(BlockFreezerMp.class, AetherBlocks.class.getDeclaredField("Freezer"), mod_Aether.idBlockFreezer)
			};
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
