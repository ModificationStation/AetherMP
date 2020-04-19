package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;

import net.mine_diver.aethermp.bukkit.entity.Sentry;
import net.mine_diver.aethermp.entities.EntitySentry;

public class CraftSentry extends CraftDungeonMob implements Sentry {

	public CraftSentry(CraftServer server, EntitySentry entity) {
		super(server, entity);
	}

	@Override
	public boolean isOn() {
		return ((EntitySentry)getHandle()).aa().b(17) == 1;
	}
	
	@Override
	public void shutdown() {
		((EntitySentry)getHandle()).shutdown();
	}
	
	@Override
    public String toString() {
        return "CraftSentry";
    }
}
