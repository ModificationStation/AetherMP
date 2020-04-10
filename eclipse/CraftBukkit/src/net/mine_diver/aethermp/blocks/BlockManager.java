package net.mine_diver.aethermp.blocks;

import net.minecraft.server.Block;
import net.minecraft.server.BlockHarvestPower;
import net.minecraft.server.Material;
import net.minecraft.server.ModLoader;
import net.minecraft.server.TileEntity;
import net.minecraft.server.ToolBase;
import net.minecraft.server.mod_AetherMp;

import static net.minecraft.server.mod_AetherMp.PackageAccess;

import java.util.Arrays;

import net.mine_diver.aethermp.blocks.tileentities.TileEntityEnchanter;
import net.mine_diver.aethermp.blocks.tileentities.TileEntityFreezer;
import net.mine_diver.aethermp.items.ItemBlockAercloud;
import net.mine_diver.aethermp.items.ItemBlockAetherLog;
import net.mine_diver.aethermp.items.ItemBlockHolystone;
import net.mine_diver.aethermp.items.ItemBlockQuicksoil;
import net.mine_diver.aethermp.items.ItemDungeonBlock;

public class BlockManager {
	
	public static void registerBlocks() {
		for (BlockInfo block : blocks) {
			Block blockInst = block.getBlock();
			PackageAccess.Block.setLightValue(
						PackageAccess.Block.setResistance(
								PackageAccess.Block.setHardness(blockInst,
										block.getHardness()),
								block.getResistance()),
					block.getLightValue());
			ModLoader.RegisterBlock(blockInst, block.getBlockItem());
			Class<? extends TileEntity> tileEntity = block.getTileEntity();
			if (tileEntity != null)
				ModLoader.RegisterTileEntity(tileEntity, blockInst.l().substring(5));
		}
		Pickaxe.mineBlocks.addAll(Arrays.asList(pickaxePower));
		Shovel.mineBlocks.addAll(Arrays.asList(shovelPower));
		Axe.mineBlocks.addAll(Arrays.asList(axePower));
	}
	
	public static boolean isGood(int i, int j) {
        return i == 0 || i == Aercloud.id;
    }
    
    public static final Block
    Portal = new BlockAetherPortal(mod_AetherMp.idBlockAetherPortal).a("AetherPortal"),
    Dirt = new BlockAetherDirt(mod_AetherMp.idBlockAetherDirt).a("AetherDirt"),
    Grass = new BlockAetherGrass(mod_AetherMp.idBlockAetherGrass).a("AetherGrass"),
    Quicksoil = new BlockQuicksoil(mod_AetherMp.idBlockQuicksoil).a("Quicksoil"),
    Holystone = new BlockHolystone(mod_AetherMp.idBlockHolystone).a("Holystone"),
    Icestone = new BlockIcestone(mod_AetherMp.idBlockIcestone).a("Icestone"),
    Aercloud = new BlockAercloud(mod_AetherMp.idBlockAercloud).a("Aercloud"),
    Aerogel = new BlockAerogel(mod_AetherMp.idBlockAerogel).a("Aerogel"),
    Log = new BlockAetherLog(mod_AetherMp.idBlockLog).a("AetherLog"),
    Plank = PackageAccess.Block.newBlock(mod_AetherMp.idBlockPlank, -1, Material.WOOD).a("AetherPlank"),
    SkyrootLeaves = new BlockAetherLeaves(mod_AetherMp.idBlockSkyrootLeaves).a("SkyrootLeaves"),
    GoldenOakLeaves = new BlockAetherLeaves(mod_AetherMp.idBlockGoldenOakLeaves).a("GoldenLeaves"),
    SkyrootSapling = new BlockAetherSapling(mod_AetherMp.idBlockSkyrootSapling).a("SkyrootSapling"),
    GoldenOakSapling = new BlockAetherSapling(mod_AetherMp.idBlockGoldenOakSapling).a("GoldenOakSapling"),
    AmbrosiumOre = new BlockAmbrosiumOre(mod_AetherMp.idBlockAmbrosiumOre).a("AmbrosiumOre"),
    AmbrosiumTorch = new BlockAmbrosiumTorch(mod_AetherMp.idBlockAmbrosiumTorch).a("AmbrosiumTorch"),
    ZaniteOre = new BlockZaniteOre(mod_AetherMp.idBlockZaniteOre).a("ZaniteOre"),
    GravititeOre = new BlockFloating(mod_AetherMp.idBlockGravititeOre, false).a("GravititeOre"),
    EnchantedGravitite = new BlockFloating(mod_AetherMp.idBlockEnchantedGravitite, true).a("EnchantedGravitite"),
    Enchanter = new BlockEnchanter(mod_AetherMp.idBlockEnchanter).a("Enchanter"),
    Trap = new BlockTrap(mod_AetherMp.idBlockTrap).a("Trap"),
    ChestMimic = new BlockChestMimic(mod_AetherMp.idBlockChestMimic).a("Mimic"),
    TreasureChest = new BlockTreasureChest(mod_AetherMp.idBlockTreasureChest).a("TreasureChest"),
    DungeonStone = new BlockDungeon(mod_AetherMp.idBlockDungeonStone).a("DungeonStone"),
    LightDungeonStone = new BlockDungeon(mod_AetherMp.idBlockLightDungeonStone).a("LightDungeonStone"),
    LockedDungeonStone = new BlockDungeon(mod_AetherMp.idBlockLockedDungeonStone).a("LockedDungeonStone"),
    LockedLightDungeonStone = new BlockDungeon(mod_AetherMp.idBlockLockedLightDungeonStone).a("LightLockedDungeonStone"),
    Pillar = new BlockPillar(mod_AetherMp.idBlockPillar).a("Pillar"),
    ZaniteBlock = PackageAccess.Block.newBlock(mod_AetherMp.idBlockZanite, Material.STONE).a("ZaniteBlock"),
    QuicksoilGlass = new BlockQuicksoilGlass(mod_AetherMp.idBlockQuicksoilGlass).a("QuicksoilGlass"),
    Freezer = new BlockFreezer(mod_AetherMp.idBlockFreezer).a("Freezer"),
    WhiteFlower = new BlockAetherFlower(mod_AetherMp.idBlockWhiteFlower).a("White_Flower"),
    PurpleFlower = new BlockAetherFlower(mod_AetherMp.idBlockPurpleFlower).a("Purple_Flower");
	
