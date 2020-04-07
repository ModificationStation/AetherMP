package net.mine_diver.aethermp.items;

import net.minecraft.server.ItemBlock;

public class ItemBlockHolystone extends ItemBlock {

    public ItemBlockHolystone(int i) {
        super(i);
        a("BlockHolystone");
        a(true);
    }
    
    @Override
    public int filterData(int meta) {
        return meta > 1 ? 3 : 1;
    }
}
