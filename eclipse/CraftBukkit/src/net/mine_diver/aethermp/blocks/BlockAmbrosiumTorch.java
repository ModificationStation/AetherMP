package net.mine_diver.aethermp.blocks;

import java.util.Random;

import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.Block;
import net.minecraft.server.Material;
import net.minecraft.server.MovingObjectPosition;
import net.minecraft.server.Vec3D;
import net.minecraft.server.World;

public class BlockAmbrosiumTorch extends Block {

    protected BlockAmbrosiumTorch(int i) {
        super(i, Material.ORIENTABLE);
        a(true);
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
    
    @Override
    public boolean canPlace(World world, int i, int j, int k) {
        if(world.e(i - 1, j, k))
            return true;
        if(world.e(i + 1, j, k))
            return true;
        if(world.e(i, j, k - 1))
            return true;
        if(world.e(i, j, k + 1))
            return true;
        else
            return world.e(i, j - 1, k);
    }
    
    @Override
    public void postPlace(World world, int i, int j, int k, int l) {
        int i1 = world.getData(i, j, k);
        if(l == 1 && world.e(i, j - 1, k))
            i1 = 5;
        if(l == 2 && world.e(i, j, k + 1))
            i1 = 4;
        if(l == 3 && world.e(i, j, k - 1))
            i1 = 3;
        if(l == 4 && world.e(i + 1, j, k))
            i1 = 2;
        if(l == 5 && world.e(i - 1, j, k))
            i1 = 1;
        world.setData(i, j, k, i1);
    }
    
    @Override
    public void a(World world, int i, int j, int k, Random random) {
        super.a(world, i, j, k, random);
        if(world.getData(i, j, k) == 0)
            c(world, i, j, k);
    }
    
    @Override
    public void c(World world, int i, int j, int k) {
        if(world.e(i - 1, j, k))
            world.setData(i, j, k, 1);
        else if(world.e(i + 1, j, k))
            world.setData(i, j, k, 2);
        else if(world.e(i, j, k - 1))
            world.setData(i, j, k, 3);
        else if(world.e(i, j, k + 1))
            world.setData(i, j, k, 4);
        else if(world.e(i, j - 1, k))
            world.setData(i, j, k, 5);
        dropTorchIfCantStay(world, i, j, k);
    }
    
    @Override
    public void doPhysics(World world, int i, int j, int k, int l) {
        if(dropTorchIfCantStay(world, i, j, k)) {
            int i1 = world.getData(i, j, k);
            boolean flag = false;
            if(!world.e(i - 1, j, k) && i1 == 1)
                flag = true;
            if(!world.e(i + 1, j, k) && i1 == 2)
                flag = true;
            if(!world.e(i, j, k - 1) && i1 == 3)
                flag = true;
            if(!world.e(i, j, k + 1) && i1 == 4)
                flag = true;
            if(!world.e(i, j - 1, k) && i1 == 5)
                flag = true;
            if(flag) {
                g(world, i, j, k, world.getData(i, j, k));
                world.setTypeId(i, j, k, 0);
            }
        }
    }
    
    private boolean dropTorchIfCantStay(World world, int i, int j, int k) {
        if(!canPlace(world, i, j, k)) {
            g(world, i, j, k, world.getData(i, j, k));
            world.setTypeId(i, j, k, 0);
            return false;
        } else
            return true;
    }
    
    @Override
    public MovingObjectPosition a(World world, int i, int j, int k, Vec3D vec3d, Vec3D vec3d1) {
        int l = world.getData(i, j, k) & 7;
        float f = 0.15F;
        if(l == 1)
            a(0.0F, 0.2F, 0.5F - f, f * 2.0F, 0.8F, 0.5F + f);
        else if(l == 2)
            a(1.0F - f * 2.0F, 0.2F, 0.5F - f, 1.0F, 0.8F, 0.5F + f);
        else if(l == 3)
            a(0.5F - f, 0.2F, 0.0F, 0.5F + f, 0.8F, f * 2.0F);
        else if(l == 4)
            a(0.5F - f, 0.2F, 1.0F - f * 2.0F, 0.5F + f, 0.8F, 1.0F);
        else {
            float f1 = 0.1F;
            a(0.5F - f1, 0.0F, 0.5F - f1, 0.5F + f1, 0.6F, 0.5F + f1);
        }
        return super.a(world, i, j, k, vec3d, vec3d1);
    }
}
