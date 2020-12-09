package net.mine_diver.aethermp.bukkit.craftbukkit;

import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginManager;
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
		PluginManager pm = getServer().getPluginManager();
		PlayerListener pListener = new PlayerListener();
		pm.registerEvent(Type.PLAYER_QUIT, pListener, Priority.Monitor, this);
		pm.registerEvent(Type.PLAYER_COMMAND_PREPROCESS, pListener, Priority.Highest, this);
		pm.registerEvent(Type.PLAYER_TELEPORT, pListener, Priority.Highest, this);
		pm.registerEvent(Type.PLAYER_PORTAL, pListener, Priority.Highest, this);
	}
}
