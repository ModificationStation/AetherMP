package net.mine_diver.aethermp.items;

import net.minecraft.server.EntityHuman;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.World;

public class ItemGummieSwet extends Item {

    public ItemGummieSwet(int i) {
        super(i);
        maxStackSize = 64;
        healAmount = 20;
        a(true);
    }
    
    @Override
    public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman) {
        itemstack.count--;
        entityhuman.b(healAmount);
        return itemstack;
    }

    public int getHealAmount() {
        return healAmount;
    }

    private int healAmount;
}
