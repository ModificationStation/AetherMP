package net.mine_diver.aethermp.entities;

import net.mine_diver.aethermp.blocks.BlockManager;
import net.minecraft.server.EntityAnimal;
import net.minecraft.server.MathHelper;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.World;

public abstract class EntityAetherAnimal extends EntityAnimal {

    public EntityAetherAnimal(World world) {
        super(world);
    }
    
    @Override
    protected float a(int i, int j, int k) {
        if(world.getTypeId(i, j - 1, k) == BlockManager.Grass.id)
            return 10F;
        else
            return world.n(i, j, k) - 0.5F;
    }

    @Override
    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
    }

    @Override
    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
    }

    @Override
    public boolean d() {
        int i = MathHelper.floor(locX);
        int j = MathHelper.floor(boundingBox.b);
        int k = MathHelper.floor(locZ);
        return world.containsEntity(boundingBox) && world.getEntities(this, boundingBox).size() == 0 && !world.c(boundingBox) && world.getTypeId(i, j - 1, k) == BlockManager.Grass.id && world.k(i, j, k) > 8 && a(i, j, k) >= 0.0F;
    }
    
    @Override
    public int e() {
        return 120;
    }
}
