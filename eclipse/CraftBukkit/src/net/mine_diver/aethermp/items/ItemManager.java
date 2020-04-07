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
    
    public static final Item Key = new ItemAetherKey(mod_AetherMp.idItemKey).a("AetherKey");
    public static final Item GoldenAmber = PackageAccess.Item.newItem(mod_AetherMp.idItemGoldenAmber).a("GoldenAmber");
    public static final Item Stick = PackageAccess.Item.newItem(mod_AetherMp.idItemStick).a("SkyrootStick");
    public static final Item AmbrosiumShard = new ItemAmbrosium(mod_AetherMp.idItemAmbrosiumShard, 1).a("AmbrosiumShard");
    public static final Item Zanite = PackageAccess.Item.newItem(mod_AetherMp.idItemZanite).a("Zanite");
    public static final Item BlueMusicDisk = new ItemAetherRecord(mod_AetherMp.idItemBlueMusicDisk, "AetherTune").a("BlueMusicDisk");
    public static final Item Bucket = new ItemSkyrootBucket(mod_AetherMp.idItemBucket).a("SkyrootBucket");
    public static final Item PickSkyroot = new ItemSkyrootPickaxe(mod_AetherMp.idItemPickSkyroot, EnumToolMaterial.WOOD).a("PickSkyroot");
    public static final Item PickHolystone = new ItemHolystonePickaxe(mod_AetherMp.idItemPickHolystone, EnumToolMaterial.STONE).a("PickHolystone");
    public static final Item PickZanite = new ItemZanitePickaxe(mod_AetherMp.idItemPickZanite, EnumToolMaterial.IRON).a("PickZanite");
    public static final Item PickGravitite = new ItemGravititePickaxe(mod_AetherMp.idItemPickGravitite, EnumToolMaterial.DIAMOND).a("PickGravitite");
    public static final Item ShovelSkyroot = new ItemSkyrootSpade(mod_AetherMp.idItemShovelSkyroot, EnumToolMaterial.WOOD).a("ShovelSkyroot");
    public static final Item ShovelHolystone = new ItemHolystoneSpade(mod_AetherMp.idItemShovelHolystone, EnumToolMaterial.STONE).a("ShovelHolystone");
    public static final Item ShovelZanite = new ItemZaniteSpade(mod_AetherMp.idItemShovelZanite, EnumToolMaterial.IRON).a("ShovelZanite");
    public static final Item ShovelGravitite = new ItemGravititeSpade(mod_AetherMp.idItemShovelGravitite, EnumToolMaterial.DIAMOND).a("ShovelGravitite");
    public static final Item AxeSkyroot = new ItemSkyrootAxe(mod_AetherMp.idItemAxeSkyroot, EnumToolMaterial.WOOD).a("AxeSkyroot");
    public static final Item AxeHolystone = new ItemHolystoneSpade(mod_AetherMp.idItemAxeHolystone, EnumToolMaterial.STONE).a("AxeHolystone");
    public static final Item AxeZanite = new ItemZaniteAxe(mod_AetherMp.idItemAxeZanite, EnumToolMaterial.IRON).a("AxeZanite");
    public static final Item AxeGravitite = new ItemGravititeAxe(mod_AetherMp.idItemAxeGravitite, EnumToolMaterial.DIAMOND).a("AxeGravitite");
    public static final Item SwordSkyroot = new ItemSword(mod_AetherMp.idItemSwordSkyroot, EnumToolMaterial.WOOD).a("SwordSkyroot");
    public static final Item SwordHolystone = new ItemSwordHolystone(mod_AetherMp.idItemSwordHolystone, EnumToolMaterial.STONE).a("SwordHolystone");
    public static final Item SwordZanite = new ItemSwordZanite(mod_AetherMp.idItemSwordZanite, EnumToolMaterial.IRON).a("SwordZanite");
    public static final Item SwordGravitite = new ItemSwordGravitite(mod_AetherMp.idItemSwordGravitite, EnumToolMaterial.DIAMOND).a("SwordGravitite");
    public static final Item PhoenixHelm = new ItemArmor(mod_AetherMp.idItemPhoenixHelm, 3, -1, 0).a("PhoenixHelm");
    public static final Item PhoenixBody = new ItemArmor(mod_AetherMp.idItemPhoenixBody, 3, -1, 1).a("PhoenixBody");
    public static final Item PhoenixLegs = new ItemArmor(mod_AetherMp.idItemPhoenixLegs, 3, -1, 2).a("PhoenixLegs");
    public static final Item PhoenixBoots = new ItemArmor(mod_AetherMp.idItemPhoenixBoots, 3, -1, 3).a("PhoenixBoots");
    public static final Item ObsidianHelm = new ItemArmor(mod_AetherMp.idItemObsidianHelm, 4, -1, 0).a("ObsidianHelm");
    public static final Item ObsidianBody = new ItemArmor(mod_AetherMp.idItemObsidianBody, 4, -1, 1).a("ObsidianBody");
    public static final Item ObsidianLegs = new ItemArmor(mod_AetherMp.idItemObsidianLegs, 4, -1, 2).a("ObsidianLegs");
    public static final Item ObsidianBoots = new ItemArmor(mod_AetherMp.idItemObsidianBoots, 4, -1, 3).a("ObsidianBoots");
    public static final Item CloudParachute = new ItemCloudParachute(mod_AetherMp.idItemCloudParachute).a("CloudParachute");
    public static final Item CloudParachuteGold = new ItemCloudParachute(mod_AetherMp.idItemCloudParachuteGold).a("CloudParachuteGold");
    public static final Item GravititeHelmet = new ItemArmor(mod_AetherMp.idItemGravititeHelmet, 3, -1, 0).a("GravHelm");
    public static final Item GravititeBodyplate = new ItemArmor(mod_AetherMp.idItemGravititeBodyplate, 3, -1, 1).a("GravBody");
    public static final Item GravititePlatelegs = new ItemArmor(mod_AetherMp.idItemGravititePlatelegs, 3, -1, 2).a("GravLegs");
    public static final Item GravititeBoots = new ItemArmor(mod_AetherMp.idItemGravititeBoots, 3, -1, 3).a("GravBoots");
    public static final Item ZaniteHelmet = new ItemArmor(mod_AetherMp.idItemZaniteHelmet, 2, -1, 0).a("ZaniteHelm");
    public static final Item ZaniteChestplate = new ItemArmor(mod_AetherMp.idItemZaniteChestplate, 2, -1, 1).a("ZaniteBody");
    public static final Item ZaniteLeggings = new ItemArmor(mod_AetherMp.idItemZaniteLeggings, 2, -1, 2).a("ZaniteLegs");
    public static final Item ZaniteBoots = new ItemArmor(mod_AetherMp.idItemZaniteBoots, 2, -1, 3).a("ZaniteBoots");
    public static final Item NeptuneHelmet = new ItemArmor(mod_AetherMp.idItemNeptuneHelmet, 3, -1, 0).a("NeptuneHelm");
    public static final Item NeptuneChestplate = new ItemArmor(mod_AetherMp.idItemNeptuneChestplate, 3, -1, 1).a("NeptuneBody");
    public static final Item NeptuneLeggings = new ItemArmor(mod_AetherMp.idItemNeptuneLeggings, 3, -1, 2).a("NeptuneLegs");
    public static final Item NeptuneBoots = new ItemArmor(mod_AetherMp.idItemNeptuneBoots, 3, -1, 3).a("NeptuneBoots");
    public static final Item HealingStone = new ItemAmbrosium(mod_AetherMp.idItemHealingStone, 4).a("Healing_Stone");
}
