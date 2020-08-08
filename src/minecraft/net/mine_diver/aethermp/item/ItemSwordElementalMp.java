package net.mine_diver.aethermp.item;

import net.minecraft.src.AetherItems;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EnumElement;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ItemSwordElemental;
import net.minecraft.src.ModLoader;

public class ItemSwordElementalMp extends ItemSwordElemental {
	
	public ItemSwordElementalMp(int i) {
		super(i, getElement(), getColor());
	}
	
	@Override
	public boolean hitEntity(ItemStack itemstack, EntityLiving entityliving, EntityLiving entityliving1) {
        if (entityliving.worldObj.multiplayerWorld)
        	return true;
        return super.hitEntity(itemstack, entityliving, entityliving1);
    }
	
	public static EnumElement getElement() {
		try {
			return (EnumElement) ModLoader.getPrivateValue(ItemSwordElemental.class, AetherItems.SwordLightning, "element");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static int getColor() {
		try {
			return (int) ModLoader.getPrivateValue(ItemSwordElemental.class, AetherItems.SwordLightning, "colour");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
