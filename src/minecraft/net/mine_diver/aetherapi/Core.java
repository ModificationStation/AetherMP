package net.mine_diver.aetherapi;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.mine_diver.aetherapi.proxy.WorldProviderAetherProxy;
import net.minecraft.src.DimensionBase;
import net.minecraft.src.mod_Aether;

public class Core {

	public void postInit() {
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
}