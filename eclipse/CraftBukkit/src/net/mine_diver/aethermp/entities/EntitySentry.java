package net.mine_diver.aethermp.entities;

import net.mine_diver.aethermp.blocks.BlockManager;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.World;

public class EntitySentry extends EntityDungeonMob {

    public EntitySentry(World world) {
        super(world);
        size = 2;
        height = 0.0F;
        aE = 1.0F;
        field_100021_a = 1.0F;
        field_100020_b = 1.0F;
        jcount = random.nextInt(20) + 10;
        func_100019_e(size);
    }

    public EntitySentry(World world, double d, double d1, double d2) {
        super(world);
        texture = "/aether/mobs/Sentry.png";
        size = 2;
        height = 0.0F;
        aE = 1.0F;
        field_100021_a = 1.0F;
        field_100020_b = 1.0F;
        jcount = random.nextInt(20) + 10;
        func_100019_e(size);
        yaw = (float)random.nextInt(4) * 1.570796F;
        setPosition(d, d1, d2);
    }

    public void func_100019_e(int i) {
        health = 10;
        length = 0.85F;
        width = 0.85F;
        setPosition(locX, locY, locZ);
        datawatcher.a(16, 0);
        datawatcher.a(17, 0);
    }
    
    @Override
    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("Size", size - 1);
        nbttagcompound.a("LostYou", datawatcher.b(16));
        nbttagcompound.a("Counter", counter);
        nbttagcompound.a("Active", datawatcher.b(17) == 1);
    }
    
    @Override
    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        size = nbttagcompound.e("Size") + 1;
        datawatcher.watch(16, nbttagcompound.e("LostYou"));
        counter = nbttagcompound.e("Counter");
        datawatcher.watch(17, nbttagcompound.m("Active") ? 1 : 0);
    }
    
    @Override
    public void m_()  {
        boolean flag = onGround;
        super.m_();
        if (onGround && !flag)
            world.makeSound(this, "mob.slime", k(), ((random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F) / 0.8F);
        else if (!onGround && flag && target != null) {
            motX *= 3D;
            motZ *= 3D;
        }
        if (target != null && target.dead)
            target = null;
    }
    
    @Override
    public void die() {
        if(health <= 0 || dead)
            super.die();
    }
    
    @Override
    public boolean damageEntity(Entity entity, int i) {
        boolean flag = super.damageEntity(entity, i);
        if (flag && (entity instanceof EntityLiving)) {
            datawatcher.watch(17, 1);;
            datawatcher.watch(16, 0);
            target = entity;
            texture = "/aether/mobs/SentryLit.png";
        }
        return flag;
    }

    public void shutdown() {
        counter = -64;
        datawatcher.watch(17, 0);
        target = null;
        texture = "/aether/mobs/Sentry.png";
        setPathEntity(null);
        az = 0.0F;
        aA = 0.0F;
        aC = false;
        motX = 0.0D;
        motZ = 0.0D;
    }
    
    @Override
    public void collide(Entity entity) {
        if(!dead && target != null && entity != null && target == entity) {
            world.a(this, locX, locY, locZ, 0.1F);
            entity.damageEntity(null, 2);
            if(entity instanceof EntityLiving) {
                EntityLiving entityliving = (EntityLiving)entity;
                double d = entityliving.locX - locX;
                double d2;
                for(d2 = entityliving.locZ - locZ; d * d + d2 * d2 < 0.0001D; d2 = (Math.random() - Math.random()) * 0.01D)
                    d = (Math.random() - Math.random()) * 0.01D;

                entityliving.a(this, 5, -d, -d2);
                entityliving.motX *= 4D;
                entityliving.motY *= 4D;
                entityliving.motZ *= 4D;
            }
            float f = 0.01745329F;
            for(int i = 0; i < 40; i++) {
                double d1 = (float)locX + random.nextFloat() * 0.25F;
                double d3 = (float)locY + 0.5F;
                double d4 = (float)locZ + random.nextFloat() * 0.25F;
                float f1 = random.nextFloat() * 360F;
                world.a("explode", d1, d3, d4, -Math.sin(f * f1) * 0.75D, 0.125D, Math.cos(f * f1) * 0.75D);
            }

            health = 0;
            die();
        }
    }
    
    @Override
    protected void c_() {
        EntityHuman entityhuman = world.findNearbyPlayer(this, 8D);
        if (datawatcher.b(17) == 0 && counter >= 8) {
            if (entityhuman != null && e(entityhuman)) {
                a(entityhuman, 10F, 10F);
                target = entityhuman;
                datawatcher.watch(17, 1);;
                datawatcher.watch(16, 0);
                texture = "/aether/mobs/SentryLit.png";
            }
            counter = 0;
        } else if (datawatcher.b(17) == 1 && counter >= 8) {
            if (target == null) {
                if (entityhuman != null && e(entityhuman)) {
                    target = entityhuman;
                    datawatcher.watch(17, 1);
                    datawatcher.watch(16, 0);
                } else {
                    datawatcher.watch(16, datawatcher.b(16)+1);
                    if (datawatcher.b(16) >= 4)
                        shutdown();
                }
            } else if (!e(target) || f(target) >= 16F) {
                datawatcher.watch(16, datawatcher.b(16)+1);
                if (datawatcher.b(16) >= 4)
                    shutdown();
            } else
                datawatcher.watch(16, 0);
            counter = 0;
        } else
            counter++;
        if (datawatcher.b(17) == 0)
            return;
        if (target != null)
            a(target, 10F, 10F);
        if (onGround && jcount-- <= 0) {
            jcount = random.nextInt(20) + 10;
            aC = true;
            az = 0.5F - random.nextFloat();
            aA = 1.0F;
            world.makeSound(this, "mob.slime", k(), ((random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F) * 0.8F);
            if (target != null) {
                jcount /= 2;
                aA = 1.0F;
            }
        } else {
            aC = false;
            if(onGround)
                az = aA = 0.0F;
        }
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
    public boolean d() {
        return super.d();
    }
    
    @Override
    protected float k() {
        return 0.6F;
    }
    
    @Override
    protected int j() {
        if(random.nextInt(5) == 0)
            return BlockManager.LightDungeonStone.id;
        else
            return BlockManager.DungeonStone.id;
    }

    public float field_100021_a;
    public float field_100020_b;
    private int jcount;
    public int size;
    public int counter;
}
