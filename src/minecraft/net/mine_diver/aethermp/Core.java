package net.mine_diver.aethermp;

import java.lang.reflect.Field;
import java.util.Map;
import sun.misc.Unsafe;
import net.mine_diver.aethermp.blocks.BlockManager;
import net.mine_diver.aethermp.entities.EntityManager;
import net.mine_diver.aethermp.gui.GuiManager;
import net.mine_diver.aethermp.network.NetClientHandlerAether;
import net.mine_diver.aethermp.network.PacketManager;
import net.mine_diver.aethermp.player.OtherPlayerMPAPI;
import net.mine_diver.aethermp.player.OtherPlayerMPBaseAether;
import net.mine_diver.aethermp.player.PlayerBaseAetherMp;
import net.mine_diver.aethermp.proxy.GuiIngameAetherMp;
import net.mine_diver.aethermp.render.RenderManager;
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
import net.minecraft.src.GuiMainMenu;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.InventoryAether;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.Packet230ModLoader;
import net.minecraft.src.PlayerAPI;
import net.minecraft.src.PlayerBaseAether;
import net.minecraft.src.Render;
import net.minecraft.src.mod_Aether;

import static net.minecraft.src.mod_AetherMp.PackageAccess;

public class Core {
	
	@SuppressWarnings("unchecked")
	public void postInit(BaseModMp mod, BaseMod aetherInstance) {
		ModLoader.SetInGameHook(aetherInstance, false, false);
		ModLoader.SetInGUIHook(mod, true, false);
		ModLoader.SetInGameHook(mod, true, false);
		AetherAchievements.enterAether.setIndependent();
		for (int i = 0; i < PlayerAPI.playerBaseClasses.size(); i++)
			if (PlayerAPI.playerBaseClasses.get(i).equals(PlayerBaseAether.class))
				PlayerAPI.playerBaseClasses.set(i, PlayerBaseAetherMp.class);
		GuiManager.registerGuis(mod);
		BlockManager.registerBlocks();
		EntityManager.registerEntities();
		OtherPlayerMPAPI.RegisterPlayerBase(OtherPlayerMPBaseAether.class);
	}
	
	public void addRenderer(Map<Class<? extends Entity>, Render> entityRenderers) {
		RenderManager.registerRenderers(entityRenderers);
	}
	
	public boolean onTickInGUI(Minecraft minecraft, GuiScreen guiscreen) {
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
		boolean postHook = false;
		double realY = 0;
		if (minecraft.theWorld.multiplayerWorld && player.posY < -2D) {
			postHook = true;
			realY = player.posY;
			player.posY = -2D;
		}
		boolean res = aetherInstance.OnTickInGame(minecraft);
		if (postHook)
			player.posY = realY;
		if (!(minecraft.ingameGUI instanceof GuiIngameAetherMp))
			minecraft.ingameGUI = new GuiIngameAetherMp(minecraft);
		long time = minecraft.theWorld.getWorldTime();
		if (player.worldObj.multiplayerWorld && clock != time) {
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
		return res;
	}
	
	
	
	public GuiScreen handleGui(int ID) {
		return GuiManager.handleGui(ID);
	}
	
	public void handlePacket(Packet230ModLoader packet) {
        PacketManager.handlePacket(packet);
	}
	
	public static OtherPlayerMPBaseAether getPlayer(EntityPlayer entityplayer) {
		return (OtherPlayerMPBaseAether) OtherPlayerMPAPI.getPlayerBase((EntityOtherPlayerMP) entityplayer, OtherPlayerMPBaseAether.class);
	}
	
	private static boolean jumpBoosted;
	private static long clock;
	
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
