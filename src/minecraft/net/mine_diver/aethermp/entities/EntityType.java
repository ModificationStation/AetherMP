package net.mine_diver.aethermp.entities;

import net.minecraft.src.Entity;

public class EntityType {
	
	public EntityType(Class<? extends Entity> clazz, int id, RegType type) {
		entityClass = clazz;
		entityID = id;
		regType = type;
	}
	
	Class<? extends Entity> getEntityClass() {
		return entityClass;
	}
	
	int getEntityID() {
		return entityID;
	}
	
	RegType getRegType() {
		return regType;
	}
	
	private final Class<? extends Entity> entityClass;
	private final int entityID;
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
