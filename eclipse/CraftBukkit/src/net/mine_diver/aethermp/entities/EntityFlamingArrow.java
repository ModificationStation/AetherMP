package net.mine_diver.aethermp.entities;

import java.util.List;

import org.bukkit.craftbukkit.entity.CraftItem;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerPickupItemEvent;

import net.mine_diver.aethermp.bukkit.craftbukkit.entity.CraftEntityAether;
import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.Block;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityItem;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.EntityTracker;
import net.minecraft.server.ISpawnable;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.MathHelper;
import net.minecraft.server.MovingObjectPosition;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.Packet22Collect;
import net.minecraft.server.Packet230ModLoader;
import net.minecraft.server.Vec3D;
import net.minecraft.server.World;
import net.minecraft.server.mod_AetherMp.PackageAccess;

public class EntityFlamingArrow extends Entity implements ISpawnable {

    public EntityFlamingArrow(World world) {
        super(world);
        xTile = -1;
        yTile = -1;
        zTile = -1;
        inTile = 0;
        inData = 0;
        inGround = false;
        doesArrowBelongToPlayer = false;
        arrowShake = 0;
        ticksInAir = 0;
        b(0.5F, 0.5F);
        fireTicks = 1;
    }

    public EntityFlamingArrow(World world, double d, double d1, double d2) {
        super(world);
        xTile = -1;
        yTile = -1;
        zTile = -1;
        inTile = 0;
        inData = 0;
        inGround = false;
        doesArrowBelongToPlayer = false;
        arrowShake = 0;
        ticksInAir = 0;
        b(0.5F, 0.5F);
        setPositionRotation(d, d1, d2, yaw, pitch);
        height = 0.0F;
    }

    public EntityFlamingArrow(World world, EntityLiving entityliving) {
        super(world);
        xTile = -1;
        yTile = -1;
        zTile = -1;
        inTile = 0;
        inData = 0;
        inGround = false;
        doesArrowBelongToPlayer = false;
        arrowShake = 0;
        ticksInAir = 0;
        owner = entityliving;
        doesArrowBelongToPlayer = entityliving instanceof EntityHuman;
        b(0.5F, 0.5F);
        setLocation(entityliving.locX, entityliving.locY + (double)entityliving.t(), entityliving.locZ, entityliving.yaw, entityliving.pitch);
        locX -= MathHelper.cos((yaw / 180F) * 3.141593F) * 0.16F;
        locY -= 0.10000000149011612D;
        locZ -= MathHelper.sin((yaw / 180F) * 3.141593F) * 0.16F;
        setPositionRotation(locX, locY, locZ, yaw, pitch);
        height = 0.0F;
        motX = -MathHelper.sin((yaw / 180F) * 3.141593F) * MathHelper.cos((pitch / 180F) * 3.141593F);
        motZ = MathHelper.cos((yaw / 180F) * 3.141593F) * MathHelper.cos((pitch / 180F) * 3.141593F);
        motY = -MathHelper.sin((pitch / 180F) * 3.141593F);
        setArrowHeading(motX, motY, motZ, 1.5F, 1.0F);
    }
    
    @Override
    protected void b() {}

    public void setArrowHeading(double d, double d1, double d2, float f, float f1) {
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
        ticksInGround = 0;
    }
    
