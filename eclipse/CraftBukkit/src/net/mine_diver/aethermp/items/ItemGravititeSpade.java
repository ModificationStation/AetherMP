package net.mine_diver.aethermp.items;

import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import net.mine_diver.aethermp.blocks.BlockManager;
import net.mine_diver.aethermp.entities.EntityFloatingBlock;
import net.minecraft.server.Block;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EnumMovingObjectType;
import net.minecraft.server.EnumToolMaterial;
import net.minecraft.server.ItemStack;
import net.minecraft.server.ItemTool;
import net.minecraft.server.MathHelper;
import net.minecraft.server.MovingObjectPosition;
import net.minecraft.server.ToolBase;
import net.minecraft.server.Vec3D;
import net.minecraft.server.World;

public class ItemGravititeSpade extends ItemTool {

    public ItemGravititeSpade(int i, EnumToolMaterial enumtoolmaterial) {
        super(i, 1, enumtoolmaterial, blocksEffectiveAgainst);
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
        return ToolBase.Shovel;
    }
    
    @Override
    public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman) {
        float f = entityhuman.pitch;
        float f1 = entityhuman.yaw;
        double d = entityhuman.locX;
        double d1 = (entityhuman.locY + 1.6200000000000001D) - (double)entityhuman.height;
        double d2 = entityhuman.locZ;
        Vec3D vec3d = Vec3D.create(d, d1, d2);
        float f2 = MathHelper.cos(-f1 * 0.01745329F - 3.141593F);
        float f3 = MathHelper.sin(-f1 * 0.01745329F - 3.141593F);
        float f4 = -MathHelper.cos(-f * 0.01745329F);
        float f5 = MathHelper.sin(-f * 0.01745329F);
        float f6 = f3 * f4;
        float f7 = f5;
        float f8 = f2 * f4;
        double d3 = 5D;
        Vec3D vec3d1 = vec3d.add((double)f6 * d3, (double)f7 * d3, (double)f8 * d3);
        MovingObjectPosition movingobjectposition = world.rayTrace(vec3d, vec3d1, false);
        if(movingobjectposition == null)
            return itemstack;
        if(movingobjectposition.type == EnumMovingObjectType.TILE) {
            int i = movingobjectposition.b;
            int j = movingobjectposition.c;
            int k = movingobjectposition.d;
            int l = world.getTypeId(i, j, k);
            int i1 = world.getData(i, j, k);
            for(int j1 = 0; j1 < blocksEffectiveAgainst.length; j1++) {
                if(l != blocksEffectiveAgainst[j1].id)
                    continue;
                if(l == BlockManager.Grass.id)
                    l = BlockManager.Dirt.id;
                BlockBreakEvent event = new BlockBreakEvent(world.getWorld().getBlockAt(i, j, k), (Player) entityhuman.getBukkitEntity());
            	world.getServer().getPluginManager().callEvent(event);
            	if (event.isCancelled())
            		return itemstack;
                EntityFloatingBlock entityfloatingblock = new EntityFloatingBlock(world, (float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F, l, i1);
                world.addEntity(entityfloatingblock);
            }
            itemstack.damage(4, entityhuman);
        }
        return itemstack;
    }

    private static Block[] blocksEffectiveAgainst = new Block[] {
            BlockManager.Quicksoil,
            BlockManager.Dirt,
            BlockManager.Grass,
            BlockManager.Aercloud
            };
}
