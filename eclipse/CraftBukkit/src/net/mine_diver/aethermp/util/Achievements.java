package net.mine_diver.aethermp.util;

import net.mine_diver.aethermp.blocks.BlockManager;
import net.mine_diver.aethermp.items.ItemManager;
import net.minecraft.server.Achievement;
import net.minecraft.server.Block;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.ModLoaderMp;
import net.minecraft.server.Packet230ModLoader;
import net.minecraft.server.mod_AetherMp;

public class Achievements {
	
	public static final Achievement
	enterAether = (new Achievement(800, "enterAether", 0, 0, Block.GLOWSTONE, null)).c(),
    defeatBronze = (new Achievement(801, "defeatBronze", -2, 3, new ItemStack(ItemManager.Key, 1, 0), enterAether)).c(),
    defeatSilver = (new Achievement(802, "defeatSilver", 0, 4, new ItemStack(ItemManager.Key, 1, 1), enterAether)).c(),
    defeatGold = (new Achievement(803, "defeatGold", 2, 3, new ItemStack(ItemManager.Key, 1, 2), enterAether)).c(),
    enchanter = (new Achievement(804, "enchanter", 2, 1, BlockManager.Enchanter, enterAether)).c(),
    //incubator = (new Achievement(805, "incubator", 2, -1, BlockManager.Incubator, enterAether)).c(),
    blueCloud = (new Achievement(806, "blueCloud", -2, -1, new ItemStack(BlockManager.Aercloud, 1, 1), enterAether)).c(),
    flyingPig = (new Achievement(807, "flyingPig", -2, 1, Item.SADDLE, enterAether)).c(),
    gravTools = (new Achievement(808, "gravTools", -1, -3, ItemManager.PickGravitite, enterAether)).c(),
    lore = (new Achievement(809, "lore", 1, -3, Item.BOOK, enterAether)).c(),
    loreception = (new Achievement(810, "loreception", 1, -5, Item.BOOK, lore)).c();
	
	public static void giveAchievement(Achievement achievement, EntityPlayer entityplayer) {
		Packet230ModLoader packet = new Packet230ModLoader();
		packet.packetType = 4;
		packet.dataInt = new int[] {achievement.e};
		ModLoaderMp.SendPacketTo(ModLoaderMp.GetModInstance(mod_AetherMp.class), entityplayer, packet);
	}
}
