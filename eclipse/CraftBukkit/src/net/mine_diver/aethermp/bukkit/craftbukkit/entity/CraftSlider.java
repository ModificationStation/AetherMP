package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import net.mine_diver.aethermp.bukkit.entity.Slider;
import net.mine_diver.aethermp.entities.EntitySlider;

public class CraftSlider extends CraftFlyingAether implements Slider {

	public CraftSlider(CraftServer server, EntitySlider entity) {
		super(server, entity);
	}

	@Override
	public LivingEntity getTarget() {
		return (LivingEntity) ((EntitySlider) getHandle()).target.getBukkitEntity();
	}

	@Override
	public void setTarget(LivingEntity entity) {
		((EntitySlider) getHandle()).target = ((CraftEntity) entity).getHandle();
	}

	@Override
	public int getBossHP() {
		return ((EntitySlider) getHandle()).getBossHP();
	}

	@Override
	public int getBossMaxHP() {
		return ((EntitySlider) getHandle()).getBossMaxHP();
	}

	@Override
	public boolean isCurrentBoss(Player player) {
		return ((EntitySlider) getHandle()).isCurrentBoss(((CraftPlayer) player).getHandle());
	}

	@Override
	public int getBossEntityID() {
		return ((EntitySlider) getHandle()).getBossEntityID();
	}

	@Override
	public String getBossTitle() {
		return ((EntitySlider) getHandle()).getBossTitle();
	}
	
	@Override
	public void stopFight() {
		((EntitySlider) getHandle()).stopFight();
	}
	
	@Override
	public String toString() {
		return "CraftSlider";
	}
}
