package net.mine_diver.aethermp.items;

import java.lang.reflect.Field;

import net.minecraft.src.Item;

public class ItemType {
	
	ItemType(Class<? extends Item> clazz, Field target, int ID, ItemFactory itemFactory) {
		itemClass = clazz;
		targetField = target;
		originalID = ID;
		factory = itemFactory;
	}
	
	ItemType(Class<? extends Item> clazz, Field target, int ID) {
		this(clazz, target, ID, ItemFactory.DEFAULT);
	}
	
	Class<? extends Item> getItemClass() {
		return itemClass;
	}
	
	Field getTargetField() {
		return targetField;
	}
	
	int getOriginalID() {
		return originalID;
	}
	
	ItemFactory getFactory() {
		return factory;
	}
	
	private final Class<? extends Item> itemClass;
	private final Field targetField;
	private final int originalID;
	private final ItemFactory factory;
}
