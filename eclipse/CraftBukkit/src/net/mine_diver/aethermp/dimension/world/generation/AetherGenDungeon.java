package net.mine_diver.aethermp.dimension.world.generation;

import java.util.Random;

import net.mine_diver.aethermp.api.event.dimension.world.generation.dungeon.DungeonLoot;
import net.mine_diver.aethermp.api.util.LootType;
import net.mine_diver.aethermp.blocks.BlockManager;
import net.mine_diver.aethermp.items.ItemManager;
import net.minecraft.server.Block;
import net.minecraft.server.ItemStack;
import net.minecraft.server.TileEntityChest;
import net.minecraft.server.World;
import net.minecraft.server.WorldGenerator;

public class AetherGenDungeon extends WorldGenerator {

    @SuppressWarnings("unused")
	private int floors() {
        return Block.DOUBLE_STEP.id;
    }

    private int walls() {
        return BlockManager.LockedDungeonStone.id;
    }

    private int ceilings() {
        return BlockManager.LockedLightDungeonStone.id;
    }

    private int torches() {
        return 0;
    }

    private int doors() {
        return 0;
    }
    
    @Override
    public boolean a(World world, Random random, int i, int j, int k) {
        return generate(world, random, i, j, k, 24);
    }
    
    public boolean generate(World world, Random random, int i, int j, int k, int l) {
        org.bukkit.World worldBukkit = world.getWorld();
        l = (int)Math.floor((double)l * 0.80000000000000004D);
        int i1 = (int)Math.sqrt((l * l) / 2);
        for(int j1 = 4; j1 > -5; j1--) {
            int l1 = i1;
            if(j1 >= 3 || j1 <= -3)
                l1--;
            if(j1 == 4 || j1 == -4)
                l1--;
            for(int j2 = -l1; j2 <= l1; j2++) {
                for(int k2 = -l1; k2 <= l1; k2++) {
                    if(j1 == 4) {
                        setBlock(world, random, i + j2, j + j1, k + k2, walls(), 2, ceilings(), 2, 10);
                        continue;
                    }
                    if(j1 > -4) {
                        if(j2 == l1 || -j2 == l1 || k2 == l1 || -k2 == l1) {
                            setBlock(world, random, i + j2, j + j1, k + k2, walls(), 2, ceilings(), 2, 10);
                            continue;
                        }
                        worldBukkit.getBlockAt(i + j2, j + j1, k + k2).setTypeId(0, false);
                        if(j1 == -2 && (j2 == l1 - 1 || -j2 == l1 - 1 || k2 == l1 - 1 || -k2 == l1 - 1) && (j2 % 3 == 0 || k2 % 3 == 0))
                            worldBukkit.getBlockAt(i + j2, j + j1 + 2, k + k2).setTypeId(torches(), false);
                        continue;
                    }
                    setBlock(world, random, i + j2, j + j1, k + k2, walls(), 2, ceilings(), 2, 10);
                    if((j2 == l1 - 2 || -j2 == l1 - 2) && (k2 == l1 - 2 || -k2 == l1 - 2)) {
                        worldBukkit.getBlockAt(i + j2, j + j1 + 1, k + k2).setTypeId(Block.NETHERRACK.id, false);
                        worldBukkit.getBlockAt(i + j2, j + j1 + 2, k + k2).setTypeId(Block.FIRE.id, false);
                    }
                }
            }
        }

        int k1 = random.nextInt(4);
        int i2 = i1;
        do {
            if(i2 >= i1 + 32)
                break;
            boolean flag = false;
            for(int l2 = -3; l2 < 2; l2++) {
                for(int i3 = -3; i3 < 4; i3++) {
                    int j3;
                    int k3;
                    if(k1 / 2 == 0) {
                        j3 = i2;
                        k3 = i3;
                    } else {
                        j3 = i3;
                        k3 = i2;
                    }
                    if(k1 % 2 == 0) {
                        j3 = -j3;
                        k3 = -k3;
                    }
                    if(!BlockManager.isGood(world.getTypeId(i + j3, j + l2, k + k3), world.getData(i + j3, j + l2, k + k3))) {
                        flag = true;
                        if(l2 == -3)
                            setBlock(world, random, i + j3, j + l2, k + k3, BlockManager.Holystone.id, 0, BlockManager.Holystone.id, 2, 5);
                        else if(l2 < 1) {
                            if(i2 == i1) {
                                if(i3 < 2 && i3 > -2 && l2 < 0)
                                    worldBukkit.getBlockAt(i + j3, j + l2, k + k3).setTypeId(doors(), false);
                                else
                                    setBlock(world, random, i + j3, j + l2, k + k3, walls(), 2, ceilings(), 2, 10);
                            } else if(i3 == 3 || i3 == -3)
                                setBlock(world, random, i + j3, j + l2, k + k3, BlockManager.Holystone.id, 0, BlockManager.Holystone.id, 2, 5);
                            else {
                                worldBukkit.getBlockAt(i + j3, j + l2, k + k3).setTypeId(0, false);
                                if(l2 == -1 && (i3 == 2 || i3 == -2) && (i2 - i1 - 2) % 3 == 0)
                                    worldBukkit.getBlockAt(i + j3, j + l2, k + k3).setTypeId(torches(), false);
                            }
                        } else if(i2 == i1)
                            setBlock(world, random, i + j3, j + l2, k + k3, walls(), 2, ceilings(), 2, 5);
                        else
                            setBlock(world, random, i + j3, j + l2, k + k3, BlockManager.Holystone.id, 0, BlockManager.Holystone.id, 2, 5);
                    }
                    j3 = -j3;
                    k3 = -k3;
                    if(i2 >= i1 + 6)
                        continue;
                    if(l2 == -3) {
                        setBlock(world, random, i + j3, j + l2, k + k3, walls(), 2, ceilings(), 2, 10);
                        continue;
                    }
                    if(l2 < 1) {
                        if(i2 == i1) {
                            if(i3 < 2 && i3 > -2 && l2 < 0)
                                setBlock(world, random, i + j3, j + l2, k + k3, walls(), 2, ceilings(), 2, 10);
                            else
                                setBlock(world, random, i + j3, j + l2, k + k3, walls(), 2, ceilings(), 2, 10);
                            continue;
                        }
                        if(i2 == i1 + 5) {
                            setBlock(world, random, i + j3, j + l2, k + k3, walls(), 2, ceilings(), 2, 10);
                            continue;
                        }
                        if(i2 == i1 + 4 && i3 == 0 && l2 == -2) {
                            worldBukkit.getBlockAt(i + j3, j + l2, k + k3).setTypeIdAndData(BlockManager.TreasureChest.id, (byte)4, false);
                            TileEntityChest tileentitychest = (TileEntityChest)world.getTileEntity(i + j3, j + l2, k + k3);
                            for(int l3 = 0; l3 < 3 + random.nextInt(3); l3++) {
                                ItemStack itemstack = getGoldLoot(random);
                                tileentitychest.setItem(random.nextInt(tileentitychest.getSize()), itemstack);
                            }

                            continue;
                        }
                        if(i3 == 3 || i3 == -3) {
                            setBlock(world, random, i + j3, j + l2, k + k3, walls(), 2, ceilings(), 2, 10);
                            continue;
                        }
                        worldBukkit.getBlockAt(i + j3, j + l2, k + k3).setTypeId(0, false);
                        if(l2 == -1 && (i3 == 2 || i3 == -2) && (i2 - i1 - 2) % 3 == 0)
                            worldBukkit.getBlockAt(i + j3, j + l2, k + k3).setTypeId(torches(), false);
                    } else
                        setBlock(world, random, i + j3, j + l2, k + k3, walls(), 2, ceilings(), 2, 10);
                }

            }

            if(!flag)
                break;
            i2++;
        } while(true);
        /*EntityFireMonster entityfiremonster = new EntityFireMonster(world, i, j - 1, k, i1, k1);
        world.entityJoinedWorld(entityfiremonster);*/
        return true;
    }

