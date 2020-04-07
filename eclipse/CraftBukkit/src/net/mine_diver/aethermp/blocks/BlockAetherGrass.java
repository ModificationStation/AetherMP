package net.mine_diver.aethermp.blocks;

import java.util.Random;

import net.mine_diver.aethermp.items.ItemManager;
import net.minecraft.server.Block;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.Material;
import net.minecraft.server.StatisticList;
import net.minecraft.server.World;
import net.minecraft.server.mod_AetherMp;

public class BlockAetherGrass extends Block {

    protected BlockAetherGrass(int ID) {
        super(ID, Material.EARTH);
        a(true);
    }
    
    @Override
    public void a(World world, int i, int j, int k, Random random) {
        if (world.getLightLevel(i, j + 1, k) < 4 && Block.q[world.getTypeId(i, j + 1, k)] > 2) {
            if (random.nextInt(4) != 0)
                return;
            world.setTypeId(i, j, k, BlockManager.Dirt.id);
        } else if (world.getLightLevel(i, j + 1, k) >= 9) {
            int l = i + random.nextInt(3) - 1;
            int i1 = j + random.nextInt(5) - 3;
            int j1 = k + random.nextInt(3) - 1;
            int k1 = world.getTypeId(l, i1 + 1, j1);
            if (world.getTypeId(l, i1, j1) == BlockManager.Dirt.id && world.getLightLevel(l, i1 + 1, j1) >= 4 && Block.q[k1] <= 2)
                world.setTypeId(l, i1, j1, BlockManager.Grass.id);
        }
    }
    
    @Override
    public int a(int i, Random random) {
        return BlockManager.Dirt.a(0, random);
    }
    
    @Override
    public void a(World world, EntityHuman entityhuman, int i, int j, int k, int l) {
        entityhuman.a(StatisticList.C[id], 1);
        if(l == 0 && ItemManager.equippedSkyrootShovel(entityhuman))
            g(world, i, j, k, l);
        g(world, i, j, k, l);
    }
    
    @Override
    public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman) {
        if(entityhuman == null)
            return false;
        ItemStack itemstack = entityhuman.G();
        if(itemstack == null)
            return false;
        if(itemstack.id != Item.INK_SACK.id)
            return false;
        if(itemstack.getData() != 15)
            return false;
        itemstack.count--;
        int l = 0;
label0:
        for(int i1 = 0; i1 < 64; i1++) {
            int j1 = i;
            int k1 = j + 1;
            int l1 = k;
            for(int i2 = 0; i2 < i1 / 16; i2++) {
                j1 += world.random.nextInt(3) - 1;
                k1 += ((world.random.nextInt(3) - 1) * world.random.nextInt(3)) / 2;
                l1 += world.random.nextInt(3) - 1;
                if(world.getTypeId(j1, k1 - 1, l1) != id || world.e(j1, k1, l1))
                    continue label0;
            }

            if(world.getTypeId(j1, k1, l1) != 0)
                continue;
            if(world.random.nextInt(20 + 10 * l) == 0) {
                world.setTypeId(j1, k1, l1, mod_AetherMp.idBlockWhiteFlower);
                l++;
                continue;
            }
            if(world.random.nextInt(10 + 2 * l) <= 2)  {
                world.setTypeId(j1, k1, l1, mod_AetherMp.idBlockPurpleFlower);
                l++;
            }
        }

        return true;
    }
}
