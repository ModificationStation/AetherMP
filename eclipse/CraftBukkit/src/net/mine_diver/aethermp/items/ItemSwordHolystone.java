package net.mine_diver.aethermp.items;

import java.util.Random;

import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.EnumToolMaterial;
import net.minecraft.server.ItemStack;
import net.minecraft.server.ItemSword;

public class ItemSwordHolystone extends ItemSword {

    public ItemSwordHolystone(int i, EnumToolMaterial enumtoolmaterial) {
        super(i, enumtoolmaterial);
    }
    
    @Override
    public boolean a(ItemStack itemstack, EntityLiving entityliving, EntityLiving entityliving1) {
        if((new Random()).nextInt(25) == 0 && entityliving1 != null && (entityliving1 instanceof EntityPlayer) && (entityliving.hurtTicks > 0 || entityliving.deathTicks > 0)) {
            entityliving.a(ItemManager.AmbrosiumShard.id, 1, 0.0F);
            itemstack.damage(1, entityliving1);
        }
        itemstack.damage(1, entityliving1);
        return true;
    }
}
