package net.mine_diver.aethermp.entities;

import java.util.*;

import net.mine_diver.aethermp.dimension.DimensionManager;
import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.ISpawnable;
import net.minecraft.server.Material;
import net.minecraft.server.ModLoader;
import net.minecraft.server.ModLoaderMp;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.Packet230ModLoader;
import net.minecraft.server.World;
import net.minecraft.server.mod_AetherMp;

public class EntityCloudParachute extends Entity implements ISpawnable {

    public EntityCloudParachute(World world) {
        super(world);
        b(1.0F, 1.0F);
    }

    public EntityCloudParachute(World world, double d, double d1, double d2) {
        this(world);
        setLocation(d, d1, d2, yaw, pitch);
    }

    public EntityCloudParachute(World world, EntityLiving entityliving, boolean flag) {
        this(world);
        if(entityliving == null)
            throw new IllegalArgumentException("entityliving cannot be null.");
        else {
            entityUsing = entityliving;
            cloudMap.put(entityliving, this);
            lastX = locX;
            lastY = locY;
            lastZ = locZ;
            moveToEntityUsing();
            gold = flag;
            return;
        }
    }

    public static EntityCloudParachute getCloudBelongingToEntity(EntityLiving entityliving) {
        return (EntityCloudParachute)cloudMap.get(entityliving);
    }

    public World getWorld() {
        return world;
    }
    
    public void closeParachute() {
        if(entityUsing != null) {
            if(cloudMap.containsKey(entityUsing))
                cloudMap.remove(entityUsing);
            for(int i = 0; i < 32; i++)
                doCloudSmoke(world, entityUsing);

            world.makeSound(entityUsing, "cloud", 1.0F, 1.0F / (random.nextFloat() * 0.1F + 0.95F));
        }
        entityUsing = null;
        dead = true;
    }
    
    public static void doCloudSmoke(World world, EntityLiving entityliving) {
        Packet230ModLoader packet = new Packet230ModLoader();
        packet.dataInt = new int[] {entityliving == null ? 0 : entityliving.id};
        packet.packetType = 3;
        packet.modId = ModLoaderMp.GetModInstance(mod_AetherMp.class).getId();
        ModLoader.getMinecraftServerInstance().serverConfigurationManager.sendPacketNearby(entityliving.locX, entityliving.locY, entityliving.locZ, ModLoader.getMinecraftServerInstance().propertyManager.getInt("view-distance", 10) * 16, DimensionManager.getCurrentDimension(world), packet);
    }

    public static boolean entityHasRoomForCloud(World world, EntityLiving entityliving) {
        AxisAlignedBB axisalignedbb = AxisAlignedBB.a(entityliving.locX - 0.5D, entityliving.boundingBox.b - 1.0D, entityliving.locZ - 0.5D, entityliving.locX + 0.5D, entityliving.boundingBox.b, entityliving.locZ + 0.5D);
        return world.getEntities(entityliving, axisalignedbb).size() == 0 && !world.b(axisalignedbb, Material.WATER);
    }
    
    @Override
    protected void b() {
    }
    
    @Override
    public boolean l_() {
        return true;
    }
    
    @Override
    public AxisAlignedBB e_() {
        return boundingBox;
    }
    
    @Override
    public void m_() {
        if(dead)
            return;
        if(entityUsing == null || entityUsing.dead) {
            entityUsing = findUser();
            if(entityUsing != null)
                cloudMap.put(entityUsing, this);
            else {
                closeParachute();
                return;
            }
        }
        if(entityUsing.motY < -0.25D)
            entityUsing.motY = -0.25D;
        entityUsing.fallDistance = 0.0F;
        doCloudSmoke(world, entityUsing);
        moveToEntityUsing();
    }

    private EntityLiving findUser() {
        @SuppressWarnings("unchecked")
		List<EntityLiving> list = world.a(net.minecraft.server.EntityLiving.class, boundingBox.clone().d(0.0D, 1.0D, 0.0D));
        double d = -1D;
        EntityLiving entityliving = null;
        for(int i = 0; i < list.size(); i++) {
            EntityLiving entityliving1 = (EntityLiving)list.get(i);
            if(!entityliving1.T())
                continue;
            double d1 = locX - entityliving1.locX;
            double d2 = boundingBox.e - entityliving1.boundingBox.b;
            double d3 = locZ - entityliving1.locZ;
            double d4 = d1 * d1 + d2 * d2 + d3 * d3;
            if(d == -1D || d4 < d) {
                d = d4;
                entityliving = entityliving1;
            }
        }

        return entityliving;
    }

    private void moveToEntityUsing() {
        setLocation(entityUsing.locX, entityUsing.boundingBox.b - (double)(width / 2.0F), entityUsing.locZ, entityUsing.yaw, entityUsing.pitch);
        motX = entityUsing.motX;
        motY = entityUsing.motY;
        motZ = entityUsing.motZ;
        yaw = entityUsing.yaw;
        if(isCollided()){
            closeParachute();
        }
    }

    private boolean isCollided() {
        return world.getEntities(this, boundingBox).size() > 0 || world.b(boundingBox, Material.WATER);
    }
    
    @Override
    public void b(EntityHuman entityhuman) {}
    
    @Override
    protected void a(NBTTagCompound nbttagcompound) {}
    
    @Override
    protected void b(NBTTagCompound nbttagcompound) {}
    
    @Override
    public Packet230ModLoader getSpawnPacket() {
        Packet230ModLoader packet = new Packet230ModLoader();
        packet.dataInt = new int[] {entityUsing == null ? 0 : entityUsing.id, gold ? 1 : 0};
        packet.dataFloat = new float[] {(float)locX, (float)locY, (float)locZ};
        return packet;
    }

    private EntityLiving entityUsing;
    private static Map<EntityLiving, EntityCloudParachute> cloudMap = new HashMap<EntityLiving, EntityCloudParachute>();
    public boolean gold;
}
