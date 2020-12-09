package net.mine_diver.aethermp.bukkit.craftbukkit.listener;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import net.mine_diver.aethermp.api.entities.IAetherBoss;
import net.mine_diver.aethermp.blocks.BlockManager;
import net.mine_diver.aethermp.player.PlayerManager;
import net.minecraft.server.mod_AetherMp;

public class PlayerListener extends org.bukkit.event.player.PlayerListener {
	
	@Override
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if (player instanceof CraftPlayer) {
			IAetherBoss boss = PlayerManager.getCurrentBoss(((CraftPlayer) player).getHandle());
			if (boss != null) {
				boss.stopFight();
				if (mod_AetherMp.punishQuittingDuringFight)
					player.setHealth(0);
			}
		}
	}
	
	@Override
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		String command = event.getMessage();
		for (String allowedCommand : mod_AetherMp.CORE.dungeonAllowedCommands)
			if (command.equals(allowedCommand) || command.startsWith(allowedCommand + " "))
				return;
		Player player = event.getPlayer();
		World world = player.getWorld();
		Location location = event.getPlayer().getLocation();
		int Ox = location.getBlockX();
		int Oz = location.getBlockZ();
		int radius = mod_AetherMp.commandCancellationDistance;
		for (int x = Ox - radius; x < Ox + radius; x++)
			for (int y = 0; y < 128; y++)
				for (int z = Oz - radius; z < Oz + radius; z++) {
					int blockId = world.getBlockAt(x, y, z).getTypeId();
					if (blockId == BlockManager.LockedDungeonStone.id || blockId == BlockManager.LockedLightDungeonStone.id) {
						event.setCancelled(true);
						player.sendMessage("It seems like my magic doesn't work near a locked dungeon!");
						return;
					}
				}
	}
	
	@Override
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		Player player = event.getPlayer();
		if (player instanceof CraftPlayer) {
			IAetherBoss boss = PlayerManager.getCurrentBoss(((CraftPlayer) player).getHandle());
			if (boss != null) {
				if (mod_AetherMp.preventTeleportDuringFight)
					event.setCancelled(true);
				if (mod_AetherMp.punishTeleportDuringFight)
					player.setHealth(0);
			}
		}
	}
	
	@Override
	public void onPlayerPortal(PlayerPortalEvent event) {
		onPlayerTeleport(event);
	}
}
