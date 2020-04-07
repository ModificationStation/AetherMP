package net.mine_diver.aethermp.dimension.world.generation;

import java.util.Random;

import org.bukkit.block.Chest;
import org.bukkit.craftbukkit.block.CraftChest;

import net.mine_diver.aethermp.blocks.BlockManager;
import net.mine_diver.aethermp.items.ItemManager;
import net.minecraft.server.Block;
import net.minecraft.server.Item;
import net.minecraft.server.TileEntityChest;
import net.minecraft.server.World;
import net.minecraft.server.ItemStack;

public class AetherGenDungeonBronze extends AetherGenBuildings {

    public AetherGenDungeonBronze(int i, int j, int k, int l, int i1, int j1, int k1, int l1, int i2) {
        lockedBlockID1 = i;
        lockedBlockID2 = j;
        wallBlockID1 = k;
        wallBlockID2 = l;
        corridorBlockID1 = i1;
        corridorMeta1 = j1;
        corridorBlockID2 = k1;
        corridorMeta2 = l1;
        numRooms = i2;
        finished = false;
    }
    
    @Override
    public boolean a(World world, Random random, int i, int j, int k) {
        org.bukkit.World worldBukkit = world.getWorld();
        replaceAir = true;
        replaceSolid = true;
        n = 0;
        if(!isBoxSolid(world, i, j, k, 16, 12, 16) || !isBoxSolid(world, i + 20, j, k + 2, 12, 12, 12))
            return false;
        setBlocks(lockedBlockID1, lockedBlockID2, 20);
        addHollowBox(world, random, i, j, k, 16, 12, 16);
        addHollowBox(world, random, i + 6, j - 2, k + 6, 4, 4, 4);
        /*EntitySlider entityslider = new EntitySlider(world);
        entityslider.setPosition(i + 8, j + 2, k + 8);
        entityslider.setDungeon(i, j, k);
        world.addEntity(entityslider);*/
        int l = i + 7 + random.nextInt(2);
        int i1 = j - 1;
        int j1 = k + 7 + random.nextInt(2);
        
        //TODO: Use vanilla
        org.bukkit.block.Block chestBlock = worldBukkit.getBlockAt(l, i1, j1);
        chestBlock.setTypeIdAndData(BlockManager.TreasureChest.id, (byte) 0, true);
        Chest chest = new CraftChest(chestBlock);
        org.bukkit.inventory.Inventory inventory = chest.getInventory();
        TileEntityChest tileentitychest = (TileEntityChest)world.getTileEntity(l, i1, j1);
        for(int k1 = 0; k1 < 3 + random.nextInt(3); k1++) {
        	ItemStack vis = getBronzeLoot(random);
            org.bukkit.inventory.ItemStack itemstack = new org.bukkit.inventory.ItemStack(vis.id, vis.count, (short) vis.damage);
            inventory.setItem(random.nextInt(tileentitychest.getSize()), itemstack);
        }

        l = i + 20;
        i1 = j;
        j1 = k + 2;
        if(!isBoxSolid(world, l, i1, j1, 12, 12, 12))
            return true;
        setBlocks(wallBlockID1, wallBlockID2, 20);
        addHollowBox(world, random, l, i1, j1, 12, 12, 12);
        setBlocks(corridorBlockID2, corridorBlockID1, 5);
        setMetadata(corridorMeta2, corridorMeta1);
        addSquareTube(world, random, l - 5, i1, j1 + 3, 6, 6, 6, 0);
        for(int l1 = l + 2; l1 < l + 10; l1 += 3)
            for(int i2 = j1 + 2; i2 < j1 + 10; i2 += 3)
                worldBukkit.getBlockAt(l1, j, i2).setTypeIdAndData(BlockManager.Trap.id, (byte)0, false);

        n++;
        generateNextRoom(world, random, l, i1, j1);
        generateNextRoom(world, random, l, i1, j1);
        if(n > numRooms || !finished)
            endCorridor(world, random, l, i1, j1);
        return true;
    }

