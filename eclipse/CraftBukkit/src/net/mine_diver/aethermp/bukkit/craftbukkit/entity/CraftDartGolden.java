package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;

import net.mine_diver.aethermp.bukkit.entity.DartGolden;
import net.mine_diver.aethermp.entities.EntityDartGolden;

public class CraftDartGolden extends AbstractProjectileBase implements DartGolden {

	public CraftDartGolden(CraftServer server, EntityDartGolden entity) {
		super(server, entity);
	}
	
	@Override
    public String toString() {
        return "CraftDartGolden";
    }
}
