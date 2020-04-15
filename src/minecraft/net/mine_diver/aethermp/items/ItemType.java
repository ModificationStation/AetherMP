package net.mine_diver.aethermp.items;

import java.lang.reflect.Field;

import net.minecraft.src.Item;

public class ItemType {
	
	ItemType(Class<? extends Item> clazz, Field target, int ID) {
		itemClass = clazz;
		targetField = target;
		originalID = ID;
	}
	
	Class<? extends Item> getBlockClass() {
		return itemClass;
	}
	
	Field getTargetField() {
		return targetField;
	}
	
	int getOriginalID() {
		return originalID;
	}
	
	private final Class<? extends Item> itemClass;
	private final Field targetField;
	private final int originalID;
}
