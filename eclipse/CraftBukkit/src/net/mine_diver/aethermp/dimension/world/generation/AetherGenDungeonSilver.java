package net.mine_diver.aethermp.dimension.world.generation;

import java.util.Random;

import org.bukkit.block.Chest;
import org.bukkit.craftbukkit.block.CraftChest;

import net.mine_diver.aethermp.blocks.BlockManager;
import net.mine_diver.aethermp.items.ItemManager;
import net.minecraft.server.Block;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.TileEntityChest;
import net.minecraft.server.World;

public class AetherGenDungeonSilver extends AetherGenBuildings {
    public AetherGenDungeonSilver(int i, int j, int k, int l, int i1, int j1, int k1, int l1, int i2) {
        rooms = new int[3][3][3];
        lockedBlockID1 = i;
        lockedBlockID2 = j;
        wallBlockID1 = k;
        wallBlockID2 = l;
        baseID1 = i1;
        baseMeta1 = j1;
        baseID2 = k1;
        baseMeta2 = l1;
        columnID = i2;
    }
    
    @Override
    public boolean a(World world, Random random, int i, int j, int k) {
        org.bukkit.World worldBukkit = world.getWorld();
        replaceAir = true;
        if(!isBoxEmpty(world, i, j, k, 55, 20, 30))
            return false;
        if(world.getTypeId(i, j - 5, k) == 0 || world.getTypeId(i + 55, j - 5, k) == 0 || world.getTypeId(i, j - 5, k + 30) == 0 || world.getTypeId(i + 55, j - 5, k + 30) == 0)
            for(int l = 0; l < 100; l++) {
                int k2 = (i - 11) + random.nextInt(77);
                int j3 = j - 7;
                int l4 = (k - 10) + random.nextInt(50);
                (new AetherGenClouds(BlockManager.Aercloud.id, 0, 10, false)).a(world, random, k2, j3, l4);
            }
        replaceSolid = true;
        setBlocks(baseID2, baseID1, 30);
        setMetadata(baseMeta2, baseMeta1);
        addSolidBox(world, random, i, j - 5, k, 55, 5, 30);
        for(int i1 = i; i1 < i + 55; i1 += 4) {
            addColumn(world, random, i1, j, k, 14);
            addColumn(world, random, i1, j, k + 27, 14);
        }

        for(int j1 = k; j1 < k + 12; j1 += 4) {
            addColumn(world, random, i, j, j1, 14);
            addColumn(world, random, i + 52, j, j1, 14);
        }

        for(int k1 = k + 19; k1 < k + 30; k1 += 4) {
            addColumn(world, random, i, j, k1, 14);
            addColumn(world, random, i + 52, j, k1, 14);
        }

        setBlocks(lockedBlockID1, lockedBlockID2, 20);
        setMetadata(1, 1);
        addHollowBox(world, random, i + 4, j - 1, k + 4, 47, 16, 22);
        addPlaneX(world, random, i + 11, j, k + 5, 15, 20);
        addPlaneX(world, random, i + 18, j, k + 5, 15, 20);
        addPlaneX(world, random, i + 25, j, k + 5, 15, 20);
        addPlaneZ(world, random, i + 5, j, k + 11, 20, 15);
        addPlaneZ(world, random, i + 5, j, k + 18, 20, 15);
        setBlocks(lockedBlockID1, BlockManager.Trap.id, 15);
        setMetadata(1, 1);
        addPlaneY(world, random, i + 5, j + 4, k + 5, 20, 20);
        addPlaneY(world, random, i + 5, j + 9, k + 5, 20, 20);
        for(int l1 = j; l1 < j + 2; l1++)
            for(int l2 = k + 14; l2 < k + 16; l2++)
                worldBukkit.getBlockAt(i + 4, l1, l2).setTypeId(0, false);

        setBlocks(0, 0, 1);
        addSolidBox(world, random, i, j - 4, k + 14, 1, 4, 2);
        addSolidBox(world, random, i + 1, j - 3, k + 14, 1, 3, 2);
        addSolidBox(world, random, i + 2, j - 2, k + 14, 1, 2, 2);
        addSolidBox(world, random, i + 3, j - 1, k + 14, 1, 1, 2);
        setBlocks(lockedBlockID1, lockedBlockID2, 20);
        setMetadata(1, 1);
        for(int i2 = 0; i2 < 7; i2++)
            addPlaneY(world, random, i - 1, j + 15 + i2, (k - 1) + 2 * i2, 57, 32 - 4 * i2);

        int j2 = random.nextInt(3);
        addStaircase(world, random, i + 19, j, k + 5 + j2 * 7, 10);
        rooms[2][0][j2] = 2;
        rooms[2][1][j2] = 2;
        rooms[2][2][j2] = 1;
        int i3 = i + 25;
        for(int k3 = j; k3 < j + 2; k3++)
            for(int i5 = k + 7 + 7 * j2; i5 < k + 9 + 7 * j2; i5++)
                worldBukkit.getBlockAt(i3, k3, i5).setTypeId(0, false);

        j2 = random.nextInt(3);
        addStaircase(world, random, i + 12, j, k + 5 + j2 * 7, 5);
        rooms[1][0][j2] = 1;
        rooms[1][1][j2] = 1;
        j2 = random.nextInt(3);
        addStaircase(world, random, i + 5, j + 5, k + 5 + j2 * 7, 5);
        rooms[0][0][j2] = 1;
        rooms[0][1][j2] = 1;
        for(int j7 = 0; j7 < 3; j7++)
            for(int l7 = 0; l7 < 3; l7++)
                for(int i8 = 0; i8 < 3; i8++) {
                    int k8 = rooms[j7][l7][i8];
                    if(j7 + 1 < 3 && (k8 == 0 || k8 == 1 || random.nextBoolean()) && k8 != 2) {
                        int l8 = rooms[j7 + 1][l7][i8];
                        if(l8 != 2 && (l8 != 1 || k8 != 1)) {
                            rooms[j7][l7][i8] = 3;
                            k8 = 3;
                            i3 = i + 11 + 7 * j7;
                            for(int l3 = j + 5 * l7; l3 < j + 2 + 5 * l7; l3++)
                                for(int j5 = k + 7 + 7 * i8; j5 < k + 9 + 7 * i8; j5++)
                                    worldBukkit.getBlockAt(i3, l3, j5).setTypeId(0, false);
                        }
                    }
                    if(j7 - 1 > 0 && (k8 == 0 || k8 == 1 || random.nextBoolean()) && k8 != 2) {
                        int i9 = rooms[j7 - 1][l7][i8];
                        if(i9 != 2 && (i9 != 1 || k8 != 1)) {
                            rooms[j7][l7][i8] = 4;
                            k8 = 4;
                            i3 = i + 4 + 7 * j7;
                            for(int i4 = j + 5 * l7; i4 < j + 2 + 5 * l7; i4++)
                                for(int k5 = k + 7 + 7 * i8; k5 < k + 9 + 7 * i8; k5++)
                                    worldBukkit.getBlockAt(i3, i4, k5).setTypeId(0, false);
                        }
                    }
                    if(i8 + 1 < 3 && (k8 == 0 || k8 == 1 || random.nextBoolean()) && k8 != 2) {
                        int j9 = rooms[j7][l7][i8 + 1];
                        if(j9 != 2 && (j9 != 1 || k8 != 1)) {
                            rooms[j7][l7][i8] = 5;
                            k8 = 5;
                            int l5 = k + 11 + 7 * i8;
                            for(int j4 = j + 5 * l7; j4 < j + 2 + 5 * l7; j4++)
                                for(i3 = i + 7 + 7 * j7; i3 < i + 9 + 7 * j7; i3++)
                                    worldBukkit.getBlockAt(i3, j4, l5).setTypeId(0, false);
                        }
                    }
                    if(i8 - 1 > 0 && (k8 == 0 || k8 == 1 || random.nextBoolean()) && k8 != 2) {
                        int k9 = rooms[j7][l7][i8 - 1];
                        if(k9 != 2 && (k9 != 1 || k8 != 1)) {
                            rooms[j7][l7][i8] = 6;
                            k8 = 6;
                            int i6 = k + 4 + 7 * i8;
                            for(int k4 = j + 5 * l7; k4 < j + 2 + 5 * l7; k4++)
                                for(i3 = i + 7 + 7 * j7; i3 < i + 9 + 7 * j7; i3++)
                                    worldBukkit.getBlockAt(i3, k4, i6).setTypeId(0, false);
                        }
                    }
                    int l9 = random.nextInt(3);
                    if(k8 >= 3) {
                        switch(l9) {
                        default:
                            break;

                        case 0:
                            worldBukkit.getBlockAt(i + 7 + j7 * 7, (j - 1) + l7 * 5, k + 7 + i8 * 7).setTypeIdAndData(BlockManager.Trap.id, (byte)1, false);
                            break;

                        case 1:
                            addPlaneY(world, random, i + 7 + 7 * j7, j + 5 * l7, k + 7 + 7 * i8, 2, 2);
                            int i10 = i + 7 + 7 * j7 + random.nextInt(2);
                            int k10 = k + 7 + 7 * i8 + random.nextInt(2);
                            if(world.getTypeId(i10, j + 5 * l7 + 1, k10) != 0)
                                break;
                            org.bukkit.block.Block chestBlock = worldBukkit.getBlockAt(i10, j + 5 * l7 + 1, k10);
                            chestBlock.setTypeId(Block.CHEST.id);
                            Chest chest = (Chest)worldBukkit.getBlockAt(i10, j + 5 * l7 + 1, k10).getState();
                            org.bukkit.inventory.Inventory inventory = chest.getInventory();
                            TileEntityChest tileentitychest1 = (TileEntityChest)world.getTileEntity(i10, j + 5 * l7 + 1, k10);
                            for(int j10 = 0; j10 < 3 + random.nextInt(3); j10++) {
                            	ItemStack vis = getNormalLoot(random);
                                org.bukkit.inventory.ItemStack itemstack1 = new org.bukkit.inventory.ItemStack(vis.id, vis.count, (short) vis.damage);
                                inventory.setItem(random.nextInt(tileentitychest1.getSize()), itemstack1);
                            }

                            break;

                        case 2:
                            addPlaneY(world, random, i + 7 + 7 * j7, j + 5 * l7, k + 7 + 7 * i8, 2, 2);
                            worldBukkit.getBlockAt(i + 7 + 7 * j7 + random.nextInt(2), j + 5 * l7 + 1, k + 7 + 7 * i8 + random.nextInt(2)).setTypeId(BlockManager.ChestMimic.id);
                            worldBukkit.getBlockAt(i + 7 + 7 * j7 + random.nextInt(2), j + 5 * l7 + 1, k + 7 + 7 * i8 + random.nextInt(2)).setTypeId(BlockManager.ChestMimic.id);
                            break;
                        }
                    }
                }

        for(i3 = 0; i3 < 24; i3++)
            for(int j6 = 0; j6 < 20; j6++) {
                int k7 = (int)(Math.sqrt(i3 * i3 + (j6 - 7) * (j6 - 7)) + Math.sqrt(i3 * i3 + (j6 - 12) * (j6 - 12)));
                if(k7 == 21) {
                    worldBukkit.getBlockAt(i + 26 + i3, j, k + 5 + j6).setTypeIdAndData(lockedBlockID2, (byte)1, false);
                    continue;
                }
                if(k7 > 21)
                    worldBukkit.getBlockAt(i + 26 + i3, j, k + 5 + j6).setTypeIdAndData(lockedBlockID1, (byte)1, false);
            }

        setBlocks(lockedBlockID1, lockedBlockID1, 1);
        setMetadata(1, 1);
        addPlaneY(world, random, i + 44, j + 1, k + 11, 6, 8);
        addSolidBox(world, random, i + 46, j + 2, k + 13, 4, 2, 4);
        addLineX(world, random, i + 46, j + 4, k + 13, 4);
        addLineX(world, random, i + 46, j + 4, k + 16, 4);
        addPlaneX(world, random, i + 49, j + 4, k + 13, 4, 4);
        setBlocks(Block.WOOL.id, Block.WOOL.id, 1);
        setMetadata(11, 11);
        addPlaneY(world, random, i + 47, j + 3, k + 14, 2, 2);
        for(i3 = 0; i3 < 2; i3++)
            for(int k6 = 0; k6 < 2; k6++)
                worldBukkit.getBlockAt(i + 44 + i3 * 5, j + 2, k + 11 + k6 * 7).setTypeId(BlockManager.AmbrosiumTorch.id, false);

        setBlocks(Block.STATIONARY_WATER.id, Block.STATIONARY_WATER.id, 1);
        setMetadata(0, 0);
        addPlaneY(world, random, i + 35, j + 1, k + 5, 6, 3);
        addPlaneY(world, random, i + 35, j + 1, k + 22, 6, 3);
        setBlocks(lockedBlockID1, lockedBlockID1, 1);
        setMetadata(1, 1);
        addLineZ(world, random, i + 34, j + 1, k + 5, 2);
        addLineZ(world, random, i + 41, j + 1, k + 5, 2);
        addLineX(world, random, i + 36, j + 1, k + 8, 4);
        worldBukkit.getBlockAt(i + 35, j + 1, k + 7).setTypeIdAndData(lockedBlockID1, (byte)1, false);
        worldBukkit.getBlockAt(i + 40, j + 1, k + 7).setTypeIdAndData(lockedBlockID1, (byte)1, false);
        addLineZ(world, random, i + 34, j + 1, k + 23, 2);
        addLineZ(world, random, i + 41, j + 1, k + 23, 2);
        addLineX(world, random, i + 36, j + 1, k + 21, 4);
        worldBukkit.getBlockAt(i + 35, j + 1, k + 22).setTypeIdAndData(lockedBlockID1, (byte)1, false);
        worldBukkit.getBlockAt(i + 40, j + 1, k + 22).setTypeIdAndData(lockedBlockID1, (byte)1, false);
        for(i3 = i + 36; i3 < i + 40; i3 += 3)
            for(int l6 = k + 8; l6 < k + 22; l6 += 13)
                worldBukkit.getBlockAt(i3, j + 2, l6).setTypeId(BlockManager.AmbrosiumTorch.id, false);

        addChandelier(world, i + 28, j, k + 10, 8);
        addChandelier(world, i + 43, j, k + 10, 8);
        addChandelier(world, i + 43, j, k + 19, 8);
        addChandelier(world, i + 28, j, k + 19, 8);
        addSapling(world, random, i + 45, j + 1, k + 6);
        addSapling(world, random, i + 45, j + 1, k + 21);
        /*EntityValkyrie entityvalkyrie = new EntityValkyrie(world, (double)i + 40D, (double)j + 1.5D, (double)k + 15D, true);
        entityvalkyrie.setPosition(i + 40, j + 2, k + 15);
        entityvalkyrie.setDungeon(i + 26, j, k + 5);
        world.entityJoinedWorld(entityvalkyrie);*/
        setBlocks(lockedBlockID1, lockedBlockID1, 1);
        setMetadata(1, 1);
        addHollowBox(world, random, i + 41, j - 2, k + 13, 4, 4, 4);
        i3 = i + 42 + random.nextInt(2);
        int i7 = k + 14 + random.nextInt(2);
        /*world.setRawTypeId(i3, j - 1, i7, BlockManager.TreasureChest.id);
        TileEntityChest chest = (TileEntityChest)world.getTileEntity(i3, j - 1, i7);
        for (int j8 = 0; j8 < 3 + random.nextInt(3); j8++) {
            ItemStack itemstack = getSilverLoot(random);
            chest.setItem(random.nextInt(chest.getSize()), itemstack);
        }*/
        
        org.bukkit.block.Block chestBlock = worldBukkit.getBlockAt(i3, j - 1, i7);
        chestBlock.setTypeId(BlockManager.TreasureChest.id, false);
        Chest treasureChest = new CraftChest(chestBlock);
        org.bukkit.inventory.Inventory inventory = treasureChest.getInventory();
        TileEntityChest tileentitychest1 = (TileEntityChest) world.getTileEntity(i3, j - 1, i7);
        for(int j10 = 0; j10 < 3 + random.nextInt(3); j10++) {
        	ItemStack vis = getSilverLoot(random);
        	if (vis != null) {
        		org.bukkit.inventory.ItemStack itemstack1 = new org.bukkit.inventory.ItemStack(vis.id, vis.count, (short) vis.damage);
            	inventory.setItem(random.nextInt(tileentitychest1.getSize()), itemstack1);
        	}
        }

        worldBukkit.getBlockAt(i3, j - 1, i7).setData((byte) 2, false);
        return true;
    }

