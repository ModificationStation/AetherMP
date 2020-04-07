package net.mine_diver.aethermp.blocks;

import net.mine_diver.aethermp.items.ItemManager;
import net.minecraft.server.Block;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.Material;
import net.minecraft.server.StatisticList;
import net.minecraft.server.World;

public class BlockAetherDirt extends Block {
	
    protected BlockAetherDirt(int ID){
        super(ID, Material.EARTH);
    }
    
    @Override
    public boolean a() {
        return true;
    }
    
    @Override
    public void postPlace(World world, int i, int j, int k, int l) {
        world.setTypeId(i, j, k, id);
        world.setData(i, j, k, 1);
    }
    
    @Override
    public void a(World world, EntityHuman entityhuman, int i, int j, int k, int l) {
        entityhuman.a(StatisticList.C[id], 1);
        if(l == 0 && ItemManager.equippedSkyrootShovel(entityhuman))
            g(world, i, j, k, l);
        g(world, i, j, k, l);
    }
}
