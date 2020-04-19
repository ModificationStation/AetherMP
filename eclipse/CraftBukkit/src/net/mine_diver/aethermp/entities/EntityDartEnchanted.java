package net.mine_diver.aethermp.entities;

import net.mine_diver.aethermp.items.ItemManager;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.ItemStack;
import net.minecraft.server.World;

public class EntityDartEnchanted extends EntityDartGolden
{

    public EntityDartEnchanted(World world) {
        super(world);
    }

    public EntityDartEnchanted(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public EntityDartEnchanted(World world, EntityLiving ent) {
        super(world, ent);
    }
    
    @Override
    public void b() {
        super.b();
        item = new ItemStack(ItemManager.Dart, 1, 2);
        dmg = 6;
    }
}
