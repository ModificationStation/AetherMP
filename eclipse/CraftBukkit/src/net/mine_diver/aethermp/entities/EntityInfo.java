package net.mine_diver.aethermp.entities;

import net.minecraft.server.Entity;

public class EntityInfo {
	
	private EntityInfo(Class<? extends Entity> clazz, int ID, RegType regType) {
		this.entityClass = clazz;
		this.ID = ID;
		this.regType = regType;
	}
	
	EntityInfo(Class<? extends Entity> clazz, String name, int id, String behavior) {
		this(clazz, id, RegType.MAIN);
		this.name = name;
		this.behavior = behavior;
	}
	
	EntityInfo(Class<? extends Entity> clazz, String name, int id, String behavior, String suffix) {
		this(clazz, name, id, behavior);
		this.suffix = suffix;
	}
	
	EntityInfo(Class<? extends Entity> clazz, int id, int trackingDistance, int updateRate) {
		this(clazz, id, trackingDistance, updateRate, false);
	}
	
	EntityInfo(Class<? extends Entity> clazz, int id, int trackingDistance, int updateRate, boolean hasOwner) {
		this(clazz, id, RegType.SECONDARY);
		this.trackingDistance = trackingDistance;
		this.updateRate = updateRate;
		this.hasOwner = false;
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
	
	public boolean getHasOwner() {
		return hasOwner;
	}
	
	public String getBehavior() {
		return behavior;
	}
	
	public String getSuffix() {
		return suffix;
	}
	
	public RegType getRegType() {
		return regType;
	}
	
	private final Class<? extends Entity> entityClass;
	private String name;
	private final int ID;
	private int trackingDistance;
	private int updateRate;
	private boolean hasOwner;
	private String behavior;
	private String suffix;
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
