package net.mine_diver.aethermp.items;

import java.util.List;

import net.mine_diver.aethermp.entities.EntityMiniCloud;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.World;

public class ItemCloudStaff extends Item {

    public ItemCloudStaff(int i) {
        super(i);
        maxStackSize = 1;
        d(60);
    }
    
    @Override
    public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman) {
        if(!cloudsExist(world, entityhuman)) {
            EntityMiniCloud c1 = new EntityMiniCloud(world, entityhuman, false);
            EntityMiniCloud c2 = new EntityMiniCloud(world, entityhuman, true);
            world.addEntity(c1);
            world.addEntity(c2);
            itemstack.damage(1, (Entity)null);
        }
        return itemstack;
    }

    private boolean cloudsExist(World world, EntityHuman entityhuman) {
        @SuppressWarnings("unchecked")
		List<Entity> list = world.b(entityhuman, entityhuman.boundingBox.b(128D, 128D, 128D));
        for(int j = 0; j < list.size(); j++) {
            Entity entity1 = list.get(j);
            if(entity1 instanceof EntityMiniCloud)
                return true;
        }
        return false;
    }
}
