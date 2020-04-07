package net.mine_diver.aethermp.dimension.world.generation;

import java.util.Random;

import net.mine_diver.aethermp.blocks.BlockManager;
import net.minecraft.server.Block;
import net.minecraft.server.World;
import net.minecraft.server.WorldGenerator;

public class AetherGenLiquids extends WorldGenerator {

    public AetherGenLiquids(int i) {
        liquidBlockId = i;
    }
    
    @Override
    public boolean a(World world, Random random, int i, int j, int k) {
        org.bukkit.World worldBukkit = world.getWorld();
        if(world.getTypeId(i, j + 1, k) != BlockManager.Holystone.id || world.getData(i, j + 1, k) >= 2)
            return false;
        if(world.getTypeId(i, j - 1, k) != BlockManager.Holystone.id || world.getData(i, j - 1, k) >= 2)
            return false;
        if(world.getTypeId(i, j, k) != 0 && (world.getTypeId(i, j, k) != BlockManager.Holystone.id || world.getData(i, j, k) >= 2))
            return false;
        int l = 0;
        if(world.getTypeId(i - 1, j, k) == BlockManager.Holystone.id || world.getData(i - 1, j, k) >= 2)
            l++;
        if(world.getTypeId(i + 1, j, k) == BlockManager.Holystone.id || world.getData(i + 1, j, k) >= 2)
            l++;
        if(world.getTypeId(i, j, k - 1) == BlockManager.Holystone.id || world.getData(i, j, k - 1) >= 2)
            l++;
        if(world.getTypeId(i, j, k + 1) == BlockManager.Holystone.id || world.getData(i, j, k + 1) >= 2)
            l++;
        int i1 = 0;
        if(world.isEmpty(i - 1, j, k))
            i1++;
        if(world.isEmpty(i + 1, j, k))
            i1++;
        if(world.isEmpty(i, j, k - 1))
            i1++;
        if(world.isEmpty(i, j, k + 1))
            i1++;
        if(l == 3 && i1 == 1) {
            worldBukkit.getBlockAt(i, j, k).setTypeId(liquidBlockId);
            world.a = true;
            Block.byId[liquidBlockId].a(world, i, j, k, random);
            world.a = false;
        }
        return true;
    }

    private int liquidBlockId;
}
