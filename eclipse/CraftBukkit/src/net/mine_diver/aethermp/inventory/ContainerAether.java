package net.mine_diver.aethermp.inventory;

import net.minecraft.server.ContainerPlayer;
import net.minecraft.server.InventoryCraftResult;
import net.minecraft.server.InventoryCrafting;
import net.minecraft.server.InventoryPlayer;
import net.minecraft.server.Slot;
import net.minecraft.server.SlotResult;

import static net.minecraft.server.mod_AetherMp.PackageAccess;

public class ContainerAether extends ContainerPlayer {

    public ContainerAether(InventoryPlayer inventoryplayer, InventoryAether inv) {
        this(inventoryplayer, inv, true);
    }

    public ContainerAether(InventoryPlayer inventoryplayer, InventoryAether inv, boolean flag) {
        super(inventoryplayer, flag);
        e.clear();
        craftInventory = new InventoryCrafting(this, 2, 2);
        resultInventory = new InventoryCraftResult();
        c = flag;
        a(new SlotResult(inventoryplayer.d, craftInventory, resultInventory, 0, 134, 62));
        for(int i = 0; i < 2; i++)
            for(int i1 = 0; i1 < 2; i1++)
                a(new Slot(craftInventory, i1 + i * 2, 125 + i1 * 18, 8 + i * 18));
        for(int j = 0; j < 4; j++) {
            int j1 = j;
            a(PackageAccess.SlotArmor.newSlotArmor(this, inventoryplayer, inventoryplayer.getSize() - 1 - j, 62, 8 + j * 18, j1));
        }
        for(int k = 0; k < 3; k++)
            for(int k1 = 0; k1 < 9; k1++)
                a(new Slot(inventoryplayer, k1 + (k + 1) * 9, 8 + k1 * 18, 84 + k * 18));
        for(int l = 0; l < 9; l++)
            a(new Slot(inventoryplayer, l, 8 + l * 18, 142));
        for(int i = 1; i < 3; i++)
            for(int j = 0; j < 4; j++) {
                int armorType = 4 * (i - 1) + j;
                int slotId = armorType;
                a(new SlotMoreArmor(this, inv, slotId, 62 + i * 18, 8 + j * 18, armorType + 4));
            }
        a(craftInventory);
    }
}
