package net.mine_diver.aethermp.entities;

import java.util.List;

import org.bukkit.Server;
import org.bukkit.craftbukkit.entity.CraftItem;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import net.mine_diver.aethermp.bukkit.craftbukkit.entity.CraftEntityAether;
import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityItem;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.EntityTracker;
import net.minecraft.server.ISpawnable;
import net.minecraft.server.ItemStack;
import net.minecraft.server.MathHelper;
import net.minecraft.server.MovingObjectPosition;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.Packet22Collect;
import net.minecraft.server.Packet230ModLoader;
import net.minecraft.server.Vec3D;
import net.minecraft.server.World;

import static net.minecraft.server.mod_AetherMp.PackageAccess;

public abstract class EntityProjectileBase extends Entity implements ISpawnable {

    public EntityProjectileBase(World world) {
        super(world);
    }

    public EntityProjectileBase(World world, double d, double d1, double d2) {
        this(world);
        setPositionRotation(d, d1, d2, yaw, pitch);
    }

    public EntityProjectileBase(World world, EntityLiving entityliving) {
        this(world);
        shooter = entityliving;
        shotByPlayer = entityliving instanceof EntityHuman;
        setPositionRotation(entityliving.locX, entityliving.locY + (double)entityliving.t(), entityliving.locZ, entityliving.yaw, entityliving.pitch);
        locX -= MathHelper.cos((yaw / 180F) * 3.141593F) * 0.16F;
        locY -= 0.10000000149011612D;
        locZ -= MathHelper.sin((yaw / 180F) * 3.141593F) * 0.16F;
        setPositionRotation(locX, locY, locZ, yaw, pitch);
        motX = -MathHelper.sin((yaw / 180F) * 3.141593F) * MathHelper.cos((pitch / 180F) * 3.141593F);
        motZ = MathHelper.cos((yaw / 180F) * 3.141593F) * MathHelper.cos((pitch / 180F) * 3.141593F);
        motY = -MathHelper.sin((pitch / 180F) * 3.141593F);
        setArrowHeading(motX, motY, motZ, speed, precision);
    }
    
    @Override
    protected void b() {
        xTile = -1;
        yTile = -1;
        zTile = -1;
        inTile = 0;
        inGround = false;
        arrowShake = 0;
        ticksFlying = 0;
        b(0.5F, 0.5F);
        height = 0.0F;
        hitBox = 0.3F;
        speed = 1.0F;
        slowdown = 0.99F;
        curvature = 0.03F;
        dmg = 4;
        precision = 1.0F;
        ttlInGround = 1200;
        item = null;
    }
    
    @Override
    public void die() {
        shooter = null;
        super.die();
    }

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
        if(arrowShake > 0)
            arrowShake--;
        if(inGround) {
            int i = world.getTypeId(xTile, yTile, zTile);
            int k = world.getData(xTile, yTile, zTile);
            if(i != inTile || k != inData) {
                inGround = false;
                motX *= random.nextFloat() * 0.2F;
                motY *= random.nextFloat() * 0.2F;
                motZ *= random.nextFloat() * 0.2F;
                ticksInGround = 0;
                ticksFlying = 0;
            } else {
                ticksInGround++;
                tickInGround();
                if(ticksInGround == ttlInGround)
                    die();
                return;
            }
        } else
            ticksFlying++;
        tickFlying();
        Vec3D vec3d = Vec3D.create(locX, locY, locZ);
        Vec3D vec3d1 = Vec3D.create(locX + motX, locY + motY, locZ + motZ);
        MovingObjectPosition movingobjectposition = world.a(vec3d, vec3d1);
        vec3d = Vec3D.create(locX, locY, locZ);
        vec3d1 = Vec3D.create(locX + motX, locY + motY, locZ + motZ);
        if(movingobjectposition != null)
            vec3d1 = Vec3D.create(movingobjectposition.f.a, movingobjectposition.f.b, movingobjectposition.f.c);
        Entity entity = null;
        @SuppressWarnings("unchecked")
		List<Entity> list = world.b(this, boundingBox.a(motX, motY, motZ).b(1.0D, 1.0D, 1.0D));
        double d = 0.0D;
        for(int j = 0; j < list.size(); j++) {
            Entity entity1 = list.get(j);
            if(!canBeShot(entity1))
                continue;
            float f4 = hitBox;
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
        if(movingobjectposition != null && onHit()) {
        	ProjectileHitEvent phe = new ProjectileHitEvent((Projectile)this.getBukkitEntity());
            this.world.getServer().getPluginManager().callEvent(phe);
            Entity ent = movingobjectposition.entity;
            if(ent != null) {
                if(onHitTarget(ent)) {
                    boolean stick = true;
                    if (ent instanceof EntityLiving) {
                        Server server = this.world.getServer();
                        org.bukkit.entity.Entity damagee = ent.getBukkitEntity();
                        Projectile projectile = (Projectile)this.getBukkitEntity();
                        EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(projectile, damagee, EntityDamageEvent.DamageCause.PROJECTILE, dmg);
                        server.getPluginManager().callEvent(event);
                        this.shooter = ((projectile.getShooter() == null) ? null : ((CraftLivingEntity)projectile.getShooter()).getHandle());
                        if (event.isCancelled())
                            stick = !projectile.doesBounce();
                        else {
                        	if (!(ent instanceof EntityHuman))
                        		((EntityLiving)ent).lastDamage = 0;
                            ent.damageEntity(this, event.getDamage());
                        }
                    } else
                        ent.damageEntity(shooter, dmg);
                    if (stick)
                        this.die();
                    else {
                        this.motX *= -0.10000000149011612;
                        this.motY *= -0.10000000149011612;
                        this.motZ *= -0.10000000149011612;
                        this.yaw += 180.0f;
                        this.lastYaw += 180.0f;
                        this.ticksFlying = 0;
                    }
                }
            } else {
                xTile = movingobjectposition.b;
                yTile = movingobjectposition.c;
                zTile = movingobjectposition.d;
                inTile = world.getTypeId(xTile, yTile, zTile);
                inData = world.getData(xTile, yTile, zTile);
                if(onHitBlock(movingobjectposition)) {
	                motX = (float)(movingobjectposition.f.a - locX);
	                motY = (float)(movingobjectposition.f.b - locY);
	                motZ = (float)(movingobjectposition.f.c - locZ);
	                float f1 = MathHelper.a(motX * motX + motY * motY + motZ * motZ);
	                locX -= (motX / (double)f1) * 0.05000000074505806D;
	                locY -= (motY / (double)f1) * 0.05000000074505806D;
	                locZ -= (motZ / (double)f1) * 0.05000000074505806D;
	                inGround = true;
	                arrowShake = 7;
                } else {
                	inTile = 0;
                	inData = 0;
                }
            }
        }
        locX += motX;
        locY += motY;
        locZ += motZ;
        handleMotionUpdate();
        float f2 = MathHelper.a(motX * motX + motZ * motZ);
        yaw = (float)((Math.atan2(motX, motZ) * 180D) / 3.1415927410125732D);
        for(pitch = (float)((Math.atan2(motY, f2) * 180D) / 3.1415927410125732D); pitch - lastPitch < -180F; lastPitch -= 360F) { }
        for(; pitch - lastPitch >= 180F; lastPitch += 360F) { }
        for(; yaw - lastYaw < -180F; lastYaw -= 360F) { }
        for(; yaw - lastYaw >= 180F; lastYaw += 360F) { }
        pitch = lastPitch + (pitch - lastPitch) * 0.2F;
        yaw = lastYaw + (yaw - lastYaw) * 0.2F;
        setPositionRotation(locX, locY, locZ, yaw, pitch);
    }

