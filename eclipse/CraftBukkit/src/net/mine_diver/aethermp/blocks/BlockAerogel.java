package net.mine_diver.aethermp.blocks;

import net.minecraft.server.Block;
import net.minecraft.server.Material;

public class BlockAerogel extends Block {

    public BlockAerogel(int blockID) {
        super(blockID, -1, Material.STONE);
    }

    public boolean isOpaqueCube() {
        return false;
    }
}