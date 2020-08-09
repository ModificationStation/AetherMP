package net.mine_diver.aetherapi.impl.block;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Properties;

import net.mine_diver.aetherapi.api.block.BlockType;
import net.mine_diver.aetherapi.api.item.ItemType;
import net.mine_diver.aetherapi.impl.util.IDResolverResolver;
import net.minecraft.src.BaseMod;
import net.minecraft.src.Block;
import net.minecraft.src.IDResolver;
import net.minecraft.src.Item;
import net.minecraft.src.ModLoader;

import static net.minecraft.src.mod_AetherAPI.PackageAccess;

public class BlockManager implements net.mine_diver.aetherapi.api.block.BlockManager {
	
	public static void registerBlocks(BaseMod aetherInstance) {
		try {
			for (BlockType block : aetherBlocks) {
				Block targetBlock = (Block) block.getTargetField().get(null);
				String longName = "";
				String ID = "";
				if (IDResolverResolver.IDResolverInstalled) {
					longName = "BlockID." + IDResolverResolver.TrimMCPMethod.invoke(null, targetBlock.getClass().getName()) + "|" + aetherInstance.getClass().getSimpleName() + "|" + block.getOriginalID();
					Properties knownIDs = (Properties) ModLoader.getPrivateValue(IDResolver.class, null, "knownIDs");
					ID = knownIDs.getProperty(longName);
					knownIDs.remove(longName);
					IDResolverResolver.StorePropertiesMethod.invoke(null, new Object[]{});
				}
				Block.blocksList[targetBlock.blockID] = null;
				Constructor<?> classConstructor = block.getBlockClass().getDeclaredConstructor(int.class);
				classConstructor.setAccessible(true);
				Block replacement = (Block) classConstructor.newInstance(targetBlock.blockID);
				ItemType itemBlock = block.getItemBlock();
				if (itemBlock != null)
					Item.itemsList[targetBlock.blockID] = itemBlock.getFactory().createInstance(itemBlock, Item.itemsList[targetBlock.blockID]);
				if (IDResolverResolver.IDResolverInstalled) {
					IDResolverResolver.RemoveEntryMethod.invoke(null, IDResolverResolver.GetlongNameMethod.invoke(null, replacement, replacement.blockID));
					Properties knownIDs = (Properties) ModLoader.getPrivateValue(IDResolver.class, null, "knownIDs");
					knownIDs.setProperty(longName, ID);
					IDResolverResolver.StorePropertiesMethod.invoke(null, new Object[]{});
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

	@Override
	public void setHandler(net.mine_diver.aetherapi.api.block.BlockManager handler) {
		INSTANCE.setHandler(handler);
	}
	
	@Override
	public void overrideBlock(BlockType block) {
		aetherBlocks = Arrays.copyOf(aetherBlocks, aetherBlocks.length + 1);
		aetherBlocks[aetherBlocks.length - 1] = block;
	}
	
	private static BlockType[] aetherBlocks = new BlockType[0];
}
