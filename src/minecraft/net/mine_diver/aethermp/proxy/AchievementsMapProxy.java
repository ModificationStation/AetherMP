package net.mine_diver.aethermp.proxy;

import java.util.HashMap;

import net.minecraft.src.Achievement;
import net.minecraft.src.ModLoader;
import net.minecraft.src.mod_Aether;

public class AchievementsMapProxy<K, V> extends HashMap<K, V> {
	
	private static final long serialVersionUID = 1204728830661192969L;
	
	@Override
	public boolean containsKey(Object key) {
		if (ModLoader.getMinecraftInstance().thePlayer != null && ModLoader.getMinecraftInstance().thePlayer.worldObj != null && !ModLoader.getMinecraftInstance().thePlayer.worldObj.multiplayerWorld)
			return super.containsKey(key);
		StackTraceElement element = new Exception().getStackTrace()[2];
		if (element.getClassName().equals(mod_Aether.class.getName()) && element.getMethodName().equals("giveAchievement") && key instanceof Achievement)
			return true;
		else
			return super.containsKey(key);
	}
}
