package net.mine_diver.aethermp.blocks;

import net.mine_diver.aethermp.items.ItemManager;
import net.minecraft.server.Block;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.Material;
import net.minecraft.server.StatisticList;
import net.minecraft.server.World;

public class BlockIcestone extends Block {

    protected BlockIcestone(int ID) {
        super(ID, Material.STONE);
    }
    
    @Override
    public void postPlace(World world, int i, int j, int k, int l) {
        world.setTypeId(i, j, k, id);
        world.setData(i, j, k, 1);
        for(int i1 = i - 3; i1 < i + 4; i1++)
            for(int j1 = j - 1; j1 < j + 2; j1++)
                for(int k1 = k - 3; k1 < k + 4; k1++) {
                    if((i1 - i) * (i1 - i) + (j1 - j) * (j1 - j) + (k1 - k) * (k1 - k) < 8 && world.getTypeId(i1, j1, k1) == Block.STATIONARY_WATER.id)
                        world.setTypeId(i1, j1, k1, Block.ICE.id);
                    if((i1 - i) * (i1 - i) + (j1 - j) * (j1 - j) + (k1 - k) * (k1 - k) < 8 && world.getTypeId(i1, j1, k1) == Block.STATIONARY_LAVA.id)
                        world.setTypeId(i1, j1, k1, Block.OBSIDIAN.id);
                }
    }
    
    @Override
    public void a(World world, EntityHuman entityhuman, int i, int j, int k, int l) {
        entityhuman.a(StatisticList.C[id], 1);
        if(l == 0 && ItemManager.equippedSkyrootPick(entityhuman))
            g(world, i, j, k, l);
        g(world, i, j, k, l);
    }
}
