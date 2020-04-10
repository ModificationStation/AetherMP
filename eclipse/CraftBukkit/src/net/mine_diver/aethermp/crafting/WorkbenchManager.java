package net.mine_diver.aethermp.crafting;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.mine_diver.aethermp.blocks.BlockManager;
import net.mine_diver.aethermp.items.ItemManager;
import net.minecraft.server.Block;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.ModLoader;

public class WorkbenchManager {
	
	public static void registerRecipes() {
		for (Entry<ItemStack, Object[]> entry : craftings.entrySet())
			ModLoader.AddRecipe(entry.getKey(), entry.getValue());
		for (Entry<ItemStack, Object[]> entry : shapelessCraftings.entrySet())
			ModLoader.AddShapelessRecipe(entry.getKey(), entry.getValue());
	}
	
	public static final Map<ItemStack, Object[]> craftings = new HashMap<ItemStack, Object[]>();
	public static final Map<ItemStack, Object[]> shapelessCraftings = new HashMap<ItemStack, Object[]>();
	
	static {
		craftings.put(new ItemStack(ItemManager.PickHolystone, 1), new Object[] {"ZZZ", " Y ", " Y ", 'Z', BlockManager.Holystone, 'Y', ItemManager.Stick});
	    craftings.put(new ItemStack(ItemManager.AxeHolystone, 1), new Object[] {"ZZ", "ZY", " Y", 'Z', BlockManager.Holystone, 'Y', ItemManager.Stick});
	    craftings.put(new ItemStack(ItemManager.ShovelHolystone, 1), new Object[] {"Z", "Y", "Y", 'Z', BlockManager.Holystone, 'Y', ItemManager.Stick});
	    craftings.put(new ItemStack(ItemManager.SwordHolystone, 1), new Object[] {"Z", "Z", "Y", 'Z', BlockManager.Holystone, 'Y', ItemManager.Stick});
	    craftings.put(new ItemStack(ItemManager.PickZanite, 1), new Object[] {"ZZZ", " Y ", " Y ", 'Z', ItemManager.Zanite, 'Y', ItemManager.Stick});
	    craftings.put(new ItemStack(ItemManager.AxeZanite, 1), new Object[] {"ZZ", "ZY", " Y", 'Z', ItemManager.Zanite, 'Y', ItemManager.Stick});
        craftings.put(new ItemStack(ItemManager.ShovelZanite, 1), new Object[] {"Z", "Y", "Y", 'Z', ItemManager.Zanite, 'Y', ItemManager.Stick});
        craftings.put(new ItemStack(ItemManager.SwordZanite, 1), new Object[] {"Z", "Z", "Y", 'Z', ItemManager.Zanite, 'Y', ItemManager.Stick});
        craftings.put(new ItemStack(ItemManager.PickGravitite, 1), new Object[] {"ZZZ", " Y ", " Y ", 'Z', BlockManager.EnchantedGravitite, 'Y', ItemManager.Stick});
        craftings.put(new ItemStack(ItemManager.AxeGravitite, 1), new Object[] {"ZZ", "ZY", " Y", 'Z', BlockManager.EnchantedGravitite, 'Y', ItemManager.Stick});
        craftings.put(new ItemStack(ItemManager.ShovelGravitite, 1), new Object[] {"Z", "Y", "Y", 'Z', BlockManager.EnchantedGravitite, 'Y', ItemManager.Stick});
        craftings.put(new ItemStack(ItemManager.SwordGravitite, 1), new Object[] {"Z", "Z", "Y", 'Z', BlockManager.EnchantedGravitite, 'Y', ItemManager.Stick});
        craftings.put(new ItemStack(ItemManager.PickSkyroot, 1), new Object[] {"ZZZ", " Y ", " Y ", 'Z', BlockManager.Plank, 'Y', ItemManager.Stick});
        craftings.put(new ItemStack(ItemManager.AxeSkyroot, 1), new Object[] {"ZZ", "ZY", " Y", 'Z', BlockManager.Plank, 'Y', ItemManager.Stick});
        craftings.put(new ItemStack(ItemManager.ShovelSkyroot, 1), new Object[] {"Z", "Y", "Y", 'Z', BlockManager.Plank, 'Y', ItemManager.Stick});
        craftings.put(new ItemStack(ItemManager.SwordSkyroot, 1), new Object[] {"Z", "Z", "Y", 'Z', BlockManager.Plank, 'Y', ItemManager.Stick});
        craftings.put(new ItemStack(ItemManager.Bucket, 1, 0), new Object[] {"Z Z", " Z ", 'Z', BlockManager.Plank});
        craftings.put(new ItemStack(ItemManager.Stick, 4), new Object[] {"#", "#", '#', BlockManager.Plank});
        craftings.put(new ItemStack(Block.WORKBENCH, 1), new Object[] {"UU", "UU", 'U', BlockManager.Plank});
        craftings.put(new ItemStack(BlockManager.AmbrosiumTorch, 2), new Object[] {" Z", " Y", 'Z', ItemManager.AmbrosiumShard, 'Y', ItemManager.Stick});
        craftings.put(new ItemStack(ItemManager.WhiteCape, 1), new Object[] {"XX", "XX", "XX", 'X', new ItemStack(Block.WOOL, 1, 0)});
        craftings.put(new ItemStack(ItemManager.RedCape, 1), new Object[] {"XX", "XX", "XX", 'X', new ItemStack(Block.WOOL, 1, 14)});
        craftings.put(new ItemStack(ItemManager.BlueCape, 1), new Object[] {"XX", "XX", "XX", 'X', new ItemStack(Block.WOOL, 1, 11)});
        craftings.put(new ItemStack(ItemManager.BlueCape, 1), new Object[] {"XX", "XX", "XX", 'X', new ItemStack(Block.WOOL, 1, 3)});
        craftings.put(new ItemStack(ItemManager.BlueCape, 1), new Object[] {"XX", "XX", "XX", 'X', new ItemStack(Block.WOOL, 1, 9)});
        craftings.put(new ItemStack(ItemManager.YellowCape, 1), new Object[] {"XX", "XX", "XX", 'X', new ItemStack(Block.WOOL, 1, 4)});
        craftings.put(new ItemStack(BlockManager.Freezer, 1), new Object[] {"XXX", "XZX", "YYY", Character.valueOf('X'), BlockManager.Holystone, Character.valueOf('Z'), BlockManager.Icestone, Character.valueOf('Y'), BlockManager.Plank});
        craftings.put(new ItemStack(BlockManager.ZaniteBlock, 1), new Object[] {"XX", "XX", 'X', ItemManager.Zanite});
        craftings.put(new ItemStack(ItemManager.CloudParachute, 1), new Object[] {"UU", "UU", 'U', new ItemStack(BlockManager.Aercloud, 1, 0)});
        craftings.put(new ItemStack(ItemManager.CloudParachuteGold, 1), new Object[] {"UU", "UU", 'U', new ItemStack(BlockManager.Aercloud, 1, 2)});
        craftings.put(new ItemStack(Item.SADDLE, 1), new Object[] {"XXX", "XZX", 'X', Item.LEATHER, 'Z', Item.STRING});
        craftings.put(new ItemStack(ItemManager.GravititeHelmet, 1), new Object[] {"XXX", "X X", 'X', BlockManager.EnchantedGravitite});
        craftings.put(new ItemStack(ItemManager.GravititeBodyplate, 1), new Object[] {"X X", "XXX", "XXX", 'X', BlockManager.EnchantedGravitite});
        craftings.put(new ItemStack(ItemManager.GravititePlatelegs, 1), new Object[] {"XXX", "X X", "X X", 'X', BlockManager.EnchantedGravitite});
        craftings.put(new ItemStack(ItemManager.GravititeBoots, 1), new Object[] {"X X", "X X", 'X', BlockManager.EnchantedGravitite});
        craftings.put(new ItemStack(ItemManager.ZaniteHelmet, 1), new Object[] {"XXX", "X X", 'X', ItemManager.Zanite});
        craftings.put(new ItemStack(ItemManager.ZaniteChestplate, 1), new Object[] {"X X", "XXX", "XXX", 'X', ItemManager.Zanite});
        craftings.put(new ItemStack(ItemManager.ZaniteLeggings, 1), new Object[] {"XXX", "X X", "X X", 'X', ItemManager.Zanite});
        craftings.put(new ItemStack(ItemManager.ZaniteBoots, 1), new Object[] {"X X", "X X", 'X', ItemManager.Zanite});
        shapelessCraftings.put(new ItemStack(ItemManager.Zanite, 4), new Object[] {BlockManager.ZaniteBlock});
        shapelessCraftings.put(new ItemStack(Item.INK_SACK, 2, 5), new Object[] {BlockManager.PurpleFlower});
        craftings.put(new ItemStack(Block.CHEST, 1), new Object[] {"PPP", "P P", "PPP", 'P', BlockManager.Plank});
        craftings.put(new ItemStack(Item.WOOD_DOOR), new Object[] {"PP", "PP", "PP", 'P', BlockManager.Plank});
        craftings.put(new ItemStack(Block.FENCE, 2), new Object[] {"SSS", "SSS", 'S', ItemManager.Stick});
        craftings.put(new ItemStack(Block.LADDER, 4), new Object[] {"S S", "SSS", "S S", 'S', ItemManager.Stick});
        craftings.put(new ItemStack(Block.JUKEBOX), new Object[] {"PPP", "PGP", "PPP", 'P', BlockManager.Plank, 'G', BlockManager.EnchantedGravitite});
        craftings.put(new ItemStack(BlockManager.Plank, 4), new Object[] {"L", 'L', BlockManager.Log});
        craftings.put(new ItemStack(BlockManager.Enchanter), new Object[] {"HHH", "HZH", "HHH", 'H', BlockManager.Holystone, 'Z', ItemManager.Zanite});
        craftings.put(new ItemStack(ItemManager.LeatherGlove), new Object[] {"C C", 'C', Item.LEATHER});
        craftings.put(new ItemStack(ItemManager.IronGlove), new Object[] {"C C", 'C', Item.IRON_INGOT});
        craftings.put(new ItemStack(ItemManager.GoldGlove), new Object[] {"C C", 'C', Item.GOLD_INGOT});
        craftings.put(new ItemStack(ItemManager.DiamondGlove), new Object[] {"C C", 'C', Item.DIAMOND });
        craftings.put(new ItemStack(ItemManager.ZaniteGlove), new Object[] {"C C", 'C', ItemManager.Zanite});
        craftings.put(new ItemStack(ItemManager.GravititeGlove), new Object[] {"C C", 'C', BlockManager.EnchantedGravitite});
        craftings.put(new ItemStack(ItemManager.IronRing), new Object[] {" C ", "C C", " C ", 'C', Item.IRON_INGOT});
        craftings.put(new ItemStack(ItemManager.GoldRing), new Object[] {" C ", "C C", " C ", 'C', Item.GOLD_INGOT});
        craftings.put(new ItemStack(ItemManager.ZaniteRing), new Object[] {" C ", "C C", " C ", 'C', ItemManager.Zanite});
        craftings.put(new ItemStack(ItemManager.IronPendant), new Object[] {"SSS", "S S", " C ", 'S', Item.STRING, 'C', Item.IRON_INGOT});
        craftings.put(new ItemStack(ItemManager.GoldPendant), new Object[] {"SSS", "S S", " C ", 'S', Item.STRING, 'C', Item.GOLD_INGOT});
        craftings.put(new ItemStack(ItemManager.ZanitePendant), new Object[] {"SSS", "S S", " C ", 'S', Item.STRING, 'C', ItemManager.Zanite});
	}
}
