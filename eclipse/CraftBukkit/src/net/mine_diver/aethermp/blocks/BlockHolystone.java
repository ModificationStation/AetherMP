package net.mine_diver.aethermp.blocks;

import net.mine_diver.aethermp.items.ItemManager;
import net.minecraft.server.Block;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.ItemStack;
import net.minecraft.server.Loc;
import net.minecraft.server.Material;
import net.minecraft.server.SAPI;
import net.minecraft.server.StatisticList;
import net.minecraft.server.World;

public class BlockHolystone extends Block{

    protected BlockHolystone(int ID) {
        super(ID, Material.STONE);
    }
    
    @Override
    public void a(World world, EntityHuman entityhuman, int i, int j, int k, int l) {
        entityhuman.a(StatisticList.C[id], 1);
        ItemStack itemstack = new ItemStack(id, 1, l > 1 ? 2 : 0);
        if(ItemManager.equippedSkyrootPick(entityhuman) && (l == 0 || l == 2))
            itemstack.count *= 2;
        SAPI.drop(world, new Loc(i, j, k), itemstack);
    }
}
