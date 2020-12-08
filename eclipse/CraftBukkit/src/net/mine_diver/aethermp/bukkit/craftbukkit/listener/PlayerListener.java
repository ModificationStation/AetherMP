package net.mine_diver.aethermp.bukkit.craftbukkit.listener;

import org.bukkit.event.player.PlayerQuitEvent;

import net.minecraft.server.mod_AetherMp;

public class PlayerListener extends org.bukkit.event.player.PlayerListener {
	
	@Override
	public void onPlayerQuit(PlayerQuitEvent event) {
		mod_AetherMp.CORE.LOGGER.info("Gottem");
	}
}
