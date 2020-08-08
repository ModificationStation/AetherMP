// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 
// Source File Name:   EntityZephyrSnowball.java

package net.mine_diver.aethermp.entity;

import net.minecraft.src.EntityZephyrSnowball;
import net.minecraft.src.World;

// Referenced classes of package net.minecraft.src:
//            Entity, AxisAlignedBB, EntityLiving, MathHelper, 
//            World, Vec3D, MovingObjectPosition, NBTTagCompound

public class EntityZephyrSnowballMp extends EntityZephyrSnowball
{

    public EntityZephyrSnowballMp(World world, double x, double y, double z) {
        super(world);
        posX = x;
        posY = y;
        posZ = z;
    }
}
