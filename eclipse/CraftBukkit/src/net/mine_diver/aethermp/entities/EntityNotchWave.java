package net.mine_diver.aethermp.entities;

import java.util.List;

import net.mine_diver.aethermp.bukkit.craftbukkit.entity.CraftEntityAether;
import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.ISpawnable;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.MathHelper;
import net.minecraft.server.MovingObjectPosition;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.Packet230ModLoader;
import net.minecraft.server.Vec3D;
import net.minecraft.server.World;

public class EntityNotchWave extends Entity implements ISpawnable {

    public EntityNotchWave(World world) {
        super(world);
        xTileSnowball = -1;
        yTileSnowball = -1;
        zTileSnowball = -1;
        inTileSnowball = 0;
        inGroundSnowball = false;
        shakeSnowball = 0;
        ticksInAirSnowball = 0;
        b(0.25F, 0.25F);
    }

    protected void b() {}

    public EntityNotchWave(World world, EntityLiving entityliving) {
        super(world);
        xTileSnowball = -1;
        yTileSnowball = -1;
        zTileSnowball = -1;
        inTileSnowball = 0;
        inGroundSnowball = false;
        shakeSnowball = 0;
        ticksInAirSnowball = 0;
        thrower = entityliving;
        b(0.25F, 0.25F);
        setLocation(entityliving.locX, entityliving.locY + (double)entityliving.t(), entityliving.locZ, entityliving.yaw, entityliving.pitch);
        locX -= MathHelper.cos((yaw / 180F) * 3.141593F) * 0.16F;
        locY -= 0.10000000149011612D;
        locZ -= MathHelper.sin((yaw / 180F) * 3.141593F) * 0.16F;
        setPositionRotation(locX, locY, locZ, yaw, pitch);
        height = 0.0F;
        float f = 0.4F;
        motX = -MathHelper.sin((yaw / 180F) * 3.141593F) * MathHelper.cos((pitch / 180F) * 3.141593F) * f;
        motZ = MathHelper.cos((yaw / 180F) * 3.141593F) * MathHelper.cos((pitch / 180F) * 3.141593F) * f;
        motY = -MathHelper.sin((pitch / 180F) * 3.141593F) * f;
        setSnowballHeading(motX, motY, motZ, 1.5F, 1.0F);
    }

    public EntityNotchWave(World world, double d, double d1, double d2) {
        super(world);
        xTileSnowball = -1;
        yTileSnowball = -1;
        zTileSnowball = -1;
        inTileSnowball = 0;
        inGroundSnowball = false;
        shakeSnowball = 0;
        ticksInAirSnowball = 0;
        b(0.25F, 0.25F);
        setPositionRotation(d, d1, d2, yaw, pitch);
        height = 0.0F;
    }

    public void setSnowballHeading(double d, double d1, double d2, float f, float f1) {
        float f2 = MathHelper.a(d * d + d1 * d1 + d2 * d2);
        d /= f2;
        d1 /= f2;
        d2 /= f2;
        d += random.nextGaussian() * 0.0074999998323619366D * (double)f1;
        d1 += random.nextGaussian() * 0.0074999998323619366D * (double)f1;
        d2 += random.nextGaussian() * 0.0074999998323619366D * (double)f1;
        d *= f;
        d1 *= f;
        d2 *= f;
        motX = d;
        motY = d1;
        motZ = d2;
        float f3 = MathHelper.a(d * d + d2 * d2);
        lastYaw = yaw = (float)((Math.atan2(d, d2) * 180D) / 3.1415927410125732D);
        lastPitch = pitch = (float)((Math.atan2(d1, f3) * 180D) / 3.1415927410125732D);
    }
    
