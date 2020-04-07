package net.mine_diver.aethermp.items;

import net.mine_diver.aethermp.entities.EntityCloudParachute;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.World;
import net.minecraft.server.mod_AetherMp;

public class ItemCloudParachute extends Item {

    public ItemCloudParachute(int i) {
        super(i);
        maxStackSize = 1;
        d(i != mod_AetherMp.idItemCloudParachuteGold ? 0 : 20);
    }
    
    @Override
    public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman) {
        if(EntityCloudParachute.entityHasRoomForCloud(world, entityhuman)) {
        	for(int i = 0; i < 32; i++)
                EntityCloudParachute.doCloudSmoke(world, entityhuman);
            if(id == ItemManager.CloudParachuteGold.id)
                itemstack.damage(1, entityhuman);
            else
                itemstack.count--;
            world.makeSound(entityhuman, "cloud", 1.0F, 1.0F / (b.nextFloat() * 0.1F + 0.95F));
            world.addEntity(new EntityCloudParachute(world, entityhuman, id == ItemManager.CloudParachuteGold.id));
        }
        return itemstack;
    }
}
