package net.mine_diver.aethermp.blocks;

import org.bukkit.event.world.PortalCreateEvent;

import net.minecraft.server.Block;
import net.minecraft.server.BlockPortal;
import net.minecraft.server.World;
import net.minecraft.server.mod_AetherMp;

public class BlockAetherPortal extends BlockPortal {

    public BlockAetherPortal(int i) {
        super(i, -1);
    }
    
    @Override
    public boolean a_(World world, int i, int j, int k) {
        int l = 0;
        int i1 = 0;
        if(world.getTypeId(i - 1, j, k) == Block.GLOWSTONE.id || world.getTypeId(i + 1, j, k) == Block.GLOWSTONE.id)
            l = 1;
        if(world.getTypeId(i, j, k - 1) == Block.GLOWSTONE.id || world.getTypeId(i, j, k + 1) == Block.GLOWSTONE.id)
            i1 = 1;
        if(l == i1)
            return false;

        // CraftBukkit start
        java.util.Collection<org.bukkit.block.Block> blocks = new java.util.HashSet<org.bukkit.block.Block>();
        org.bukkit.World bworld = world.getWorld();
        // CraftBukkit end

        if(world.getTypeId(i - l, j, k - i1) == 0) {
            i -= l;
            k -= i1;
        }
        int j1;
        int l1;
        for(j1 = -1; j1 <= 2; j1++)
            for(l1 = -1; l1 <= 3; l1++) {
                boolean flag = j1 == -1 || j1 == 2 || l1 == -1 || l1 == 3;
                if((j1 == -1 || j1 == 2) && (l1 == -1 || l1 == 3))
                    continue;
                int j2 = world.getTypeId(i + l * j1, j + l1, k + i1 * j1);
                if(flag) {
                    if(j2 != Block.GLOWSTONE.id)
                        return false;
                    continue;
                }
                if(j2 != 0 && j2 != Block.WATER.id)
                    return false;
            }

        // CraftBukkit start
        for (j1 = 0; j1 < 2; ++j1)
            for (l1 = 0; l1 < 3; ++l1)
                blocks.add(bworld.getBlockAt(i + l * j1, j + l1, k + i1 * j1));

        PortalCreateEvent event = new PortalCreateEvent(blocks, bworld);
        world.getServer().getPluginManager().callEvent(event);

        if (event.isCancelled())
            return false;
        // CraftBukkit end

        world.suppressPhysics = true;
        for(int k1 = 0; k1 < 2; k1++)
            for(int i2 = 0; i2 < 3; i2++)
                world.setTypeId(i + l * k1, j + i2, k + i1 * k1, id);

        world.suppressPhysics = false;
        return true;
    }
    
    @Override
    public void doPhysics(World world, int i, int j, int k, int l) {
        int i1 = 0;
        int j1 = 1;
        if(world.getTypeId(i - 1, j, k) == id || world.getTypeId(i + 1, j, k) == id) {
            i1 = 1;
            j1 = 0;
        }
        int k1;
        for(k1 = j; world.getTypeId(i, k1 - 1, k) == id; k1--) { }
        if(world.getTypeId(i, k1 - 1, k) != Block.GLOWSTONE.id) {
            world.setTypeId(i, j, k, 0);
            return;
        }
        int l1;
        for(l1 = 1; l1 < 4 && world.getTypeId(i, k1 + l1, k) == id; l1++) { }
        if(l1 != 3 || world.getTypeId(i, k1 + l1, k) != Block.GLOWSTONE.id) {
            world.setTypeId(i, j, k, 0);
            return;
        }
        boolean flag = world.getTypeId(i - 1, j, k) == id || world.getTypeId(i + 1, j, k) == id;
        boolean flag1 = world.getTypeId(i, j, k - 1) == id || world.getTypeId(i, j, k + 1) == id;
        if(flag && flag1) {
            world.setTypeId(i, j, k, 0);
            return;
        }
        if((world.getTypeId(i + i1, j, k + j1) != Block.GLOWSTONE.id || world.getTypeId(i - i1, j, k - j1) != id) && (world.getTypeId(i - i1, j, k - j1) != Block.GLOWSTONE.id || world.getTypeId(i + i1, j, k + j1) != id)) {
            world.setTypeId(i, j, k, 0);
            return;
        } else
            return;
    }
    
    @Override
    public int getDimNumber() {
        return mod_AetherMp.idDimensionAether;
    }
}
