package net.mine_diver.aethermp.items;

import java.lang.reflect.Constructor;
import java.util.Properties;

import net.mine_diver.aethermp.util.IDResolverResolver;
import net.minecraft.src.AetherItems;
import net.minecraft.src.BaseMod;
import net.minecraft.src.Block;
import net.minecraft.src.IDResolver;
import net.minecraft.src.Item;
import net.minecraft.src.ModLoader;
import net.minecraft.src.mod_Aether;

import static net.minecraft.src.mod_AetherMp.PackageAccess;

public class ItemManager {
	
	public static void registerItems(BaseMod aetherInstance) {
		try {
			for (ItemType item : aetherItems) {
				Item targetItem = (Item) item.getTargetField().get(null);
				String longName = "";
				String ID = "";
				if (IDResolverResolver.IDResolverInstalled) {
					longName = "ItemID." + IDResolverResolver.TrimMCPMethod.invoke(null, targetItem.getClass().getName()) + "|" + aetherInstance.getClass().getSimpleName() + "|" + (item.getOriginalID() + Block.blocksList.length);
					Properties knownIDs = (Properties) ModLoader.getPrivateValue(IDResolver.class, null, "knownIDs");
					ID = knownIDs.getProperty(longName);
					knownIDs.remove(longName);
					IDResolverResolver.StorePropertiesMethod.invoke(null, new Object[]{});
				}
				Item.itemsList[targetItem.shiftedIndex] = null;
				Constructor<?> classConstructor = item.getBlockClass().getDeclaredConstructor(int.class);
				Item replacement = (Item) classConstructor.newInstance(targetItem.shiftedIndex - Block.blocksList.length);
				if (IDResolverResolver.IDResolverInstalled) {
					IDResolverResolver.RemoveEntryMethod.invoke(null, IDResolverResolver.GetlongNameMethod.invoke(null, replacement, replacement.shiftedIndex));
					Properties knownIDs = (Properties) ModLoader.getPrivateValue(IDResolver.class, null, "knownIDs");
					knownIDs.setProperty(longName, ID);
					IDResolverResolver.StorePropertiesMethod.invoke(null, new Object[]{});
				}
				replacement.setIconIndex(PackageAccess.Item.getIconIndex(targetItem));
				replacement.setItemName(targetItem.getItemName().substring(5));
				item.getTargetField().set(null, replacement);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static final ItemType[] aetherItems;
	static {
		try {
			aetherItems = new ItemType[] {
					new ItemType(ItemLoreBookMp.class, AetherItems.class.getDeclaredField("LoreBook"), mod_Aether.idItemLoreBook)
			};
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