    private void addSapling(World world, Random random, int i, int j, int k) {
        org.bukkit.World worldBukkit = world.getWorld();
        setBlocks(lockedBlockID1, lockedBlockID1, 1);
        setMetadata(1, 1);
        addPlaneY(world, random, i, j, k, 3, 3);
        worldBukkit.getBlockAt(i + 1, j, k + 1).setTypeId(BlockManager.Dirt.id, false);
        worldBukkit.getBlockAt(i + 1, j + 1, k + 1).setTypeId(BlockManager.GoldenOakSapling.id, false);
        for(int l = i; l < i + 3; l += 2)
            for(int i1 = k; i1 < k + 3; i1 += 2)
                worldBukkit.getBlockAt(l, j + 1, i1).setTypeId(BlockManager.AmbrosiumTorch.id, false);
    }

    private void addChandelier(World world, int i, int j, int k, int l) {
        org.bukkit.World worldBukkit = world.getWorld();
        for(int i1 = j + l + 3; i1 < j + l + 6; i1++)
            worldBukkit.getBlockAt(i, i1, k).setTypeId(Block.FENCE.id, false);

        for(int j1 = i - 1; j1 < i + 2; j1++)
            worldBukkit.getBlockAt(j1, j + l + 1, k).setTypeId(Block.GLOWSTONE.id, false);

        for(int k1 = j + l; k1 < j + l + 3; k1++)
            worldBukkit.getBlockAt(i, k1, k).setTypeId(Block.GLOWSTONE.id, false);

        for(int l1 = k - 1; l1 < k + 2; l1++)
            worldBukkit.getBlockAt(i, j + l + 1, l1).setTypeId(Block.GLOWSTONE.id, false);
    }

