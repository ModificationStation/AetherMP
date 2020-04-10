package net.mine_diver.aethermp.util;

import net.minecraft.server.ItemStack;

public class Frozen {

    public Frozen(ItemStack from, ItemStack to, int i) {
        frozenFrom = from;
        frozenTo = to;
        frozenPowerNeeded = i;
    }

    public ItemStack frozenFrom;
    public ItemStack frozenTo;
    public int frozenPowerNeeded;
}
