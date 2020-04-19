package net.mine_diver.aethermp.items;

import net.mine_diver.aethermp.player.PlayerBaseAether;
import net.minecraft.server.Block;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.EnumToolMaterial;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.PlayerAPI;

public class ItemVampireBlade extends Item {

    public ItemVampireBlade(int i) {
        super(i);
        maxStackSize = 1;
        d(EnumToolMaterial.DIAMOND.a());
        weaponDamage = 4 + EnumToolMaterial.DIAMOND.c() * 2;
    }
    
    @Override
    public float a(ItemStack itemstack, Block block) {
        return 1.5F;
    }

    public boolean a(ItemStack itemstack, EntityLiving entityliving, EntityLiving entityliving1) {
        EntityPlayer player = (EntityPlayer)entityliving1;
        if(player.health < ((PlayerBaseAether)PlayerAPI.getPlayerBase(player, PlayerBaseAether.class)).maxHealth)
            player.health++;
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

    private int weaponDamage;

}
