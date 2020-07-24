package net.mine_diver.aethermp.player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import net.mine_diver.aethermp.api.entities.IAetherBoss;
import net.mine_diver.aethermp.api.player.AdditionalHealth;
import net.mine_diver.aethermp.api.player.CanFightBoss;
import net.mine_diver.aethermp.api.player.PlayerApplicableInterface;
import net.mine_diver.aethermp.api.player.Poisonable;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.PlayerBase;

public class PlayerManager {

	public static void setCurrentBoss(EntityPlayer player, IAetherBoss boss) {
		getPlayerBasesWithFunction(player, CanFightBoss.class).forEach((func) -> func.setCurrentBoss(boss));
	}
	
	public static IAetherBoss getCurrentBoss(EntityPlayer player) {
		List<IAetherBoss> bosses = new ArrayList<IAetherBoss>();
		getPlayerBasesWithFunction(player, CanFightBoss.class).forEach((func) -> bosses.add(func.getCurrentBoss()));
		return bosses.get(0);
	}
	
	public static boolean afflictPoison(EntityPlayer player) {
		AtomicBoolean flag = new AtomicBoolean(false);
		getPlayerBasesWithFunction(player, Poisonable.class).forEach((func) -> {if (func.afflictPoison()) flag.set(true);});
		return flag.get();
	}
	
	public static void increaseMaxHP(EntityPlayer player, int HP) {
		getPlayerBasesWithFunction(player, AdditionalHealth.class).forEach((func) -> func.increaseMaxHP(HP));
	}
	
	public static boolean curePoison(EntityPlayer player) {
		AtomicBoolean flag = new AtomicBoolean(false);
		getPlayerBasesWithFunction(player, Poisonable.class).forEach((func) -> {if (func.curePoison()) flag.set(true);});
		return flag.get();
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends PlayerApplicableInterface> List<T> getPlayerBasesWithFunction(EntityPlayer player, Class<T> function) {
		List<T> list = new ArrayList<T>();
		((List<PlayerBase>)player.playerBases).forEach((PlayerBase playerbase) -> {if (function.isInstance(playerbase)) list.add((T) playerbase);});
		return list;
	}
}