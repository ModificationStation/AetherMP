package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;

import net.mine_diver.aethermp.bukkit.entity.DartEnchanted;
import net.mine_diver.aethermp.entities.EntityDartEnchanted;

public class CraftDartEnchanted extends CraftDartGolden implements DartEnchanted {

	public CraftDartEnchanted(CraftServer server, EntityDartEnchanted entity) {
		super(server, entity);
	}
	
	@Override
    public String toString() {
        return "CraftDartEnchanted";
    }
}
