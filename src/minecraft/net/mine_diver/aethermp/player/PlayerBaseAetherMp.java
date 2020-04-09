package net.mine_diver.aethermp.player;

import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.mine_diver.aethermp.Core;
import net.mine_diver.aethermp.proxy.PrintStreamFilter;
import net.mine_diver.aethermp.proxy.SaveHandlerProxy;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.ISaveHandler;
import net.minecraft.src.PlayerBaseAether;
import net.minecraft.src.SaveHandlerMP;
import net.minecraft.src.World;

import static net.minecraft.src.mod_AetherMp.PackageAccess;

public class PlayerBaseAetherMp extends PlayerBaseAether {
	
	public PlayerBaseAetherMp(EntityPlayerSP player) {
		super(checkIfMultiplayer(player));
		if (restore) {
			try {
				saveHandlerField.set(player.worldObj, saveHandlerMp);
				System.setOut(out);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			restore = false;
		}
	}
	
	@Override
	public boolean onLivingUpdate() {
		if (!player.worldObj.multiplayerWorld)
			return super.onLivingUpdate();
		return false;
	}
	
	private static EntityPlayerSP checkIfMultiplayer(EntityPlayerSP player) {
		ISaveHandler savehandler = PackageAccess.World.getSaveHandler(player.worldObj);
		if (savehandler instanceof SaveHandlerMP) {
			saveHandlerMp = (SaveHandlerMP) savehandler;
			out = System.out;
			restore = true;
			try {
				saveHandlerField.set(player.worldObj, Core.unsafe.allocateInstance(SaveHandlerProxy.class));
				System.setOut(new PrintStreamFilter());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return player;
	}
	
	private static SaveHandlerMP saveHandlerMp;
	private static PrintStream out;
	private static boolean restore = false;
	private static final Field saveHandlerField;
	static {
		Field field = null;
		try {
			field = World.class.getDeclaredField("w");
		} catch (Exception e) {
			try {
				field = World.class.getDeclaredField("saveHandler");
			} catch (Exception e1) {
				throw new RuntimeException(e1);
			}
		}
		field.setAccessible(true);
		Field modifiersField = null;
		try {
			modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
		} catch (Exception e) {
			throw new RuntimeException(e);
 		}
		saveHandlerField = field;
	}
}
