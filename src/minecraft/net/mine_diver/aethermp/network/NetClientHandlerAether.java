package net.mine_diver.aethermp.network;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.UnknownHostException;

import net.mine_diver.aethermp.player.OtherPlayerMPAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityOtherPlayerMP;
import net.minecraft.src.NetClientHandler;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet5PlayerInventory;

public class NetClientHandlerAether extends NetClientHandler {

	public NetClientHandlerAether(Minecraft minecraft, String s, int i) throws UnknownHostException, IOException {
		super(minecraft, s, i);
	}
	
	@Override
	public void handlePlayerInventory(Packet5PlayerInventory packet5playerinventory) {
        Entity entity;
		try {
			entity = (Entity) getEntityByIDMethod.invoke(this, packet5playerinventory.entityID);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		if (!(entity instanceof EntityOtherPlayerMP && OtherPlayerMPAPI.outfitWithItem((EntityOtherPlayerMP) entity, packet5playerinventory.slot, packet5playerinventory.itemID, packet5playerinventory.itemDamage)))
			super.handlePlayerInventory(packet5playerinventory);
    }
	
	private static final Method getEntityByIDMethod;
	
	static {
		Method method = null;
		try {
			method = new Object(){}.getClass().getEnclosingClass().getSuperclass().getDeclaredMethod("b", int.class);
		} catch (Exception e) {
			try {
				method = new Object(){}.getClass().getEnclosingClass().getSuperclass().getDeclaredMethod("getEntityByID", int.class);
			} catch (Exception e1) {
				throw new RuntimeException(e1);
			}
		}
		method.setAccessible(true);
		getEntityByIDMethod = method;
	}
	
	public void initSuper(NetClientHandler netclienthandler) {
		try {
			for (Field field : NetClientHandler.class.getDeclaredFields()) {
				field.setAccessible(true);
				field.set(this, field.get(netclienthandler));
				if (field.getName().equals("a") || field.getName().equals("netManager")) {
					NetworkManager netManager = (NetworkManager) field.get(this);
					Field netHandlerField;
					try {
						netHandlerField = netManager.getClass().getDeclaredField("p");
					} catch (Exception e) {
						netHandlerField = netManager.getClass().getDeclaredField("netHandler");
					}
					netHandlerField.setAccessible(true);
					netHandlerField.set(netManager, this);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}