    private void setBlock(World world, Random random, int i, int j, int k, int l, int i1, int j1, int k1, int l1) {
        org.bukkit.World worldBukkit = world.getWorld();
        org.bukkit.block.Block block = worldBukkit.getBlockAt(i, j, k);
        if(random.nextInt(l1) == 0)
            block.setTypeIdAndData(j1, (byte)k1, false);
        else
            block.setTypeIdAndData(l, (byte)i1, false);
    }
    
    public ItemStack getGoldLoot(Random random) {
    	return DungeonLoot.EVENT.getInvoker().getLoot(getDefaultGoldLoot(random), LootType.GOLD, random);
    }

    public ItemStack getDefaultGoldLoot(Random random) {
        int i = random.nextInt(8);
        switch(i) {
        default:
            break;

        case 0:
            return new ItemStack(ItemManager.IronBubble);

        case 1:
            return new ItemStack(ItemManager.VampireBlade);

        case 2:
            return new ItemStack(ItemManager.PigSlayer);

        case 3:
            if(random.nextBoolean())
                return new ItemStack(ItemManager.PhoenixHelm);
            if(random.nextBoolean())
                return new ItemStack(ItemManager.PhoenixLegs);
            if(random.nextBoolean())
                return new ItemStack(ItemManager.PhoenixBody);
            break;

        case 4:
            if(random.nextBoolean())
                return new ItemStack(ItemManager.PhoenixBoots);
            else
                return new ItemStack(ItemManager.PhoenixGlove);

        case 5:
            return new ItemStack(ItemManager.LifeShard);

        case 6:
            if(random.nextBoolean())
                return new ItemStack(ItemManager.GravititeHelmet);
            if(random.nextBoolean())
                return new ItemStack(ItemManager.GravititePlatelegs);
            if(random.nextBoolean())
                return new ItemStack(ItemManager.GravititeBodyplate);
            break;

        case 7:
            if(random.nextBoolean())
                return new ItemStack(ItemManager.GravititeBoots);
            else
                return new ItemStack(ItemManager.GravititeGlove);
        }
        return new ItemStack(ItemManager.ObsidianBody);
    }

    public int xoff;
    public int yoff;
    public int zoff;
    public int rad;
    public int truey;
}
