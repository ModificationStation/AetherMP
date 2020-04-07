package net.mine_diver.aethermp.blocks;

import java.util.Random;

import net.mine_diver.aethermp.entities.EntityFloatingBlock;
import net.minecraft.server.Block;
import net.minecraft.server.Material;
import net.minecraft.server.World;

public class BlockFloating extends Block {

    public BlockFloating(int i, boolean flag) {
        super(i, Material.STONE);
        enchanted = flag;
    }
    
    @Override
    public void c(World world, int i, int j, int k) {
        world.c(i, j, k, id, c());
    }
    
    @Override
    public void doPhysics(World world, int i, int j, int k, int l) {
        world.c(i, j, k, id, c());
    }
    
    @Override
    public void a(World world, int i, int j, int k, Random random) {
        if(!enchanted || enchanted && world.isBlockPowered(i, j, k))
            tryToFall(world, i, j, k);
    }

    private void tryToFall(World world, int i, int j, int k) {
        int l = i;
        int i1 = j;
        int j1 = k;
        if(canFallAbove(world, l, i1 + 1, j1) && i1 < 128) {
            byte byte0 = 32;
            if(fallInstantly || !world.a(i - byte0, j - byte0, k - byte0, i + byte0, j + byte0, k + byte0)) {
                world.setTypeId(i, j, k, 0);
                for(; canFallAbove(world, i, j + 1, k) && j < 128; j++) { }
                if(j > 0)
                    world.setTypeId(i, j, k, id);
            } else {
                EntityFloatingBlock entityfloatingblock = new EntityFloatingBlock(world, (float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F, id);
                world.addEntity(entityfloatingblock);
            }
        }
    }
    
    @Override
    public int c() {
        return 3;
    }
    
    public static boolean canFallAbove(World world, int i, int j, int k) {
        int l = world.getTypeId(i, j, k);
        if(l == 0)
            return true;
        if(l == Block.FIRE.id)
            return true;
        Material material = Block.byId[l].material;
        if(material == Material.WATER)
            return true;
        else
            return material == Material.LAVA;
    }
    
    public static boolean fallInstantly = false;
    private boolean enchanted;

}
