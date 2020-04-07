package net.mine_diver.aethermp.entities;

import net.minecraft.server.Entity;

public class EntityInfo {
	
	private EntityInfo(Class<? extends Entity> clazz, int ID, RegType regType) {
		this.entityClass = clazz;
		this.ID = ID;
		this.regType = regType;
	}
	
	EntityInfo(Class<? extends Entity> clazz, String name, int id) {
		this(clazz, id, RegType.MAIN);
		this.name = name;
	}
	
	EntityInfo(Class<? extends Entity> clazz, int id, int trackingDistance, int updateRate) {
		this(clazz, id, RegType.SECONDARY);
		this.trackingDistance = trackingDistance;
		this.updateRate = updateRate;
	}
	
	public Class<? extends Entity> getEntityClass() {
		return entityClass;
	}
	
	public String getName() {
		return name;
	}
	
	public int getID() {
		return ID;
	}
	
	public int getTrackingDistance() {
		return trackingDistance;
	}
	
	public int getUpdateRate() {
		return updateRate;
	}
	
	public RegType getRegType() {
		return regType;
	}
	
	private final Class<? extends Entity> entityClass;
	private String name;
	private final int ID;
	private int trackingDistance;
	private int updateRate;
	private final RegType regType;
	
	static enum RegType {
		MAIN,
		SECONDARY;
		
		private RegType() {
			if (!new Exception().getStackTrace()[1].getClassName().equals(getClass().getName()))
				throw new RuntimeException("Access denied");
		}
	}
}
