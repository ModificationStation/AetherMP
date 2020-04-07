package net.mine_diver.aethermp.blocks;

import java.util.Random;

import net.mine_diver.aethermp.items.ItemManager;
import net.minecraft.server.Block;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.ItemStack;
import net.minecraft.server.Loc;
import net.minecraft.server.Material;
import net.minecraft.server.SAPI;
import net.minecraft.server.StatisticList;
import net.minecraft.server.World;

public class BlockAetherLog extends Block {

    protected BlockAetherLog(int i) {
        super(i, Material.WOOD);
    }
    
    @Override
    public int a(Random random) {
        return 0;
    }
    
    @Override
    public void a(World world, EntityHuman entityhuman, int i, int j, int k, int l) {
        entityhuman.a(StatisticList.C[id], 1);
        ItemStack itemstack = new ItemStack(id, 1, 0);
        if(ItemManager.equippedSkyrootAxe(entityhuman) && l != 1)
            itemstack.count *= 2;
        SAPI.drop(world, new Loc(i, j, k), itemstack);
        ItemStack itemstack2 = entityhuman.inventory.getItemInHand();
        if(itemstack2 == null || itemstack2.id != ItemManager.AxeZanite.id && itemstack2.id != ItemManager.AxeGravitite.id && l >= 2)
            return;
        if(l > 1 && rand.nextBoolean()) {
            ItemStack itemstack1 = new ItemStack(ItemManager.GoldenAmber.id, 2 + rand.nextInt(2), 0);
            SAPI.drop(world, new Loc(i, j, k), itemstack1);
        }
    }
    
    private static Random rand = new Random();
}
