package net.mine_diver.aethermp.items;

import net.mine_diver.aethermp.blocks.BlockManager;
import net.minecraft.server.Block;
import net.minecraft.server.EnumToolMaterial;
import net.minecraft.server.ItemTool;
import net.minecraft.server.ToolBase;

public class ItemSkyrootPickaxe extends ItemTool {

    protected ItemSkyrootPickaxe(int i, EnumToolMaterial enumtoolmaterial) {
        super(i, 2, enumtoolmaterial, blocksEffectiveAgainst);
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

    private static Block[] blocksEffectiveAgainst = new Block[] {
            BlockManager.Holystone,
            BlockManager.AmbrosiumOre,
            //BlockManager.Freezer,
            BlockManager.QuicksoilGlass,
            //BlockManager.Incubator
            };
}