    public void handleMotionUpdate() {
        float slow = slowdown;
        if(f_())
            slow *= 0.8F;
        motX *= slow;
        motY *= slow;
        motZ *= slow;
        motY -= curvature;
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
        nbttagcompound.a("player", shotByPlayer);
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
        shotByPlayer = nbttagcompound.m("player");
    }
    
    @Override
    public void b(EntityHuman entityhuman) {
        if(item == null)
            return;
        if (this.inGround && this.shotByPlayer && this.arrowShake <= 0 && entityhuman.inventory.canHold(item) > 0) {
            final EntityItem entityitem = new EntityItem(this.world, this.locX, this.locY, this.locZ, item);
            final PlayerPickupItemEvent event = new PlayerPickupItemEvent((Player)entityhuman.getBukkitEntity(), new CraftItem(this.world.getServer(), entityitem), 0);
            this.world.getServer().getPluginManager().callEvent(event);
            if (event.isCancelled())
                return;
        }
        if(inGround && shotByPlayer && arrowShake <= 0 && entityhuman.inventory.pickup(item.cloneItemStack())) {
        	if (entityhuman instanceof EntityPlayer && !dead) {
        	      EntityTracker entitytracker = PackageAccess.EntityPlayer.getMCServer((EntityPlayer) entityhuman).getTracker(entityhuman.dimension);
        	      if (this instanceof EntityProjectileBase)
        	    	  entitytracker.a(this, new Packet22Collect(id, entityhuman.id));
        	}
            entityhuman.receive(this, 1);
            die();
        }
    }

    public boolean canBeShot(Entity ent) {
        return ent.l_() && (ent != shooter || ticksFlying >= 5) && (!(ent instanceof EntityLiving) || ((EntityLiving)ent).deathTicks <= 0);
    }

    public boolean onHit() {
        return true;
    }
    
    public boolean onHitTarget(Entity target) {
        return true;
    }

    public void tickFlying() {}

    public void tickInGround() {}
    
    public boolean onHitBlock(MovingObjectPosition mop) {
        return onHitBlock();
    }
    
    public boolean onHitBlock() {
        return true;
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
		packet.dataInt = new int[] {id, shooter == null ? id : shooter.id};
		packet.dataFloat = new float[] {(float)locX, (float)locY, (float)locZ, yaw, pitch};
		return packet;
	}

    public float speed;
    public float slowdown;
    public float curvature;
    public float precision;
    public float hitBox;
    public int dmg;
    public ItemStack item;
    public int ttlInGround;
    public int xTile;
    public int yTile;
    public int zTile;
    public int inTile;
    public int inData;
    public boolean inGround;
    public int arrowShake;
    public EntityLiving shooter;
    public int ticksInGround;
    public int ticksFlying;
    public boolean shotByPlayer;
}
