package net.mine_diver.aethermp.items;

import net.mine_diver.aethermp.entities.EntityFlamingArrow;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.World;

public class ItemPhoenixBow extends Item {

    public ItemPhoenixBow(int i) {
        super(i);
        maxStackSize = 1;
    }
    
    @Override
    public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman) {
        if(entityhuman.inventory.b(Item.ARROW.id))
                world.addEntity(new EntityFlamingArrow(world, entityhuman));
        return itemstack;
    }
}
