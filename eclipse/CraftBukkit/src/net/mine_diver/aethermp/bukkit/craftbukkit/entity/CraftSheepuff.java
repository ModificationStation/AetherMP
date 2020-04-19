package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import org.bukkit.DyeColor;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Sheep;

import net.mine_diver.aethermp.bukkit.entity.Sheepuff;
import net.mine_diver.aethermp.entities.EntitySheepuff;

public class CraftSheepuff extends AbstractAetherAnimal implements Sheepuff, Sheep {

	public CraftSheepuff(CraftServer server, EntitySheepuff entity) {
		super(server, entity);
	}

	@Override
	public boolean isSheared() {
		return ((EntitySheepuff)getHandle()).getSheared();
	}

	@Override
	public void setSheared(boolean sheared) {
		((EntitySheepuff)getHandle()).setSheared(sheared);
	}

	@Override
	public DyeColor getColor() {
		return DyeColor.getByData((byte) ((EntitySheepuff)getHandle()).getFleeceColor());
	}

	@Override
	public boolean isPuffed() {
		return ((EntitySheepuff)getHandle()).getPuffed();
	}

	@Override
	public void setPuffed(boolean puffed) {
		((EntitySheepuff)getHandle()).setPuffed(puffed);
	}

	@Override
	public void setColor(DyeColor color) {
		((EntitySheepuff)getHandle()).setFleeceColor(color.getData());
	}
	
	public String toString() {
		return "CraftSheepuff";
	}
}
