package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftEntity;

import net.mine_diver.aethermp.bukkit.entity.FloatingBlock;
import net.mine_diver.aethermp.entities.EntityFloatingBlock;

public class CraftFloatingBlock extends CraftEntity implements FloatingBlock {

	public CraftFloatingBlock(CraftServer server, EntityFloatingBlock entity) {
		super(server, entity);
	}
	
	@Override
	public int getTypeId() {
		return ((EntityFloatingBlock)getHandle()).blockID;
	}
	
	@Override
	public String toString() {
		return "CraftFloatingBlock";
	}
}
