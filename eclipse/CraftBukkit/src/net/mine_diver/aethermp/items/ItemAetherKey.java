package net.mine_diver.aethermp.items;

import net.minecraft.server.Item;

public class ItemAetherKey extends Item {

    protected ItemAetherKey(int i) {
        super(i);
        a("AetherKey");
        a(true);
        maxStackSize = 1;
    }
}
