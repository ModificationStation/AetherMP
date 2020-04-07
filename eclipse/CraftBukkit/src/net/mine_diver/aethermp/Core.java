package net.mine_diver.aethermp;

import net.mine_diver.aethermp.blocks.BlockManager;
import net.mine_diver.aethermp.crafting.WorkbenchManager;
import net.mine_diver.aethermp.dimension.DimensionManager;
import net.mine_diver.aethermp.entities.EntityManager;
import net.mine_diver.aethermp.items.ItemManager;
import net.mine_diver.aethermp.player.PlayerBaseAether;
import net.mine_diver.aethermp.util.BlockPlacementHandler;
import net.minecraft.server.PlayerAPI;
import net.minecraft.server.SAPI;

public class Core {
	
	public void postInit() {
		BlockManager.registerBlocks();
		ItemManager.registerItems();
		WorkbenchManager.registerRecipes();
		EntityManager.registerEntities();
		SAPI.interceptAdd(new BlockPlacementHandler());
		PlayerAPI.RegisterPlayerBase(PlayerBaseAether.class);
		DimensionManager.registerDimensions();
	}
}