    @Override
    public void m_() {
    	bo = locX;
    	bp = locY;
    	bq = locZ;
        super.m_();
        if(shakeSnowball > 0)
            shakeSnowball--;
        if(ticksInAirSnowball > 100)
            die();
        if(inGroundSnowball)
            die();
        else
            ticksInAirSnowball++;
        Vec3D vec3d = Vec3D.create(locX, locY, locZ);
        Vec3D vec3d1 = Vec3D.create(locX + motX, locY + motY, locZ + motZ);
        MovingObjectPosition movingobjectposition = world.a(vec3d, vec3d1);
        vec3d = Vec3D.create(locX, locY, locZ);
        vec3d1 = Vec3D.create(locX + motX, locY + motY, locZ + motZ);
        if(movingobjectposition != null)
            vec3d1 = Vec3D.create(movingobjectposition.f.a, movingobjectposition.f.b, movingobjectposition.f.c);
        Entity entity = null;
        @SuppressWarnings("unchecked")
		List<Entity> list = world.b(this, boundingBox.a(motX, motY, motZ).b(4D, 4D, 4D));
        double d = 0.0D;
        for(int l = 0; l < list.size(); l++) {
            Entity entity1 = (Entity)list.get(l);
            if(!entity1.l_() || entity1 == thrower && ticksInAirSnowball < 5)
                continue;
            float f4 = 0.3F;
            if(entity1 != thrower)
                entity1.damageEntity(thrower, 5);
            AxisAlignedBB axisalignedbb = entity1.boundingBox.b(f4, f4, f4);
            MovingObjectPosition movingobjectposition1 = axisalignedbb.a(vec3d, vec3d1);
            if(movingobjectposition1 == null)
                continue;
            double d1 = vec3d.a(movingobjectposition1.f);
            if(d1 < d || d == 0.0D) {
                entity = entity1;
                d = d1;
            }
        }

        if(entity != null)
            movingobjectposition = new MovingObjectPosition(entity);
        if(movingobjectposition != null) {
            if(movingobjectposition.entity != null && movingobjectposition.entity != thrower/* && movingobjectposition.entityHit != slider*/) {
                if(movingobjectposition.entity.damageEntity(thrower, 0));
                movingobjectposition.entity.motX += motX;
                movingobjectposition.entity.motY += 0.59999999999999998D;
                movingobjectposition.entity.motZ += motZ;
            }

            die();
        }
        locX += motX;
        locY += motY;
        locZ += motZ;
        float f = MathHelper.a(motX * motX + motZ * motZ);
        yaw = (float)((Math.atan2(motX, motZ) * 180D) / 3.1415927410125732D);
        for(pitch = (float)((Math.atan2(motY, f) * 180D) / 3.1415927410125732D); pitch - lastPitch < -180F; lastPitch -= 360F) { }
        for(; pitch - lastPitch >= 180F; lastPitch += 360F) { }
        for(; yaw - lastYaw < -180F; lastYaw -= 360F) { }
        for(; yaw - lastYaw >= 180F; lastYaw += 360F) { }
        pitch = lastPitch + (pitch - lastPitch) * 0.2F;
        yaw = lastYaw + (yaw - lastYaw) * 0.2F;
        float f1 = 0.99F;
        if(ad())
            f1 = 0.8F;
        motX *= f1;
        motY *= f1;
        motZ *= f1;
        setPositionRotation(locX, locY, locZ, yaw, pitch);
    }
    
    @Override
    public void b(NBTTagCompound nbttagcompound) {
        nbttagcompound.a("xTile", (short)xTileSnowball);
        nbttagcompound.a("yTile", (short)yTileSnowball);
        nbttagcompound.a("zTile", (short)zTileSnowball);
        nbttagcompound.a("inTile", (byte)inTileSnowball);
        nbttagcompound.a("shake", (byte)shakeSnowball);
        nbttagcompound.a("inGround", (byte)(inGroundSnowball ? 1 : 0));
    }
    
    @Override
    public void a(NBTTagCompound nbttagcompound) {
        xTileSnowball = nbttagcompound.d("xTile");
        yTileSnowball = nbttagcompound.d("yTile");
        zTileSnowball = nbttagcompound.d("zTile");
        inTileSnowball = nbttagcompound.c("inTile") & 0xff;
        shakeSnowball = nbttagcompound.c("shake") & 0xff;
        inGroundSnowball = nbttagcompound.c("inGround") == 1;
    }
    
    @Override
    public void b(EntityHuman entityhuman) {
        if(inGroundSnowball && thrower == entityhuman && shakeSnowball <= 0 && entityhuman.inventory.pickup(new ItemStack(Item.ARROW, 1))) {
            entityhuman.receive(this, 1);
            die();
        }
    }
    
    public final void setThrower(EntityLiving entityliving) {
    	thrower = entityliving;
    }
    
    public final EntityLiving getThrower() {
    	return thrower;
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
		packet.dataInt = new int[] {id, thrower == null ? id : thrower.id};
		packet.dataFloat = new float[] {(float)locX, (float)locY, (float)locZ, yaw, pitch};
		return packet;
	}

    private int xTileSnowball;
    private int yTileSnowball;
    private int zTileSnowball;
    private int inTileSnowball;
    private boolean inGroundSnowball;
    public int shakeSnowball;
    private EntityLiving thrower;
    //private EntitySlider slider;
    private int ticksInAirSnowball;
}
