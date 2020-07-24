package net.mine_diver.aethermp;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import org.lwjgl.input.Keyboard;

import sun.misc.Unsafe;
import net.mine_diver.aethermp.blocks.BlockManager;
import net.mine_diver.aethermp.entities.EntityManager;
import net.mine_diver.aethermp.gui.GuiManager;
import net.mine_diver.aethermp.items.ItemManager;
import net.mine_diver.aethermp.network.NetClientHandlerAether;
import net.mine_diver.aethermp.network.PacketManager;
import net.mine_diver.aethermp.player.OtherPlayerMPAPI;
import net.mine_diver.aethermp.player.OtherPlayerMPBaseAether;
import net.mine_diver.aethermp.player.PlayerBaseAetherMp;
import net.mine_diver.aethermp.proxy.AchievementsMapProxy;
import net.mine_diver.aethermp.proxy.GuiIngameAetherMp;
import net.mine_diver.aethermp.render.RenderManager;
import net.mine_diver.aethermp.util.AchievementHandler;
import net.mine_diver.aethermp.util.AetherPoisonMp;
import net.minecraft.client.Minecraft;
import net.minecraft.src.AetherAchievements;
import net.minecraft.src.AetherItems;
import net.minecraft.src.BaseMod;
import net.minecraft.src.BaseModMp;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityOtherPlayerMP;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.GuiConnecting;
import net.minecraft.src.GuiInventory;
import net.minecraft.src.GuiInventoryMoreSlots;
import net.minecraft.src.GuiMainMenu;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.InventoryAether;
import net.minecraft.src.ItemStack;
import net.minecraft.src.KeyBinding;
import net.minecraft.src.ModLoader;
import net.minecraft.src.ModLoaderMp;
import net.minecraft.src.Packet230ModLoader;
import net.minecraft.src.PlayerAPI;
import net.minecraft.src.PlayerBaseAether;
import net.minecraft.src.Render;
import net.minecraft.src.StatBase;
import net.minecraft.src.StatFileWriter;
import net.minecraft.src.mod_Aether;
import net.minecraft.src.mod_AetherMp;

import static net.minecraft.src.mod_AetherMp.PackageAccess;

public class Core {
	