    @Override
    public void m_() {
        super.m_();
        if(lastPitch == 0.0F && lastYaw == 0.0F) {
            float f = MathHelper.a(motX * motX + motZ * motZ);
            lastYaw = yaw = (float)((Math.atan2(motX, motZ) * 180D) / 3.1415927410125732D);
            lastPitch = pitch = (float)((Math.atan2(motY, f) * 180D) / 3.1415927410125732D);
        }
        int i = world.getTypeId(xTile, yTile, zTile);
        if(i > 0) {
            Block.byId[i].a(world, xTile, yTile, zTile);
            AxisAlignedBB axisalignedbb = Block.byId[i].e(world, xTile, yTile, zTile);
            if(axisalignedbb != null && axisalignedbb.a(Vec3D.create(locX, locY, locZ)))
                inGround = true;
        }
        if(arrowShake > 0)
            arrowShake--;
        if(inGround) {
            int j = world.getTypeId(xTile, yTile, zTile);
            int k = world.getData(xTile, yTile, zTile);
            if(j != inTile || k != inData) {
                inGround = false;
                motX *= random.nextFloat() * 0.2F;
                motY *= random.nextFloat() * 0.2F;
                motZ *= random.nextFloat() * 0.2F;
                ticksInGround = 0;
                ticksInAir = 0;
                return;
            }
            ticksInGround++;
            if(ticksInGround == 1200)
                die();
            return;
        }
        ticksInAir++;
        Vec3D vec3d = Vec3D.create(locX, locY, locZ);
        Vec3D vec3d1 = Vec3D.create(locX + motX, locY + motY, locZ + motZ);
        MovingObjectPosition movingobjectposition = world.rayTrace(vec3d, vec3d1, false, true);
        vec3d = Vec3D.create(locX, locY, locZ);
        vec3d1 = Vec3D.create(locX + motX, locY + motY, locZ + motZ);
        if(movingobjectposition != null)
            vec3d1 = Vec3D.create(movingobjectposition.f.a, movingobjectposition.f.b, movingobjectposition.f.c);
        Entity entity = null;
        @SuppressWarnings("unchecked")
		List<Entity> list = world.b(this, boundingBox.a(motX, motY, motZ).b(1.0D, 1.0D, 1.0D));
        double d = 0.0D;
        for(int l = 0; l < list.size(); l++) {
            Entity entity1 = list.get(l);
            if(!entity1.l_() || entity1 == owner && ticksInAir < 5)
                continue;
            float f4 = 0.3F;
            AxisAlignedBB axisalignedbb1 = entity1.boundingBox.b(f4, f4, f4);
            MovingObjectPosition movingobjectposition1 = axisalignedbb1.a(vec3d, vec3d1);
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
            if(movingobjectposition.entity != null) {
                if(movingobjectposition.entity.damageEntity(owner, 4)) {
                    movingobjectposition.entity.fireTicks = 100;
                    int x = MathHelper.floor(movingobjectposition.entity.boundingBox.a);
                    int y = MathHelper.floor(movingobjectposition.entity.boundingBox.b);
                    int z = MathHelper.floor(movingobjectposition.entity.boundingBox.c);
                    world.setTypeId(x, y, z, 51);
                    die();
                } else {
                    motX *= -0.10000000149011612D;
                    motY *= -0.10000000149011612D;
                    motZ *= -0.10000000149011612D;
                    yaw += 180F;
                    lastYaw += 180F;
                    ticksInAir = 0;
                }
            } else {
                xTile = movingobjectposition.b;
                yTile = movingobjectposition.c;
                zTile = movingobjectposition.d;
                inTile = world.getTypeId(xTile, yTile, zTile);
                inData = world.getData(xTile, yTile, zTile);
                motX = (float)(movingobjectposition.f.a - locX);
                motY = (float)(movingobjectposition.f.b - locY);
                motZ = (float)(movingobjectposition.f.c - locZ);
                float f1 = MathHelper.a(motX * motX + motY * motY + motZ * motZ);
                locX -= (motX / (double)f1) * 0.05000000074505806D;
                locY -= (motY / (double)f1) * 0.05000000074505806D;
                locZ -= (motZ / (double)f1) * 0.05000000074505806D;
                int xPos = MathHelper.floor(locX);
                int yPos = MathHelper.floor(locY);
                int zPos = MathHelper.floor(locZ);
                world.setTypeId(xPos, yPos, zPos, 51);
                inGround = true;
                arrowShake = 7;
            }
        }
        locX += motX;
        locY += motY;
        locZ += motZ;
        float f2 = MathHelper.a(motX * motX + motZ * motZ);
        yaw = (float)((Math.atan2(motX, motZ) * 180D) / 3.1415927410125732D);
        for(pitch = (float)((Math.atan2(motY, f2) * 180D) / 3.1415927410125732D); pitch - lastPitch < -180F; lastPitch -= 360F) { }
        for(; pitch - lastPitch >= 180F; lastPitch += 360F) { }
        for(; yaw - lastYaw < -180F; lastYaw -= 360F) { }
        for(; yaw - lastYaw >= 180F; lastYaw += 360F) { }
        pitch = lastPitch + (pitch - lastPitch) * 0.2F;
        yaw = lastYaw + (yaw - lastYaw) * 0.2F;
        float f3 = 0.99F;
        float f5 = 0.03F;
        if(ad())
            f3 = 0.8F;
        motX *= f3;
        motY *= f3;
        motZ *= f3;
        motY -= f5;
        setPositionRotation(locX, locY, locZ, yaw, pitch);
    }
    
