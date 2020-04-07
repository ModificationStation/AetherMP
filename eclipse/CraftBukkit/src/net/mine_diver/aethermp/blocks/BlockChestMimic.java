package net.mine_diver.aethermp.blocks;

import java.util.Random;

import net.mine_diver.aethermp.entities.EntityMimic;
import net.minecraft.server.Block;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.Material;
import net.minecraft.server.World;

public class BlockChestMimic extends Block {

    protected BlockChestMimic(int i) {
        super(i, Material.WOOD);
    }
    
    @Override
    public boolean canPlace(World world, int i, int j, int k) {
        int l = 0;
        if(world.getTypeId(i - 1, j, k) == id)
            l++;
        if(world.getTypeId(i + 1, j, k) == id)
            l++;
        if(world.getTypeId(i, j, k - 1) == id)
            l++;
        if(world.getTypeId(i, j, k + 1) == id)
            l++;
        if(l > 1)
            return false;
        if(isThereANeighborChest(world, i - 1, j, k))
            return false;
        if(isThereANeighborChest(world, i + 1, j, k))
            return false;
        if(isThereANeighborChest(world, i, j, k - 1))
            return false;
        else
            return !isThereANeighborChest(world, i, j, k + 1);
    }
    
    private boolean isThereANeighborChest(World world, int i, int j, int k) {
        if(world.getTypeId(i, j, k) != id)
            return false;
        if(world.getTypeId(i - 1, j, k) == id)
            return true;
        if(world.getTypeId(i + 1, j, k) == id)
            return true;
        if(world.getTypeId(i, j, k - 1) == id)
            return true;
        else
            return world.getTypeId(i, j, k + 1) == id;
    }
    
    @Override
    public void remove(World world, int i, int j, int k) {
        world.setTypeId(i, j, k, 0);
        EntityMimic entitymimic = new EntityMimic(world);
        entitymimic.setPosition((double)i + 0.5D, (double)j + 1.5D, (double)k + 0.5D);
        world.addEntity(entitymimic);
    }
    
    @Override
    public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman) {
        world.setTypeId(i, j, k, 0);
        return true;
    }
    
    @Override
    public int a(Random random) {
        return 0;
    }
}
