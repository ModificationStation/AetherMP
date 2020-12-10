package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import java.net.InetSocketAddress;

import org.bukkit.Achievement;
import org.bukkit.Effect;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.Statistic;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.map.MapView;

import net.minecraft.server.EntityPlayer;

public class FakeCraftPlayer extends CraftPlayer {

	public FakeCraftPlayer(CraftServer server, EntityPlayer entity) {
		super(server, entity);
	}
	
	@Override
	public InetSocketAddress getAddress() {
		return null;
	}
	
	@Override
	public void sendRawMessage(String message) {
		
	}
	
	@Override
	public void kickPlayer(String message) {
		
	}
	
	@Override
	public void setCompassTarget(Location loc) {
		
	}
	
	@Override
	public void chat(String msg) {
		
	}
	
	@Override
	public void playNote(Location loc, byte instrument, byte note) {
		
	}
	
	@Override
	public void playNote(Location loc, Instrument instrument, Note note) {
		
	}
	
	@Override
	public void playEffect(Location loc, Effect effect, int data) {
		
	}
	
	@Override
	public void sendBlockChange(Location loc, int material, byte data) {
		
	}
	
	@Override
	public boolean sendChunkChange(Location loc, int sx, int sy, int sz, byte[] data) {
		int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();

        int cx = x >> 4;
        int cz = z >> 4;

        if (sx <= 0 || sy <= 0 || sz <= 0) {
            return false;
        }

        if ((x + sx - 1) >> 4 != cx || (z + sz - 1) >> 4 != cz || y < 0 || y + sy > 128) {
            return false;
        }

        if (data.length != (sx * sy * sz * 5) / 2) {
            return false;
        }

        return true;
	}
	
	@Override
	public void sendMap(MapView map) {
		
	}
	
	@Override
	public boolean teleport(Location location) {
		// From = Players current Location
        Location from = this.getLocation();
        // To = Players new Location if Teleport is Successful
        Location to = location;
        // Create & Call the Teleport Event.
        PlayerTeleportEvent event = new PlayerTeleportEvent((Player) this, from, to);
        server.getPluginManager().callEvent(event);
        // Return False to inform the Plugin that the Teleport was unsuccessful/cancelled.
        return !event.isCancelled();
	}
	
	@Override
	public void awardAchievement(Achievement achievement) {
		
	}
	
	@Override
	public void incrementStatistic(Statistic statistic, int amount) {
		
	}
	
	@Override
	public void incrementStatistic(Statistic statistic, Material material, int amount) {
		
	}
}
