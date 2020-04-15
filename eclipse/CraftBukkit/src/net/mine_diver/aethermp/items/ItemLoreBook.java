package net.mine_diver.aethermp.items;

import net.mine_diver.aethermp.gui.GuiManager;
import net.mine_diver.aethermp.inventory.ContainerLore;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.World;
import net.minecraft.server.mod_AetherMp;

public class ItemLoreBook extends Item {

    public ItemLoreBook(int i) {
        super(i);
        maxStackSize = 1;
        a(true);
        d(0);
    }
    
    @Override
    public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman) {
        ContainerLore containerlore = new ContainerLore(entityhuman.inventory); 
        GuiManager.OpenGUIWithMeta(entityhuman, mod_AetherMp.idGuiLore, containerlore.loreSlot, containerlore, itemstack.getData());
        return itemstack;
    }
}
