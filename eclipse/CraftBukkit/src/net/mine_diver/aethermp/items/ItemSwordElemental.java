package net.mine_diver.aethermp.items;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.mine_diver.aethermp.entities.EntityAetherLightning;
import net.mine_diver.aethermp.entities.EntityManager;
import net.mine_diver.aethermp.util.EnumElement;
import net.minecraft.server.Block;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EnumToolMaterial;
import net.minecraft.server.ItemStack;
import net.minecraft.server.ItemSword;

public class ItemSwordElemental extends ItemSword {

    public ItemSwordElemental(int i, EnumElement element) {
        super(i, EnumToolMaterial.DIAMOND);
        maxStackSize = 1;
        d(element != EnumElement.Holy ? 32 : 128);
        weaponDamage = 4;
        holyDamage = 20;
        this.element = element;
    }
    
    @Override
    public float a(ItemStack itemstack, Block block) {
        return 1.5F;
    }
    
    @Override
    public boolean a(ItemStack itemstack, int i, int j, int k, int l, EntityLiving entityliving) {
        itemstack.damage(2, entityliving);
        return true;
    }

    public boolean a(ItemStack itemstack, EntityLiving entityliving, EntityLiving entityliving1) {
        if(element == EnumElement.Fire)
            entityliving.fireTicks = 600;
        else if(element == EnumElement.Lightning)
            EntityManager.strikeAetherLightning(new EntityAetherLightning(entityliving.world, (int)entityliving.locX, (int)entityliving.locY, (int)entityliving.locZ));
        itemstack.damage(1, entityliving1);
        return true;
    }
    
    @Override
    public int a(Entity entity) {
label0: {
            if(element != EnumElement.Holy || !(entity instanceof EntityLiving))
                break label0;
            EntityLiving living = (EntityLiving)entity;
            Iterator<Class<? extends Entity>> i$ = undead.iterator();
            Class<? extends Entity> cls;
            do {
                if(!i$.hasNext())
                    break label0;
                cls = i$.next();
            } while(!living.getClass().isAssignableFrom(cls));
            return holyDamage;
        }
        return weaponDamage;
    }

    public static List<Class<? extends Entity>> undead;
    private int weaponDamage;
    private int holyDamage;
    private EnumElement element;

    static {
        undead = new ArrayList<Class<? extends Entity>>();
        undead.add(net.minecraft.server.EntityZombie.class);
        undead.add(net.minecraft.server.EntitySkeleton.class);
        undead.add(net.minecraft.server.EntityPigZombie.class);
    }
}
