package net.mine_diver.aethermp.item;

import java.lang.reflect.Field;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import net.mine_diver.aetherapi.api.item.ItemType;
import net.mine_diver.aethermp.proxy.RandomProxy;
import net.minecraft.src.AetherItems;
import net.minecraft.src.Block;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.ItemHolystoneAxe;
import net.minecraft.src.ItemHolystonePickaxe;
import net.minecraft.src.ItemHolystoneSpade;
import net.minecraft.src.mod_Aether;

public class ItemManager {
	
	public static void registerItems() {
		for (ItemType item : aetherItems)
			net.mine_diver.aetherapi.impl.item.ItemManager.INSTANCE.overrideItem(item);
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
	
	private static final ItemType[] aetherItems;
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
