package net.mine_diver.aethermp.blocks;

import java.util.Random;

import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.Block;
import net.minecraft.server.Material;
import net.minecraft.server.World;

public class BlockAetherFlower extends Block {

    protected BlockAetherFlower(int i) {
        super(i, Material.PLANT);
        a(true);
        float f = 0.2F;
        a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 3F, 0.5F + f);
    }
    
    @Override
    public boolean canPlace(World world, int i, int j, int k) {
        return super.canPlace(world, i, j, k) && canThisPlantGrowOnThisBlockID(world.getTypeId(i, j - 1, k));
    }
    
    protected boolean canThisPlantGrowOnThisBlockID(int i) {
        return i == BlockManager.Grass.id || i == BlockManager.Dirt.id;
    }
    
    @Override
    public void doPhysics(World world, int i, int j, int k, int l) {
        super.doPhysics(world, i, j, k, l);
        checkFlowerChange(world, i, j, k);
    }
    
    @Override
    public void a(World world, int i, int j, int k, Random random) {
    	checkFlowerChange(world, i, j, k);
    }
    
    protected final void checkFlowerChange(World world, int i, int j, int k) {
        if(!f(world, i, j, k)) {
            g(world, i, j, k, world.getData(i, j, k));
            world.setTypeId(i, j, k, 0);
        }
    }
    
    @Override
    public boolean f(World world, int i, int j, int k) {
        return (world.k(i, j, k) >= 8 || world.isChunkLoaded(i, j, k)) && canThisPlantGrowOnThisBlockID(world.getTypeId(i, j - 1, k));
    }
    
    @Override
    public AxisAlignedBB e(World world, int i, int j, int k) {
        return null;
    }
    
    @Override
    public boolean a() {
        return false;
    }
    
    @Override
    public boolean b() {
        return false;
    }
}
