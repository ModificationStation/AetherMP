package net.mine_diver.aethermp.entities;

import java.util.List;

import net.mine_diver.aethermp.items.ItemManager;
import net.mine_diver.aethermp.player.PlayerManager;
import net.mine_diver.aethermp.util.AetherPoison;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.ItemStack;
import net.minecraft.server.World;

public class EntityDartPoison extends EntityDartGolden {

    public EntityDartPoison(World world) {
        super(world);
    }

    public EntityDartPoison(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public EntityDartPoison(World world, EntityLiving ent) {
        super(world, ent);
    }
    
    @Override
    public void b() {
        super.b();
        item = new ItemStack(ItemManager.Dart, 1, 1);
        dmg = 2;
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
            if(!(lr2 instanceof EntityDartPoison))
                continue;
            EntityDartPoison arr = (EntityDartPoison)lr2;
            if(arr.victim == ent) {
                arr.poisonTime = 500;
                arr.dead = false;
                ent.damageEntity(shooter, dmg);
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
            if(poisonTime % 50 == 0)
                victim.damageEntity(shooter, 1);
        }
    }

    public EntityLiving victim;
    public int poisonTime;
}
