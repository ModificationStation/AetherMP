package net.mine_diver.aetherapi.impl.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

import net.mine_diver.aetherapi.api.event.dimension.world.generation.dungeon.DungeonLoot;
import net.mine_diver.aetherapi.api.util.LootType;
import net.minecraft.src.AetherBlocks;
import net.minecraft.src.AetherGenDungeon;
import net.minecraft.src.Block;
import net.minecraft.src.EntityFireMonster;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntityChest;
import net.minecraft.src.World;

public class AetherGenDungeonProxy extends AetherGenDungeon {
	
	@Override
	public boolean generate(World world, Random random, int x, int y, int z, int r) {
		try {
			return generateThrows(world, random, x, y, z, r);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
	
	public boolean generateThrows(World world, Random random, int x, int y, int z, int r) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        r = (int)Math.floor((double)r * 0.80000000000000004D);
        int wid = (int)Math.sqrt((r * r) / 2);
        for(int j = 4; j > -5; j--) {
            int a = wid;
            if(j >= 3 || j <= -3)
                a--;
            if(j == 4 || j == -4)
                a--;
            for(int i = -a; i <= a; i++)
                for(int k = -a; k <= a; k++) {
                    if(j == 4) {
                        setBlockMethod.invoke(this, world, random, x + i, y + j, z + k, wallsMethod.invoke(this), 2, ceilingsMethod.invoke(this), 2, 10);
                        continue;
                    }
                    if(j > -4) {
                        if(i == a || -i == a || k == a || -k == a) {
                            setBlockMethod.invoke(this, world, random, x + i, y + j, z + k, wallsMethod.invoke(this), 2, ceilingsMethod.invoke(this), 2, 10);
                            continue;
                        }
                        world.setBlock(x + i, y + j, z + k, 0);
                        if(j == -2 && (i == a - 1 || -i == a - 1 || k == a - 1 || -k == a - 1) && (i % 3 == 0 || k % 3 == 0))
                            world.setBlock(x + i, y + j + 2, z + k, (int) torchesMethod.invoke(this));
                        continue;
                    }
                    setBlockMethod.invoke(this, world, random, x + i, y + j, z + k, wallsMethod.invoke(this), 2, ceilingsMethod.invoke(this), 2, 10);
                    if((i == a - 2 || -i == a - 2) && (k == a - 2 || -k == a - 2)) {
                        world.setBlock(x + i, y + j + 1, z + k, Block.netherrack.blockID);
                        world.setBlock(x + i, y + j + 2, z + k, Block.fire.blockID);
                    }
                }
        }

        int direction = random.nextInt(4);
        int i = wid;
        do {
            if(i >= wid + 32)
                break;
            boolean flag = false;
            for(int j = -3; j < 2; j++)
                for(int k = -3; k < 4; k++) {
                    int a;
                    int b;
                    if(direction / 2 == 0) {
                        a = i;
                        b = k;
                    } else {
                        a = k;
                        b = i;
                    }
                    if(direction % 2 == 0) {
                        a = -a;
                        b = -b;
                    }
                    if(!AetherBlocks.isGood(world.getBlockId(x + a, y + j, z + b), world.getBlockMetadata(x + a, y + j, z + b))) {
                        flag = true;
                        if(j == -3)
                            setBlockMethod.invoke(this, world, random, x + a, y + j, z + b, AetherBlocks.Holystone.blockID, 0, AetherBlocks.Holystone.blockID, 2, 5);
                        else if(j < 1) {
                            if(i == wid) {
                                if(k < 2 && k > -2 && j < 0)
                                    world.setBlock(x + a, y + j, z + b, (int) doorsMethod.invoke(this));
                                else
                                    setBlockMethod.invoke(this, world, random, x + a, y + j, z + b, wallsMethod.invoke(this), 2, ceilingsMethod.invoke(this), 2, 10);
                            } else if(k == 3 || k == -3)
                                setBlockMethod.invoke(this, world, random, x + a, y + j, z + b, AetherBlocks.Holystone.blockID, 0, AetherBlocks.Holystone.blockID, 2, 5);
                            else {
                                world.setBlock(x + a, y + j, z + b, 0);
                                if(j == -1 && (k == 2 || k == -2) && (i - wid - 2) % 3 == 0)
                                    world.setBlock(x + a, y + j, z + b, (int) torchesMethod.invoke(this));
                            }
                        } else if(i == wid)
                            setBlockMethod.invoke(this, world, random, x + a, y + j, z + b, wallsMethod.invoke(this), 2, ceilingsMethod.invoke(this), 2, 5);
                        else
                            setBlockMethod.invoke(this, world, random, x + a, y + j, z + b, AetherBlocks.Holystone.blockID, 0, AetherBlocks.Holystone.blockID, 2, 5);
                    }
                    a = -a;
                    b = -b;
                    if(i >= wid + 6)
                        continue;
                    if(j == -3) {
                        setBlockMethod.invoke(this, world, random, x + a, y + j, z + b, wallsMethod.invoke(this), 2, ceilingsMethod.invoke(this), 2, 10);
                        continue;
                    }
                    if(j < 1) {
                        if(i == wid) {
                            if(k < 2 && k > -2 && j < 0)
                                setBlockMethod.invoke(this, world, random, x + a, y + j, z + b, wallsMethod.invoke(this), 2, ceilingsMethod.invoke(this), 2, 10);
                            else
                                setBlockMethod.invoke(this, world, random, x + a, y + j, z + b, wallsMethod.invoke(this), 2, ceilingsMethod.invoke(this), 2, 10);
                            continue;
                        }
                        if(i == wid + 5) {
                            setBlockMethod.invoke(this, world, random, x + a, y + j, z + b, wallsMethod.invoke(this), 2, ceilingsMethod.invoke(this), 2, 10);
                            continue;
                        }
                        if(i == wid + 4 && k == 0 && j == -2) {
                            world.setBlockAndMetadata(x + a, y + j, z + b, AetherBlocks.TreasureChest.blockID, 4);
                            TileEntityChest chest = (TileEntityChest)world.getBlockTileEntity(x + a, y + j, z + b);
                            for(int p = 0; p < guaranteedAmountOfGoldLoot + random.nextInt(maximumAmountOfGoldLoot - guaranteedAmountOfGoldLoot); p++) {
                                ItemStack item = getGoldLoot(random);
                                chest.setInventorySlotContents(random.nextInt(chest.getSizeInventory()), item);
                            }

                            continue;
                        }
                        if(k == 3 || k == -3) {
                            setBlockMethod.invoke(this, world, random, x + a, y + j, z + b, wallsMethod.invoke(this), 2, ceilingsMethod.invoke(this), 2, 10);
                            continue;
                        }
                        world.setBlock(x + a, y + j, z + b, 0);
                        if(j == -1 && (k == 2 || k == -2) && (i - wid - 2) % 3 == 0)
                            world.setBlock(x + a, y + j, z + b, (int) torchesMethod.invoke(this));
                    } else
                        setBlockMethod.invoke(this, world, random, x + a, y + j, z + b, wallsMethod.invoke(this), 2, ceilingsMethod.invoke(this), 2, 10);
                }

            if(!flag)
                break;
            i++;
        } while(true);
        EntityFireMonster boss = new EntityFireMonster(world, x, y - 1, z, wid, direction);
        world.entityJoinedWorld(boss);
        return true;
    }
	
	public ItemStack getGoldLoot(Random random) {
		try {
			return getGoldLootThrows(random);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
	
	public ItemStack getGoldLootThrows(Random random) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return DungeonLoot.EVENT.getInvoker().getLoot((ItemStack) getGoldLootMethod.invoke(this, random), LootType.GOLD, random);
	}
	
	public static int guaranteedAmountOfGoldLoot = 3;
	public static int maximumAmountOfGoldLoot = 6;
	
	private static final Method setBlockMethod;
	private static final Method wallsMethod;
	private static final Method ceilingsMethod;
	private static final Method torchesMethod;
	private static final Method doorsMethod;
	private static final Method getGoldLootMethod;
	static {
		try {
			setBlockMethod = AetherGenDungeon.class.getDeclaredMethod("setBlock", World.class, Random.class, int.class, int.class, int.class, int.class, int.class, int.class, int.class, int.class);
			setBlockMethod.setAccessible(true);
			wallsMethod = AetherGenDungeon.class.getDeclaredMethod("walls");
			wallsMethod.setAccessible(true);
			ceilingsMethod = AetherGenDungeon.class.getDeclaredMethod("ceilings");
			ceilingsMethod.setAccessible(true);
			torchesMethod = AetherGenDungeon.class.getDeclaredMethod("torches");
			torchesMethod.setAccessible(true);
			doorsMethod = AetherGenDungeon.class.getDeclaredMethod("doors");
			doorsMethod.setAccessible(true);
			getGoldLootMethod = AetherGenDungeon.class.getDeclaredMethod("getGoldLoot", Random.class);
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}
	}
}
