package net.mine_diver.aethermp.items;

import net.minecraft.server.Block;
import net.minecraft.server.BlockJukeBox;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.ItemRecord;
import net.minecraft.server.ItemStack;
import net.minecraft.server.World;

public class ItemAetherRecord extends ItemRecord
{

    protected ItemAetherRecord(int i, String s) {
        super(i, s);
    }
    
    @Override
    public boolean a(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l) {
        if(world.getTypeId(i, j, k) == Block.JUKEBOX.id && world.getData(i, j, k) == 0) {
            ((BlockJukeBox) Block.JUKEBOX).f(world, i, j, k, id);
            world.a((EntityHuman)null, 1005, i, j, k, id);
            itemstack.count--;
            return true;
        } else
            return false;
    }
}