    private void addColumn(World world, Random random, int i, int j, int k, int l) {
        org.bukkit.World worldBukkit = world.getWorld();
        setBlocks(wallBlockID1, wallBlockID2, 20);
        setMetadata(1, 1);
        addPlaneY(world, random, i, j, k, 3, 3);
        addPlaneY(world, random, i, j + l, k, 3, 3);
        setBlocks(columnID, columnID, 1);
        setMetadata(0, 0);
        addLineY(world, random, i + 1, j, k + 1, l - 1);
        worldBukkit.getBlockAt(i + 1, (j + l) - 1, k + 1).setTypeIdAndData(columnID, (byte)1, false);
    }

    private void addStaircase(World world, Random random, int i, int j, int k, int l) {
        org.bukkit.World worldBukkit = world.getWorld();
        setBlocks(0, 0, 1);
        addSolidBox(world, random, i + 1, j, k + 1, 4, l, 4);
        setBlocks(lockedBlockID1, lockedBlockID2, 5);
        setMetadata(1, 1);
        addSolidBox(world, random, i + 2, j, k + 2, 2, l + 4, 2);
        worldBukkit.getBlockAt(i + 1, j + 0, k + 1).setTypeId(Block.STEP.id, false);
        worldBukkit.getBlockAt(i + 2, j + 0, k + 1).setTypeId(Block.DOUBLE_STEP.id, false);
        worldBukkit.getBlockAt(i + 3, j + 1, k + 1).setTypeId(Block.STEP.id, false);
        worldBukkit.getBlockAt(i + 4, j + 1, k + 1).setTypeId(Block.DOUBLE_STEP.id, false);
        worldBukkit.getBlockAt(i + 4, j + 2, k + 2).setTypeId(Block.STEP.id, false);
        worldBukkit.getBlockAt(i + 4, j + 2, k + 3).setTypeId(Block.DOUBLE_STEP.id, false);
        worldBukkit.getBlockAt(i + 4, j + 3, k + 4).setTypeId(Block.STEP.id, false);
        worldBukkit.getBlockAt(i + 3, j + 3, k + 4).setTypeId(Block.DOUBLE_STEP.id, false);
        worldBukkit.getBlockAt(i + 2, j + 4, k + 4).setTypeId(Block.STEP.id, false);
        worldBukkit.getBlockAt(i + 1, j + 4, k + 4).setTypeId(Block.DOUBLE_STEP.id, false);
        if(l == 5)
            return;
        else {
            worldBukkit.getBlockAt(i + 1, j + 5, k + 3).setTypeId(Block.STEP.id, false);
            worldBukkit.getBlockAt(i + 1, j + 5, k + 2).setTypeId(Block.DOUBLE_STEP.id, false);
            worldBukkit.getBlockAt(i + 1, j + 6, k + 1).setTypeId(Block.STEP.id, false);
            worldBukkit.getBlockAt(i + 2, j + 6, k + 1).setTypeId(Block.DOUBLE_STEP.id, false);
            worldBukkit.getBlockAt(i + 3, j + 7, k + 1).setTypeId(Block.STEP.id, false);
            worldBukkit.getBlockAt(i + 4, j + 7, k + 1).setTypeId(Block.DOUBLE_STEP.id, false);
            worldBukkit.getBlockAt(i + 4, j + 8, k + 2).setTypeId(Block.STEP.id, false);
            worldBukkit.getBlockAt(i + 4, j + 8, k + 3).setTypeId(Block.DOUBLE_STEP.id, false);
            worldBukkit.getBlockAt(i + 4, j + 9, k + 4).setTypeId(Block.STEP.id, false);
            worldBukkit.getBlockAt(i + 3, j + 9, k + 4).setTypeId(Block.DOUBLE_STEP.id, false);
            return;
        }
    }

