package net.mine_diver.aethermp.dimension.world.generation;

import java.util.Random;

import net.minecraft.server.World;
import net.minecraft.server.WorldGenerator;

public class AetherGenBuildings extends WorldGenerator {
	
	@Override
    public boolean a(World world, Random random, int i, int j, int k) {
        return false;
    }

    public void setBlocks(int i, int j, int k) {
        blockID1 = i;
        blockID2 = j;
        chance = k;
        if(chance < 1)
            chance = 1;
    }

    public void setMetadata(int i, int j) {
        meta1 = i;
        meta2 = j;
    }

    public void addLineX(World world, Random random, int i, int j, int k, int l) {
        org.bukkit.World worldBukkit = world.getWorld();
        org.bukkit.block.Block blockBukkit;
        for(int i1 = i; i1 < i + l; i1++) {
            blockBukkit = worldBukkit.getBlockAt(i1, j, k);
            if(!replaceAir && world.getTypeId(i1, j, k) == 0 || !replaceSolid && world.getTypeId(i1, j, k) != 0)
                continue;
            if(random.nextInt(chance) == 0)
                blockBukkit.setTypeIdAndData(blockID2, (byte)meta2, false);
            else
                blockBukkit.setTypeIdAndData(blockID1, (byte)meta1, false);
        }
    }

    public void addLineY(World world, Random random, int i, int j, int k, int l) {
        org.bukkit.World worldBukkit = world.getWorld();
        org.bukkit.block.Block blockBukkit;
        for(int i1 = j; i1 < j + l; i1++) {
            blockBukkit = worldBukkit.getBlockAt(i, i1, k);
            if(!replaceAir && world.getTypeId(i, i1, k) == 0 || !replaceSolid && world.getTypeId(i, i1, k) != 0)
                continue;
            if(random.nextInt(chance) == 0)
                blockBukkit.setTypeIdAndData(blockID2, (byte)meta2, false);
            else
                blockBukkit.setTypeIdAndData(blockID1, (byte)meta1, false);
        }
    }

    public void addLineZ(World world, Random random, int i, int j, int k, int l) {
        org.bukkit.World worldBukkit = world.getWorld();
        org.bukkit.block.Block blockBukkit;
        for(int i1 = k; i1 < k + l; i1++) {
            blockBukkit = worldBukkit.getBlockAt(i, j, i1);
            if(!replaceAir && world.getTypeId(i, j, i1) == 0 || !replaceSolid && world.getTypeId(i, j, i1) != 0)
                continue;
            if(random.nextInt(chance) == 0)
                blockBukkit.setTypeIdAndData(blockID2, (byte)meta2, false);
            else
                blockBukkit.setTypeIdAndData(blockID1, (byte)meta1, false);
        }
    }

    public void addPlaneX(World world, Random random, int i, int j, int k, int l, int i1) {
        org.bukkit.World worldBukkit = world.getWorld();
        org.bukkit.block.Block blockBukkit;
        for(int j1 = j; j1 < j + l; j1++)
            for(int k1 = k; k1 < k + i1; k1++) {
                blockBukkit = worldBukkit.getBlockAt(i, j1, k1);
                if(!replaceAir && world.getTypeId(i, j1, k1) == 0 || !replaceSolid && world.getTypeId(i, j1, k1) != 0)
                    continue;
                if(random.nextInt(chance) == 0)
                    blockBukkit.setTypeIdAndData(blockID2, (byte)meta2, false);
                else
                    blockBukkit.setTypeIdAndData(blockID1, (byte)meta1, false);
            }
    }

    public void addPlaneY(World world, Random random, int i, int j, int k, int l, int i1) {
        org.bukkit.World worldBukkit = world.getWorld();
        org.bukkit.block.Block blockBukkit;
        for(int j1 = i; j1 < i + l; j1++)
            for(int k1 = k; k1 < k + i1; k1++) {
                blockBukkit = worldBukkit.getBlockAt(j1, j, k1);
                if(!replaceAir && world.getTypeId(j1, j, k1) == 0 || !replaceSolid && world.getTypeId(j1, j, k1) != 0)
                    continue;
                if(random.nextInt(chance) == 0)
                    blockBukkit.setTypeIdAndData(blockID2, (byte)meta2, false);
                else
                    blockBukkit.setTypeIdAndData(blockID1, (byte)meta1, false);
            }
    }

