package net.mine_diver.aethermp.blocks;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Properties;

import net.minecraft.src.AetherBlocks;
import net.minecraft.src.BaseMod;
import net.minecraft.src.Block;
import net.minecraft.src.IDResolver;
import net.minecraft.src.ModLoader;
import net.minecraft.src.mod_Aether;

import static net.minecraft.src.mod_AetherMp.PackageAccess;

public class BlockManager {
	
	public static void registerBlocks(BaseMod aetherInstance) {
		try {
			for (BlockType block : aetherBlocks) {
				Block targetBlock = (Block) block.getTargetField().get(null);
				String longName = "";
				String ID = "";
				if (IDResolverInstalled) {
					longName = "BlockID." + TrimMCPMethod.invoke(null, targetBlock.getClass().getName()) + "|" + aetherInstance.getClass().getSimpleName() + "|" + block.getOriginalID();
					Properties knownIDs = (Properties) ModLoader.getPrivateValue(IDResolver.class, null, "knownIDs");
					ID = knownIDs.getProperty(longName);
					knownIDs.remove(longName);
					StorePropertiesMethod.invoke(null, new Object[]{});
				}
				Block.blocksList[targetBlock.blockID] = null;
				Constructor<?> classConstructor = block.getBlockClass().getDeclaredConstructor(int.class);
				Block replacement = (Block) classConstructor.newInstance(targetBlock.blockID);
				if (IDResolverInstalled) {
					RemoveEntryMethod.invoke(null, GetlongNameMethod.invoke(null, replacement, replacement.blockID));
					Properties knownIDs = (Properties) ModLoader.getPrivateValue(IDResolver.class, null, "knownIDs");
					knownIDs.setProperty(longName, ID);
					StorePropertiesMethod.invoke(null, new Object[]{});
				}
				PackageAccess.Block.setHardness(replacement, targetBlock.getHardness());
				PackageAccess.Block.setResistance(replacement, PackageAccess.Block.getResistance(targetBlock));
				PackageAccess.Block.setStepSound(replacement, targetBlock.stepSound);
				replacement.setBlockName(targetBlock.getBlockName().substring(5));
				block.getTargetField().set(null, replacement);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static final BlockType[] aetherBlocks;
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
	
	private static final boolean IDResolverInstalled = ModLoader.isModLoaded("mod_IDResolver");
	private static final Method GetlongNameMethod;
	private static final Method RemoveEntryMethod;
	private static final Method StorePropertiesMethod;
	private static final Method TrimMCPMethod;
	static {
		Method method = null;
		if (IDResolverInstalled)
			try {
				method = IDResolver.class.getDeclaredMethod("GetlongName", Object.class, int.class);
				method.setAccessible(true);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		GetlongNameMethod = method;
		if (IDResolverInstalled)
			try {
				method = IDResolver.class.getDeclaredMethod("RemoveEntry", String.class);
				method.setAccessible(true);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		RemoveEntryMethod = method;
		if (IDResolverInstalled)
			try {
				method = IDResolver.class.getDeclaredMethod("StoreProperties");
				method.setAccessible(true);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		StorePropertiesMethod = method;
		if (IDResolverInstalled)
			try {
				method = IDResolver.class.getDeclaredMethod("TrimMCP", String.class);
				method.setAccessible(true);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		TrimMCPMethod = method;
	}
}
