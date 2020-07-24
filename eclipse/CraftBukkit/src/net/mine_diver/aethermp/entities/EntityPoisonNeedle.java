package net.mine_diver.aethermp.entities;

import java.util.List;

import net.mine_diver.aethermp.player.PlayerManager;
import net.mine_diver.aethermp.util.AetherPoison;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.World;

public class EntityPoisonNeedle extends EntityProjectileBase {

    public EntityPoisonNeedle(World world) {
        super(world);
    }

    public EntityPoisonNeedle(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public EntityPoisonNeedle(World world, EntityLiving ent) {
        super(world, ent);
    }
    
    @Override
    public void b() {
        super.b();
        dmg = 0;
        speed = 1.5F;
    }
    
    @Override
    public boolean f_() {
        return victim == null && super.f_();
    }
    
    @Override
    public boolean onHitTarget(Entity entity) {
        if(!(entity instanceof EntityLiving) || !AetherPoison.canPoison(entity))
            return super.onHitTarget(entity);
        EntityLiving ent = (EntityLiving)entity;
        if(ent instanceof EntityPlayer) {
        	PlayerManager.afflictPoison((EntityPlayer) ent);
            return super.onHitTarget(entity);
        }
        @SuppressWarnings("unchecked")
		List<Entity> list = world.b(this, ent.boundingBox.b(2D, 2D, 2D));
        for(int i = 0; i < list.size(); i++) {
            Entity lr2 = list.get(i);
            if(!(lr2 instanceof EntityPoisonNeedle))
                continue;
            EntityPoisonNeedle arr = (EntityPoisonNeedle)lr2;
            if(arr.victim == ent) {
                arr.poisonTime = 500;
                arr.dead = false;
                die();
                return false;
            }
        }
        victim = ent;
        ent.damageEntity(shooter, dmg);
        poisonTime = 500;
        return false;
    }
    
    @Override
    public void die() {
        victim = null;
        super.die();
    }
    
    @Override
    public boolean onHitBlock() {
        return victim == null;
    }
    
    @Override
    public boolean canBeShot(Entity ent) {
        return super.canBeShot(ent) && victim == null;
    }
    
    @Override
    public void m_() {
        super.m_();
        if(dead)
            return;
        if(victim != null) {
            if(victim.dead || poisonTime == 0) {
                die();
                return;
            }
            dead = false;
            inGround = false;
            locX = victim.locX;
            locY = victim.boundingBox.b + (double)victim.height * 0.80000000000000004D;
            locZ = victim.locZ;
            AetherPoison.distractEntity(victim);
            poisonTime--;
        }
    }

    public EntityLiving victim;
    public int poisonTime;
}
