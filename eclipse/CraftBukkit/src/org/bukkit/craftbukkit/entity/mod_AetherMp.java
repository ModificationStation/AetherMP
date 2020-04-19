package org.bukkit.craftbukkit.entity;

import org.bukkit.plugin.java.JavaPlugin;

import net.minecraft.server.Entity;

public class mod_AetherMp extends JavaPlugin {

	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEnable() {
		// TODO Auto-generated method stub
		
	}
	
	public static class PackageAccess {
		
		public static class CraftEntity {
			
			public static Entity getEntity(org.bukkit.craftbukkit.entity.CraftEntity craftentity) {
				return craftentity.entity;
			}
		}
	}
}
