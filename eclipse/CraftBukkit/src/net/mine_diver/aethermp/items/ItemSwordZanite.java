package net.mine_diver.aethermp.items;

import net.minecraft.server.Block;
import net.minecraft.server.EnumToolMaterial;
import net.minecraft.server.ItemStack;
import net.minecraft.server.ItemSword;

public class ItemSwordZanite extends ItemSword {

    public ItemSwordZanite(int i, EnumToolMaterial enumtoolmaterial) {
        super(i, enumtoolmaterial);
    }
    
    @Override
    public float a(ItemStack itemstack, Block block) {
        return super.a(itemstack, block) * ((2.0F * (float)itemstack.getData()) / (float)itemstack.getItem().e() + 0.5F);
    }
}
