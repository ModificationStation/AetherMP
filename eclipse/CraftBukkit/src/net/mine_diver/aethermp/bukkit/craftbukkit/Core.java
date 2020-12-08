package net.mine_diver.aethermp.bukkit.craftbukkit;

import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.java.JavaPlugin;

import net.mine_diver.aethermp.bukkit.craftbukkit.listener.PlayerListener;
import net.minecraft.server.mod_AetherMp;

public class Core extends JavaPlugin {

	@Override
	public void onDisable() {
		mod_AetherMp.CORE.LOGGER.info("Disabling plugin...");
	}

	@Override
	public void onEnable() {
		mod_AetherMp.CORE.LOGGER.info("Enabling plugin...");
		this.getServer().getPluginManager().registerEvent(Type.PLAYER_QUIT, new PlayerListener(), Priority.Monitor, this);
	}
}
