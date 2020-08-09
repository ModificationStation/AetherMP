package net.mine_diver.aetherapi;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.mine_diver.aetherapi.impl.block.BlockManager;
import net.mine_diver.aetherapi.impl.item.ItemManager;
import net.mine_diver.aetherapi.impl.proxy.WorldProviderAetherProxy;
import net.minecraft.src.BaseMod;
import net.minecraft.src.DimensionBase;
import net.minecraft.src.mod_Aether;

public class Core {
	
	public void preInit() {
		net.mine_diver.aetherapi.api.block.BlockManager.INSTANCE.setHandler(new BlockManager());
		net.mine_diver.aetherapi.api.item.ItemManager.INSTANCE.setHandler(new ItemManager());
	}

	public void init() {
		try {
			Field dimField = mod_Aether.class.getDeclaredField("dim");
			dimField.setAccessible(true);
			DimensionBase aetherDim = (DimensionBase) dimField.get(null);
			Field worldProviderField = DimensionBase.class.getDeclaredField("worldProvider");
			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.set(worldProviderField, worldProviderField.getModifiers() & ~Modifier.FINAL);
			worldProviderField.set(aetherDim, WorldProviderAetherProxy.class);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void postInit(BaseMod aetherInstance) {
		BlockManager.registerBlocks(aetherInstance);
		ItemManager.registerItems(aetherInstance);
	}
}