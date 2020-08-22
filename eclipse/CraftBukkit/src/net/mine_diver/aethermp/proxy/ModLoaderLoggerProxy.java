package net.mine_diver.aethermp.proxy;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.logging.Logger;

import net.minecraft.server.ModLoader;
import net.minecraft.server.mod_AetherMp;

public class ModLoaderLoggerProxy extends Logger {

	public ModLoaderLoggerProxy() {
		super("ModLoader", null);
		try {
			modLoaderLogger = (Logger) loggerField.get(null);
			loggerField.set(null, this);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void fine(String msg) {
		if (msg.equals("Initialized")) {
			mod_AetherMp.CORE.init();
			try {
				loggerField.set(null, modLoaderLogger);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
			modLoaderLogger.fine(msg);
		} else
			super.fine(msg);
	}
	
	private final Logger modLoaderLogger;
	private static final Field loggerField;
	static {
		Field field = null;
		try {
			field = ModLoader.class.getDeclaredField("logger");
			field.setAccessible(true);
			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.set(field, field.getModifiers() & ~Modifier.FINAL);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		loggerField = field;
	}
}
