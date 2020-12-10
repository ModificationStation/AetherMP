package net.mine_diver.aethermp.entities;

import org.bukkit.entity.Entity;

import net.mine_diver.aethermp.bukkit.craftbukkit.entity.FakeCraftPlayer;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.ItemInWorldManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.World;

public class FakeEntityPlayer extends EntityPlayer {

	public FakeEntityPlayer(MinecraftServer minecraftserver, World world, String s,
			ItemInWorldManager iteminworldmanager) {
		super(minecraftserver, world, s, iteminworldmanager);
	}
	
	@Override
	public Entity getBukkitEntity() {
		if (bukkitEntity == null)
			bukkitEntity = new FakeCraftPlayer(world.getServer(), this);
		return super.getBukkitEntity();
	}
}
