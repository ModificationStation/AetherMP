package net.mine_diver.aethermp.entities;

import java.util.List;

import net.mine_diver.aethermp.blocks.BlockFloating;
import net.mine_diver.aethermp.blocks.BlockManager;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityFallingSand;
import net.minecraft.server.ItemStack;
import net.minecraft.server.MathHelper;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.World;

public class EntityFloatingBlock extends Entity {

    public EntityFloatingBlock(World world) {
        super(world);
        flytime = 0;
    }

    public EntityFloatingBlock(World world, double d, double d1, double d2, int i, int j) {
        super(world);
        flytime = 0;
        blockID = i;
        metadata = j;
        aI = true;
        b(0.98F, 0.98F);
        height = width / 2.0F;
        setPosition(d, d1, d2);
        motX = 0.0D;
        motY = 0.0D;
        motZ = 0.0D;
        lastX = d;
        lastY = d1;
        lastZ = d2;
    }

    public EntityFloatingBlock(World world, double d, double d1, double d2, int i) {
        this(world, d, d1, d2, i, 0);
    }
    
    @Override
    protected boolean n() {
        return false;
    }
    
    @Override
    protected void b() {}
    
    @Override
    public boolean l_() {
        return !dead;
    }
    
    @Override
    public void m_() {
        if(blockID == 0) {
            die();
            return;
        }
        lastX = locX;
        lastY = locY;
        lastZ = locZ;
        flytime++;
        motY += 0.040000000000000001D;
        move(motX, motY, motZ);
        motX *= 0.98000001907348633D;
        motY *= 0.98000001907348633D;
        motZ *= 0.98000001907348633D;
        int i = MathHelper.floor(locX);
        int j = MathHelper.floor(locY);
        int k = MathHelper.floor(locZ);
        if(world.getTypeId(i, j, k) == blockID || world.getTypeId(i, j, k) == BlockManager.Grass.id && blockID == BlockManager.Dirt.id)
            world.setTypeId(i, j, k, 0);
        @SuppressWarnings("unchecked")
		List<Entity> list = world.b(this, boundingBox.b(0.0D, 1.0D, 0.0D));
        for(int l = 0; l < list.size(); l++)
            if((list.get(l) instanceof EntityFallingSand) && world.a(blockID, i, j, k, true, 1)) {
                world.setTypeIdAndData(i, j, k, blockID, metadata);
                die();
            }

        if(bc && !onGround) {
            motX *= 0.69999998807907104D;
            motZ *= 0.69999998807907104D;
            motY *= -0.5D;
            die();
            if(world.a(blockID, i, j, k, true, 1) && !BlockFloating.canFallAbove(world, i, j + 1, k) && world.setTypeIdAndData(i, j, k, blockID, metadata) || false);
        } else if(flytime > 100 && true)
            die();
    }
    
    @Override
    protected void b(NBTTagCompound nbttagcompound) {
        nbttagcompound.a("Tile", (byte)blockID);
    }
    
    @Override
    protected void a(NBTTagCompound nbttagcompound) {
        blockID = nbttagcompound.c("Tile") & 0xff;
    }
    
    @Override
    public ItemStack[] getEquipment(){
        return new ItemStack[] {new ItemStack(blockID, 1, metadata)};
    }

    public int blockID;
    public int metadata;
    public int flytime;
}
