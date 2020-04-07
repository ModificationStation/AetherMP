package net.mine_diver.aethermp.render;

import net.minecraft.src.Entity;
import net.minecraft.src.Render;

public class RenderType {
	
	public RenderType(Class<? extends Entity> clazz, Render render, RegType type) {
		entity = clazz;
		instance = render;
		regType = type;
	}
	
	Class<? extends Entity> getEntity() {
		return entity;
	}
	
	Render getRender() {
		return instance;
	}
	
	RegType getRegType() {
		return regType;
	}
	
	private final Class<? extends Entity> entity;
	private final Render instance;
	private final RegType regType;
	
	
	public static enum RegType {
		ADD,
		REPLACE;
		
		private RegType() {
			if (!new Exception().getStackTrace()[1].getClassName().equals(getClass().getName()))
				throw new RuntimeException("Access denied");
		}
	}
}
