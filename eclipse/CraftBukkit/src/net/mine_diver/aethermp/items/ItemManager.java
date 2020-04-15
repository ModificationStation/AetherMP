package net.mine_diver.aethermp.items;

import net.minecraft.server.EntityHuman;
import net.minecraft.server.EnumToolMaterial;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.mod_AetherMp;
import net.minecraft.server.ItemArmor;
import net.minecraft.server.ItemSword;

import static net.minecraft.server.mod_AetherMp.PackageAccess;

public class ItemManager {
	
	public static void registerItems() {
		for (ItemInfo item : items) {
			Item itemInst = item.getItem();
			PackageAccess.Item.setMaxDamage(itemInst, item.getMaxDamage());
		}
	}
	
	public static boolean equippedSkyrootPick(EntityHuman entityhuman) {
        ItemStack itemstack = entityhuman.inventory.getItemInHand();
        return itemstack != null && itemstack.id == PickSkyroot.id;
    }
	
    public static boolean equippedSkyrootAxe(EntityHuman entityhuman) {
        ItemStack itemstack = entityhuman.inventory.getItemInHand();
        return itemstack != null && itemstack.id == AxeSkyroot.id;
    }
    
    public static boolean equippedSkyrootShovel(EntityHuman entityhuman) {
        ItemStack itemstack = entityhuman.inventory.getItemInHand();
        return itemstack != null && itemstack.id == ShovelSkyroot.id;
    }
    
