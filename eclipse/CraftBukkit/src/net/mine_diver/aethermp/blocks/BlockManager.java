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
    
    public static final Block Portal = new BlockAetherPortal(mod_AetherMp.idBlockAetherPortal).a("AetherPortal");
    public static final Block Dirt = new BlockAetherDirt(mod_AetherMp.idBlockAetherDirt).a("AetherDirt");
    public static final Block Grass = new BlockAetherGrass(mod_AetherMp.idBlockAetherGrass).a("AetherGrass");
    public static final Block Quicksoil = new BlockQuicksoil(mod_AetherMp.idBlockQuicksoil).a("Quicksoil");
    public static final Block Holystone = new BlockHolystone(mod_AetherMp.idBlockHolystone).a("Holystone");
    public static final Block Icestone = new BlockIcestone(mod_AetherMp.idBlockIcestone).a("Icestone");
    public static final Block Aercloud = new BlockAercloud(mod_AetherMp.idBlockAercloud).a("Aercloud");
    public static final Block Aerogel = new BlockAerogel(mod_AetherMp.idBlockAerogel).a("Aerogel");
    public static final Block Log = new BlockAetherLog(mod_AetherMp.idBlockLog).a("AetherLog");
    public static final Block Plank = PackageAccess.Block.newBlock(mod_AetherMp.idBlockPlank, -1, Material.WOOD).a("AetherPlank");
    public static final Block SkyrootLeaves = new BlockAetherLeaves(mod_AetherMp.idBlockSkyrootLeaves).a("SkyrootLeaves");
    public static final Block GoldenOakLeaves = new BlockAetherLeaves(mod_AetherMp.idBlockGoldenOakLeaves).a("GoldenLeaves");
    public static final Block SkyrootSapling = new BlockAetherSapling(mod_AetherMp.idBlockSkyrootSapling).a("SkyrootSapling");
    public static final Block GoldenOakSapling = new BlockAetherSapling(mod_AetherMp.idBlockGoldenOakSapling).a("GoldenOakSapling");
    public static final Block AmbrosiumOre = new BlockAmbrosiumOre(mod_AetherMp.idBlockAmbrosiumOre).a("AmbrosiumOre");
    public static final Block AmbrosiumTorch = new BlockAmbrosiumTorch(mod_AetherMp.idBlockAmbrosiumTorch).a("AmbrosiumTorch");
    public static final Block ZaniteOre = new BlockZaniteOre(mod_AetherMp.idBlockZaniteOre).a("ZaniteOre");
    public static final Block GravititeOre = new BlockFloating(mod_AetherMp.idBlockGravititeOre, false).a("GravititeOre");
    public static final Block EnchantedGravitite = new BlockFloating(mod_AetherMp.idBlockEnchantedGravitite, true).a("EnchantedGravitite");
    public static final Block Enchanter = new BlockEnchanter(mod_AetherMp.idBlockEnchanter).a("Enchanter");
    public static final Block Trap = new BlockTrap(mod_AetherMp.idBlockTrap).a("Trap");
    public static final Block ChestMimic = new BlockChestMimic(mod_AetherMp.idBlockChestMimic).a("Mimic");
    public static final Block TreasureChest = new BlockTreasureChest(mod_AetherMp.idBlockTreasureChest).a("TreasureChest");
    public static final Block DungeonStone = new BlockDungeon(mod_AetherMp.idBlockDungeonStone).a("DungeonStone");
    public static final Block LightDungeonStone = new BlockDungeon(mod_AetherMp.idBlockLightDungeonStone).a("LightDungeonStone");
    public static final Block LockedDungeonStone = new BlockDungeon(mod_AetherMp.idBlockLockedDungeonStone).a("LockedDungeonStone");
    public static final Block LockedLightDungeonStone = new BlockDungeon(mod_AetherMp.idBlockLockedLightDungeonStone).a("LightLockedDungeonStone");
    public static final Block Pillar = new BlockPillar(mod_AetherMp.idBlockPillar).a("Pillar");
    public static final Block ZaniteBlock = PackageAccess.Block.newBlock(mod_AetherMp.idBlockZanite, Material.STONE).a("ZaniteBlock");
    public static final Block QuicksoilGlass = new BlockQuicksoilGlass(mod_AetherMp.idBlockQuicksoilGlass).a("QuicksoilGlass");
    public static final Block WhiteFlower = new BlockAetherFlower(mod_AetherMp.idBlockWhiteFlower).a("White_Flower");
    public static final Block PurpleFlower = new BlockAetherFlower(mod_AetherMp.idBlockPurpleFlower).a("Purple_Flower");
	
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
			new BlockInfo(WhiteFlower).setHardness(0),
			new BlockInfo(PurpleFlower).setHardness(0)
	};
	
	public static final ToolBase Pickaxe = new ToolBase();
    public static final ToolBase Shovel = new ToolBase();
    public static final ToolBase Axe = new ToolBase();
    
    public static final BlockHarvestPower[] pickaxePower = new BlockHarvestPower[] {
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
    };
    
    public static final BlockHarvestPower[] shovelPower = new BlockHarvestPower[] {
            new BlockHarvestPower(Dirt.id, 0.0F),
            new BlockHarvestPower(Grass.id, 0.0F),
            new BlockHarvestPower(Quicksoil.id, 0.0F),
            new BlockHarvestPower(Aercloud.id, 0.0F)
    };
    
    public static final BlockHarvestPower[] axePower = new BlockHarvestPower[] {
            new BlockHarvestPower(Log.id, 0.0F),
            new BlockHarvestPower(Plank.id, 0.0F),
            new BlockHarvestPower(SkyrootLeaves.id, 0.0F),
            new BlockHarvestPower(GoldenOakLeaves.id, 60F)
    };
}
