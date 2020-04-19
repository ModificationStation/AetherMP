package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;

import net.mine_diver.aethermp.bukkit.entity.DartPoison;
import net.mine_diver.aethermp.entities.EntityDartPoison;

public class CraftDartPoison extends CraftDartGolden implements DartPoison {

	public CraftDartPoison(CraftServer server, EntityDartPoison entity) {
		super(server, entity);
	}
	
	@Override
    public String toString() {
        return "CraftDartPoison";
    }
}
