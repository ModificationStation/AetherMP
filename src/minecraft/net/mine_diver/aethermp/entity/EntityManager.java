package net.mine_diver.aethermp.entity;

import net.mine_diver.aethermp.Core;
import net.mine_diver.aethermp.entity.EntityType.RegType;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityAechorPlant;
import net.minecraft.src.EntityList;
import net.minecraft.src.EntityMimic;
import net.minecraft.src.EntitySentry;
import net.minecraft.src.EntitySheepuff;
import net.minecraft.src.EntityZephyr;
import net.minecraft.src.ModLoader;
import net.minecraft.src.ModLoaderMp;
import net.minecraft.src.WorldClient;
import net.minecraft.src.mod_AetherMp;

public class EntityManager {
	
	public static void registerEntities() {
		for (EntityType entity : aetherEntities)
			switch (entity.getRegType()) {
				case MAIN: {
					ModLoader.RegisterEntityID(entity.getEntityClass(), getEntityName(entity.getEntityClass()), entity.getEntityID());
					break;
				}
				case SECONDARY: {
					ModLoaderMp.RegisterNetClientHandlerEntity(entity.getEntityClass(), entity.getHasOwner(), entity.getEntityID());
					break;
				}
			}
	}
	
	public static String getEntityName(Class<? extends Entity> entity) {
		try {
			return EntityList.getEntityString((Entity) Core.unsafe.allocateInstance(entity));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Entity getEntityByID(int i) {
        if(i == ModLoader.getMinecraftInstance().thePlayer.entityId)
            return ModLoader.getMinecraftInstance().thePlayer;
        else
            return ((WorldClient) ModLoader.getMinecraftInstance().theWorld).getEntityByID(i);
    }
	
	public static final EntityType[] aetherEntities = new EntityType[] {
			new EntityType(EntityCloudParachuteMp.class, mod_AetherMp.idEntityCloudParachute, RegType.SECONDARY),
			new EntityType(EntityFloatingBlockMp.class, mod_AetherMp.idEntityFloatingBlock, RegType.SECONDARY),
			new EntityType(EntityMimic.class, mod_AetherMp.idEntityMimic, RegType.MAIN),
			new EntityType(EntityMimicMp.class, mod_AetherMp.idEntityMimic, RegType.MAIN),
			new EntityType(EntitySentry.class, mod_AetherMp.idEntitySentry, RegType.MAIN),
			new EntityType(EntitySentryMp.class, mod_AetherMp.idEntitySentry, RegType.MAIN),
			new EntityType(EntitySheepuff.class, mod_AetherMp.idEntitySheepuff, RegType.MAIN),
			new EntityType(EntityZephyr.class, mod_AetherMp.idEntityZephyr, RegType.MAIN),
			new EntityType(EntityZephyrSnowballMp.class, mod_AetherMp.idEntityZephyrSnowball, RegType.SECONDARY),
			new EntityType(EntityDartEnchantedMp.class, mod_AetherMp.idEntityDartEnchanted, RegType.SECONDARY),
			new EntityType(EntityDartGoldenMp.class, mod_AetherMp.idEntityDartGolden, RegType.SECONDARY),
			new EntityType(EntityDartPoisonMp.class, mod_AetherMp.idEntityDartPoison, RegType.SECONDARY),
			new EntityType(EntityLightningKnifeMp.class, mod_AetherMp.idEntityLightningKnife, RegType.SECONDARY),
			new EntityType(EntityNotchWaveMp.class, mod_AetherMp.idEntityNotchWave, RegType.SECONDARY),
			new EntityType(EntityFlamingArrowMp.class, mod_AetherMp.idEntityFlamingArrow, RegType.MAIN),
			new EntityType(EntityFlamingArrowMp.class, mod_AetherMp.idEntityFlamingArrow, RegType.SECONDARY),
			new EntityType(EntityMiniCloudMp.class, mod_AetherMp.idEntityMiniCloud, RegType.SECONDARY),
			new EntityType(EntityFiroBallMp.class, mod_AetherMp.idEntityFiroBall, RegType.SECONDARY),
			new EntityType(EntityAechorPlant.class, mod_AetherMp.idEntityAechorPlant, RegType.MAIN),
			new EntityType(EntityAechorPlantMp.class, mod_AetherMp.idEntityAechorPlant, RegType.MAIN),
			new EntityType(EntityPoisonNeedleMp.class, mod_AetherMp.idEntityPoisonNeedle, RegType.SECONDARY),
			new EntityType(EntitySliderMp.class, mod_AetherMp.idEntitySlider, RegType.SECONDARY)
	};
}