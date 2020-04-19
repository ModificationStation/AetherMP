package net.mine_diver.aethermp.entities;

import java.util.List;

import net.mine_diver.aethermp.bukkit.craftbukkit.entity.CraftEntityAether;
import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.Block;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityWeatherStorm;
import net.minecraft.server.MathHelper;
import net.minecraft.server.World;

public class EntityAetherLightning extends EntityWeatherStorm {

    public EntityAetherLightning(World var1, double var2, double var4, double var6) {
        super(var1, var2, var4, var6);
        boltVertex = 0L;
    }
    
    @Override
    public void m_() {
        super.m_();
        /*if(lightningState == 2) {
            world.playSoundEffect(locX, locY, locZ, "ambient.weather.thunder", 10000F, 0.8F + random.nextFloat() * 0.2F);
            world.playSoundEffect(locX, locY, locZ, "random.explode", 2.0F, 0.5F + random.nextFloat() * 0.2F);
        }*/
        lightningState--;
        if(lightningState < 0) {
            if(boltLivingTime == 0)
                die();
            else if(lightningState < -random.nextInt(10)) {
                boltLivingTime--;
                lightningState = 1;
                boltVertex = random.nextLong();
                if(world.areChunksLoaded(MathHelper.floor(locX), MathHelper.floor(locY), MathHelper.floor(locZ), 10)) {
                    int var1 = MathHelper.floor(locX);
                    int var2 = MathHelper.floor(locY);
                    int var3 = MathHelper.floor(locZ);
                    if(world.getTypeId(var1, var2, var3) == 0 && Block.FIRE.canPlace(world, var1, var2, var3))
                        world.setTypeId(var1, var2, var3, Block.FIRE.id);
                }
            }
        }
        if(lightningState >= 0) {
            double var6 = 3D;
            @SuppressWarnings("unchecked")
			List<Entity> var7 = world.b(this, AxisAlignedBB.b(locX - var6, locY - var6, locZ - var6, locX + var6, locY + 6D + var6, locZ + var6));
            for(int var4 = 0; var4 < var7.size(); var4++) {
                Entity var5 = var7.get(var4);
                if(!(var5 instanceof EntityHuman))
                    var5.a(this);
            }
            world.n = 2;
        }
    }
    
    @Override
    public org.bukkit.entity.Entity getBukkitEntity() {
        if (this.bukkitEntity == null)
            this.bukkitEntity = CraftEntityAether.getEntity(this.world.getServer(), this);
        return this.bukkitEntity;
    }

    private int lightningState;
    public long boltVertex;
    private int boltLivingTime;
}
