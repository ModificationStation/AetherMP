package net.mine_diver.aethermp.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityCloudParachute;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.ISpawnable;
import net.minecraft.src.Material;
import net.minecraft.src.Packet230ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.WorldClient;

import static net.minecraft.src.mod_AetherMp.PackageAccess;

public class EntityCloudParachuteMp extends EntityCloudParachute implements ISpawnable {

	public EntityCloudParachuteMp(World world) {
        super(world);
        setSize(1.0F, 1.0F);
    }

    public EntityCloudParachuteMp(World world, double d, double d1, double d2) {
        this(world);
        setPositionAndRotation(d, d1, d2, rotationYaw, rotationPitch);
    }

    public EntityCloudParachuteMp(World world, EntityLiving entityliving, boolean flag) {
        this(world);
        if(entityliving == null)
            throw new IllegalArgumentException("entityliving cannot be null.");
        else {
            entityUsing = entityliving;
            cloudMap.put(entityliving, this);
            prevPosX = posX;
            prevPosY = posY;
            prevPosZ = posZ;
            moveToEntityUsing();
            gold = flag;
            return;
        }
    }
	
	public static EntityCloudParachute getCloudBelongingToEntity(EntityLiving entityliving) {
        return (EntityCloudParachute)cloudMap.get(entityliving);
    }
    
	
    public void closeParachute() {
        if(entityUsing != null) {
            if(cloudMap.containsKey(entityUsing))
                cloudMap.remove(entityUsing);
            for(int i = 0; i < 32; i++)
                doCloudSmoke(worldObj, entityUsing, true);
            worldObj.playSoundAtEntity(entityUsing, "cloud", 1.0F, 1.0F / (rand.nextFloat() * 0.1F + 0.95F));
        }
        entityUsing = null;
        isDead = true;
        if (worldObj.multiplayerWorld)
        	((WorldClient) worldObj).setEntityDead(this);
    }

    public static void doCloudSmoke(World world, EntityLiving entityliving, boolean onlySinglePlayer) {
    	if (!(onlySinglePlayer && world.multiplayerWorld) && entityliving != null)
    	    EntityCloudParachute.doCloudSmoke(world, entityliving);
    }

    public boolean isInRangeToRenderDist(double d) {
        if(entityUsing != null)
            return entityUsing.isInRangeToRenderDist(d);
        else
            return super.isInRangeToRenderDist(d);
    }

    public void onUpdate() {
        if(isDead)
            return;
        if (justServerSpawned) {
        	entityUsing = findUser();
        	if(entityUsing != null) {
                cloudMap.put(entityUsing, this);
                justServerSpawned = false;
        	}
        	else
        		return;
        }
        if(entityUsing == null || entityUsing.isDead) {
            entityUsing = findUser();
            if(entityUsing != null)
                cloudMap.put(entityUsing, this);
            else {
                closeParachute();
                return;
            }
        }
        if(entityUsing.motionY < -0.25D)
            entityUsing.motionY = -0.25D;
        PackageAccess.Entity.setFallDistance(entityUsing, 0.0F);
        doCloudSmoke(worldObj, entityUsing, true);
        moveToEntityUsing();
    }

    private EntityLiving findUser() {
        EntityLiving entityliving = null;
    	@SuppressWarnings("unchecked")
		List<Entity> list = worldObj.getEntitiesWithinAABB(net.minecraft.src.EntityLiving.class, boundingBox.copy().offset(0.0D, 1.0D, 0.0D));
        double d = -1D;
        for(int i = 0; i < list.size(); i++) {
            EntityLiving entityliving1 = (EntityLiving)list.get(i);
            if(!entityliving1.isEntityAlive())
                continue;
            double d1 = posX - entityliving1.posX;
            double d2 = boundingBox.maxY - entityliving1.boundingBox.minY;
            double d3 = posZ - entityliving1.posZ;
            double d4 = d1 * d1 + d2 * d2 + d3 * d3;
            if(d == -1D || d4 < d) {
                d = d4;
                entityliving = entityliving1;
            }
        }
        return entityliving;
    }

    private void moveToEntityUsing() {
        setPositionAndRotation(entityUsing.posX, entityUsing.boundingBox.minY - (double)(height / 2.0F), entityUsing.posZ, entityUsing.rotationYaw, entityUsing.rotationPitch);
        motionX = entityUsing.motionX;
        motionY = entityUsing.motionY;
        motionZ = entityUsing.motionZ;
        rotationYaw = entityUsing.rotationYaw;
        if(isCollided())
            closeParachute();
    }

    private boolean isCollided() {
        return worldObj.getCollidingBoundingBoxes(this, boundingBox).size() > 0 || worldObj.isAABBInMaterial(boundingBox, Material.water);
    }
	
    @Override
	public void spawn(Packet230ModLoader packet) {
		Entity entity = EntityManager.getEntityByID(packet.dataInt[0]);
    	entityUsing = entity instanceof EntityLiving ? (EntityLiving) entity : null;
    	if (entityUsing == null)
    		justServerSpawned = true;
    	gold = packet.dataInt[1] == 0 ? false : true;
    	posX = packet.dataFloat[0];posY = packet.dataFloat[1];posZ = packet.dataFloat[2];
    }
	
    private boolean justServerSpawned;
    private EntityLiving entityUsing;
    private static Map<EntityLiving, EntityCloudParachute> cloudMap = new HashMap<EntityLiving, EntityCloudParachute>();
}
