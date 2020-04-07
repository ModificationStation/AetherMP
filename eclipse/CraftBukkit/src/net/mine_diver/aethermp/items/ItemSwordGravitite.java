package net.mine_diver.aethermp.items;

import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.EnumToolMaterial;
import net.minecraft.server.ItemStack;
import net.minecraft.server.ItemSword;

public class ItemSwordGravitite extends ItemSword {

    public ItemSwordGravitite(int i, EnumToolMaterial enumtoolmaterial) {
        super(i, enumtoolmaterial);
    }
    
    @Override
    public boolean a(ItemStack itemstack, EntityLiving entityliving, EntityLiving entityliving1) {
        if(entityliving1 != null && (entityliving1 instanceof EntityPlayer) && (entityliving.hurtTicks > 0 || entityliving.deathTicks > 0)) {
            entityliving.motY++;
            itemstack.damage(1, entityliving1);
        }
        return true;
    }
}
