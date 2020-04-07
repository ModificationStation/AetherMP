package net.mine_diver.aethermp.items;

import net.minecraft.server.ItemBlock;

public class ItemBlockAetherLog extends ItemBlock {

    public ItemBlockAetherLog(int i) {
        super(i);
        a("BlockAetherLog");
        a(true);
    }
    
    @Override
    public int filterData(int i) {
        return i > 1 ? 3 : 1;
    }
}
