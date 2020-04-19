package net.mine_diver.aethermp.entities;

import net.mine_diver.aethermp.bukkit.craftbukkit.entity.CraftEntityAether;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityFlying;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.ISpawnable;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.Packet230ModLoader;
import net.minecraft.server.Vec3D;
import net.minecraft.server.World;

public class EntityMiniCloud extends EntityFlying implements ISpawnable {

    public EntityMiniCloud(World world) {
        super(world);
        texture = "/aether/mobs/minicloud.png";
        b(0.0F, 0.0F);
        bt = true;
        bu = 1.75F;
    }

    public EntityMiniCloud(World world, EntityHuman eh, boolean flag) {
        super(world);
        texture = "/aether/mobs/minicloud.png";
        b(0.5F, 0.45F);
        dude = eh;
        toLeft = flag;
        lifeSpan = 3600;
        getTargetPos();
        setPosition(targetX, targetY, targetZ);
        pitch = dude.pitch;
        yaw = dude.yaw;
        bu = 1.75F;
    }

    public void getTargetPos() {
        if(f(dude) > 2.0F) {
            targetX = dude.locX;
            targetY = dude.locY - 0.10000000149011612D;
            targetZ = dude.locZ;
        } else {
            double angle = dude.yaw;
            if(toLeft)
                angle -= 90D;
            else
                angle += 90D;
            angle /= -57.295773195318432D;
            targetX = dude.locX + Math.sin(angle) * 1.05D;
            targetY = dude.locY + 1.4D;
            targetZ = dude.locZ + Math.cos(angle) * 1.05D;
        }
    }

    public boolean atShoulder() {
        double a = locX - targetX;
        double b = locY - targetY;
        double c = locZ - targetZ;
        return Math.sqrt(a * a + b * b + c * c) < 0.29999999999999999D;
    }

    public void approachTarget() {
        double a = targetX - locX;
        double b = targetY - locY;
        double c = targetZ - locZ;
        double d = Math.sqrt(a * a + b * b + c * c) * 3.25D;
        motX = (motX + a / d) / 2D;
        motY = (motY + b / d) / 2D;
        motZ = (motZ + c / d) / 2D;
    }

    protected Entity findPlayer() {
        EntityHuman entityplayer = world.findNearbyPlayer(this, 16D);
        if(entityplayer != null && e(entityplayer))
            return entityplayer;
        else
            return null;
    }
    
    @Override
    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("LifeSpan", (short)lifeSpan);
        nbttagcompound.a("ShotTimer", (short)shotTimer);
        gotPlayer = dude != null;
        nbttagcompound.a("GotPlayer", gotPlayer);
        nbttagcompound.a("ToLeft", toLeft);
    }
    
    @Override
    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        lifeSpan = nbttagcompound.d("LifeSpan");
        shotTimer = nbttagcompound.d("ShotTimer");
        gotPlayer = nbttagcompound.m("GotPlayer");
        toLeft = nbttagcompound.m("ToLeft");
    }
    
    @Override
    public void c_() {
        super.c_();
        lifeSpan--;
        if(lifeSpan <= 0) {
            dead = true;
            return;
        }
        if(shotTimer > 0)
            shotTimer--;
        if(gotPlayer && dude == null) {
            gotPlayer = false;
            dude = (EntityLiving)findPlayer();
        }
        if(dude == null || dude.dead) {
            dead = true;
            return;
        }
        getTargetPos();
        if(atShoulder()) {
            motX *= 0.65000000000000002D;
            motY *= 0.65000000000000002D;
            motZ *= 0.65000000000000002D;
            yaw = dude.yaw + (toLeft ? 1.0F : -1F);
            pitch = dude.pitch;
            if(shotTimer <= 0 && (dude instanceof EntityHuman) && ((EntityHuman)dude).p) {
                float spanish = yaw - (toLeft ? 1.0F : -1F);
                double a = locX + Math.sin((double)spanish / -57.295773195318432D) * 1.6000000000000001D;
                double b = locY - 0.25D;
                double c = locZ + Math.cos((double)spanish / -57.295773195318432D) * 1.6000000000000001D;
                EntityFiroBall eh = new EntityFiroBall(world, a, b, c, true, true);
                world.addEntity(eh);
                Vec3D vec3d = Z();
                if(vec3d != null) {
                    eh.smotionX = vec3d.a * 1.5D;
                    eh.smotionY = vec3d.b * 1.5D;
                    eh.smotionZ = vec3d.c * 1.5D;
                }
                eh.smacked = true;
                shotTimer = 40;
            }
        } else
            approachTarget();
    }
    
    @Override
    public boolean damageEntity(Entity e, int i) {
        if(e != null && e == dude)
            return false;
        else
            return super.damageEntity(e, i);
    }
    
    @Override
    public org.bukkit.entity.Entity getBukkitEntity() {
        if (this.bukkitEntity == null)
            this.bukkitEntity = CraftEntityAether.getEntity(this.world.getServer(), this);
        return this.bukkitEntity;
    }
    
	@Override
	public Packet230ModLoader getSpawnPacket() {
		Packet230ModLoader packet = new Packet230ModLoader();
		packet.dataInt = new int[] {id, dude.id, toLeft ? 1 : 0};
		packet.dataFloat = new float[] {(float) locX, (float) locY, (float) locZ};
		return packet;
	}

    public int shotTimer;
    public int lifeSpan;
    public boolean gotPlayer;
    public boolean toLeft;
    public EntityLiving dude;
    public double targetX;
    public double targetY;
    public double targetZ;
}
