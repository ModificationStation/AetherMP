package net.mine_diver.aethermp.items;

import java.util.Random;

import net.mine_diver.aethermp.blocks.BlockManager;
import net.minecraft.server.Block;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EnumToolMaterial;
import net.minecraft.server.ItemStack;
import net.minecraft.server.ItemTool;
import net.minecraft.server.ToolBase;

public class ItemHolystoneSpade extends ItemTool {

    public ItemHolystoneSpade(int i, EnumToolMaterial enumtoolmaterial) {
        super(i, 1, enumtoolmaterial, blocksEffectiveAgainst);
    }
    
    @Override
    public boolean a(ItemStack itemstack, int i, int j, int k, int l, EntityLiving entityliving) {
        if(random.nextInt(50) == 0)
            entityliving.a(ItemManager.AmbrosiumShard.id, 1, 0.0F);
        return super.a(itemstack, i, j, k, l, entityliving);
    }
    
    @Override
    public ToolBase getToolBase() {
        return ToolBase.Shovel;
    }
    
    @Override
    public boolean a(Block block) {
        for(int i = 0; i < blocksEffectiveAgainst.length; i++)
            if(blocksEffectiveAgainst[i].id == block.id)
                return true;
        return false;
    }

    private static Block[] blocksEffectiveAgainst = new Block[] {
            BlockManager.Quicksoil,
            BlockManager.Dirt,
            BlockManager.Grass,
            BlockManager.Aercloud
            };
    private static Random random = new Random();
}