    private ItemStack getNormalLoot(Random random) {
        int i = random.nextInt(15);
        switch(i) {
        default:
            break;

        case 0:
            return new ItemStack(ItemManager.PickZanite);

        case 1:
            return new ItemStack(ItemManager.Bucket, 1, 2);

        /*case 2:
            return new ItemStack(ItemManager.DartShooter.id, 1);

        case 3:
            return new ItemStack(ItemManager.MoaEgg.id, 1, (short)0);*/

        case 4:
            return new ItemStack(ItemManager.AmbrosiumShard, random.nextInt(10) + 1);

        /*case 5:
            return new ItemStack(ItemManager.Dart.id, random.nextInt(5) + 1, 0);

        case 6:
            return new ItemStack(ItemManager.Dart.id, random.nextInt(3) + 1, 1);

        case 7:
            return new ItemStack(ItemManager.Dart.id, random.nextInt(3) + 1, 2);*/

        case 8:
            if(random.nextInt(20) == 0)
                return new ItemStack(ItemManager.BlueMusicDisk);
            break;

        case 9:
            return new ItemStack(ItemManager.Bucket);

        case 10:
            if(random.nextInt(10) == 0)
                return new ItemStack(Item.byId[Item.GOLD_RECORD.id + random.nextInt(2)]);
            break;

        case 11:
            if(random.nextInt(2) == 0)
                return new ItemStack(ItemManager.ZaniteBoots);
            if(random.nextInt(2) == 0)
                return new ItemStack(ItemManager.ZaniteHelmet);
            if(random.nextInt(2) == 0)
                return new ItemStack(ItemManager.ZaniteLeggings);
            if(random.nextInt(2) == 0)
                return new ItemStack(ItemManager.ZaniteChestplate);
            break;

        case 12:
            if(random.nextInt(4) == 0)
                return new ItemStack(ItemManager.IronPendant);

        case 13:
            if(random.nextInt(10) == 0)
                return new ItemStack(ItemManager.GoldPendant);

        case 14:
            if(random.nextInt(15) == 0)
                return new ItemStack(ItemManager.ZaniteRing);
            break;
        }
        return new ItemStack(BlockManager.AmbrosiumTorch, random.nextInt(5));
    }

