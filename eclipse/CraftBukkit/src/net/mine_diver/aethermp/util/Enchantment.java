package net.mine_diver.aethermp.util;

import net.minecraft.server.ItemStack;

public class Enchantment {

    public Enchantment(ItemStack itemstack, ItemStack itemstack1, int i) {
        enchantFrom = itemstack;
        enchantTo = itemstack1;
        enchantPowerNeeded = i;
    }

    public ItemStack enchantFrom;
    public ItemStack enchantTo;
    public int enchantPowerNeeded;
}
