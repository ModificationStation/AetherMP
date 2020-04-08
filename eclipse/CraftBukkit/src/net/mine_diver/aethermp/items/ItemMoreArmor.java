package net.mine_diver.aethermp.items;

import net.minecraft.server.Item;

public class ItemMoreArmor extends Item {

    public ItemMoreArmor(int i, int j, int l) {
        super(i);
        armorLevel = j;
        armorType = l;
        damageReduceAmount = damageReduceAmountArray[l];
        d(maxDamageArray[l] * 3 << j);
        maxStackSize = 1;
    }

    public boolean isTypeValid(int i) {
        if(i == armorType)
            return true;
        if((i == 8 || i == 9) && (armorType == 8 || armorType == 9))
            return true;
        return (i == 7 || i == 11) && (armorType == 7 || armorType == 11);
    }
    
    public boolean damageType() {
        return damageType(armorType);
    }

    public boolean damageType(int i) {
        return i < 4 || i == 6 || i == 10;
    }

    private static final int damageReduceAmountArray[] = {
        3, 8, 6, 3, 0, 1, 0, 0, 0, 0, 
        2, 0
    };
    private static final int maxDamageArray[] = {
        11, 16, 15, 13, 10, 10, 8, 10, 10, 10, 
        10, 10
    };
    public final int armorLevel;
    public final int armorType;
    public final int damageReduceAmount;

}
