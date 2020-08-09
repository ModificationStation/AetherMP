package net.mine_diver.aethermp.api.event.dimension.world.generation.dungeon;

import java.util.Random;

import net.mine_diver.aethermp.api.event.AetherEvent;
import net.mine_diver.aethermp.api.event.Event;
import net.mine_diver.aethermp.api.util.LootType;
import net.minecraft.server.ItemStack;

public interface DungeonLoot {
	
	Event<DungeonLoot> EVENT = new AetherEvent<>(DungeonLoot.class, (listeners) -> 
	(loot, type, random) -> {
		ItemStack ret = loot;
		for (DungeonLoot listener : listeners)
			ret = listener.getLoot(ret, type, random);
		return ret;
		});

	ItemStack getLoot(ItemStack loot, LootType type, Random random);
}
