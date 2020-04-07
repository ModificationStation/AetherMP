package net.mine_diver.aethermp.blocks;

import java.util.Random;

import net.mine_diver.aethermp.dimension.DimensionManager;
import net.mine_diver.aethermp.entities.EntitySentry;
import net.mine_diver.aethermp.network.PacketManager;
import net.minecraft.server.BlockBreakable;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.Material;
import net.minecraft.server.MathHelper;
import net.minecraft.server.World;

public class BlockTrap extends BlockBreakable {

    public BlockTrap(int i) {
        super(i, -1, Material.STONE, false);
        a(true);
    }
    
    @Override
    public boolean a() {
        return true;
    }
    
    @Override
    public int a(Random random) {
        return 1;
    }
    
    @Override
    public void b(World world, int i, int j, int k, Entity entity) {
        if(entity instanceof EntityHuman) {
            PacketManager.makeSound((float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F, "aether.sound.other.dungeontrap.activateTrap", 1.0F, 1.0F, DimensionManager.getCurrentDimension(world));
            int l = MathHelper.floor(i);
            int i1 = MathHelper.floor(j);
            int j1 = MathHelper.floor(k);
            switch(world.getData(i, j, k))
            {
            case 0: // '\0'
                EntitySentry entitysentry = new EntitySentry(world);
                entitysentry.setPosition((double)l + 0.5D, (double)i1 + 1.5D, (double)j1 + 0.5D);
                world.addEntity(entitysentry);
                break;

            /*case 1: // '\001'
                EntityValkyrie entityvalkyrie = new EntityValkyrie(world);
                entityvalkyrie.setPosition((double)l + 0.5D, (double)i1 + 1.5D, (double)j1 + 0.5D);
                world.entityJoinedWorld(entityvalkyrie);
                break;*/
            }
            world.setTypeIdAndData(i, j, k, BlockManager.LockedDungeonStone.id, world.getData(i, j, k));
        }
    }
    
    @Override
    protected int a_(int i) {
        return i;
    }

}
