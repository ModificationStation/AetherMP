package net.mine_diver.aetherapi.impl.proxy;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

import net.mine_diver.aetherapi.api.event.dimension.world.generation.dungeon.DungeonLoot;
import net.mine_diver.aetherapi.api.util.LootType;
import net.minecraft.src.AetherBlocks;
import net.minecraft.src.AetherGenClouds;
import net.minecraft.src.AetherGenDungeonSilver;
import net.minecraft.src.Block;
import net.minecraft.src.EntityValkyrie;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntityChest;
import net.minecraft.src.World;

public class AetherGenDungeonSilverProxy extends AetherGenDungeonSilver {

	public AetherGenDungeonSilverProxy(int i, int j, int k, int l, int i1, int j1, int k1, int l1, int i2) {
		super(i, j, k, l, i1, j1, k1, l1, i2);
	}
	
	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		try {
			return generateThrows(world, random, i, j, k);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public boolean generateThrows(World world, Random random, int i, int j, int k) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        replaceAir = true;
        if(!isBoxEmpty(world, i, j, k, 55, 20, 30))
            return false;
        if(world.getBlockId(i, j - 5, k) == 0 || world.getBlockId(i + 55, j - 5, k) == 0 || world.getBlockId(i, j - 5, k + 30) == 0 || world.getBlockId(i + 55, j - 5, k + 30) == 0)
            for(int l = 0; l < 100; l++) {
                int k2 = (i - 11) + random.nextInt(77);
                int j3 = j - 7;
                int l4 = (k - 10) + random.nextInt(50);
                (new AetherGenClouds(AetherBlocks.Aercloud.blockID, 0, 10, false)).generate(world, random, k2, j3, l4);
            }
        replaceSolid = true;
        setBlocks(baseID2Field.getInt(this), baseID1Field.getInt(this), 30);
        setMetadata(baseMeta2Field.getInt(this), baseMeta1Field.getInt(this));
        addSolidBox(world, random, i, j - 5, k, 55, 5, 30);
        for(int i1 = i; i1 < i + 55; i1 += 4) {
            addColumnMethod.invoke(this, world, random, i1, j, k, 14);
            addColumnMethod.invoke(this, world, random, i1, j, k + 27, 14);
        }

        for(int j1 = k; j1 < k + 12; j1 += 4) {
            addColumnMethod.invoke(this, world, random, i, j, j1, 14);
            addColumnMethod.invoke(this, world, random, i + 52, j, j1, 14);
        }

        for(int k1 = k + 19; k1 < k + 30; k1 += 4) {
            addColumnMethod.invoke(this, world, random, i, j, k1, 14);
            addColumnMethod.invoke(this, world, random, i + 52, j, k1, 14);
        }

        setBlocks(lockedBlockID1Field.getInt(this), lockedBlockID2Field.getInt(this), 20);
        setMetadata(1, 1);
        addHollowBox(world, random, i + 4, j - 1, k + 4, 47, 16, 22);
        addPlaneX(world, random, i + 11, j, k + 5, 15, 20);
        addPlaneX(world, random, i + 18, j, k + 5, 15, 20);
        addPlaneX(world, random, i + 25, j, k + 5, 15, 20);
        addPlaneZ(world, random, i + 5, j, k + 11, 20, 15);
        addPlaneZ(world, random, i + 5, j, k + 18, 20, 15);
        setBlocks(lockedBlockID1Field.getInt(this), AetherBlocks.Trap.blockID, 15);
        setMetadata(1, 1);
        addPlaneY(world, random, i + 5, j + 4, k + 5, 20, 20);
        addPlaneY(world, random, i + 5, j + 9, k + 5, 20, 20);
        for(int l1 = j; l1 < j + 2; l1++)
            for(int l2 = k + 14; l2 < k + 16; l2++)
                world.setBlock(i + 4, l1, l2, 0);
        setBlocks(0, 0, 1);
        addSolidBox(world, random, i, j - 4, k + 14, 1, 4, 2);
        addSolidBox(world, random, i + 1, j - 3, k + 14, 1, 3, 2);
        addSolidBox(world, random, i + 2, j - 2, k + 14, 1, 2, 2);
        addSolidBox(world, random, i + 3, j - 1, k + 14, 1, 1, 2);
        setBlocks(lockedBlockID1Field.getInt(this), lockedBlockID2Field.getInt(this), 20);
        setMetadata(1, 1);
        for(int i2 = 0; i2 < 7; i2++)
            addPlaneY(world, random, i - 1, j + 15 + i2, (k - 1) + 2 * i2, 57, 32 - 4 * i2);

        int j2 = random.nextInt(3);
        addStaircaseMethod.invoke(this, world, random, i + 19, j, k + 5 + j2 * 7, 10);
        ((int[][][]) roomsField.get(this))[2][0][j2] = 2;
        ((int[][][]) roomsField.get(this))[2][1][j2] = 2;
        ((int[][][]) roomsField.get(this))[2][2][j2] = 1;
        int i3 = i + 25;
        for(int k3 = j; k3 < j + 2; k3++)
            for(int i5 = k + 7 + 7 * j2; i5 < k + 9 + 7 * j2; i5++)
                world.setBlock(i3, k3, i5, 0);
        j2 = random.nextInt(3);
        addStaircaseMethod.invoke(this, world, random, i + 12, j, k + 5 + j2 * 7, 5);
        ((int[][][]) roomsField.get(this))[1][0][j2] = 1;
        ((int[][][]) roomsField.get(this))[1][1][j2] = 1;
        j2 = random.nextInt(3);
        addStaircaseMethod.invoke(this, world, random, i + 5, j + 5, k + 5 + j2 * 7, 5);
        ((int[][][]) roomsField.get(this))[0][0][j2] = 1;
        ((int[][][]) roomsField.get(this))[0][1][j2] = 1;
        for(int j7 = 0; j7 < 3; j7++)
            for(int l7 = 0; l7 < 3; l7++)
                for(int i8 = 0; i8 < 3; i8++) {
                    int k8 = ((int[][][]) roomsField.get(this))[j7][l7][i8];
                    if(j7 + 1 < 3 && (k8 == 0 || k8 == 1 || random.nextBoolean()) && k8 != 2) {
                        int l8 = ((int[][][]) roomsField.get(this))[j7 + 1][l7][i8];
                        if(l8 != 2 && (l8 != 1 || k8 != 1)) {
                        	((int[][][]) roomsField.get(this))[j7][l7][i8] = 3;
                            k8 = 3;
                            i3 = i + 11 + 7 * j7;
                            for(int l3 = j + 5 * l7; l3 < j + 2 + 5 * l7; l3++)
                                for(int j5 = k + 7 + 7 * i8; j5 < k + 9 + 7 * i8; j5++)
                                    world.setBlock(i3, l3, j5, 0);

                        }
                    }
                    if(j7 - 1 > 0 && (k8 == 0 || k8 == 1 || random.nextBoolean()) && k8 != 2) {
                        int i9 = ((int[][][]) roomsField.get(this))[j7 - 1][l7][i8];
                        if(i9 != 2 && (i9 != 1 || k8 != 1)) {
                        	((int[][][]) roomsField.get(this))[j7][l7][i8] = 4;
                            k8 = 4;
                            i3 = i + 4 + 7 * j7;
                            for(int i4 = j + 5 * l7; i4 < j + 2 + 5 * l7; i4++)
                                for(int k5 = k + 7 + 7 * i8; k5 < k + 9 + 7 * i8; k5++)
                                    world.setBlock(i3, i4, k5, 0);

                        }
                    }
                    if(i8 + 1 < 3 && (k8 == 0 || k8 == 1 || random.nextBoolean()) && k8 != 2) {
                        int j9 = ((int[][][]) roomsField.get(this))[j7][l7][i8 + 1];
                        if(j9 != 2 && (j9 != 1 || k8 != 1)) {
                        	((int[][][]) roomsField.get(this))[j7][l7][i8] = 5;
                            k8 = 5;
                            int l5 = k + 11 + 7 * i8;
                            for(int j4 = j + 5 * l7; j4 < j + 2 + 5 * l7; j4++)
                                for(i3 = i + 7 + 7 * j7; i3 < i + 9 + 7 * j7; i3++)
                                    world.setBlock(i3, j4, l5, 0);

                        }
                    }
                    if(i8 - 1 > 0 && (k8 == 0 || k8 == 1 || random.nextBoolean()) && k8 != 2) {
                        int k9 = ((int[][][]) roomsField.get(this))[j7][l7][i8 - 1];
                        if(k9 != 2 && (k9 != 1 || k8 != 1)) {
                        	((int[][][]) roomsField.get(this))[j7][l7][i8] = 6;
                            k8 = 6;
                            int i6 = k + 4 + 7 * i8;
                            for(int k4 = j + 5 * l7; k4 < j + 2 + 5 * l7; k4++)
                                for(i3 = i + 7 + 7 * j7; i3 < i + 9 + 7 * j7; i3++)
                                    world.setBlock(i3, k4, i6, 0);
                        }
                    }
                    int l9 = random.nextInt(3);
                    if(k8 >= 3) {
                        switch(l9) {
                        default:
                            break;

                        case 0:
                            world.setBlockAndMetadata(i + 7 + j7 * 7, (j - 1) + l7 * 5, k + 7 + i8 * 7, AetherBlocks.Trap.blockID, 1);
                            break;

                        case 1:
                            addPlaneY(world, random, i + 7 + 7 * j7, j + 5 * l7, k + 7 + 7 * i8, 2, 2);
                            int i10 = i + 7 + 7 * j7 + random.nextInt(2);
                            int k10 = k + 7 + 7 * i8 + random.nextInt(2);
                            if(world.getBlockId(i10, j + 5 * l7 + 1, k10) != 0)
                                break;
                            world.setBlockWithNotify(i10, j + 5 * l7 + 1, k10, Block.chest.blockID);
                            TileEntityChest tileentitychest1 = (TileEntityChest)world.getBlockTileEntity(i10, j + 5 * l7 + 1, k10);
                            for(int j10 = 0; j10 < LootType.SILVER_NORMAL.getGuaranteedLootPerChest() + random.nextInt(LootType.SILVER_NORMAL.getMaximumLootPerChest() - LootType.SILVER_NORMAL.getGuaranteedLootPerChest()); j10++) {
                                ItemStack itemstack1 = getNormalLoot(random);
                                tileentitychest1.setInventorySlotContents(random.nextInt(tileentitychest1.getSizeInventory()), itemstack1);
                            }

                            break;

                        case 2:
                            addPlaneY(world, random, i + 7 + 7 * j7, j + 5 * l7, k + 7 + 7 * i8, 2, 2);
                            world.setBlockWithNotify(i + 7 + 7 * j7 + random.nextInt(2), j + 5 * l7 + 1, k + 7 + 7 * i8 + random.nextInt(2), AetherBlocks.ChestMimic.blockID);
                            world.setBlockWithNotify(i + 7 + 7 * j7 + random.nextInt(2), j + 5 * l7 + 1, k + 7 + 7 * i8 + random.nextInt(2), AetherBlocks.ChestMimic.blockID);
                            break;
                        }
                    }
                }
        for(i3 = 0; i3 < 24; i3++)
            for(int j6 = 0; j6 < 20; j6++) {
                int k7 = (int)(Math.sqrt(i3 * i3 + (j6 - 7) * (j6 - 7)) + Math.sqrt(i3 * i3 + (j6 - 12) * (j6 - 12)));
                if(k7 == 21) {
                    world.setBlockAndMetadata(i + 26 + i3, j, k + 5 + j6, lockedBlockID2Field.getInt(this), 1);
                    continue;
                }
                if(k7 > 21)
                    world.setBlockAndMetadata(i + 26 + i3, j, k + 5 + j6, lockedBlockID1Field.getInt(this), 1);
            }
        setBlocks(lockedBlockID1Field.getInt(this), lockedBlockID1Field.getInt(this), 1);
        setMetadata(1, 1);
        addPlaneY(world, random, i + 44, j + 1, k + 11, 6, 8);
        addSolidBox(world, random, i + 46, j + 2, k + 13, 4, 2, 4);
        addLineX(world, random, i + 46, j + 4, k + 13, 4);
        addLineX(world, random, i + 46, j + 4, k + 16, 4);
        addPlaneX(world, random, i + 49, j + 4, k + 13, 4, 4);
        setBlocks(Block.cloth.blockID, Block.cloth.blockID, 1);
        setMetadata(11, 11);
        addPlaneY(world, random, i + 47, j + 3, k + 14, 2, 2);
        for(i3 = 0; i3 < 2; i3++)
            for(int k6 = 0; k6 < 2; k6++)
                world.setBlock(i + 44 + i3 * 5, j + 2, k + 11 + k6 * 7, AetherBlocks.AmbrosiumTorch.blockID);
        setBlocks(Block.waterStill.blockID, Block.waterStill.blockID, 1);
        setMetadata(0, 0);
        addPlaneY(world, random, i + 35, j + 1, k + 5, 6, 3);
        addPlaneY(world, random, i + 35, j + 1, k + 22, 6, 3);
        setBlocks(lockedBlockID1Field.getInt(this), lockedBlockID1Field.getInt(this), 1);
        setMetadata(1, 1);
        addLineZ(world, random, i + 34, j + 1, k + 5, 2);
        addLineZ(world, random, i + 41, j + 1, k + 5, 2);
        addLineX(world, random, i + 36, j + 1, k + 8, 4);
        world.setBlockAndMetadata(i + 35, j + 1, k + 7, lockedBlockID1Field.getInt(this), 1);
        world.setBlockAndMetadata(i + 40, j + 1, k + 7, lockedBlockID1Field.getInt(this), 1);
        addLineZ(world, random, i + 34, j + 1, k + 23, 2);
        addLineZ(world, random, i + 41, j + 1, k + 23, 2);
        addLineX(world, random, i + 36, j + 1, k + 21, 4);
        world.setBlockAndMetadata(i + 35, j + 1, k + 22, lockedBlockID1Field.getInt(this), 1);
        world.setBlockAndMetadata(i + 40, j + 1, k + 22, lockedBlockID1Field.getInt(this), 1);
        for(i3 = i + 36; i3 < i + 40; i3 += 3)
            for(int l6 = k + 8; l6 < k + 22; l6 += 13)
                world.setBlock(i3, j + 2, l6, AetherBlocks.AmbrosiumTorch.blockID);
        addChandelierMethod.invoke(this, world, i + 28, j, k + 10, 8);
        addChandelierMethod.invoke(this, world, i + 43, j, k + 10, 8);
        addChandelierMethod.invoke(this, world, i + 43, j, k + 19, 8);
        addChandelierMethod.invoke(this, world, i + 28, j, k + 19, 8);
        addSaplingMethod.invoke(this, world, random, i + 45, j + 1, k + 6);
        addSaplingMethod.invoke(this, world, random, i + 45, j + 1, k + 21);
        EntityValkyrie entityvalkyrie = new EntityValkyrie(world, (double)i + 40D, (double)j + 1.5D, (double)k + 15D, true);
        entityvalkyrie.setPosition(i + 40, j + 2, k + 15);
        entityvalkyrie.setDungeon(i + 26, j, k + 5);
        world.entityJoinedWorld(entityvalkyrie);
        setBlocks(lockedBlockID1Field.getInt(this), lockedBlockID1Field.getInt(this), 1);
        setMetadata(1, 1);
        addHollowBox(world, random, i + 41, j - 2, k + 13, 4, 4, 4);
        i3 = i + 42 + random.nextInt(2);
        int i7 = k + 14 + random.nextInt(2);
        world.setBlock(i3, j - 1, i7, AetherBlocks.TreasureChest.blockID);
        TileEntityChest tileentitychest = (TileEntityChest)world.getBlockTileEntity(i3, j - 1, i7);
        for(int j8 = 0; j8 < LootType.SILVER.getGuaranteedLootPerChest() + random.nextInt(LootType.SILVER.getMaximumLootPerChest() - LootType.SILVER.getGuaranteedLootPerChest()); j8++) {
            ItemStack itemstack = getSilverLoot(random);
            tileentitychest.setInventorySlotContents(random.nextInt(tileentitychest.getSizeInventory()), itemstack);
        }
        world.setBlockMetadata(i3, j - 1, i7, 2);
        return true;
    }
	
