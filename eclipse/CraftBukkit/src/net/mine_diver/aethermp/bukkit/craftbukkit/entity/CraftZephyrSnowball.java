package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.entity.mod_AetherMp;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import net.mine_diver.aethermp.bukkit.entity.ZephyrSnowball;
import net.mine_diver.aethermp.entities.EntityZephyrSnowball;
import net.minecraft.server.EntityLiving;

public class CraftZephyrSnowball extends AbstractProjectileAether implements ZephyrSnowball {

	public CraftZephyrSnowball(CraftServer server, EntityZephyrSnowball entity) {
		super(server, entity);
	}
	
	@Override
	public LivingEntity getShooter() {
        if (((EntityZephyrSnowball)this.getHandle()).getShooter() != null)
            return (LivingEntity)((EntityZephyrSnowball)this.getHandle()).getShooter().getBukkitEntity();
        return null;
    }

	@Override
	public void setShooter(LivingEntity shooter) {
		if (shooter instanceof CraftLivingEntity)
            ((EntityZephyrSnowball)this.getHandle()).setShooter((EntityLiving)mod_AetherMp.PackageAccess.CraftEntity.getEntity(((CraftLivingEntity)shooter)));
	}

	@Override
	public void setDirection(Vector vector) {
		((EntityZephyrSnowball)this.getHandle()).setDirection(vector.getX(), vector.getY(), vector.getZ());
	}

	@Override
	public Vector getDirection() {
		return new Vector(((EntityZephyrSnowball)this.getHandle()).field_9405_b, ((EntityZephyrSnowball)this.getHandle()).field_9404_c, ((EntityZephyrSnowball)this.getHandle()).field_9403_d);
	}
	
	@Override
    public String toString() {
        return "CraftZephyrSnowball";
    }
}
