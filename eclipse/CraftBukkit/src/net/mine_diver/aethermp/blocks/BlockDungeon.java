package net.mine_diver.aethermp.blocks;

import net.minecraft.server.Block;
import net.minecraft.server.Material;

public class BlockDungeon extends Block {

    protected BlockDungeon(int i) {
        super(i, Material.STONE);
    }
    
    @Override
    protected int a_(int i) {
        return i;
    }
}
