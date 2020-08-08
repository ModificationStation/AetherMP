package net.mine_diver.aetherapi.proxy;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

import net.mine_diver.aetherapi.event.dimension.world.generation.dungeon.DungeonLoot;
import net.mine_diver.aetherapi.util.LootType;
import net.minecraft.src.AetherBlocks;
import net.minecraft.src.AetherGenDungeonBronze;
import net.minecraft.src.Block;
import net.minecraft.src.EntitySlider;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntityChest;
import net.minecraft.src.World;

public class AetherGenDungeonBronzeProxy extends AetherGenDungeonBronze {

	public AetherGenDungeonBronzeProxy(int i, int j, int k, int l, int m, int m1, int o, int o1, int p, boolean flag) {
		super(i, j, k, l, m, m1, o, o1, p, flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		try {
			return generateThrows(world, random, i, j, k);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
	public boolean generateThrows(World world, Random random, int i, int j, int k) throws IllegalArgumentException, IllegalAccessException {
        replaceAir = true;
        replaceSolid = true;
        nField.set(this, 0);
        if(!isBoxSolid(world, i, j, k, 16, 12, 16) || !isBoxSolid(world, i + 20, j, k + 2, 12, 12, 12))
            return false;
        setBlocks(lockedBlockID1Field.getInt(this), lockedBlockID2Field.getInt(this), 20);
        addHollowBox(world, random, i, j, k, 16, 12, 16);
        addHollowBox(world, random, i + 6, j - 2, k + 6, 4, 4, 4);
        EntitySlider slider = new EntitySlider(world);
        slider.setPosition(i + 8, j + 2, k + 8);
        slider.setDungeon(i, j, k);
        world.entityJoinedWorld(slider);
        int x = i + 7 + random.nextInt(2);
        int y = j - 1;
        int z = k + 7 + random.nextInt(2);
        world.setBlockAndMetadataWithNotify(x, y, z, AetherBlocks.TreasureChest.blockID, 0);
        TileEntityChest chest = (TileEntityChest)world.getBlockTileEntity(x, y, z);
        for(int p = 0; p < guaranteedAmountOfBronzeLoot + random.nextInt(maximumAmountOfBronzeLoot - guaranteedAmountOfBronzeLoot); p++) {
            ItemStack item = getBronzeLoot(random);
            chest.setInventorySlotContents(random.nextInt(chest.getSizeInventory()), item);
        }

        x = i + 20;
        y = j;
        z = k + 2;
        if(!isBoxSolid(world, x, y, z, 12, 12, 12))
            return true;
        setBlocks(wallBlockID1Field.getInt(this), wallBlockID2Field.getInt(this), 20);
        addHollowBox(world, random, x, y, z, 12, 12, 12);
        setBlocks(corridorBlockID2Field.getInt(this), corridorBlockID1Field.getInt(this), 5);
        setMetadata(corridorMeta2Field.getInt(this), corridorMeta1Field.getInt(this));
        addSquareTube(world, random, x - 5, y, z + 3, 6, 6, 6, 0);
        for(int p = x + 2; p < x + 10; p += 3)
            for(int q = z + 2; q < z + 10; q += 3)
                world.setBlockAndMetadata(p, j, q, AetherBlocks.Trap.blockID, 0);

        nField.set(this, nField.getInt(this) + 1);
        generateNextRoom(world, random, x, y, z);
        generateNextRoom(world, random, x, y, z);
        if(nField.getInt(this) > numRoomsField.getInt(this) || !finishedField.getBoolean(this))
            endCorridor(world, random, x, y, z);
        return true;
    }
	
	public ItemStack getBronzeLoot(Random random) {
		try {
			return getBronzeLootThrows(random);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
	
	public ItemStack getBronzeLootThrows(Random random) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return DungeonLoot.EVENT.getInvoker().getLoot((ItemStack) getBronzeLootMethod.invoke(this, random), LootType.BRONZE, random);
    }

	@Override
	public boolean generateNextRoom(World world, Random random, int i, int j, int k) {
		try {
			return generateNextRoomThrows(world, random, i, j, k);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
	public boolean generateNextRoomThrows(World world, Random random, int i, int j, int k) throws IllegalArgumentException, IllegalAccessException {
        if(nField.getInt(this) > numRoomsField.getInt(this) && !finishedField.getBoolean(this)) {
            endCorridor(world, random, i, j, k);
            return false;
        }
        int dir = random.nextInt(4);
        int x = i;
        int y = j;
        int z = k;
        if(dir == 0) {
            x += 16;
            z += 0;
        }
        if(dir == 1) {
            x += 0;
            z += 16;
        }
        if(dir == 2) {
            x -= 16;
            z += 0;
        }
        if(dir == 3) {
            x += 0;
            z -= 16;
        }
        if(!isBoxSolid(world, x, y, z, 12, 8, 12))
            return false;
        setBlocks(wallBlockID1Field.getInt(this), wallBlockID2Field.getInt(this), 20);
        setMetadata(0, 0);
        addHollowBox(world, random, x, y, z, 12, 8, 12);
        for(int p = x; p < x + 12; p++)
            for(int q = y; q < y + 8; q++)
                for(int r = z; r < z + 12; r++)
                    if(world.getBlockId(p, q, r) == wallBlockID1Field.getInt(this) && random.nextInt(100) == 0)
                        world.setBlock(p, q, r, AetherBlocks.Trap.blockID);
        for(int p = x + 2; p < x + 10; p += 7)
            for(int q = z + 2; q < z + 10; q += 7)
                world.setBlockAndMetadata(p, j, q, AetherBlocks.Trap.blockID, 0);
        addPlaneY(world, random, x + 4, y + 1, z + 4, 4, 4);
        int type = random.nextInt(2);
        int p = x + 5 + random.nextInt(2);
        int q = z + 5 + random.nextInt(2);
        switch(type) {
        default:
            break;
        case 0:
            world.setBlockWithNotify(p, y + 2, q, AetherBlocks.ChestMimic.blockID);
            break;
        case 1:
            if(world.getBlockId(p, y + 2, q) != 0)
                break;
            world.setBlockWithNotify(p, y + 2, q, Block.chest.blockID);
            TileEntityChest chest = (TileEntityChest)world.getBlockTileEntity(p, y + 2, q);
            for(p = 0; p < guaranteedAmountOfNormalLoot + random.nextInt(maximumAmountOfNormalLoot - guaranteedAmountOfNormalLoot); p++) {
                ItemStack item = getNormalLoot(random);
                chest.setInventorySlotContents(random.nextInt(chest.getSizeInventory()), item);
            }
            break;
        }
        setBlocks(corridorBlockID2Field.getInt(this), corridorBlockID1Field.getInt(this), 5);
        setMetadata(corridorMeta2Field.getInt(this), corridorMeta1Field.getInt(this));
        switch(dir) {
        case 0:
            addSquareTube(world, random, x - 5, y, z + 3, 6, 6, 6, 0);
            break;
        case 1:
            addSquareTube(world, random, x + 3, y, z - 5, 6, 6, 6, 2);
            break;

        case 2:
            addSquareTube(world, random, x + 11, y, z + 3, 6, 6, 6, 0);
            break;

        case 3:
            addSquareTube(world, random, x + 3, y, z + 11, 6, 6, 6, 2);
            break;
        }
        nField.set(this, nField.getInt(this) + 1);
        if(!generateNextRoom(world, random, x, y, z))
            return false;
        else
            return generateNextRoom(world, random, x, y, z);
    }
	
	public ItemStack getNormalLoot(Random random) {
		try {
			return getNormalLootThrows(random);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
	
	public ItemStack getNormalLootThrows(Random random) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return DungeonLoot.EVENT.getInvoker().getLoot((ItemStack) getNormalLootMethod.invoke(this, random), LootType.BRONZE_NORMAL, random);
	}
	
	public static int guaranteedAmountOfBronzeLoot = 3;
	public static int maximumAmountOfBronzeLoot = 6;
	public static int guaranteedAmountOfNormalLoot = 3;
	public static int maximumAmountOfNormalLoot = 6;
	
	private static final Field nField;
	private static final Field lockedBlockID1Field;
	private static final Field lockedBlockID2Field;
	private static final Field wallBlockID1Field;
	private static final Field wallBlockID2Field;
	private static final Field corridorBlockID2Field;
	private static final Field corridorBlockID1Field;
	private static final Field corridorMeta2Field;
	private static final Field corridorMeta1Field;
	private static final Field numRoomsField;
	private static final Field finishedField;
	private static final Method getBronzeLootMethod;
	private static final Method getNormalLootMethod;
	static {
		try {
			nField = AetherGenDungeonBronze.class.getDeclaredField("n");
			nField.setAccessible(true);
			lockedBlockID1Field = AetherGenDungeonBronze.class.getDeclaredField("lockedBlockID1");
			lockedBlockID1Field.setAccessible(true);
			lockedBlockID2Field = AetherGenDungeonBronze.class.getDeclaredField("lockedBlockID2");
			lockedBlockID2Field.setAccessible(true);
			wallBlockID1Field = AetherGenDungeonBronze.class.getDeclaredField("wallBlockID1");
			wallBlockID1Field.setAccessible(true);
			wallBlockID2Field = AetherGenDungeonBronze.class.getDeclaredField("wallBlockID2");
			wallBlockID2Field.setAccessible(true);
			corridorBlockID2Field = AetherGenDungeonBronze.class.getDeclaredField("corridorBlockID2");
			corridorBlockID2Field.setAccessible(true);
			corridorBlockID1Field = AetherGenDungeonBronze.class.getDeclaredField("corridorBlockID1");
			corridorBlockID1Field.setAccessible(true);
			corridorMeta2Field = AetherGenDungeonBronze.class.getDeclaredField("corridorMeta2Field");
			corridorMeta2Field.setAccessible(true);
			corridorMeta1Field = AetherGenDungeonBronze.class.getDeclaredField("corridorMeta1Field");
			corridorMeta1Field.setAccessible(true);
			numRoomsField = AetherGenDungeonBronze.class.getDeclaredField("numRooms");
			numRoomsField.setAccessible(true);
			finishedField = AetherGenDungeonBronze.class.getDeclaredField("finished");
			finishedField.setAccessible(true);
			getBronzeLootMethod = AetherGenDungeonBronze.class.getDeclaredMethod("getBronzeLoot", Random.class);
			getBronzeLootMethod.setAccessible(true);
			getNormalLootMethod = AetherGenDungeonBronze.class.getDeclaredMethod("getNormalLoot", Random.class);
			getNormalLootMethod.setAccessible(true);
		} catch (NoSuchFieldException | SecurityException | NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}
}