    public boolean generateNextRoom(World world, Random random, int i, int j, int k) {
        org.bukkit.World worldBukkit = world.getWorld();
        if(n > numRooms && !finished) {
            endCorridor(world, random, i, j, k);
            return false;
        }
        int l = random.nextInt(4);
        int i1 = i;
        int j1 = j;
        int k1 = k;
        if(l == 0) {
            i1 += 16;
            k1 += 0;
        }
        if(l == 1) {
            i1 += 0;
            k1 += 16;
        }
        if(l == 2) {
            i1 -= 16;
            k1 += 0;
        }
        if(l == 3) {
            i1 += 0;
            k1 -= 16;
        }
        if(!isBoxSolid(world, i1, j1, k1, 12, 8, 12))
            return false;
        setBlocks(wallBlockID1, wallBlockID2, 20);
        setMetadata(0, 0);
        addHollowBox(world, random, i1, j1, k1, 12, 8, 12);
        for(int l1 = i1; l1 < i1 + 12; l1++)
            for(int k2 = j1; k2 < j1 + 8; k2++)
                for(int k3 = k1; k3 < k1 + 12; k3++)
                    if(world.getTypeId(l1, k2, k3) == wallBlockID1 && random.nextInt(100) == 0)
                        worldBukkit.getBlockAt(l1, k2, k3).setTypeId(BlockManager.Trap.id, false);

        for(int i2 = i1 + 2; i2 < i1 + 10; i2 += 7)
            for(int l2 = k1 + 2; l2 < k1 + 10; l2 += 7)
                worldBukkit.getBlockAt(i2, j, l2).setTypeIdAndData(BlockManager.Trap.id, (byte)0, false);

        addPlaneY(world, random, i1 + 4, j1 + 1, k1 + 4, 4, 4);
        int j2 = random.nextInt(2);
        int i3 = i1 + 5 + random.nextInt(2);
        int l3 = k1 + 5 + random.nextInt(2);
        switch(j2) {
        default:
            break;

        case 0:
            worldBukkit.getBlockAt(i3, j1 + 2, l3).setTypeId(BlockManager.ChestMimic.id);
            break;

        case 1:
            if(world.getTypeId(i3, j1 + 2, l3) != 0)
                break;
            
            //TODO: Use vanilla
            org.bukkit.block.Block chestBlock = worldBukkit.getBlockAt(i3, j1 + 2, l3);
            chestBlock.setTypeId(Block.CHEST.id);
            Chest chest = (Chest)worldBukkit.getBlockAt(i3, j1 + 2, l3).getState();
            org.bukkit.inventory.Inventory inventory = chest.getInventory();
            TileEntityChest tileentitychest = (TileEntityChest)world.getTileEntity(i3, j1 + 2, l3);
            for(int j3 = 0; j3 < 3 + random.nextInt(3); j3++) {
            	ItemStack vis = getNormalLoot(random);
                org.bukkit.inventory.ItemStack itemstack = new org.bukkit.inventory.ItemStack(vis.id, vis.count, (short) vis.damage);
                inventory.setItem(random.nextInt(tileentitychest.getSize()), itemstack);
            }

            break;
        }
        setBlocks(corridorBlockID2, corridorBlockID1, 5);
        setMetadata(corridorMeta2, corridorMeta1);
        switch(l) {
        case 0: // '\0'
            addSquareTube(world, random, i1 - 5, j1, k1 + 3, 6, 6, 6, 0);
            break;

        case 1: // '\001'
            addSquareTube(world, random, i1 + 3, j1, k1 - 5, 6, 6, 6, 2);
            break;

        case 2: // '\002'
            addSquareTube(world, random, i1 + 11, j1, k1 + 3, 6, 6, 6, 0);
            break;

        case 3: // '\003'
            addSquareTube(world, random, i1 + 3, j1, k1 + 11, 6, 6, 6, 2);
            break;
        }
        n++;
        if(!generateNextRoom(world, random, i1, j1, k1))
            return false;
        else
            return generateNextRoom(world, random, i1, j1, k1);
    }

