package net.mine_diver.aethermp.items;

import net.minecraft.server.Block;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EnumToolMaterial;
import net.minecraft.server.IReach;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.SAPI;

public class ItemLance extends Item implements IReach {

    public ItemLance(int i, EnumToolMaterial enumtoolmaterial) {
        super(i);
        maxStackSize = 1;
        d(enumtoolmaterial.a());
        weaponDamage = 4 + enumtoolmaterial.c() * 2;
        SAPI.reachAdd(this);
    }
    
    @Override
    public float a(ItemStack itemstack, Block block) {
        return block.id == Block.WEB.id ? 15F : 1.5F;
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
    public boolean a(Block block) {
        return block.id == Block.WEB.id;
    }
    
    @Override
    public boolean reachItemMatches(ItemStack itemstack) {
        if(itemstack == null)
            return false;
        else
            return itemstack.id == ItemManager.Lance.id;
    }
    
    @Override
    public float getReach(ItemStack itemstack) {
        return 10F;
    }

    private int weaponDamage;
}
