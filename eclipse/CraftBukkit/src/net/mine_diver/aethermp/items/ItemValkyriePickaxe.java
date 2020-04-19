package net.mine_diver.aethermp.items;

import net.mine_diver.aethermp.blocks.BlockManager;
import net.minecraft.server.Block;
import net.minecraft.server.EnumToolMaterial;
import net.minecraft.server.IReach;
import net.minecraft.server.ItemStack;
import net.minecraft.server.ItemTool;
import net.minecraft.server.SAPI;
import net.minecraft.server.ToolBase;

public class ItemValkyriePickaxe extends ItemTool implements IReach {

    protected ItemValkyriePickaxe(int i, EnumToolMaterial enumtoolmaterial) {
        super(i, 2, enumtoolmaterial, blocksEffectiveAgainst);
        SAPI.reachAdd(this);
    }
    
    @Override
    public boolean a(Block block) {
        for(int i = 0; i < blocksEffectiveAgainst.length; i++)
            if(blocksEffectiveAgainst[i].id == block.id)
                return true;
        return false;
    }
    
    @Override
    public ToolBase getToolBase() {
        return ToolBase.Pickaxe;
    }
    
    @Override
    public boolean reachItemMatches(ItemStack itemstack) {
        if(itemstack == null)
            return false;
        else
            return itemstack.id == ItemManager.PickValkyrie.id;
    }
    
    @Override
    public float getReach(ItemStack itemstack) {
        return 10F;
    }

    private static Block blocksEffectiveAgainst[];

    static {
        blocksEffectiveAgainst = (new Block[] {
            BlockManager.Holystone, BlockManager.Icestone, BlockManager.EnchantedGravitite, BlockManager.GravititeOre, BlockManager.ZaniteOre, BlockManager.AmbrosiumOre, BlockManager.LightDungeonStone, BlockManager.DungeonStone, BlockManager.Pillar, BlockManager.Aerogel, 
            BlockManager.Enchanter/*, BlockManager.Incubator*/, BlockManager.ZaniteBlock, BlockManager.Freezer, BlockManager.QuicksoilGlass
        });
    }
}
