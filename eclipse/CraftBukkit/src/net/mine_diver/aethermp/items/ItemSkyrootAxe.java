package net.mine_diver.aethermp.items;

import net.mine_diver.aethermp.blocks.BlockManager;
import net.minecraft.server.Block;
import net.minecraft.server.EnumToolMaterial;
import net.minecraft.server.ItemTool;
import net.minecraft.server.ToolBase;

public class ItemSkyrootAxe extends ItemTool {

    protected ItemSkyrootAxe(int i, EnumToolMaterial enumtoolmaterial) {
        super(i, 3, enumtoolmaterial, blocksEffectiveAgainst);
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
        return ToolBase.Axe;
    }

    private static Block[] blocksEffectiveAgainst = new Block[] {
            BlockManager.Plank,
            BlockManager.Log
            };
}
