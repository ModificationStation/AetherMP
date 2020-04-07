package net.mine_diver.aethermp.blocks;

import java.util.Random;

import net.mine_diver.aethermp.dimension.world.generation.AetherGenGoldenOak;
import net.mine_diver.aethermp.dimension.world.generation.AetherGenSkyroot;
import net.minecraft.server.BlockFlower;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.World;
import net.minecraft.server.WorldGenerator;

public class BlockAetherSapling extends BlockFlower {

    protected BlockAetherSapling(int i) {
        super(i, -1);
        float f = 0.4F;
        a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
    }
    
    @Override
    public void a(World world, int i, int j, int k, Random random) {
        super.a(world, i, j, k, random);
        if(world.getLightLevel(i, j + 1, k) >= 9 && random.nextInt(30) == 0)
            growTree(world, i, j, k, random);
    }
    
    @Override
    public boolean canPlace(World world, int i, int j, int k) {
        return super.canPlace(world, i, j, k) && c(world.getTypeId(i, j - 1, k));
    }
    
    @Override
    protected boolean c(int i) {
        return i == BlockManager.Grass.id || i == BlockManager.Dirt.id;
    }
    
    @Override
    public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman) {
        if(entityhuman == null)
            return false;
        ItemStack itemstack = entityhuman.G();
        if(itemstack == null)
            return false;
        if(itemstack.id != Item.INK_SACK.id)
            return false;
        if(itemstack.getData() != 15)
            return false;
        else {
            growTree(world, i, j, k, world.random);
            itemstack.count--;
            return true;
        }
    }
    
    public void growTree(World world, int i, int j, int k, Random random) {
        world.setRawTypeId(i, j, k, 0);
        Object obj = null;
        if(id == BlockManager.GoldenOakSapling.id)
            obj = new AetherGenGoldenOak();
        else
            obj = new AetherGenSkyroot();
        if(!((WorldGenerator) obj).a(world, random, i, j, k))
            world.setRawTypeId(i, j, k, id);
    }

}
