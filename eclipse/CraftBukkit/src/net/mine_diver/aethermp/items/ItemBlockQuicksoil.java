package net.mine_diver.aethermp.items;

import net.minecraft.server.ItemBlock;

public class ItemBlockQuicksoil extends ItemBlock {

    public ItemBlockQuicksoil(int i) {
        super(i);
        a("BlockQuicksoil");
        a(true);
    }
    
    @Override
    public int filterData(int i) {
        return 1;
    }
}
