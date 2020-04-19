package net.mine_diver.aethermp.items;

import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntityTypes;
import net.minecraft.server.EnumToolMaterial;
import net.minecraft.server.ItemStack;
import net.minecraft.server.ItemSword;
import net.minecraft.server.mod_AetherMp.PackageAccess;

public class ItemPigSlayer extends ItemSword {

    public ItemPigSlayer(int i) {
        super(i, EnumToolMaterial.IRON);
        d(0);
    }
    
    @Override
    public boolean a(ItemStack itemstack, EntityLiving entityliving, EntityLiving entityliving1) {
        if(entityliving == null || entityliving1 == null)
            return false;
        String s = EntityTypes.b(entityliving);
        if(!s.equals("") && s.toLowerCase().contains("pig")) {
            if(entityliving.health > 0) {
                entityliving.health = 1;
                entityliving.hurtTicks = 0;
                entityliving.damageEntity(entityliving1, 9999);
            }

            PackageAccess.EntityLiving.dropFewItems(entityliving);
            entityliving.dead = true;
        }
        return true;
    }
    
    @Override
    public boolean a(ItemStack itemstack, int i, int j, int i1, int j1, EntityLiving entityliving1) {
        return true;
    }
}
