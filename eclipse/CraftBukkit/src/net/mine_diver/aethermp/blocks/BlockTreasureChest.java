package net.mine_diver.aethermp.blocks;

import java.util.Random;

import net.mine_diver.aethermp.gui.GuiManager;
import net.mine_diver.aethermp.items.ItemManager;
import net.minecraft.server.BlockContainer;
import net.minecraft.server.ContainerChest;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.ItemStack;
import net.minecraft.server.Material;
import net.minecraft.server.TileEntity;
import net.minecraft.server.TileEntityChest;
import net.minecraft.server.World;
import net.minecraft.server.mod_AetherMp;

public class BlockTreasureChest extends BlockContainer {

    protected BlockTreasureChest(int i) {
        super(i, Material.STONE);
    }
    
    @Override
    public int a(Random random1) {
        return 0;
    }
    
    @Override
    public boolean canPlace(World world, int i, int j, int k) {
        return false;
    }
    
    @Override
    public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman) {
        int l = world.getData(i, j, k);
        TileEntityChest tileentitychest = (TileEntityChest) world.getTileEntity(i, j, k);
        if (l % 2 == 1) {
            GuiManager.OpenGUIWithMeta(entityhuman, mod_AetherMp.idGuiTreasureChest, tileentitychest, new ContainerChest(entityhuman.inventory, tileentitychest), l);
            return true;
        }
        ItemStack itemstack = entityhuman.inventory.getItemInHand();
        if (itemstack != null && itemstack.id == ItemManager.Key.id && itemstack.getData() == 0 && l == 0) {
            entityhuman.inventory.splitStack(entityhuman.inventory.itemInHandIndex, 1);
            world.setData(i, j, k, l + 1);
            world.setTileEntity(i, j, k, tileentitychest);
            return true;
        }
        if (itemstack != null && itemstack.id == ItemManager.Key.id && itemstack.getData() == 1 && l == 2) {
            entityhuman.inventory.splitStack(entityhuman.inventory.itemInHandIndex, 1);
            world.setData(i, j, k, l + 1);
            world.setTileEntity(i, j, k, tileentitychest);
            return true;
        }
        if (itemstack != null && itemstack.id == ItemManager.Key.id && itemstack.getData() == 2 && l == 4) {
            entityhuman.inventory.splitStack(entityhuman.inventory.itemInHandIndex, 1);
            world.setData(i, j, k, l + 1);
            world.setTileEntity(i, j, k, tileentitychest);
            return true;
        } else
            return false;
    }
    
    @Override
    protected TileEntity a_() {
        return new TileEntityChest();
    }
}
