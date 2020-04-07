package net.mine_diver.aethermp.entities;

import net.minecraft.server.Block;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.World;

public class EntityMimic extends EntityDungeonMob {

    public EntityMimic(World world) {
        super(world);
        height = 0.0F;
        b(1.0F, 2.0F);
        health = 40;
        attackStrength = 5;
        target = world.findNearbyPlayer(this, 64D);
        datawatcher.a(16, 0);
    }
    
    @Override
    public void m_() {
        super.m_();
        if(motX > 0.001D || motX < -0.001D || motZ > 0.001D || motZ < -0.001D)
            datawatcher.watch(16, 1);
        else
            datawatcher.watch(16, 0);
    }
    
    @Override
    public void collide(Entity entity) {
        if(!dead && entity != null)
            entity.damageEntity(this, 4);
    }
    
    @Override
    public boolean damageEntity(Entity entity, int i) {
        if(entity instanceof EntityPlayer) {
            a(entity, 10F, 10F);
            target = (EntityPlayer) entity;
        }
        return super.damageEntity(entity, i);
    }
    
    @Override
    protected String h() {
        return "mob.slime";
    }
    
    @Override
    protected String i() {
        return "mob.slime";
    }
    
    @Override
    protected float k() {
        return 0.6F;
    }
    
    @Override
    protected int j() {
        return Block.CHEST.id;
    }
}
