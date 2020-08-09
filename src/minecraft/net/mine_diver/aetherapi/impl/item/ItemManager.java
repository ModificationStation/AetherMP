package net.mine_diver.aetherapi.impl.item;

import java.util.Arrays;
import java.util.Properties;

import net.mine_diver.aetherapi.api.item.ItemType;
import net.mine_diver.aetherapi.impl.util.IDResolverResolver;
import net.minecraft.src.BaseMod;
import net.minecraft.src.Block;
import net.minecraft.src.IDResolver;
import net.minecraft.src.Item;
import net.minecraft.src.ModLoader;

import static net.minecraft.src.mod_AetherAPI.PackageAccess;

public class ItemManager implements net.mine_diver.aetherapi.api.item.ItemManager {
	
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
				Item replacement = item.getFactory().createInstance(item, targetItem);
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

	@Override
	public void setHandler(net.mine_diver.aetherapi.api.item.ItemManager handler) {
		INSTANCE.setHandler(handler);
	}
	
	@Override
	public void overrideItem(ItemType item) {
		aetherItems = Arrays.copyOf(aetherItems, aetherItems.length + 1);
		aetherItems[aetherItems.length - 1] = item;
	}
	
	private static ItemType[] aetherItems = new ItemType[0];
}