	@SuppressWarnings("unchecked")
	public void postInit(BaseModMp mod, BaseMod aetherInstance) {
		ModLoader.SetInGameHook(aetherInstance, false, false);
		ModLoader.SetInGUIHook(mod, true, false);
		ModLoader.SetInGameHook(mod, true, false);
		for (int i = 0; i < PlayerAPI.playerBaseClasses.size(); i++)
			if (PlayerAPI.playerBaseClasses.get(i).equals(PlayerBaseAether.class))
				PlayerAPI.playerBaseClasses.set(i, PlayerBaseAetherMp.class);
		GuiManager.registerGuis(mod);
		BlockManager.registerBlocks(aetherInstance);
		ItemManager.registerItems(aetherInstance);
		EntityManager.registerEntities();
		OtherPlayerMPAPI.RegisterPlayerBase(OtherPlayerMPBaseAether.class);
		try {
			Map<KeyBinding, boolean[]> keyList = ((Map<BaseMod, Map<KeyBinding, boolean[]>>) ModLoader.getPrivateValue(ModLoader.class, null, "keyList")).get(aetherInstance);
			key_loreGain = (KeyBinding) ModLoader.getPrivateValue(mod_Aether.class, aetherInstance, "key_loreGain");
			ModLoader.RegisterKey(mod, key_loreGain, keyList.get(key_loreGain)[0]);
			keyList.remove(key_loreGain);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void addRenderer(Map<Class<? extends Entity>, Render> entityRenderers) {
		RenderManager.registerRenderers(entityRenderers);
	}
	
	public boolean onTickInGUI(Minecraft minecraft, GuiScreen guiscreen) {
		if (firstTick) {
			firstTick = false;
			try {
				@SuppressWarnings("unchecked")
				Map<StatBase, Integer> map = (Map<StatBase, Integer>) field_25102_aField.get(ModLoader.getMinecraftInstance().statFileWriter);
				Map<StatBase, Integer> proxy = new AchievementsMapProxy<StatBase, Integer>();
				proxy.putAll(map);
				field_25102_aField.set(ModLoader.getMinecraftInstance().statFileWriter, proxy);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		if (guiscreen instanceof GuiConnecting && PackageAccess.GuiConnecting.getNetClientHandler((GuiConnecting) guiscreen) != null) {
			GuiConnecting guiconnecting = (GuiConnecting) guiscreen;
			NetClientHandlerAether ncha;
			try {
				ncha = (NetClientHandlerAether) unsafe.allocateInstance(NetClientHandlerAether.class);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			ncha.initSuper(PackageAccess.GuiConnecting.getNetClientHandler(guiconnecting));
			PackageAccess.GuiConnecting.setNetClientHandler(guiconnecting, ncha);
		}
		if (guiscreen instanceof GuiMainMenu && !(minecraft.ingameGUI instanceof GuiIngameAetherMp))
			minecraft.ingameGUI = new GuiIngameAetherMp(minecraft);
		return true;
	}
	
	public boolean onTickInGame(Minecraft minecraft, BaseMod aetherInstance) {
		EntityPlayerSP player = minecraft.thePlayer;
		if (player.worldObj.multiplayerWorld) {
			if (mod_Aether.getCurrentDimension() == 3)
                AchievementHandler.handleAchievement(AetherAchievements.enterAether, true);
			if (!(minecraft.ingameGUI instanceof GuiIngameAetherMp))
				minecraft.ingameGUI = new GuiIngameAetherMp(minecraft);
			if (ModLoader.isGUIOpen(null))
	            renderHearts((mod_Aether) aetherInstance);
			if (minecraft.currentScreen instanceof GuiInventory)
	            minecraft.displayGuiScreen(new GuiInventoryMoreSlots(minecraft.thePlayer));
			AetherPoisonMp.tickRender(minecraft);
			renderBossHP((mod_Aether) aetherInstance);
			long time = minecraft.theWorld.getWorldTime();
			if (clock != time) {
				InventoryAether inv = mod_Aether.getPlayer(player).inv;
				if(player.inventory.armorInventory[3] != null && player.inventory.armorInventory[3].itemID == AetherItems.PhoenixHelm.shiftedIndex && player.inventory.armorInventory[2] != null && player.inventory.armorInventory[2].itemID == AetherItems.PhoenixBody.shiftedIndex && player.inventory.armorInventory[1] != null && player.inventory.armorInventory[1].itemID == AetherItems.PhoenixLegs.shiftedIndex && player.inventory.armorInventory[0] != null && player.inventory.armorInventory[0].itemID == AetherItems.PhoenixBoots.shiftedIndex && inv.slots[6] != null && inv.slots[6].itemID == AetherItems.PhoenixGlove.shiftedIndex) {
	                player.fire = 0;
	                if(!GuiMainMenu.mmactive)
	                    player.worldObj.spawnParticle("flame", player.posX + player.getRandom().nextGaussian() / 5D, (player.posY - 0.5D) + player.getRandom().nextGaussian() / 5D, player.posZ + player.getRandom().nextGaussian() / 3D, 0.0D, 0.0D, 0.0D);
				}
				for (EntityOtherPlayerMP otherplayer : OtherPlayerMPAPI.playerBases.keySet()) {
					ItemStack[] otherinv = getPlayer(otherplayer).inv;
					if(otherplayer.inventory.armorInventory[3] != null && otherplayer.inventory.armorInventory[3].itemID == AetherItems.PhoenixHelm.shiftedIndex && otherplayer.inventory.armorInventory[2] != null && otherplayer.inventory.armorInventory[2].itemID == AetherItems.PhoenixBody.shiftedIndex && otherplayer.inventory.armorInventory[1] != null && otherplayer.inventory.armorInventory[1].itemID == AetherItems.PhoenixLegs.shiftedIndex && otherplayer.inventory.armorInventory[0] != null && otherplayer.inventory.armorInventory[0].itemID == AetherItems.PhoenixBoots.shiftedIndex && otherinv[1] != null && otherinv[1].itemID == AetherItems.PhoenixGlove.shiftedIndex) {
						otherplayer.fire = 0;
		                otherplayer.worldObj.spawnParticle("flame", otherplayer.posX + PackageAccess.Entity.getRand(otherplayer).nextGaussian() / 5D, otherplayer.posY + PackageAccess.Entity.getRand(otherplayer).nextGaussian() / 5D + 1, otherplayer.posZ + PackageAccess.Entity.getRand(otherplayer).nextGaussian() / 3D, 0.0D, 0.0D, 0.0D);
					}
				}
				if(player.inventory.armorInventory[3] != null && player.inventory.armorInventory[3].itemID == AetherItems.GravititeHelmet.shiftedIndex && player.inventory.armorInventory[2] != null && player.inventory.armorInventory[2].itemID == AetherItems.GravititeBodyplate.shiftedIndex && player.inventory.armorInventory[1] != null && player.inventory.armorInventory[1].itemID == AetherItems.GravititePlatelegs.shiftedIndex && player.inventory.armorInventory[0] != null && player.inventory.armorInventory[0].itemID == AetherItems.GravititeBoots.shiftedIndex && inv.slots[6] != null && inv.slots[6].itemID == AetherItems.GravititeGlove.shiftedIndex && PackageAccess.EntityLiving.getIsJumping(player) && !jumpBoosted) {
		            player.motionY = 1.0D;
		            jumpBoosted = true;
		        }
				if(!PackageAccess.EntityLiving.getIsJumping(player) && player.onGround)
		            jumpBoosted = false;
				if(inv.slots[3] != null && inv.slots[3].itemID == AetherItems.IronBubble.shiftedIndex || inv.slots[7] != null && inv.slots[7].itemID == AetherItems.IronBubble.shiftedIndex)
	                player.air = 20;
				if((inv.slots[3] != null && inv.slots[3].itemID == AetherItems.GoldenFeather.shiftedIndex || inv.slots[7] != null && inv.slots[7].itemID == AetherItems.GoldenFeather.shiftedIndex) && !player.onGround && player.motionY < 0.0D && !PackageAccess.Entity.getInWater(player))
	                player.motionY *= 0.59999999999999998D;
				if(inv.slots[1] != null && inv.slots[1].itemID == AetherItems.AgilityCape.shiftedIndex)
	                player.stepHeight = 1.0F;
	            else
	                player.stepHeight = 0.5F;
				clock = time;
			}
			return true;
		} else 
			return aetherInstance.OnTickInGame(minecraft);
	}
	
	public void keyboardEvent(KeyBinding event, BaseMod aetherInstance) {
		if (event.equals(key_loreGain) && ModLoader.getMinecraftInstance().thePlayer.worldObj.multiplayerWorld) {
			if (!Keyboard.areRepeatEventsEnabled())
				ModLoaderMp.SendKey(ModLoaderMp.GetModInstance(mod_AetherMp.class), 0);
		} else
			aetherInstance.KeyboardEvent(event);
	}
	
	public GuiScreen handleGui(int ID) {
		return GuiManager.handleGui(ID);
	}
	
	public void handlePacket(Packet230ModLoader packet) {
        PacketManager.handlePacket(packet);
	}
	
	public void renderHearts(mod_Aether aetherInstance) {
		try {
			renderHeartsMethod.invoke(aetherInstance, new Object[] {});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void renderBossHP(mod_Aether aetherInstance) {
		try {
			renderBossHPMethod.invoke(aetherInstance, new Object[] {});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static OtherPlayerMPBaseAether getPlayer(EntityPlayer entityplayer) {
		return (OtherPlayerMPBaseAether) OtherPlayerMPAPI.getPlayerBase((EntityOtherPlayerMP) entityplayer, OtherPlayerMPBaseAether.class);
	}
	
	private boolean firstTick = true;
	private boolean jumpBoosted;
	private long clock;
	private KeyBinding key_loreGain;
	
	private static final Field field_25102_aField;
	private static final Method renderHeartsMethod;
	private static final Method renderBossHPMethod;
	static {
		Field field = null;
		try {
			field = StatFileWriter.class.getDeclaredField("a");
		} catch (Exception e) {
			try {
				field = StatFileWriter.class.getDeclaredField("field_25102_a");
			} catch (Exception e1) {
				throw new RuntimeException(e1);
			}
		}
		field.setAccessible(true);
		field_25102_aField = field;
		Method method = null;
		try {
			method = mod_Aether.class.getDeclaredMethod("renderHearts", new Class[] {});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		method.setAccessible(true);
		renderHeartsMethod = method;
		try {
			method = mod_Aether.class.getDeclaredMethod("renderBossHP", new Class[] {});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		method.setAccessible(true);
		renderBossHPMethod = method;
	}
	
	public static final Unsafe unsafe;
	static {
		Unsafe instance = null;
		try {
			Field field = Unsafe.class.getDeclaredField("theUnsafe");
			field.setAccessible(true);
			instance = (Unsafe) field.get(null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		unsafe = instance;
	}
}
