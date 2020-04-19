package net.mine_diver.aethermp.items;

import net.mine_diver.aethermp.entities.EntityNotchWave;
import net.minecraft.server.Block;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EnumToolMaterial;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.World;

public class ItemNotchHammer extends Item {

    public ItemNotchHammer(int i) {
        super(i);
        maxStackSize = 1;
        d(EnumToolMaterial.IRON.a());
        weaponDamage = 4 + EnumToolMaterial.IRON.c() * 2;
    }
    
    @Override
    public float a(ItemStack itemstack, Block block) {
        return 1.5F;
    }
    
    @Override
    public boolean a(ItemStack itemstack, EntityLiving entityliving, EntityLiving entityliving1) {
        itemstack.damage(1, entityliving1);
        return true;
    }
    
    @Override
    public boolean a(ItemStack itemstack, int i, int j, int k, int l, EntityLiving entityliving) {
        itemstack.damage(2, entityliving);
        return true;
    }
    
    @Override
    public int a(Entity entity) {
        return weaponDamage;
    }
    
    @Override
    public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman) {
        itemstack.damage(1, entityhuman);
        EntityNotchWave notchwave = new EntityNotchWave(world, entityhuman);
        world.addEntity(notchwave);
        return itemstack;
    }

    private int weaponDamage;
}
