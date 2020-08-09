package net.mine_diver.aetherapi.api.block;

import java.lang.reflect.Field;

import net.mine_diver.aetherapi.api.item.ItemType;
import net.minecraft.src.Block;

public class BlockType {
	
	public BlockType(Class<? extends Block> clazz, Field target, int ID, ItemType replaceItemBlock) {
		blockClass = clazz;
		targetField = target;
		originalID = ID;
		itemBlock = replaceItemBlock;
	}
	
	public BlockType(Class<? extends Block> clazz, Field target, int ID) {
		this(clazz, target, ID, null);
	}
	
	public Class<? extends Block> getBlockClass() {
		return blockClass;
	}

	public Field getTargetField() {
		return targetField;
	}

	public int getOriginalID() {
		return originalID;
	}

	public ItemType getItemBlock() {
		return itemBlock;
	}
	
	private final Class<? extends Block> blockClass;
	private final Field targetField;
	private final int originalID;
	private final ItemType itemBlock;
}
