package net.mine_diver.aethermp.blocks;

import java.util.Random;

import net.mine_diver.aethermp.items.ItemManager;
import net.minecraft.server.Block;
import net.minecraft.server.Material;

public class BlockZaniteOre extends Block {

    protected BlockZaniteOre(int i) {
        super(i, Material.STONE);
    }
    
    @Override
    public int a(int i, Random random) {
        return ItemManager.Zanite.id;
    }
}
