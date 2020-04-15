package net.mine_diver.aethermp.blocks;

import java.lang.reflect.Field;

import net.minecraft.src.Block;

public class BlockType {
	
	BlockType(Class<? extends Block> clazz, Field target, int ID) {
		blockClass = clazz;
		targetField = target;
		originalID = ID;
	}
	
	Class<? extends Block> getBlockClass() {
		return blockClass;
	}
	
	Field getTargetField() {
		return targetField;
	}
	
	int getOriginalID() {
		return originalID;
	}
	
	private final Class<? extends Block> blockClass;
	private final Field targetField;
	private final int originalID;
}
