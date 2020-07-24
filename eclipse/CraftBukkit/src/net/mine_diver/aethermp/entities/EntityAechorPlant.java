package net.mine_diver.aethermp.entities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.event.entity.EntityDeathEvent;

import net.mine_diver.aethermp.blocks.BlockManager;
import net.mine_diver.aethermp.items.ItemManager;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityCreeper;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.ItemStack;
import net.minecraft.server.MathHelper;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.World;

public class EntityAechorPlant extends EntityAetherAnimal {

    public EntityAechorPlant(World world1) {
        super(world1);
        texture = "/aether/mobs/aechorplant.png";
        size = random.nextInt(4) + 1;
        health = 10 + size * 2;
        sinage = random.nextFloat() * 6F;
        smokeTime = attTime = 0;
        seeprey = false;
        b(0.75F + (float)size * 0.125F, 0.5F + (float)size * 0.075F);
        setPosition(locX, locY, locZ);
        poisonLeft = 2;
    }
    
    @Override
    public int l() {
        return 3;
    }
    
    @Override
    public boolean d() {
        int i = MathHelper.floor(locX);
        int j = MathHelper.floor(boundingBox.b);
        int k = MathHelper.floor(locZ);
        return world.getTypeId(i, j - 1, k) == BlockManager.Grass.id && world.k(i, j, k) > 8 && super.d();
    }
    
    @Override
    public void v() {
        if(health <= 0 || !grounded) {
            super.v();
            if(health <= 0)
                return;
        } else {
            ay++;
            U();
        }
        if(onGround)
            grounded = true;
        if(hurtTicks > 0)
            sinage += 0.9F;
        else if(seeprey)
            sinage += 0.3F;
        else
            sinage += 0.1F;
        if(sinage > 6.283186F)
            sinage -= 6.283186F;
        if(target == null) {
            @SuppressWarnings("unchecked")
			List<Entity> list = world.b(this, boundingBox.b(10D, 10D, 10D));
            for (int j = 0; j < list.size(); j++) {
                Entity entity1 = (Entity)list.get(j);
                if (!(entity1 instanceof EntityLiving) || (entity1 instanceof EntityAechorPlant) || (entity1 instanceof EntityCreeper))
                    continue;
                target = (EntityLiving)entity1;
                break;
            }

        }
        if(target != null) {
            if(target.dead || (double)target.f(this) > 12D) {
                target = null;
                attTime = 0;
            }
            if(target != null && attTime >= 20 && e(target) && (double)target.f(this) < 5.5D + (double)size / 2D) {
                shootTarget();
                attTime = -10;
            }
            if(attTime < 20)
                attTime++;
        }
        smokeTime++;
        if(smokeTime >= (seeprey ? 3 : 8)) {
            smokeTime = 0;
            int i = MathHelper.floor(locX);
            int j = MathHelper.floor(boundingBox.b);
            int k = MathHelper.floor(locZ);
            if(world.getTypeId(i, j - 1, k) != BlockManager.Grass.id && grounded)
                dead = true;
        }
        seeprey = target != null;
    }
    
    @Override
    public void die() {
        if(!noDespawn || health <= 0)
            super.die();
    }

    public void shootTarget() {
        if(world.spawnMonsters == 0)
            return;
        else {
            double d1 = target.locX - locX;
            double d2 = target.locZ - locZ;
            double d3 = 1.5D / Math.sqrt(d1 * d1 + d2 * d2 + 0.10000000000000001D);
            double d4 = 0.10000000000000001D + Math.sqrt(d1 * d1 + d2 * d2 + 0.10000000000000001D) * 0.5D + (locY - target.locY) * 0.25D;
            d1 *= d3;
            d2 *= d3;
            EntityPoisonNeedle entityarrow = new EntityPoisonNeedle(world, this);
            entityarrow.locY = locY + 1.5D;
            world.addEntity(entityarrow);
            entityarrow.setArrowHeading(d1, d4, d2, 0.285F + (float)d4 * 0.05F, 1.0F);
            entityarrow.velocityChanged = true;
            return;
        }
    }
    
    @Override
    protected String h() {
        return "damage.hurtflesh";
    }
    
    @Override
    protected String i() {
        return "damage.fallbig";
    }
    
    @Override
    public void a(Entity entity, int ii, double dd, double dd1) {
        if(health > 0)
            return;
        else {
            super.a(entity, ii, dd, dd1);
            return;
        }
    }
    
    @Override
    public boolean a(EntityHuman entityhuman) {
        boolean flag = false;
        ItemStack stack = entityhuman.inventory.getItemInHand();
        if(stack != null && stack.id == ItemManager.Bucket.id && poisonLeft > 0) {
            poisonLeft--;
            entityhuman.inventory.setItem(entityhuman.inventory.itemInHandIndex, null);
            entityhuman.inventory.setItem(entityhuman.inventory.itemInHandIndex, new ItemStack(ItemManager.Bucket, 1, 2));
            return true;
        }
        if(flag)
            noDespawn = true;
        return false;
    }
    
    @Override
    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("Grounded", grounded);
        nbttagcompound.a("NoDespawn", noDespawn);
        nbttagcompound.a("AttTime", (short)attTime);
        nbttagcompound.a("Size", (short)size);
    }
    
    @Override
    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        grounded = nbttagcompound.m("Grounded");
        noDespawn = nbttagcompound.m("NoDespawn");
        attTime = nbttagcompound.d("AttTime");
        size = nbttagcompound.d("Size");
        b(0.75F + (float)size * 0.125F, 0.5F + (float)size * 0.075F);
        setPosition(locX, locY, locZ);
    }
    
    @Override
    public void die(Entity entity) {
    	if (W >= 0 && entity != null)
            entity.c(this, W);
        if (entity != null)
            entity.a(this);
        this.ak = true;
        if (entity instanceof EntityHuman)
            dropFewItems((EntityHuman) entity);
        else
        	q();
        world.a(this, (byte)3);
    }
    
    protected void dropFewItems(EntityHuman entityhuman) {
        List<org.bukkit.inventory.ItemStack> loot = new ArrayList<org.bukkit.inventory.ItemStack>();
        int count = 2 * (entityhuman != null && ItemManager.equippedSkyrootSword(entityhuman) ? 2 : 1);
        loot.add(new org.bukkit.inventory.ItemStack(ItemManager.AechorPetal.id, count));
        CraftEntity entity = (CraftEntity)this.getBukkitEntity();
        EntityDeathEvent event = new EntityDeathEvent(entity, loot);
        this.world.getServer().getPluginManager().callEvent(event);
        for (org.bukkit.inventory.ItemStack stack : event.getDrops())
            b(stack.getTypeId(), stack.getAmount());
    }
    
    @Override
    protected void q() {
    	dropFewItems(null);
    }

    public EntityLiving target;
    public int size;
    public int attTime;
    public int smokeTime;
    public boolean seeprey;
    public boolean grounded;
    public boolean noDespawn;
    public float sinage;
    private int poisonLeft;
}
