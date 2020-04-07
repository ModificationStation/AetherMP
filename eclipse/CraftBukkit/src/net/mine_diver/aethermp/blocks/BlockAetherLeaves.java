package net.mine_diver.aethermp.blocks;

import java.util.*;
import org.bukkit.event.block.LeavesDecayEvent;

import net.minecraft.server.BlockLeavesBase;
import net.minecraft.server.Item;
import net.minecraft.server.Loc;
import net.minecraft.server.Material;
import net.minecraft.server.World;

public class BlockAetherLeaves extends BlockLeavesBase {

    protected BlockAetherLeaves(int i) {
        super(i, -1, Material.LEAVES, false);
        a(true);
    }
    
    @Override
    public int a(Random random) {
        if(id == BlockManager.GoldenOakLeaves.id)
            return random.nextInt(60) == 0 ? 1 : 0;
        else
            return random.nextInt(40) == 0 ? 1 : 0;
    }
    
    @Override
    public int a(int i, Random random) {
        if(id == BlockManager.SkyrootLeaves.id)
            return BlockManager.SkyrootSapling.id;
        if(random.nextInt(10) == 0)
            return Item.GOLDEN_APPLE.id;
        else
            return BlockManager.GoldenOakSapling.id;
    }
    
    @Override
    public void remove(World world, int i, int j, int k) {
        int l = 1;
        int i1 = l + 1;
        if(world.a(i - i1, j - i1, k - i1, i + i1, j + i1, k + i1)) 
            for(int j1 = -l; j1 <= l; j1++) 
                for(int k1 = -l; k1 <= l; k1++) 
                    for(int l1 = -l; l1 <= l; l1++) {
                        int i2 = world.getTypeId(i + j1, j + k1, k + l1);
                        if(i2 == id) {
                            int j2 = world.getData(i + j1, j + k1, k + l1);
                            world.setRawData(i + j1, j + k1, k + l1, j2 | 8);
                        }
                    }
    }
    
    @Override
    public void a(World world, int i, int j, int k, Random random) {
        if(!nearTrunk(world, i, j, k))
        	removeLeaves(world, i, j, k);
    }
    
    private void removeLeaves(World world, int i, int j, int k) {
        // CraftBukkit start
        LeavesDecayEvent event = new LeavesDecayEvent(world.getWorld().getBlockAt(i, j, k));
        world.getServer().getPluginManager().callEvent(event);

        if (event.isCancelled()) return;
        // CraftBukkit end
        g(world, i, j, k, world.getData(i, j, k));
        world.setTypeId(i, j, k, 0);
    }

    private boolean nearTrunk(World world, int i, int j, int k) {
        Loc loc = new Loc(i, j, k);
        LinkedList<Loc> linkedlist = new LinkedList<Loc>();
        List<Loc> arraylist = new ArrayList<Loc>();
        linkedlist.offer(new Loc(i, j, k));
        int l = id;
        do {
            if(linkedlist.isEmpty())
                break;
            Loc loc1 = (Loc)linkedlist.poll();
            if(!arraylist.contains(loc1)) {
                if(loc1.distSimple(loc) <= 4) {
                    int i1 = loc1.getBlock(world);
                    int j1 = loc1.getMeta(world);
                    if(i1 == BlockManager.Log.id && isMyTrunkMeta(j1))
                        return true;
                    if(i1 == l)
                        linkedlist.addAll(Arrays.asList(loc1.adjacent()));
                }
                arraylist.add(loc1);
            }
        } while(true);
        return false;
    }

    private boolean isMyTrunkMeta(int i) {
        if(id == BlockManager.SkyrootLeaves.id)
            return i <= 1;
        else
            return i >= 2;
    }
    
    @Override
    protected int a_(int i) {
        return i & 3;
    }
    
    @Override
    public boolean a() {
        return false;
    }
}