    public void addPlaneZ(World world, Random random, int i, int j, int k, int l, int i1) {
        org.bukkit.World worldBukkit = world.getWorld();
        org.bukkit.block.Block blockBukkit;
        for(int j1 = i; j1 < i + l; j1++)
            for(int k1 = j; k1 < j + i1; k1++) {
                blockBukkit = worldBukkit.getBlockAt(j1, k1, k);
                if(!replaceAir && world.getTypeId(j1, k1, k) == 0 || !replaceSolid && world.getTypeId(j1, k1, k) != 0)
                    continue;
                if(random.nextInt(chance) == 0)
                    blockBukkit.setTypeIdAndData(blockID2, (byte)meta2, false);
                else
                    blockBukkit.setTypeIdAndData(blockID1, (byte)meta1, false);
            }
    }

    public void addHollowBox(World world, Random random, int i, int j, int k, int l, int i1, int j1) {
        int k1 = blockID1;
        int l1 = blockID2;
        setBlocks(0, 0, chance);
        addSolidBox(world, random, i, j, k, l, i1, j1);
        setBlocks(k1, l1, chance);
        addPlaneY(world, random, i, j, k, l, j1);
        addPlaneY(world, random, i, (j + i1) - 1, k, l, j1);
        addPlaneX(world, random, i, j, k, i1, j1);
        addPlaneX(world, random, (i + l) - 1, j, k, i1, j1);
        addPlaneZ(world, random, i, j, k, l, i1);
        addPlaneZ(world, random, i, j, (k + j1) - 1, l, i1);
    }

    public void addSquareTube(World world, Random random, int i, int j, int k, int l, int i1, int j1, int k1) {
        int l1 = blockID1;
        int i2 = blockID2;
        setBlocks(0, 0, chance);
        addSolidBox(world, random, i, j, k, l, i1, j1);
        setBlocks(l1, i2, chance);
        if(k1 == 0 || k1 == 2) {
            addPlaneY(world, random, i, j, k, l, j1);
            addPlaneY(world, random, i, (j + i1) - 1, k, l, j1);
        }
        if(k1 == 1 || k1 == 2) {
            addPlaneX(world, random, i, j, k, i1, j1);
            addPlaneX(world, random, (i + l) - 1, j, k, i1, j1);
        }
        if(k1 == 0 || k1 == 1) {
            addPlaneZ(world, random, i, j, k, l, i1);
            addPlaneZ(world, random, i, j, (k + j1) - 1, l, i1);
        }
    }

    public void addSolidBox(World world, Random random, int i, int j, int k, int l, int i1, int j1) {
        org.bukkit.World worldBukkit = world.getWorld();
        org.bukkit.block.Block blockBukkit;
        for(int k1 = i; k1 < i + l; k1++)
            for(int l1 = j; l1 < j + i1; l1++)
                for(int i2 = k; i2 < k + j1; i2++) {
                    blockBukkit = worldBukkit.getBlockAt(k1, l1, i2);
                    if(!replaceAir && world.getTypeId(k1, l1, i2) == 0 || !replaceSolid && world.getTypeId(k1, l1, i2) != 0)
                        continue;
                    if(random.nextInt(chance) == 0)
                        blockBukkit.setTypeIdAndData(blockID2, (byte)meta2, false);
                    else
                        blockBukkit.setTypeIdAndData(blockID1, (byte)meta1, false);
                }
    }

    public boolean isBoxSolid(World world, int i, int j, int k, int l, int i1, int j1) {
        for(int k1 = i; k1 < i + l; k1++)
            for(int l1 = j; l1 < j + i1; l1++)
                for(int i2 = k; i2 < k + j1; i2++)
                    if(world.getTypeId(k1, l1, i2) == 0)
                        return false;
        return true;
    }

    public boolean isBoxEmpty(World world, int i, int j, int k, int l, int i1, int j1) {
        for(int k1 = i; k1 < i + l; k1++)
            for(int l1 = j; l1 < j + i1; l1++)
                for(int i2 = k; i2 < k + j1; i2++)
                    if(world.getTypeId(k1, l1, i2) != 0)
                        return false;
        return true;
    }

    public int blockID1;
    public int blockID2;
    public int meta1;
    public int meta2;
    public int chance;
    public boolean replaceAir;
    public boolean replaceSolid;
}
