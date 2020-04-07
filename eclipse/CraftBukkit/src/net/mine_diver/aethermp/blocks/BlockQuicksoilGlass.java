package net.mine_diver.aethermp.blocks;

import java.util.Random;

import net.minecraft.server.BlockBreakable;
import net.minecraft.server.Material;

public class BlockQuicksoilGlass extends BlockBreakable {

    public BlockQuicksoilGlass(int i) {
        super(i, -1, Material.SHATTERABLE, false);
        frictionFactor = 1.05F;
    }
    
    @Override
    public int a(Random random) {
        return 0;
    }
}
