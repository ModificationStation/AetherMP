package net.mine_diver.aethermp.blocks;

import net.mine_diver.aethermp.util.Achievements;
import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.Block;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.Material;
import net.minecraft.server.World;

public class BlockAercloud extends Block {

    protected BlockAercloud(int ID) {
        super(ID, Material.ICE);
    }
    
    @Override
    public void a(World world, int i, int j, int k, Entity entity) {
        entity.fallDistance = 0.0F;
        if(world.getData(i, j, k) == 1) {
            entity.motY = 2D;
            if(entity instanceof EntityPlayer)
                Achievements.giveAchievement(Achievements.blueCloud, (EntityPlayer) entity);
        } else if(entity.motY < 0.0D)
            entity.motY *= 0.0050000000000000001D;
    }
    
    @Override
    public boolean a() {
        return false;
    }
    
    @Override
    protected int a_(int i) {
        return i;
    }
    
    @Override
    public AxisAlignedBB e(World world, int i, int j, int k) {
        if(world.getData(i, j, k) == 1)
            return AxisAlignedBB.b(i, j, k, i, j, k);
        else
            return AxisAlignedBB.b(i, j, k, i + 1, j, k + 1);
    }

    public static final int bouncingMeta = 1;
}
