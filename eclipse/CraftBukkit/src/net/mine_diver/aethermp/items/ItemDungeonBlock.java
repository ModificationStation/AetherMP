package net.mine_diver.aethermp.items;

import net.minecraft.server.ItemBlock;

public class ItemDungeonBlock extends ItemBlock {

    public ItemDungeonBlock(int i) {
        super(i);
        a(true);
    }
    
    @Override
    public int filterData(int i) {
        return i;
    }
}
