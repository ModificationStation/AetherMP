package net.mine_diver.aethermp.items;

import net.mine_diver.aethermp.player.PlayerManager;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.World;

public class ItemLifeShard extends Item {

    public ItemLifeShard(int i) {
        super(i);
        maxStackSize = 1;
    }
    
    @Override
    public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman) {
        itemstack.count--;
        if (entityhuman instanceof EntityPlayer)
        	PlayerManager.increaseMaxHP((EntityPlayer) entityhuman, 2);
        return itemstack;
    }
}
