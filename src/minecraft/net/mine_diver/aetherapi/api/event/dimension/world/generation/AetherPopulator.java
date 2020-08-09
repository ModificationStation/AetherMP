package net.mine_diver.aetherapi.api.event.dimension.world.generation;

import java.util.Random;

import net.mine_diver.aetherapi.api.event.AetherEvent;
import net.mine_diver.aetherapi.api.event.Event;
import net.minecraft.src.World;

public interface AetherPopulator {
	
	Event<AetherPopulator> EVENT = new AetherEvent<>(AetherPopulator.class, (listeners) ->
	(world, random, i, j) -> {
		for (AetherPopulator listener : listeners)
			listener.GenerateAether(world, random, i, j);
		});
	
	void GenerateAether(World world, Random random, int i, int j);
}
