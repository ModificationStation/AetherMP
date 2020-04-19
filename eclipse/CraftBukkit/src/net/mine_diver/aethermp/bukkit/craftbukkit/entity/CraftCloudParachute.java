package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;

import net.mine_diver.aethermp.bukkit.entity.CloudParachute;
import net.mine_diver.aethermp.entities.EntityCloudParachute;

public class CraftCloudParachute extends CraftEntityAether implements CloudParachute {

	public CraftCloudParachute(CraftServer server, EntityCloudParachute entity) {
		super(server, entity);
	}

	@Override
	public boolean isGolden() {
		return ((EntityCloudParachute)getHandle()).gold;
	}

	@Override
	public LivingEntity getAttachedEntity() {
		return (LivingEntity)((EntityCloudParachute)getHandle()).getEntityUsing().getBukkitEntity();
	}
	
	public static CraftCloudParachute getParachuteAttachedTo(CraftLivingEntity entity) {
		return (CraftCloudParachute)EntityCloudParachute.getCloudBelongingToEntity(entity.getHandle()).getBukkitEntity();
	}
	
	@Override
    public String toString() {
        return "CraftCloudParachute";
    }
}
