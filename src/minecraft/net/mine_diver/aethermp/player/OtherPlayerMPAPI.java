package net.mine_diver.aethermp.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.src.EntityOtherPlayerMP;

public class OtherPlayerMPAPI {
	
	public static void RegisterPlayerBase(Class<? extends OtherPlayerMPBase> playerBaseClass) {
		playerBaseClasses.add(playerBaseClass);
	}
	
	public static OtherPlayerMPBase getPlayerBase(EntityOtherPlayerMP entityotherplayermp, Class<? extends OtherPlayerMPBase> playerBaseClass) {
		for (OtherPlayerMPBase playerBase : playerBases.get(entityotherplayermp))
			if (playerBaseClass.isInstance(playerBase))
				return playerBase;
		return null;
	}
	
	public static boolean outfitWithItem(EntityOtherPlayerMP entityotherplayermp, int slot, int ID, int meta) {
		checkAndInit(entityotherplayermp);
		boolean flag = false;
		for (OtherPlayerMPBase playerBase : playerBases.get(entityotherplayermp))
			if (playerBase.outfitWithItem(slot, ID, meta))
				flag = true;
		return flag;
	}
	
	public static void checkAndInit(EntityOtherPlayerMP entityotherplayermp) {
		if (!playerBases.containsKey(entityotherplayermp))
			playerBases.put(entityotherplayermp, playerInit(entityotherplayermp));
	}
	
	public static List<OtherPlayerMPBase> playerInit(EntityOtherPlayerMP entityotherplayermp) {
		List<OtherPlayerMPBase> playerBases = new ArrayList<OtherPlayerMPBase>();
        for (Class<? extends OtherPlayerMPBase> playerBaseClass : playerBaseClasses)
            try {
                playerBases.add(playerBaseClass.getDeclaredConstructor(EntityOtherPlayerMP.class).newInstance(entityotherplayermp));
            } catch (Exception e) {
            	throw new RuntimeException(e);
            }
        return playerBases;
	}
	
	public static List<Class<? extends OtherPlayerMPBase>> playerBaseClasses = new ArrayList<Class<? extends OtherPlayerMPBase>>();
	public static Map<EntityOtherPlayerMP, List<OtherPlayerMPBase>> playerBases = new HashMap<EntityOtherPlayerMP, List<OtherPlayerMPBase>>();
}
