package net.mine_diver.aethermp.entities;

import java.util.List;

import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.MathHelper;
import net.minecraft.server.ModLoaderMp;
import net.minecraft.server.MovingObjectPosition;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.Packet230ModLoader;
import net.minecraft.server.Vec3D;
import net.minecraft.server.World;
import net.minecraft.server.mod_AetherMp;

public class EntityZephyrSnowball extends Entity {

    public EntityZephyrSnowball(World world, double i, double i1, double i2) {
        super(world);
        field_9402_e = -1;
        field_9401_f = -1;
        field_9400_g = -1;
        field_9399_h = 0;
        field_9398_i = false;
        field_9406_a = 0;
        field_9395_l = 0;
        b(1.0F, 1.0F);
    }
    
    @Override
    protected void b() {}

    public EntityZephyrSnowball(World world, EntityLiving entityliving, double d, double d1, double d2) {
        super(world);
        field_9402_e = -1;
        field_9401_f = -1;
        field_9400_g = -1;
        field_9399_h = 0;
        field_9398_i = false;
        field_9406_a = 0;
        field_9395_l = 0;
        field_9397_j = entityliving;
        b(1.0F, 1.0F);
        setPositionRotation(entityliving.locX, entityliving.locY, entityliving.locZ, entityliving.yaw, entityliving.pitch);
        setPosition(locX, locY, locZ);
        height = 0.0F;
        motX = motY = motZ = 0.0D;
        d += random.nextGaussian() * 0.40000000000000002D;
        d1 += random.nextGaussian() * 0.40000000000000002D;
        d2 += random.nextGaussian() * 0.40000000000000002D;
        double d3 = MathHelper.a(d * d + d1 * d1 + d2 * d2);
        field_9405_b = (d / d3) * 0.10000000000000001D;
        field_9404_c = (d1 / d3) * 0.10000000000000001D;
        field_9403_d = (d2 / d3) * 0.10000000000000001D;
    }
    
