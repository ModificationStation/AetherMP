package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.entity.mod_AetherMp.PackageAccess;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;

import net.mine_diver.aethermp.entities.EntityProjectileBase;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityLiving;

public abstract class AbstractProjectileBase extends CraftEntityAether implements Projectile {
	
	private boolean doesBounce;
	
	public AbstractProjectileBase(CraftServer server, Entity entity) {
		super(server, entity);
	}
	
	@Override
	public boolean doesBounce() {
        return this.doesBounce;
    }
    
	@Override
    public void setBounce(final boolean doesBounce) {
        this.doesBounce = doesBounce;
    }
	
	@Override
    public LivingEntity getShooter() {
        if (((EntityProjectileBase)this.getHandle()).shooter != null)
            return (LivingEntity)((EntityProjectileBase)this.getHandle()).shooter.getBukkitEntity();
        return null;
    }
    
	@Override
    public void setShooter(LivingEntity shooter) {
        if (shooter instanceof CraftLivingEntity)
            ((EntityProjectileBase)this.getHandle()).shooter = (EntityLiving) PackageAccess.CraftEntity.getEntity((CraftLivingEntity)shooter);
    }
}
