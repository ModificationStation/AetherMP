package net.mine_diver.aethermp.items;

import net.mine_diver.aethermp.api.player.AdditionalHealth;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.PlayerAPI;
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
        	((AdditionalHealth)PlayerAPI.getPlayerBase((EntityPlayer) entityhuman, AdditionalHealth.class)).increaseMaxHP(2);
        return itemstack;
    }
}
