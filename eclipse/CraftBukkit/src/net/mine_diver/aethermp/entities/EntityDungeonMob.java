package net.mine_diver.aethermp.entities;

import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.event.entity.EntityTargetEvent;

import net.minecraft.server.Entity;
import net.minecraft.server.EntityCreature;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EnumSkyBlock;
import net.minecraft.server.IMonster;
import net.minecraft.server.MathHelper;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.World;

public class EntityDungeonMob extends EntityCreature implements IMonster {

    public EntityDungeonMob(World world) {
        super(world);
        attackStrength = 2;
        health = 20;
    }
    
    @Override
    public void v() {
        float f = c(1.0F);
        if(f > 0.5F)
            ay += 2;
        super.v();
    }
    
    @Override
    protected Entity findTarget() {
        EntityHuman entityhuman = world.findNearbyPlayer(this, 16D);
        if(entityhuman != null && e(entityhuman))
            return entityhuman;
        else
            return null;
    }
    
    @Override
    public boolean damageEntity(Entity entity, int i) {
        if(super.damageEntity(entity, i)) {
            if(passenger == entity || vehicle == entity)
                return true;
            if(entity != this) {
                // CraftBukkit start
                org.bukkit.entity.Entity bukkitTarget = entity == null ? null : entity.getBukkitEntity();

                EntityTargetEvent event = new EntityTargetEvent(this.getBukkitEntity(), bukkitTarget, EntityTargetEvent.TargetReason.TARGET_ATTACKED_ENTITY);
                this.world.getServer().getPluginManager().callEvent(event);

                if (!event.isCancelled()) {
                    if (event.getTarget() == null)
                        this.target = null;
                    else
                        this.target = ((CraftEntity) event.getTarget()).getHandle();
                }
                // CraftBukkit end
            }
            return true;
        } else
            return false;
    }
    
    @Override
    protected void a(Entity entity, float f) {
        if(attackTicks <= 0 && f < 2.0F && entity.boundingBox.e > boundingBox.b && entity.boundingBox.b < boundingBox.e) {
            attackTicks = 20;
            entity.damageEntity(this, attackStrength);
        }
    }
    
    @Override
    protected float a(int i, int j, int k) {
        return 0.5F - world.n(i, j, k);
    }
    
    @Override
    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
    }
    
    @Override
    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
    }
    
    @Override
    public boolean d() {
        int i = MathHelper.floor(locX);
        int j = MathHelper.floor(boundingBox.b);
        int k = MathHelper.floor(locZ);
        if(world.a(EnumSkyBlock.SKY, i, j, k) > random.nextInt(32))
            return false;
        int l = world.getLightLevel(i, j, k);
        if(world.u()) {
            int i1 = world.f;
            world.f = 10;
            l = world.getLightLevel(i, j, k);
            world.f = i1;
        }
        return l <= random.nextInt(8) && super.d();
    }

    protected int attackStrength;
}
