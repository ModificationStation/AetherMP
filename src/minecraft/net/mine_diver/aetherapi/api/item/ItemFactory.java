package net.mine_diver.aetherapi.api.item;

import java.lang.reflect.InvocationTargetException;

import net.minecraft.src.Block;
import net.minecraft.src.Item;

public interface ItemFactory {
	
	Item createInstance(ItemType item, Item targetItem) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException;
	
	public static final ItemFactory DEFAULT = (item, targetItem) -> {
		return item.getItemClass().getDeclaredConstructor(int.class).newInstance(targetItem.shiftedIndex - Block.blocksList.length);
	};
}
