package net.mine_diver.aethermp.items;

import java.lang.reflect.Field;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import net.mine_diver.aethermp.proxy.RandomProxy;
import net.mine_diver.aethermp.util.IDResolverResolver;
import net.minecraft.src.AetherItems;
import net.minecraft.src.BaseMod;
import net.minecraft.src.Block;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.IDResolver;
import net.minecraft.src.Item;
import net.minecraft.src.ItemHolystoneAxe;
import net.minecraft.src.ItemHolystonePickaxe;
import net.minecraft.src.ItemHolystoneSpade;
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
	
	public static void fixItems() {
		try {
			Field seedField = Random.class.getDeclaredField("seed");
			seedField.setAccessible(true);
			Field randomField = ItemHolystoneAxe.class.getDeclaredField("random");
			randomField.setAccessible(true);
			randomField.set(null, new RandomProxy(((AtomicLong) seedField.get(randomField.get(null))).get()));
			randomField = ItemHolystonePickaxe.class.getDeclaredField("random");
			randomField.setAccessible(true);
			randomField.set(null, new RandomProxy(((AtomicLong) seedField.get(randomField.get(null))).get()));
			randomField = ItemHolystoneSpade.class.getDeclaredField("random");
			randomField.setAccessible(true);
			randomField.set(null, new RandomProxy(((AtomicLong) seedField.get(randomField.get(null))).get()));
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static final ItemType[] aetherItems;
	static {
		try {
			aetherItems = new ItemType[] {
					new ItemType(ItemLoreBookMp.class, AetherItems.class.getDeclaredField("LoreBook"), mod_Aether.idItemLoreBook),
					new ItemType(ItemSwordElementalMp.class, AetherItems.class.getDeclaredField("SwordLightning"), mod_Aether.idItemSwordLightning),
					new ItemType(ItemPigSlayerMp.class, AetherItems.class.getDeclaredField("PigSlayer"), mod_Aether.idItemPigSlayer),
					new ItemType(ItemCloudStaffMp.class, AetherItems.class.getDeclaredField("CloudStaff"), mod_Aether.idItemCloudStaff),
					new ItemType(ItemSwordHolystoneMp.class, AetherItems.class.getDeclaredField("SwordHolystone"), mod_Aether.idItemSwordHolystone, (item, targetItem) -> {
						return new ItemSwordHolystoneMp(targetItem.shiftedIndex - Block.blocksList.length, EnumToolMaterial.STONE);
					})
			};
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
