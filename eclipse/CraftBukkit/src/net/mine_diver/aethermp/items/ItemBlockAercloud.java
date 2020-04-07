package net.mine_diver.aethermp.items;

import net.minecraft.server.ItemBlock;

public class ItemBlockAercloud extends ItemBlock {

    public ItemBlockAercloud(int i) {
        super(i);
        a("BlockAercloud");
        a(true);
    }
    
    @Override
    public int filterData(int i) {
        return i;
    }
}