    public static final Item
    Key = new ItemAetherKey(mod_AetherMp.idItemKey).a("AetherKey"),
    LoreBook = new ItemLoreBook(mod_AetherMp.idItemLoreBook).a("LoreBook"),
    GoldenAmber = PackageAccess.Item.newItem(mod_AetherMp.idItemGoldenAmber).a("GoldenAmber"),
    Stick = PackageAccess.Item.newItem(mod_AetherMp.idItemStick).a("SkyrootStick"),
    AmbrosiumShard = new ItemAmbrosium(mod_AetherMp.idItemAmbrosiumShard, 1).a("AmbrosiumShard"),
    Zanite = PackageAccess.Item.newItem(mod_AetherMp.idItemZanite).a("Zanite"),
    BlueMusicDisk = new ItemAetherRecord(mod_AetherMp.idItemBlueMusicDisk, "AetherTune").a("BlueMusicDisk"),
    Bucket = new ItemSkyrootBucket(mod_AetherMp.idItemBucket).a("SkyrootBucket"),
    PickSkyroot = new ItemSkyrootPickaxe(mod_AetherMp.idItemPickSkyroot, EnumToolMaterial.WOOD).a("PickSkyroot"),
    PickHolystone = new ItemHolystonePickaxe(mod_AetherMp.idItemPickHolystone, EnumToolMaterial.STONE).a("PickHolystone"),
    PickZanite = new ItemZanitePickaxe(mod_AetherMp.idItemPickZanite, EnumToolMaterial.IRON).a("PickZanite"),
    PickGravitite = new ItemGravititePickaxe(mod_AetherMp.idItemPickGravitite, EnumToolMaterial.DIAMOND).a("PickGravitite"),
    ShovelSkyroot = new ItemSkyrootSpade(mod_AetherMp.idItemShovelSkyroot, EnumToolMaterial.WOOD).a("ShovelSkyroot"),
    ShovelHolystone = new ItemHolystoneSpade(mod_AetherMp.idItemShovelHolystone, EnumToolMaterial.STONE).a("ShovelHolystone"),
    ShovelZanite = new ItemZaniteSpade(mod_AetherMp.idItemShovelZanite, EnumToolMaterial.IRON).a("ShovelZanite"),
    ShovelGravitite = new ItemGravititeSpade(mod_AetherMp.idItemShovelGravitite, EnumToolMaterial.DIAMOND).a("ShovelGravitite"),
    AxeSkyroot = new ItemSkyrootAxe(mod_AetherMp.idItemAxeSkyroot, EnumToolMaterial.WOOD).a("AxeSkyroot"),
    AxeHolystone = new ItemHolystoneSpade(mod_AetherMp.idItemAxeHolystone, EnumToolMaterial.STONE).a("AxeHolystone"),
    AxeZanite = new ItemZaniteAxe(mod_AetherMp.idItemAxeZanite, EnumToolMaterial.IRON).a("AxeZanite"),
    AxeGravitite = new ItemGravititeAxe(mod_AetherMp.idItemAxeGravitite, EnumToolMaterial.DIAMOND).a("AxeGravitite"),
    SwordSkyroot = new ItemSword(mod_AetherMp.idItemSwordSkyroot, EnumToolMaterial.WOOD).a("SwordSkyroot"),
    SwordHolystone = new ItemSwordHolystone(mod_AetherMp.idItemSwordHolystone, EnumToolMaterial.STONE).a("SwordHolystone"),
    SwordZanite = new ItemSwordZanite(mod_AetherMp.idItemSwordZanite, EnumToolMaterial.IRON).a("SwordZanite"),
    SwordGravitite = new ItemSwordGravitite(mod_AetherMp.idItemSwordGravitite, EnumToolMaterial.DIAMOND).a("SwordGravitite"),
    IronBubble = new ItemMoreArmor(mod_AetherMp.idItemIronBubble, 0, 7).a("IronBubble"),
    PhoenixHelm = new ItemArmor(mod_AetherMp.idItemPhoenixHelm, 3, -1, 0).a("PhoenixHelm"),
    PhoenixBody = new ItemArmor(mod_AetherMp.idItemPhoenixBody, 3, -1, 1).a("PhoenixBody"),
    PhoenixLegs = new ItemArmor(mod_AetherMp.idItemPhoenixLegs, 3, -1, 2).a("PhoenixLegs"),
    PhoenixBoots = new ItemArmor(mod_AetherMp.idItemPhoenixBoots, 3, -1, 3).a("PhoenixBoots"),
    ObsidianHelm = new ItemArmor(mod_AetherMp.idItemObsidianHelm, 4, -1, 0).a("ObsidianHelm"),
    ObsidianBody = new ItemArmor(mod_AetherMp.idItemObsidianBody, 4, -1, 1).a("ObsidianBody"),
    ObsidianLegs = new ItemArmor(mod_AetherMp.idItemObsidianLegs, 4, -1, 2).a("ObsidianLegs"),
    ObsidianBoots = new ItemArmor(mod_AetherMp.idItemObsidianBoots, 4, -1, 3).a("ObsidianBoots"),
    CloudParachute = new ItemCloudParachute(mod_AetherMp.idItemCloudParachute).a("CloudParachute"),
    CloudParachuteGold = new ItemCloudParachute(mod_AetherMp.idItemCloudParachuteGold).a("CloudParachuteGold"),
    GravititeHelmet = new ItemArmor(mod_AetherMp.idItemGravititeHelmet, 3, -1, 0).a("GravHelm"),
    GravititeBodyplate = new ItemArmor(mod_AetherMp.idItemGravititeBodyplate, 3, -1, 1).a("GravBody"),
    GravititePlatelegs = new ItemArmor(mod_AetherMp.idItemGravititePlatelegs, 3, -1, 2).a("GravLegs"),
    GravititeBoots = new ItemArmor(mod_AetherMp.idItemGravititeBoots, 3, -1, 3).a("GravBoots"),
    ZaniteHelmet = new ItemArmor(mod_AetherMp.idItemZaniteHelmet, 2, -1, 0).a("ZaniteHelm"),
    ZaniteChestplate = new ItemArmor(mod_AetherMp.idItemZaniteChestplate, 2, -1, 1).a("ZaniteBody"),
    ZaniteLeggings = new ItemArmor(mod_AetherMp.idItemZaniteLeggings, 2, -1, 2).a("ZaniteLegs"),
    ZaniteBoots = new ItemArmor(mod_AetherMp.idItemZaniteBoots, 2, -1, 3).a("ZaniteBoots"),
    GoldenFeather = new ItemMoreArmor(mod_AetherMp.idItemGoldenFeather, 0, 7).a("GoldenFeather"),
    RepShield = new ItemMoreArmor(mod_AetherMp.idItemRepShield, 0, 6).a("RepShield"),
    AetherCape = new ItemMoreArmor(mod_AetherMp.idItemAetherCape, 0, 5).a("AetherCape"),
    IronRing = new ItemMoreArmor(mod_AetherMp.idItemIronRing, 0, 8).a("IronRing"),
    GoldRing = new ItemMoreArmor(mod_AetherMp.idItemGoldRing, 0, 8).a("GoldRing"),
    ZaniteRing = new ItemMoreArmor(mod_AetherMp.idItemZaniteRing, 0, 8).a("ZaniteRing"),
    IronPendant = new ItemMoreArmor(mod_AetherMp.idItemIronPendant, 0, 4).a("IronPendant"),
    GoldPendant = new ItemMoreArmor(mod_AetherMp.idItemGoldPendant, 0, 4).a("GoldPendant"),
    ZanitePendant = new ItemMoreArmor(mod_AetherMp.idItemZanitePendant, 0, 4).a("ZanitePendant"),
    LeatherGlove = new ItemMoreArmor(mod_AetherMp.idItemLeatherGlove, 0, 10).a("LeatherGlove"),
    IronGlove = new ItemMoreArmor(mod_AetherMp.idItemIronGlove, 2, 10).a("IronGlove"),
    GoldGlove = new ItemMoreArmor(mod_AetherMp.idItemGoldGlove, 1, 10).a("GoldGlove"),
    DiamondGlove = new ItemMoreArmor(mod_AetherMp.idItemDiamondGlove, 3, 10).a("DiamondGlove"),
    ZaniteGlove = new ItemMoreArmor(mod_AetherMp.idItemZaniteGlove, 2, 10).a("ZaniteGlove"),
    GravititeGlove = new ItemMoreArmor(mod_AetherMp.idItemGravititeGlove, 3, 10).a("GravititeGlove"),
    PhoenixGlove = new ItemMoreArmor(mod_AetherMp.idItemPhoenixGlove, 3, 10).a("PhoenixGlove"),
    ObsidianGlove = new ItemMoreArmor(mod_AetherMp.idItemObsidianGlove, 4, 10).a("ObsidianGlove"),
    NeptuneGlove = new ItemMoreArmor(mod_AetherMp.idItemNeptuneGlove, 3, 10).a("NeptuneGlove"),
    NeptuneHelmet = new ItemArmor(mod_AetherMp.idItemNeptuneHelmet, 3, -1, 0).a("NeptuneHelm"),
    NeptuneChestplate = new ItemArmor(mod_AetherMp.idItemNeptuneChestplate, 3, -1, 1).a("NeptuneBody"),
    NeptuneLeggings = new ItemArmor(mod_AetherMp.idItemNeptuneLeggings, 3, -1, 2).a("NeptuneLegs"),
    NeptuneBoots = new ItemArmor(mod_AetherMp.idItemNeptuneBoots, 3, -1, 3).a("NeptuneBoots"),
    RegenerationStone = new ItemMoreArmor(mod_AetherMp.idItemRegenerationStone, 0, 7).a("RegenerationStone"),
    InvisibilityCloak = new ItemMoreArmor(mod_AetherMp.idItemInvisibilityCloak, 0, 5).a("InvisibilityCloak"),
    HealingStone = new ItemAmbrosium(mod_AetherMp.idItemHealingStone, 4).a("Healing_Stone"),
    AgilityCape = new ItemMoreArmor(mod_AetherMp.idItemAgilityCape, 0, 5).a("AgilityCape"),
    WhiteCape = new ItemMoreArmor(mod_AetherMp.idItemWhiteCape, 0, 5).a("WhiteCape"),
    RedCape = new ItemMoreArmor(mod_AetherMp.idItemRedCape, 0, 5).a("RedCape"),
    YellowCape = new ItemMoreArmor(mod_AetherMp.idItemYellowCape, 0, 5).a("YellowCape"),
    BlueCape = new ItemMoreArmor(mod_AetherMp.idItemBlueCape, 0, 5).a("BlueCape"),
    IceRing = new ItemMoreArmor(mod_AetherMp.idItemIceRing, 0, 8).a("IceRing"),
    IcePendant = new ItemMoreArmor(mod_AetherMp.idItemIcePendant, 0, 4).a("IcePendant");
    
    public static final ItemInfo[] items = new ItemInfo[] {
    		new ItemInfo(RepShield).setMaxDamage(512)
    };
}
