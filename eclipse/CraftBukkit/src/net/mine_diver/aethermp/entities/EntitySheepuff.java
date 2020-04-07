package net.mine_diver.aethermp.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.event.entity.EntityDeathEvent;

import net.mine_diver.aethermp.blocks.BlockManager;
import net.minecraft.server.Block;
import net.minecraft.server.BlockCloth;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityItem;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.MathHelper;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.World;

public class EntitySheepuff extends EntityAetherAnimal {

    public EntitySheepuff(World world) {
        super(world);
        b(0.9F, 1.3F);
        setFleeceColor(getRandomFleeceColor(random));
        amountEaten = 0;
    }
    
    @Override
    protected void b() {
        super.b();
        datawatcher.a(16, new Byte((byte)0));
    }
    
    @Override
    protected void q() {
        final List<org.bukkit.inventory.ItemStack> loot = new ArrayList<org.bukkit.inventory.ItemStack>();
        if (!this.getSheared())
            loot.add(new org.bukkit.inventory.ItemStack(org.bukkit.Material.WOOL, 1, (short)0, Byte.valueOf((byte) this.getFleeceColor())));
        final org.bukkit.World bworld = this.world.getWorld();
        final org.bukkit.entity.Entity entity = this.getBukkitEntity();
        final EntityDeathEvent event = new EntityDeathEvent(entity, loot);
        this.world.getServer().getPluginManager().callEvent(event);
        for (final org.bukkit.inventory.ItemStack stack : event.getDrops())
            bworld.dropItemNaturally(entity.getLocation(), stack);
    }
    
    @Override
    public boolean a(EntityHuman entityhuman) {
        ItemStack itemstack = entityhuman.inventory.getItemInHand();
        if (itemstack != null && itemstack.id == Item.SHEARS.id && !getSheared()) {
            if (getPuffed()) {
                setPuffed(false);
                int i = 2 + random.nextInt(3);
                for (int l = 0; l < i; l++) {
                    EntityItem entityitem = a(new ItemStack(Block.WOOL.id, 1, getFleeceColor()), 1.0F);
                    entityitem.motY += random.nextFloat() * 0.05F;
                    entityitem.motX += (random.nextFloat() - random.nextFloat()) * 0.1F;
                    entityitem.motZ += (random.nextFloat() - random.nextFloat()) * 0.1F;
                }

            } else {
                setSheared(true);
                int j = 2 + random.nextInt(3);
                for (int i1 = 0; i1 < j; i1++) {
                    EntityItem entityitem1 = a(new ItemStack(Block.WOOL.id, 1, getFleeceColor()), 1.0F);
                    entityitem1.motY += random.nextFloat() * 0.05F;
                    entityitem1.motX += (random.nextFloat() - random.nextFloat()) * 0.1F;
                    entityitem1.motZ += (random.nextFloat() - random.nextFloat()) * 0.1F;
                }

            }
            itemstack.damage(1, entityhuman);
        }
        if (itemstack != null && itemstack.id == Item.INK_SACK.id && !getSheared()) {
            int k = BlockCloth.c(itemstack.getData());
            if (getFleeceColor() != k) {
                if (getPuffed() && itemstack.count >= 2) {
                    setFleeceColor(k);
                    itemstack.count -= 2;
                } else if (!getPuffed()) {
                    setFleeceColor(k);
                    itemstack.count--;
                }
            }
        }
        return false;
    }
    
    @Override
    protected void O() {
        if (getPuffed()) {
            motY = 1.8D;
            motX += random.nextGaussian() * 0.5D;
            motZ += random.nextGaussian() * 0.5D;
        } else
            motY = 0.41999998688697815D;
    }
    
    @Override
    public void m_() {
        super.m_();
        if (getPuffed()) {
            fallDistance = 0.0F;
            if(motY < -0.050000000000000003D)
                motY = -0.050000000000000003D;
        }
        if (random.nextInt(100) == 0) {
            int i = MathHelper.floor(locX);
            int j = MathHelper.floor(locY);
            int k = MathHelper.floor(locZ);
            if(world.getTypeId(i, j - 1, k) == BlockManager.Grass.id) {
                world.setTypeId(i, j - 1, k, BlockManager.Dirt.id);
                amountEaten++;
            }
        }
        if (amountEaten == 5 && !getSheared() && !getPuffed()) {
            setPuffed(true);
            amountEaten = 0;
        }
        if (amountEaten == 10 && getSheared() && !getPuffed()) {
            setSheared(false);
            setFleeceColor(0);
            amountEaten = 0;
        }
    }
    
    @Override
    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("Sheared", getSheared());
        nbttagcompound.a("Puffed", getPuffed());
        nbttagcompound.a("Color", (byte) getFleeceColor());
    }
    
    @Override
    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        setSheared(nbttagcompound.m("Sheared"));
        setPuffed(nbttagcompound.m("Puffed"));
        setFleeceColor(nbttagcompound.c("Color"));
    }
    
    @Override
    protected String g() {
        return "mob.sheep";
    }
    
    @Override
    protected String h() {
        return "mob.sheep";
    }
    
    @Override
    protected String i() {
        return "mob.sheep";
    }
    
    public int getFleeceColor() {
        return datawatcher.a(16) & 0xf;
    }

    public void setFleeceColor(int i) {
        byte byte0 = datawatcher.a(16);
        datawatcher.watch(16, Byte.valueOf((byte) (byte0 & 0xf0 | i & 0xf)));
    }

    public boolean getSheared() {
        return (datawatcher.a(16) & 0x10) != 0;
    }

    public void setSheared(boolean flag) {
        byte byte0 = datawatcher.a(16);
        if(flag)
            datawatcher.watch(16, Byte.valueOf((byte) (byte0 | 0x10)));
        else
            datawatcher.watch(16, Byte.valueOf((byte) (byte0 & 0xffffffef)));
    }

    public boolean getPuffed() {
        return (datawatcher.a(16) & 0x20) != 0;
    }

    public void setPuffed(boolean flag) {
        byte byte0 = datawatcher.a(16);
        if(flag)
            datawatcher.watch(16, Byte.valueOf((byte) (byte0 | 0x20)));
        else
            datawatcher.watch(16, Byte.valueOf((byte) (byte0 & 0xffffffdf)));
    }

    public static int getRandomFleeceColor(Random random) {
        int i = random.nextInt(100);
        if(i < 5)
            return 3;
        if(i < 10)
            return 9;
        if(i < 15)
            return 5;
        if(i < 18)
            return 6;
        else
            return random.nextInt(500) == 0 ? 10 : 0;
    }
    
    private int amountEaten;

}
