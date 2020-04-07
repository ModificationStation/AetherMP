package net.mine_diver.aethermp.proxy;

import java.io.File;

import net.minecraft.client.Minecraft;
import net.minecraft.src.ModLoader;
import net.minecraft.src.SaveHandler;

public class SaveHandlerProxy extends SaveHandler {
	
	public SaveHandlerProxy(File file, String s, boolean flag) {
		super(file, s, flag);
	}
	
	@Override
	protected File getSaveDirectory() {
		try {
			return new File(Minecraft.getMinecraftDir() + "/" + (File)ModLoader.getPrivateValue(ModLoader.class, null, "modDir") + "/aethermp/tmp/");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