    @Override
    public void b(NBTTagCompound nbttagcompound) {
        nbttagcompound.a("xTile", (short)xTile);
        nbttagcompound.a("yTile", (short)yTile);
        nbttagcompound.a("zTile", (short)zTile);
        nbttagcompound.a("inTile", (byte)inTile);
        nbttagcompound.a("inData", (byte)inData);
        nbttagcompound.a("shake", (byte)arrowShake);
        nbttagcompound.a("inGround", (byte)(inGround ? 1 : 0));
        nbttagcompound.a("player", doesArrowBelongToPlayer);
    }
    
    @Override
    public void a(NBTTagCompound nbttagcompound) {
        xTile = nbttagcompound.d("xTile");
        yTile = nbttagcompound.d("yTile");
        zTile = nbttagcompound.d("zTile");
        inTile = nbttagcompound.c("inTile") & 0xff;
        inData = nbttagcompound.c("inData") & 0xff;
        arrowShake = nbttagcompound.c("shake") & 0xff;
        inGround = nbttagcompound.c("inGround") == 1;
        doesArrowBelongToPlayer = nbttagcompound.m("player");
    }
    
    @Override
    public void b(EntityHuman entityhuman) {
    	ItemStack itemstack = new ItemStack(Item.ARROW, 1);
        if (this.inGround && doesArrowBelongToPlayer && arrowShake <= 0 && entityhuman.inventory.canHold(itemstack) > 0) {
            EntityItem item = new EntityItem(world, locX, locY, locZ, itemstack);
            PlayerPickupItemEvent event = new PlayerPickupItemEvent((Player)entityhuman.getBukkitEntity(), new CraftItem(world.getServer(), item), 0);
            this.world.getServer().getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                return;
            }
        }
        if(inGround && doesArrowBelongToPlayer && arrowShake <= 0 && entityhuman.inventory.pickup(new ItemStack(Item.ARROW, 1))) {
        	if (entityhuman instanceof EntityPlayer && !dead) {
      	        EntityTracker entitytracker = PackageAccess.EntityPlayer.getMCServer((EntityPlayer) entityhuman).getTracker(entityhuman.dimension);
      	        if (this instanceof EntityFlamingArrow)
      	    	    entitytracker.a(this, new Packet22Collect(id, entityhuman.id));
      	    }
            entityhuman.receive(this, 1);
            die();
        }
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
		packet.dataInt = new int[] {id, owner == null ? id : owner.id};
		packet.dataFloat = new float[] {(float)locX, (float)locY, (float)locZ, yaw, pitch};
		return packet;
	}

    private int xTile;
    private int yTile;
    private int zTile;
    private int inTile;
    private int inData;
    private boolean inGround;
    public boolean doesArrowBelongToPlayer;
    public int arrowShake;
    public EntityLiving owner;
    private int ticksInGround;
    private int ticksInAir;
}
