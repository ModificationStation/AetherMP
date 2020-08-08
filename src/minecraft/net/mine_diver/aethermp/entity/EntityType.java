package net.mine_diver.aethermp.entity;

import net.minecraft.src.Entity;

public class EntityType {
	
	public EntityType(Class<? extends Entity> clazz, int id, RegType type) {
		this(clazz, id, false, type);
	}
	
	public EntityType(Class<? extends Entity> clazz, int id, boolean owned, RegType type) {
		entityClass = clazz;
		entityID = id;
		hasOwner = owned;
		regType = type;
	}
	
	Class<? extends Entity> getEntityClass() {
		return entityClass;
	}
	
	int getEntityID() {
		return entityID;
	}
	
	boolean getHasOwner() {
		return hasOwner;
	}
	
	RegType getRegType() {
		return regType;
	}
	
	private final Class<? extends Entity> entityClass;
	private final int entityID;
	private final boolean hasOwner;
	private final RegType regType;
	
	public static enum RegType {
		MAIN,
		SECONDARY;
		
		private RegType() {
			if (!new Exception().getStackTrace()[1].getClassName().equals(getClass().getName()))
				throw new RuntimeException("Access denied");
		}
	}
}
