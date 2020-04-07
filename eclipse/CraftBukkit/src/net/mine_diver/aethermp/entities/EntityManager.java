package net.mine_diver.aethermp.entities;

import net.minecraft.server.ModLoader;
import net.minecraft.server.ModLoaderMp;
import net.minecraft.server.mod_AetherMp;

public class EntityManager {
	
	public static void registerEntities() {
		for (EntityInfo entity : entities)
			switch(entity.getRegType()){
			case MAIN: {
				ModLoader.RegisterEntityID(entity.getEntityClass(), entity.getName(), entity.getID());
				break;
			}
			case SECONDARY: {
				ModLoaderMp.RegisterEntityTracker(entity.getEntityClass(), entity.getTrackingDistance(), entity.getUpdateRate());
				ModLoaderMp.RegisterEntityTrackerEntry(entity.getEntityClass(), entity.getID());
				break;
			}
			}
	}
	
	public static final EntityInfo[] entities = new EntityInfo[] {
			new EntityInfo(EntityCloudParachute.class, mod_AetherMp.idEntityCloudParachute, 160, 5),
			new EntityInfo(EntityFloatingBlock.class, mod_AetherMp.idEntityFloatingBlock, 160, 20),
			new EntityInfo(EntityMimic.class, "Mimic", mod_AetherMp.idEntityMimic),
			new EntityInfo(EntitySentry.class, "Sentry", mod_AetherMp.idEntitySentry),
			new EntityInfo(EntitySheepuff.class, "Sheepuff", mod_AetherMp.idEntitySheepuff),
			new EntityInfo(EntityZephyr.class, "Zephyr", mod_AetherMp.idEntityZephyr),
			new EntityInfo(EntityZephyrSnowball.class, mod_AetherMp.idEntityZephyrSnowball, 64, 10)
	};
}