	public static final BlockInfo[] blocks = new BlockInfo[] {
			new BlockInfo(Portal).setHardness(-1).setResistance(6000000),
			new BlockInfo(Dirt).setHardness(0.2F),
			new BlockInfo(Grass).setHardness(0.2F),
			new BlockInfo(Quicksoil).setHardness(0.5F).setBlockItem(ItemBlockQuicksoil.class),
			new BlockInfo(Holystone).setHardness(0.5F).setBlockItem(ItemBlockHolystone.class),
			new BlockInfo(Icestone).setHardness(3),
			new BlockInfo(Aercloud).setHardness(0.2F).setLightOpacity(3).setBlockItem(ItemBlockAercloud.class),
			new BlockInfo(Aerogel).setHardness(1).setResistance(2000).setLightOpacity(3),
			new BlockInfo(Log).setHardness(2).setBlockItem(ItemBlockAetherLog.class),
			new BlockInfo(Plank).setHardness(2).setResistance(5),
			new BlockInfo(SkyrootLeaves).setHardness(0.2F).setLightOpacity(1),
			new BlockInfo(GoldenOakLeaves).setHardness(0.2F).setLightOpacity(1),
			new BlockInfo(SkyrootSapling).setHardness(0),
			new BlockInfo(GoldenOakSapling).setHardness(0),
			new BlockInfo(AmbrosiumOre).setHardness(3).setResistance(5),
			new BlockInfo(AmbrosiumTorch).setLightValue(0.9375F),
			new BlockInfo(ZaniteOre).setHardness(3),
			new BlockInfo(GravititeOre).setHardness(5),
			new BlockInfo(EnchantedGravitite).setHardness(5),
			new BlockInfo(Enchanter).setHardness(2).setTileEntity(TileEntityEnchanter.class),
			new BlockInfo(Trap).setHardness(-1).setResistance(1000000),
			new BlockInfo(ChestMimic).setHardness(2),
			new BlockInfo(TreasureChest).setHardness(-1),
			new BlockInfo(DungeonStone).setHardness(0.5F).setBlockItem(ItemDungeonBlock.class),
			new BlockInfo(LightDungeonStone).setHardness(0.5F).setLightValue(0.75F).setBlockItem(ItemDungeonBlock.class),
			new BlockInfo(LockedDungeonStone).setHardness(-1).setResistance(1000000),
			new BlockInfo(LockedLightDungeonStone).setHardness(-1).setResistance(1000000).setLightValue(0.5F),
			new BlockInfo(Pillar).setHardness(0.5F).setBlockItem(ItemDungeonBlock.class),
			new BlockInfo(ZaniteBlock).setHardness(3),
			new BlockInfo(QuicksoilGlass).setHardness(0.2F).setLightValue(0.7375F).setLightOpacity(0),
			new BlockInfo(Freezer).setHardness(2.5F).setTileEntity(TileEntityFreezer.class),
			new BlockInfo(WhiteFlower).setHardness(0),
			new BlockInfo(PurpleFlower).setHardness(0)
	};
	
	public static final ToolBase
	Pickaxe = new ToolBase(),
    Shovel = new ToolBase(),
    Axe = new ToolBase();
    
    public static final BlockHarvestPower[]
    pickaxePower = new BlockHarvestPower[] {
			new BlockHarvestPower(Holystone.id, 20F),
			new BlockHarvestPower(Icestone.id, 20F),
			new BlockHarvestPower(AmbrosiumOre.id, 20F),
			new BlockHarvestPower(DungeonStone.id, 20F),
			new BlockHarvestPower(LightDungeonStone.id, 20F),
			new BlockHarvestPower(Pillar.id, 20F),
			new BlockHarvestPower(TreasureChest.id, 20F),
			new BlockHarvestPower(ZaniteOre.id, 40F),
			new BlockHarvestPower(GravititeOre.id, 60F),
			new BlockHarvestPower(EnchantedGravitite.id, 60F),
			new BlockHarvestPower(Aerogel.id, 60F)
    },
    shovelPower = new BlockHarvestPower[] {
            new BlockHarvestPower(Dirt.id, 0.0F),
            new BlockHarvestPower(Grass.id, 0.0F),
            new BlockHarvestPower(Quicksoil.id, 0.0F),
            new BlockHarvestPower(Aercloud.id, 0.0F)
    },
    axePower = new BlockHarvestPower[] {
            new BlockHarvestPower(Log.id, 0.0F),
            new BlockHarvestPower(Plank.id, 0.0F),
            new BlockHarvestPower(SkyrootLeaves.id, 0.0F),
            new BlockHarvestPower(GoldenOakLeaves.id, 60F)
    };
}
