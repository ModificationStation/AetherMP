package net.mine_diver.aethermp.util;

import net.minecraft.src.Achievement;
import net.minecraft.src.AetherAchievements;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.ModLoader;
import net.minecraft.src.Packet200Statistic;

public class AchievementHandler {
	
	public static void handleAchievement(Achievement achievement, boolean local) {
		EntityPlayerSP player = ModLoader.getMinecraftInstance().thePlayer;
		if (player instanceof EntityClientPlayerMP) {
			if (ModLoader.getMinecraftInstance().statFileWriter.hasAchievementUnlocked(achievement)) {
				if (!local && achievement.statId == AetherAchievements.enterAether.statId)
					ModLoader.getMinecraftInstance().theWorld.playSoundAtEntity(player, "random.pop", 0.2F, 1.0F);
			} else {
				Packet200Statistic packet = new Packet200Statistic();
				packet.field_27052_a = achievement.statId;
				packet.field_27051_b = 1;
	            ModLoader.getMinecraftInstance().sndManager.playSoundFX("aether.sound.other.achievement.achievementGen", 1.0F, 1.0F);
				packet.processPacket(((EntityClientPlayerMP) player).sendQueue);
				if (!local && achievement.statId == AetherAchievements.enterAether.statId)
					ModLoader.getMinecraftInstance().theWorld.playSoundAtEntity(player, "random.pop", 0.2F, 1.0F);
			}
		}
	}
}