    @Override
    public void m_() {
        super.m_();
        if (field_9406_a > 0)
            field_9406_a--;
        if (field_9398_i) {
            int i = world.getTypeId(field_9402_e, field_9401_f, field_9400_g);
            if (i != field_9399_h) {
                field_9398_i = false;
                motX *= random.nextFloat() * 0.2F;
                motY *= random.nextFloat() * 0.2F;
                motZ *= random.nextFloat() * 0.2F;
                field_9396_k = 0;
                field_9395_l = 0;
            } else {
                field_9396_k++;
                if (field_9396_k == 1200)
                    die();
                return;
            }
        } else
            field_9395_l++;
        Vec3D vec3d = Vec3D.create(locX, locY, locZ);
        Vec3D vec3d1 = Vec3D.create(locX + motX, locY + motY, locZ + motZ);
        MovingObjectPosition movingobjectposition = world.a(vec3d, vec3d1);
        vec3d = Vec3D.create(locX, locY, locZ);
        vec3d1 = Vec3D.create(locX + motX, locY + motY, locZ + motZ);
        if (movingobjectposition != null)
            vec3d1 = Vec3D.create(movingobjectposition.f.a, movingobjectposition.f.b, movingobjectposition.f.c);
        Entity entity = null;
        @SuppressWarnings("unchecked")
		List<Entity> list = world.b(this, boundingBox.a(motX, motY, motZ).b(1.0D, 1.0D, 1.0D));
        double d = 0.0D;
        for(int j = 0; j < list.size(); j++) {
            Entity entity1 = (Entity)list.get(j);
            if (!entity1.l_() || entity1 == field_9397_j && field_9395_l < 25)
                continue;
            float f2 = 0.3F;
            AxisAlignedBB axisalignedbb = entity1.boundingBox.b(f2, f2, f2);
            MovingObjectPosition movingobjectposition1 = axisalignedbb.a(vec3d, vec3d1);
            if (movingobjectposition1 == null)
                continue;
            double d1 = vec3d.a(movingobjectposition1.f);
            if (d1 < d || d == 0.0D) {
                entity = entity1;
                d = d1;
            }
        }

        if (entity != null)
            movingobjectposition = new MovingObjectPosition(entity);
        if (movingobjectposition != null) {
            if (movingobjectposition.entity != null) {
                if (movingobjectposition.entity.damageEntity(field_9397_j, 0));
                movingobjectposition.entity.motX += motX;
                movingobjectposition.entity.motY += 0.20000000000000001D;
                movingobjectposition.entity.motZ += motZ;
                if (movingobjectposition.entity instanceof EntityPlayer){
                    Packet230ModLoader packet = new Packet230ModLoader();
                    float[] dataFloat = new float[3];
                    dataFloat[0] = (float) movingobjectposition.entity.motX;
                    dataFloat[1] = (float) movingobjectposition.entity.motY;
                    dataFloat[2] = (float) movingobjectposition.entity.motZ;
                    packet.dataFloat = dataFloat;
                    packet.packetType = 2;
                    ModLoaderMp.SendPacketTo(ModLoaderMp.GetModInstance(mod_AetherMp.class), (EntityPlayer)movingobjectposition.entity, packet);
                }
            }
            die();
        }
        locX += motX;
        locY += motY;
        locZ += motZ;
        float f = MathHelper.a(motX * motX + motZ * motZ);
        yaw = (float)((Math.atan2(motX, motZ) * 180D) / 3.1415927410125732D);
        for(pitch = (float)((Math.atan2(motY, f) * 180D) / 3.1415927410125732D); pitch - lastPitch < -180F; lastPitch -= 360F) { }
        for(; pitch - lastPitch >= 180F; pitch += 360F) { }
        for(; yaw - lastYaw < -180F; lastYaw -= 360F) { }
        for(; yaw - lastYaw >= 180F; lastYaw += 360F) { }
        pitch = lastPitch + (pitch - lastPitch) * 0.2F;
        yaw = lastYaw + (yaw - lastYaw) * 0.2F;
        float f1 = 0.95F;
        if (ad()) {
            for(int k = 0; k < 4; k++) {
                float f3 = 0.25F;
                world.a("bubble", locX - motX * (double)f3, locY - motY * (double)f3, locZ - motZ * (double)f3, motX, motY, motZ);
            }

            f1 = 0.8F;
        }
        motX += field_9405_b;
        motY += field_9404_c;
        motZ += field_9403_d;
        motX *= f1;
        motY *= f1;
        motZ *= f1;
        world.a("smoke", locX, locY + 0.5D, locZ, 0.0D, 0.0D, 0.0D);
        setPosition(locX, locY, locZ);
    }
    
    @Override
    public void b(NBTTagCompound nbttagcompound) {
        nbttagcompound.a("xTile", (short)field_9402_e);
        nbttagcompound.a("yTile", (short)field_9401_f);
        nbttagcompound.a("zTile", (short)field_9400_g);
        nbttagcompound.a("inTile", (byte)field_9399_h);
        nbttagcompound.a("shake", (byte)field_9406_a);
        nbttagcompound.a("inGround", (byte)(field_9398_i ? 1 : 0));
    }
    
    @Override
    public void a(NBTTagCompound nbttagcompound) {
        field_9402_e = nbttagcompound.d("xTile");
        field_9401_f = nbttagcompound.d("yTile");
        field_9400_g = nbttagcompound.d("zTile");
        field_9399_h = nbttagcompound.c("inTile") & 0xff;
        field_9406_a = nbttagcompound.c("shake") & 0xff;
        field_9398_i = nbttagcompound.c("inGround") == 1;
    }
    
    @Override
    public boolean damageEntity(Entity entity, int i) {
        af();
        if (entity != null) {
            Vec3D vec3d = entity.Z();
            if (vec3d != null) {
                motX = vec3d.a;
                motY = vec3d.b;
                motZ = vec3d.c;
                field_9405_b = motX * 0.10000000000000001D;
                field_9404_c = motY * 0.10000000000000001D;
                field_9403_d = motZ * 0.10000000000000001D;
            }
            return true;
        } else
            return false;
    }

    private int field_9402_e;
    private int field_9401_f;
    private int field_9400_g;
    private int field_9399_h;
    private boolean field_9398_i;
    public int field_9406_a;
    private EntityLiving field_9397_j;
    private int field_9396_k;
    private int field_9395_l;
    public double field_9405_b;
    public double field_9404_c;
    public double field_9403_d;
}
