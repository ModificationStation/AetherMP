package net.mine_diver.aetherapi.api.event.dimension.world.generation.dungeon;

import java.util.Random;

import net.mine_diver.aetherapi.api.event.AetherEvent;
import net.mine_diver.aetherapi.api.event.Event;
import net.mine_diver.aetherapi.api.util.LootType;
import net.minecraft.src.ItemStack;

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
