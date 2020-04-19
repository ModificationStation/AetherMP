package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;

import net.mine_diver.aethermp.bukkit.entity.FiroBall;
import net.mine_diver.aethermp.entities.EntityFiroBall;

public class CraftFiroBall extends CraftFlyingAether implements FiroBall {

	public CraftFiroBall(CraftServer server, EntityFiroBall entity) {
		super(server, entity);
	}

	@Override
	public boolean isFrosty() {
		return ((EntityFiroBall)getHandle()).frosty;
	}

	@Override
	public boolean isSmacked() {
		return ((EntityFiroBall)getHandle()).smacked;
	}

	@Override
	public boolean isFromCloud() {
		return ((EntityFiroBall)getHandle()).fromCloud;
	}


	@Override
	public int getTicksAlive() {
		return ((EntityFiroBall)getHandle()).life;
	}

	@Override
	public void setFrosty(boolean frosty) {
		((EntityFiroBall)getHandle()).frosty = frosty;
	}

	@Override
	public void setSmacked(boolean smacked) {
		((EntityFiroBall)getHandle()).smacked = smacked;
	}

	@Override
	public void setFromCloud(boolean fromCloud) {
		((EntityFiroBall)getHandle()).fromCloud = fromCloud;
	}

	@Override
	public void setTicksAlive(int life) {
		((EntityFiroBall)getHandle()).life = life;
	}
	
	@Override
	public String toString() {
		return "CraftFiroBall";
	}
}
