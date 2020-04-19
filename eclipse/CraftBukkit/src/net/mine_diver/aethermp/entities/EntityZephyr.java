package net.mine_diver.aethermp.entities;

import net.mine_diver.aethermp.blocks.BlockManager;
import net.mine_diver.aethermp.bukkit.craftbukkit.entity.CraftEntityAether;
import net.mine_diver.aethermp.network.PacketManager;
import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityFlying;
import net.minecraft.server.IMonster;
import net.minecraft.server.MathHelper;
import net.minecraft.server.Vec3D;
import net.minecraft.server.World;

public class EntityZephyr extends EntityFlying implements IMonster {

    public EntityZephyr(World world) {
        super(world);
        checkTime = 0L;
        checkX = 0.0D;
        checkY = 0.0D;
        checkZ = 0.0D;
        isStuckWarning = false;
        courseChangeCooldown = 0;
        targetedEntity = null;
        aggroCooldown = 0;
        datawatcher.a(16, 0);
        datawatcher.a(17, 0);
        b(4F, 4F);
    }
    
    @Override
    protected void c_() {
        if (locY < -2D || locY > 130D)
            U();
        datawatcher.watch(16, datawatcher.b(17));
        double d = waypointX - locX;
        double d1 = waypointY - locY;
        double d2 = waypointZ - locZ;
        double d3 = MathHelper.a(d * d + d1 * d1 + d2 * d2);
        if (d3 < 1.0D || d3 > 60D) {
            waypointX = locX + (double)((random.nextFloat() * 2.0F - 1.0F) * 16F);
            waypointY = locY + (double)((random.nextFloat() * 2.0F - 1.0F) * 16F);
            waypointZ = locZ + (double)((random.nextFloat() * 2.0F - 1.0F) * 16F);
        }
        if (courseChangeCooldown-- <= 0) {
            courseChangeCooldown += random.nextInt(5) + 2;
            if (isCourseTraversable(waypointX, waypointY, waypointZ, d3)) {
                motX += (d / d3) * 0.10000000000000001D;
                motY += (d1 / d3) * 0.10000000000000001D;
                motZ += (d2 / d3) * 0.10000000000000001D;
            } else {
                waypointX = locX;
                waypointY = locY;
                waypointZ = locZ;
            }
        }
        if (targetedEntity != null && targetedEntity.dead)
            targetedEntity = null;
        if (targetedEntity == null || aggroCooldown-- <= 0) {
            targetedEntity = world.findNearbyPlayer(this, 100D);
            if (targetedEntity != null)
                aggroCooldown = 20;
        }
        double d4 = 64D;
        if (targetedEntity != null && targetedEntity.g(this) < d4 * d4) {
            double d5 = targetedEntity.locX - locX;
            double d6 = (targetedEntity.boundingBox.b + (double)(targetedEntity.height / 2.0F)) - (locY + (double)(height / 2.0F));
            double d7 = targetedEntity.locZ - locZ;
            K = yaw = (-(float)Math.atan2(d5, d7) * 180F) / 3.141593F;
            if (e(targetedEntity)) {
                if (datawatcher.b(17) == 10)
                    PacketManager.makeSound(this, "aether.sound.mobs.zephyr.zephyrCall", k(), (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
                datawatcher.watch(17, datawatcher.b(17)+1);
                if (datawatcher.b(17) == 20) {
                    PacketManager.makeSound(this, "aether.sound.mobs.zephyr.zephyrShoot", k(), (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
                    EntityZephyrSnowball entityzephyrsnowball = new EntityZephyrSnowball(world, this, d5, d6, d7);
                    double d8 = 4D;
                    Vec3D vec3d = b(1.0F);
                    entityzephyrsnowball.locX = locX + vec3d.a * d8;
                    entityzephyrsnowball.locY = locY + (double)(width / 2.0F) + 0.5D;
                    entityzephyrsnowball.locZ = locZ + vec3d.c * d8;
                    world.addEntity(entityzephyrsnowball);
                    datawatcher.watch(17, -40);
                }
            } else if (datawatcher.b(17) > 0)
                datawatcher.watch(17, datawatcher.b(17)-1);
        } else {
            K = yaw = (-(float)Math.atan2(motX, motZ) * 180F) / 3.141593F;
            if (datawatcher.b(17) > 0)
                datawatcher.watch(17, datawatcher.b(17) - 1);
        }
        if (world.spawnMonsters == 0)
            die();
        checkForBeingStuck();
    }

    private boolean isCourseTraversable(double d, double d1, double d2, double d3) {
        double d4 = (waypointX - locX) / d3;
        double d5 = (waypointY - locY) / d3;
        double d6 = (waypointZ - locZ) / d3;
        AxisAlignedBB axisalignedbb = boundingBox.clone();
        for(int i = 1; (double)i < d3; i++) {
            axisalignedbb.d(d4, d5, d6);
            if (world.getEntities(this, axisalignedbb).size() > 0)
                return false;
        }

        return true;
    }
    
    @Override
    protected String g() {
        return "aether.sound.mobs.zephyr.zephyrCall";
    }
    
    @Override
    protected String h() {
        return "aether.sound.mobs.zephyr.zephyrCall";
    }
    
    @Override
    protected String i() {
        return "aether.sound.mobs.zephyr.zephyrCall";
    }
    
    @Override
    protected int j() {
        return BlockManager.Aercloud.id;
    }
    
    @Override
    public boolean h_() {
        return true;
    }
    
    @Override
    protected float k() {
        return 3F;
    }

    private void checkForBeingStuck() {
        long l = System.currentTimeMillis();
        if (l > checkTime + 3000L) {
            double d = locX - checkX;
            double d1 = locY - checkY;
            double d2 = locZ - checkZ;
            double d3 = Math.sqrt(d * d + d1 * d1 + d2 * d2);
            if (d3 < 3D) {
                if (!isStuckWarning)
                    isStuckWarning = true;
                else
                    die();
            }
            checkX = locX;
            checkY = locY;
            checkZ = locZ;
            checkTime = l;
        }
    }
    
    @Override
    public boolean d() {
        int i = MathHelper.floor(locX);
        int j = MathHelper.floor(boundingBox.b);
        int k = MathHelper.floor(locZ);
        return random.nextInt(85) == 0 && world.containsEntity(boundingBox) && world.getEntities(this, boundingBox).size() == 0 && !world.c(boundingBox) && world.getTypeId(i, j - 1, k) != BlockManager.DungeonStone.id && world.getTypeId(i, j - 1, k) != BlockManager.LightDungeonStone.id && world.getTypeId(i, j - 1, k) != BlockManager.LockedDungeonStone.id && world.getTypeId(i, j - 1, k) != BlockManager.LockedLightDungeonStone.id && world.getTypeId(i, j - 1, k) != BlockManager.Holystone.id && world.spawnMonsters > 0;
    }
    
    @Override
    public int l() {
        return 1;
    }
    
    public final void setTargetedEntity(Entity entity) {
    	targetedEntity = entity;
    }
    
    public final Entity getTargetedEntity() {
    	return targetedEntity;
    }
    
    @Override
    public org.bukkit.entity.Entity getBukkitEntity() {
        if (this.bukkitEntity == null)
            this.bukkitEntity = CraftEntityAether.getEntity(this.world.getServer(), this);
        return this.bukkitEntity;
    }

    private long checkTime;
    private double checkX;
    private double checkY;
    private double checkZ;
    private boolean isStuckWarning;
    public int courseChangeCooldown;
    public double waypointX;
    public double waypointY;
    public double waypointZ;
    private Entity targetedEntity;
    private int aggroCooldown;
}
