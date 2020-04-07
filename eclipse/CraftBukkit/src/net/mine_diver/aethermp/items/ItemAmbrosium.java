package net.mine_diver.aethermp.items;

import net.minecraft.server.EntityHuman;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.World;

public class ItemAmbrosium extends Item {

    public ItemAmbrosium(int i, int j) {
        super(i);
        healAmount = j;
        maxStackSize = 64;
    }
    
    @Override
    public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman) {
        itemstack.count--;
        entityhuman.b(healAmount);
        return itemstack;
    }

    private int healAmount;
}