	public ItemStack getNormalLoot(Random random) {
		try {
			return getNormalLootThrows(random);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
	
	public ItemStack getNormalLootThrows(Random random) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return DungeonLoot.EVENT.getInvoker().getLoot((ItemStack) getNormalLootMethod.invoke(this, random), LootType.SILVER_NORMAL, random);
	}
	
	public ItemStack getSilverLoot(Random random) {
		try {
			return getSilverLootThrows(random);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
	
	public ItemStack getSilverLootThrows(Random random) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return DungeonLoot.EVENT.getInvoker().getLoot((ItemStack) getSilverLootMethod.invoke(this, random), LootType.SILVER, random);
	}
	
	private static final Field baseID2Field;
	private static final Field baseID1Field;
	private static final Field baseMeta2Field;
	private static final Field baseMeta1Field;
	private static final Method addColumnMethod;
	private static final Field lockedBlockID1Field;
	private static final Field lockedBlockID2Field;
	private static final Method addStaircaseMethod;
	private static final Field roomsField;
	private static final Method addChandelierMethod;
	private static final Method addSaplingMethod;
	private static final Method getNormalLootMethod;
	private static final Method getSilverLootMethod;
	static {
		try {
			baseID2Field = AetherGenDungeonSilver.class.getDeclaredField("baseID2");
			baseID2Field.setAccessible(true);
			baseID1Field = AetherGenDungeonSilver.class.getDeclaredField("baseID1");
			baseID1Field.setAccessible(true);
			baseMeta2Field = AetherGenDungeonSilver.class.getDeclaredField("baseMeta2");
			baseMeta2Field.setAccessible(true);
			baseMeta1Field = AetherGenDungeonSilver.class.getDeclaredField("baseMeta1");
			baseMeta1Field.setAccessible(true);
			addColumnMethod = AetherGenDungeonSilver.class.getDeclaredMethod("addColumn", World.class, Random.class, int.class, int.class, int.class, int.class);
			addColumnMethod.setAccessible(true);
			lockedBlockID1Field = AetherGenDungeonSilver.class.getDeclaredField("lockedBlockID1");
			lockedBlockID1Field.setAccessible(true);
			lockedBlockID2Field = AetherGenDungeonSilver.class.getDeclaredField("lockedBlockID2");
			lockedBlockID2Field.setAccessible(true);
			addStaircaseMethod = AetherGenDungeonSilver.class.getDeclaredMethod("addStaircase", World.class, Random.class, int.class, int.class, int.class, int.class);
			addStaircaseMethod.setAccessible(true);
			roomsField = AetherGenDungeonSilver.class.getDeclaredField("rooms");
			roomsField.setAccessible(true);
			addChandelierMethod = AetherGenDungeonSilver.class.getDeclaredMethod("addChandelier", World.class, int.class, int.class, int.class, int.class);
			addChandelierMethod.setAccessible(true);
			addSaplingMethod = AetherGenDungeonSilver.class.getDeclaredMethod("addSapling", World.class, Random.class, int.class, int.class, int.class);
			addSaplingMethod.setAccessible(true);
			getNormalLootMethod = AetherGenDungeonSilver.class.getDeclaredMethod("getNormalLoot", Random.class);
			getNormalLootMethod.setAccessible(true);
			getSilverLootMethod = AetherGenDungeonSilver.class.getDeclaredMethod("getSilverLoot", Random.class);
			getSilverLootMethod.setAccessible(true);
		} catch (NoSuchFieldException | SecurityException | NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}
}
