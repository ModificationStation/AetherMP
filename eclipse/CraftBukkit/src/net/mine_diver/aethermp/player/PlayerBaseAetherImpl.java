package net.mine_diver.aethermp.player;

import java.util.Arrays;

import org.bukkit.World.Environment;

import net.mine_diver.aethermp.api.player.*;
import net.mine_diver.aethermp.dimension.DimensionManager;
import net.mine_diver.aethermp.util.Achievements;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.ItemStack;
import net.minecraft.server.ModLoader;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.Packet5EntityEquipment;
import net.minecraft.server.PlayerBase;
import net.minecraft.server.mod_AetherMp;

public abstract class PlayerBaseAetherImpl extends PlayerBase implements Poisonable, Fallable, EntranceBonus, HasAccessories, HasMoreArmor, AdditionalHealth {

	public PlayerBaseAetherImpl(EntityPlayer var1) {
		super(var1);
		try {
			ItemStack[] equipment = (ItemStack[]) ModLoader.getPrivateValue(EntityPlayer.class, player, "bN");
			equipment = Arrays.copyOf(equipment, equipment.length + 4);
			ModLoader.setPrivateValue(EntityPlayer.class, player, "bN", equipment);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void playerInit() {}
	
	@Override
	public boolean onLivingUpdate() {
		if (DimensionManager.getCurrentDimension(player.world).equals(Environment.valueOf(mod_AetherMp.nameDimensionAether.toUpperCase())))
	        doAetherUpdate();
		updatePoison();
		doAccessoriesPhysics();
		return false;
	}
	
	@Override
	public boolean onUpdate() {
		sendInv();
		prevLocX = player.locX;
		prevLocZ = player.locZ;
		return false;
	}
	
	public void doAetherUpdate() {
		if (player.locY < -2)
        	onFallFromAether();
        if (!enteredAether) {
        	Achievements.giveAchievement(Achievements.enterAether, player);
        	for (ItemStack itemstack : getBonus())
        		player.inventory.pickup(itemstack);
        	enteredAether = true;
		}
	}
	
	public void sendInv() {
		for (int i = 0; i < 4; ++i) {
            ItemStack itemstack = getMoreArmorEquipment(i);
            if (itemstack != this.moreArmor[i]) {
                player.b.getTracker(player.dimension).a(player, new Packet5EntityEquipment(player.id, i + 5, itemstack));
                this.moreArmor[i] = itemstack;
                ItemStack[] equipment;
                try {
					equipment = (ItemStack[]) ModLoader.getPrivateValue(EntityPlayer.class, player, "bN");
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
                equipment[i + 5] = itemstack;
            }
        }
	}
	
	@Override
	public boolean writeEntityToNBT(NBTTagCompound tag) {
        NBTTagCompound customData = new NBTTagCompound();
        customData.a("EnteredAether", enteredAether);
        writeCustomData(customData);
        tag.a("Aether", customData);
		return false;
	}
	
	@Override
	public boolean readEntityFromNBT(NBTTagCompound tag) {
        NBTTagCompound customData = tag.k("Aether");
        enteredAether = customData.m("EnteredAether");
        readCustomData(customData);
		return false;
	}
	
	public abstract void writeCustomData(NBTTagCompound customData);
	
	public abstract void readCustomData(NBTTagCompound customData);
	
	public boolean enteredAether = false;
    public double prevLocX;
    public double prevLocZ;
    public ItemStack[] moreArmor = new ItemStack[getMoreArmorAmount()];
}
