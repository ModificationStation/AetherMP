package org.bukkit.craftbukkit.entity;

import net.minecraft.server.Entity;

public class mod_AetherMp {
	
	public static class PackageAccess {
		
		public static class CraftEntity {
			
			public static Entity getEntity(org.bukkit.craftbukkit.entity.CraftEntity craftentity) {
				return craftentity.entity;
			}
		}
	}
}
