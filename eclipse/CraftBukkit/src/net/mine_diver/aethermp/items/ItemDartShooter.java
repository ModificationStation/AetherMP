package net.mine_diver.aethermp.items;

import net.mine_diver.aethermp.entities.EntityDartEnchanted;
import net.mine_diver.aethermp.entities.EntityDartGolden;
import net.mine_diver.aethermp.entities.EntityDartPoison;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.IInventory;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.World;

public class ItemDartShooter extends Item {

    public ItemDartShooter(int i) {
        super(i);
        a(true);
        maxStackSize = 1;
    }
    
    @Override
    public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman) {
        int consume = consumeItem(entityhuman, ItemManager.Dart.id, itemstack.getData());
        if(consume != -1) {
            EntityDartGolden dart = null;
            if(consume == 1)
                dart = new EntityDartPoison(world, entityhuman);
            if(consume == 2)
                dart = new EntityDartEnchanted(world, entityhuman);
            if(dart == null)
                dart = new EntityDartGolden(world, entityhuman);
            world.addEntity(dart);
        }
        return itemstack;
    }

    private int consumeItem(EntityHuman player, int id, int maxDamage) {
        IInventory inv = player.inventory;
        for(int i = 0; i < inv.getSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if(stack == null)
                continue;
            int damage = stack.getData();
            if(stack.id != id || stack.getData() != maxDamage)
                continue;
            stack.count--;
            if(stack.count == 0)
                stack = null;
            inv.setItem(i, stack);
            return damage;
        }
        return -1;
    }
}
