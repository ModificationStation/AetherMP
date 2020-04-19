package net.mine_diver.aethermp.entities;

import net.mine_diver.aethermp.items.ItemManager;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.ItemStack;
import net.minecraft.server.World;

public class EntityDartGolden extends EntityProjectileBase {

    public EntityDartGolden(World world) {
        super(world);
    }

    public EntityDartGolden(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public EntityDartGolden(World world, EntityLiving ent) {
        super(world, ent);
    }
    
    @Override
    public void b() {
        super.b();
        item = new ItemStack(ItemManager.Dart, 1, 0);
        curvature = 0.0F;
        dmg = 4;
        speed = 1.5F;
    }
    
    @Override
    public boolean f_() {
        return victim == null && super.f_();
    }
    
    @Override
    public void die() {
        victim = null;
        super.die();
    }
    
    @Override
    public boolean onHitBlock() {
        curvature = 0.03F;
        return victim == null;
    }
    
    @Override
    public boolean canBeShot(Entity ent) {
        return super.canBeShot(ent) && victim == null;
    }

    public EntityLiving victim;
}