    public void endCorridor(World world, Random random, int i, int j, int k) {
        replaceAir = false;
        boolean flag = true;
        int l = random.nextInt(3);
        int i1 = i;
        int j1 = j;
        int k1 = k;
        if(l == 0) {
            i1 += 11;
            k1 += 3;
            while(flag) {
                if(isBoxEmpty(world, i1, j1, k1, 1, 8, 6) || i1 - i > 100)
                    flag = false;
                for(boolean flag1 = true; flag1 && (world.getTypeId(i1, j1, k1) == wallBlockID1 || world.getTypeId(i1, j1, k1) == wallBlockID2 || world.getTypeId(i1, j1, k1) == lockedBlockID1 || world.getTypeId(i1, j1, k1) == lockedBlockID2);) {
                    if(world.getTypeId(i1 + 1, j1, k1) == wallBlockID1 || world.getTypeId(i1 + 1, j1, k1) == wallBlockID2 || world.getTypeId(i1 + 1, j1, k1) == lockedBlockID1 || world.getTypeId(i1 + 1, j1, k1) == lockedBlockID2)
                        i1++;
                    else
                        flag1 = false;
                }

                setBlocks(corridorBlockID2, corridorBlockID1, 5);
                setMetadata(corridorMeta2, corridorMeta1);
                addPlaneX(world, random, i1, j1, k1, 8, 6);
                setBlocks(0, 0, 1);
                addPlaneX(world, random, i1, j1 + 1, k1 + 1, 6, 4);
                i1++;
            }
        }
        if(l == 1) {
            i1 += 3;
            for(k1 += 11; flag; k1++) {
                if(isBoxEmpty(world, i1, j1, k1, 6, 8, 1) || k1 - k > 100)
                    flag = false;
                for(boolean flag2 = true; flag2 && (world.getTypeId(i1, j1, k1) == wallBlockID1 || world.getTypeId(i1, j1, k1) == wallBlockID2 || world.getTypeId(i1, j1, k1) == lockedBlockID1 || world.getTypeId(i1, j1, k1) == lockedBlockID2);) {
                    if(world.getTypeId(i1, j1, k1 + 1) == wallBlockID1 || world.getTypeId(i1, j1, k1 + 1) == wallBlockID2 || world.getTypeId(i1, j1, k1 + 1) == lockedBlockID1 || world.getTypeId(i1, j1, k1 + 1) == lockedBlockID2)
                        k1++;
                    else
                        flag2 = false;
                }

                setBlocks(corridorBlockID2, corridorBlockID1, 5);
                setMetadata(corridorMeta2, corridorMeta1);
                addPlaneZ(world, random, i1, j1, k1, 6, 8);
                setBlocks(0, 0, 1);
                addPlaneZ(world, random, i1 + 1, j1 + 1, k1, 4, 6);
            }
        }
        if(l == 2) {
            i1 += 3;
            for(k1 += 0; flag; k1--) {
                if(isBoxEmpty(world, i1, j1, k1, 6, 8, 1) || j - k1 > 100)
                    flag = false;
                for(boolean flag3 = true; flag3 && (world.getTypeId(i1, j1, k1) == wallBlockID1 || world.getTypeId(i1, j1, k1) == wallBlockID2 || world.getTypeId(i1, j1, k1) == lockedBlockID1 || world.getTypeId(i1, j1, k1) == lockedBlockID2);) {
                    if(world.getTypeId(i1, j1, k1 - 1) == wallBlockID1 || world.getTypeId(i1, j1, k1 - 1) == wallBlockID2 || world.getTypeId(i1, j1, k1 - 1) == lockedBlockID1 || world.getTypeId(i1, j1, k1 - 1) == lockedBlockID2)
                        k1--;
                    else
                        flag3 = false;
                }

                setBlocks(corridorBlockID2, corridorBlockID1, 5);
                setMetadata(corridorMeta2, corridorMeta1);
                addPlaneZ(world, random, i1, j1, k1, 6, 8);
                setBlocks(0, 0, 1);
                addPlaneZ(world, random, i1 + 1, j1 + 1, k1, 4, 6);
            }
        }
        finished = true;
    }

    private ItemStack getNormalLoot(Random random) {
        int i = random.nextInt(14);
        switch(i) {
        default:
            break;

        case 0:
            return new ItemStack(ItemManager.PickZanite);

        case 1:
            return new ItemStack(ItemManager.AxeZanite);

        case 2:
            return new ItemStack(ItemManager.SwordZanite);

        case 3:
            return new ItemStack(ItemManager.ShovelZanite);

        /*case 4:
            return new ItemStack(ItemManager.AgilityCape);*/

        case 5:
            return new ItemStack(ItemManager.AmbrosiumShard, random.nextInt(10) + 1);

        /*case 6:
            return new ItemStack(ItemManager.Dart, random.nextInt(5) + 1, 0);

        case 7:
            return new ItemStack(ItemManager.Dart, random.nextInt(3) + 1, 1);

        case 8:
            return new ItemStack(ItemManager.Dart, random.nextInt(3) + 1, 2);*/

        case 9:
            if(random.nextInt(20) == 0)
                return new ItemStack(ItemManager.BlueMusicDisk);
            break;

        case 10:
            return new ItemStack(ItemManager.Bucket);

        case 11:
            if(random.nextInt(10) == 0)
                return new ItemStack(Item.byId[Item.GOLD_RECORD.id + random.nextInt(2)]);
            break;

        /*case 12:
            if(random.nextInt(4) == 0)
                return new ItemStack(ItemManager.IronRing.id, 1);
            break;

        case 13:
            if(random.nextInt(10) == 0)
                return new ItemStack(ItemManager.GoldRing.id, 1);
            break;*/
        }
        return new ItemStack(BlockManager.AmbrosiumTorch);
    }

    private ItemStack getBronzeLoot(Random random) {
        /*int i = random.nextInt(7);
        switch(i) {
        case 0:
            return new ItemStack(ItemManager.GummieSwet, random.nextInt(8), random.nextInt(2));

        case 1:
            return new ItemStack(ItemManager.PhoenixBow);

        case 2:
            return new ItemStack(ItemManager.SwordFire);

        case 3:
            return new ItemStack(ItemManager.HammerNotch);

        case 4:
            return new ItemStack(ItemManager.LightningKnife, random.nextInt(16), 1);

        case 5:
            return new ItemStack(ItemManager.Lance);

        case 6:
            return new ItemStack(ItemManager.AgilityCape);
        }*/
        return new ItemStack(ItemManager.Stick);
    }

    private int corridorMeta1;
    private int corridorMeta2;
    private int lockedBlockID1;
    private int lockedBlockID2;
    private int wallBlockID1;
    private int wallBlockID2;
    private int corridorBlockID1;
    private int corridorBlockID2;
    private int numRooms;
    private int n;
    private boolean finished;
}
