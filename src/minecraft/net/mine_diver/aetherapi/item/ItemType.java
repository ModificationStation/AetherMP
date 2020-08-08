package net.mine_diver.aetherapi.item;

import java.lang.reflect.Field;

import net.minecraft.src.Item;

public class ItemType {
	
	public ItemType(Class<? extends Item> clazz, Field target, int ID, ItemFactory itemFactory) {
		itemClass = clazz;
		targetField = target;
		originalID = ID;
		factory = itemFactory;
	}
	
	public ItemType(Class<? extends Item> clazz, Field target, int ID) {
		this(clazz, target, ID, ItemFactory.DEFAULT);
	}
	
	public Class<? extends Item> getItemClass() {
		return itemClass;
	}
	
	public Field getTargetField() {
		return targetField;
	}
	
	public int getOriginalID() {
		return originalID;
	}
	
	public ItemFactory getFactory() {
		return factory;
	}
	
	private final Class<? extends Item> itemClass;
	private final Field targetField;
	private final int originalID;
	private final ItemFactory factory;
}
