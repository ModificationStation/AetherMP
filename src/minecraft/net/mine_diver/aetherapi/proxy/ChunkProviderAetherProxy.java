package net.mine_diver.aetherapi.proxy;

import java.lang.reflect.Field;
import java.util.Random;

import net.mine_diver.aetherapi.event.dimension.world.generation.AetherPopulator;
import net.minecraft.src.ChunkProviderAether;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.World;

public class ChunkProviderAetherProxy extends ChunkProviderAether {

	public ChunkProviderAetherProxy(World world, long l) {
		super(world, l);
	}
	
	public void populate(IChunkProvider ichunkprovider, int i, int j) {
		super.populate(ichunkprovider, i, j);
		try {
			AetherPopulator.EVENT.getInvoker().GenerateAether((World) worldObjField.get(this), (Random) randomField.get(this), i, j);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static final Field worldObjField;
	private static final Field randomField;
	static {
		Field field = null;
		try {
			field = ChunkProviderAether.class.getDeclaredField("worldObj");
		} catch (NoSuchFieldException | SecurityException e) {
			throw new RuntimeException(e);
		}
		field.setAccessible(true);
		worldObjField = field;
		try {
			field = ChunkProviderAether.class.getDeclaredField("random");
		} catch (NoSuchFieldException | SecurityException e) {
			throw new RuntimeException(e);
		}
		field.setAccessible(true);
		randomField = field;
	}
}