    private ItemStack getSilverLoot(Random random) {
        int i = random.nextInt(9);
        switch(i) {
        default:
            break;

        /*case 0:
            return new ItemStack(ItemManager.GummieSwet, random.nextInt(16));

        case 1:
            return new ItemStack(ItemManager.SwordLightning);

        case 2:
            if(random.nextBoolean())
                return new ItemStack(ItemManager.AxeValkyrie);
            if(random.nextBoolean())
                return new ItemStack(ItemManager.ShovelValkyrie);
            if(random.nextBoolean())
                return new ItemStack(ItemManager.PickValkyrie);
            break;

        case 3:
            return new ItemStack(ItemManager.SwordHoly);*/

        case 4:
            return new ItemStack(ItemManager.GoldenFeather);

        case 5:
            return new ItemStack(ItemManager.RegenerationStone);

        case 6:
            if(random.nextBoolean())
                return new ItemStack(ItemManager.NeptuneHelmet);
            if(random.nextBoolean())
                return new ItemStack(ItemManager.NeptuneLeggings);
            if(random.nextBoolean())
                return new ItemStack(ItemManager.NeptuneChestplate);
            break;

        case 7:
            if(random.nextBoolean())
                return new ItemStack(ItemManager.NeptuneBoots);
            else
                return new ItemStack(ItemManager.NeptuneGlove);

        case 8:
            return new ItemStack(ItemManager.InvisibilityCloak);
        }
        return new ItemStack(ItemManager.ZanitePendant);
    }
    
    private int baseMeta1;
    private int baseMeta2;
    private int lockedBlockID1;
    private int lockedBlockID2;
    private int wallBlockID1;
    private int wallBlockID2;
    private int baseID1;
    private int baseID2;
    private int columnID;
    private int rooms[][][];
}
