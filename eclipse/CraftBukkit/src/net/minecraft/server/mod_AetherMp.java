package net.minecraft.server;

import net.mine_diver.aethermp.Core;
import net.mine_diver.aethermp.info;

public class mod_AetherMp extends BaseModMp {

	@Override
	public String Version() {
		return info.VERSION;
	}
	
	@Override
	public void ModsLoaded() {
		super.ModsLoaded();
		CORE.postInit();
	}
	
	public static final Core CORE = new Core();
	
	@MLProp
	public static String
	nameDimensionAether = "Aether";
	
	@MLProp
	public static int
	idDimensionAether = 3,
	idGuiEnchanter = 80,
	idGuiTreasureChest = 81,
	idGuiFreezer = 82,
	idEntityFloatingBlock = 80,
	idEntityMimic = 81,
	idEntityZephyr = 85,
	idEntitySheepuff = 86,
	idEntitySentry = 89,
	idEntityZephyrSnowball = 100,
	idEntityCloudParachute = 101,
	rarityZephyr = 5,
	raritySheepuff = 10,
    idBlockAetherPortal = 165,
    idBlockAetherDirt = 166,
    idBlockAetherGrass = 167,
    idBlockQuicksoil = 168,
    idBlockHolystone = 169,
    idBlockIcestone = 170,
    idBlockAercloud = 171,
    idBlockAerogel = 172,
    idBlockEnchanter = 173,
    idBlockLog = 175,
    idBlockPlank = 176,
    idBlockSkyrootLeaves = 177,
    idBlockGoldenOakLeaves = 178,
    idBlockSkyrootSapling = 179,
    idBlockGoldenOakSapling = 180,
    idBlockAmbrosiumOre = 181,
    idBlockAmbrosiumTorch = 182,
    idBlockZaniteOre = 183,
    idBlockGravititeOre = 184,
    idBlockEnchantedGravitite = 185,
    idBlockTrap = 186,
    idBlockChestMimic = 187,
    idBlockTreasureChest = 188,
    idBlockDungeonStone = 189,
    idBlockLightDungeonStone = 190,
    idBlockLockedDungeonStone = 191,
    idBlockLockedLightDungeonStone = 192,
    idBlockPillar = 193,
    idBlockZanite = 194,
    idBlockQuicksoilGlass = 195,
    idBlockFreezer = 196,
    idBlockWhiteFlower = 197,
    idBlockPurpleFlower = 198,
    idItemKey = 17001,
    idItemBlueMusicDisk = 17004,
    idItemGoldenAmber = 17005,
    idItemStick = 17007,
    idItemAmbrosiumShard = 17010,
    idItemZanite = 17011,
    idItemBucket = 17012,
    idItemPickSkyroot = 17013,
    idItemPickHolystone = 17014,
    idItemPickZanite = 17015,
    idItemPickGravitite = 17016,
    idItemShovelSkyroot = 17017,
    idItemShovelHolystone = 17018,
    idItemShovelZanite = 17019,
    idItemShovelGravitite = 17020,
    idItemAxeSkyroot = 17021,
    idItemAxeHolystone = 17022,
    idItemAxeZanite = 17023,
    idItemAxeGravitite = 17024,
    idItemSwordSkyroot = 17025,
    idItemSwordHolystone = 17026,
    idItemSwordZanite = 17027,
    idItemSwordGravitite = 17028,
    idItemIronBubble = 17029,
    idItemCloudParachute = 17040,
    idItemCloudParachuteGold = 17041,
    idItemGoldenFeather = 17044,
    idItemIronRing = 17046,
    idItemGoldRing = 17047,
    idItemZaniteRing = 17048,
    idItemIronPendant = 17049,
    idItemGoldPendant = 17050,
    idItemZanitePendant = 17051,
    idItemRepShield = 17052,
    idItemAetherCape = 17053,
    idItemLeatherGlove = 17054,
    idItemIronGlove = 17055,
    idItemGoldGlove = 17056,
    idItemDiamondGlove = 17057,
    idItemZaniteGlove = 17058,
    idItemZaniteHelmet = 17059,
    idItemZaniteChestplate = 17060,
    idItemZaniteLeggings = 17061,
    idItemZaniteBoots = 17062,
    idItemGravititeGlove = 17063,
    idItemGravititeHelmet = 17064,
    idItemGravititeBodyplate = 17065,
    idItemGravititePlatelegs = 17066,
    idItemGravititeBoots = 17067,
    idItemPhoenixGlove = 17068,
    idItemPhoenixHelm = 17069,
    idItemPhoenixBody = 17070,
    idItemPhoenixLegs = 17071,
    idItemPhoenixBoots = 17072,
    idItemObsidianGlove = 17073,
    idItemObsidianBody = 17074,
    idItemObsidianHelm = 17075,
    idItemObsidianLegs = 17076,
    idItemObsidianBoots = 17077,
    idItemNeptuneGlove = 17078,
    idItemNeptuneHelmet = 17079,
    idItemNeptuneChestplate = 17080,
    idItemNeptuneLeggings = 17081,
    idItemNeptuneBoots = 17082,
    idItemRegenerationStone = 17083,
    idItemInvisibilityCloak = 17084,
    idItemAgilityCape = 17085,
    idItemWhiteCape = 17086,
    idItemRedCape = 17087,
    idItemYellowCape = 17088,
    idItemBlueCape = 17089,
    idItemHealingStone = 17093,
    idItemIceRing = 17094,
    idItemIcePendant = 17095;
	
	public static class PackageAccess {
		
		public static class Block {
			
			public static net.minecraft.server.Block newBlock(int i, int j, Material material) {
				return new net.minecraft.server.Block(i, j, material);
			}
			
			public static net.minecraft.server.Block newBlock(int i, Material material) {
				return new net.minecraft.server.Block(i, material);
			}
			
			public static net.minecraft.server.Block setHardness(net.minecraft.server.Block block, float hardness) {
				block.c(hardness);
				return block;
			}
			
			public static net.minecraft.server.Block setResistance(net.minecraft.server.Block block, float resistance) {
				block.b(resistance);
				return block;
			}
			
			public static net.minecraft.server.Block setLightValue(net.minecraft.server.Block block, float lightValue) {
				block.a(lightValue);
				return block;
			}
			
			public static net.minecraft.server.Block setLightValue(net.minecraft.server.Block block, int lightOpacity) {
				block.f(lightOpacity);
				return block;
			}
		}
		
		public static class Item {
			
			public static net.minecraft.server.Item newItem(int ID) {
				return new net.minecraft.server.Item(ID);
			}
			
			public static void setMaxDamage(net.minecraft.server.Item item, int maxDamage) {
				item.d(maxDamage);
			}
		}
		
		public static class SlotArmor {
			
			public static net.minecraft.server.SlotArmor newSlotArmor(ContainerPlayer container, IInventory inventory, int slot, int xDisplay, int yDisplay, int armorType) {
				return new net.minecraft.server.SlotArmor(container, inventory, slot, xDisplay, yDisplay, armorType);
			}
		}
		
		public static class Entity {
			
			public static boolean getInWater(net.minecraft.server.Entity entity) {
				return entity.bA;
			}
			
			public static void setIsImmuneToFire(net.minecraft.server.Entity entity, boolean isImmuneToFire) {
				entity.fireProof = isImmuneToFire;
			}
			
			public static void setEntityFlag(net.minecraft.server.Entity entity, int ID, boolean flag) {
				entity.a(ID, flag);
			}
		}
	}
}
