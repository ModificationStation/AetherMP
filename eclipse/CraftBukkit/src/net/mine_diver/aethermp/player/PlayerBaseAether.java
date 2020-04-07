package net.mine_diver.aethermp.player;

import org.bukkit.Location;

import net.mine_diver.aethermp.blocks.BlockAetherPortal;
import net.mine_diver.aethermp.blocks.BlockManager;
import net.mine_diver.aethermp.dimension.DimensionManager;
import net.mine_diver.aethermp.entities.EntityCloudParachute;
import net.mine_diver.aethermp.items.ItemManager;
import net.minecraft.server.Block;
import net.minecraft.server.DimensionBase;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.ItemStack;
import net.minecraft.server.MathHelper;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.PlayerBase;

public class PlayerBaseAether extends PlayerBase {

	public PlayerBaseAether(EntityPlayer var1) {
		super(var1);
	}
	
	@Override
	public void playerInit() {}
	
	public void increaseMaxHP(int i) {
        if(maxHealth <= 40 - i) {
            maxHealth += i;
            player.health += i;
        }
    }
	
	@Override
	public boolean heal(int i) {
        if(player.health <= 0)
            return false;
        player.health += i;
        if(player.health > maxHealth)
            player.health = maxHealth;
        player.noDamageTicks = player.maxNoDamageTicks / 2;
        return true;
    }
	
	@Override
	public boolean onLivingUpdate() {
		if (player.dimension == DimensionManager.Aether.number && player.locY < -2) {
			Class<? extends Entity> entityClass = null;
			NBTTagCompound tag = new NBTTagCompound();
			if (player.vehicle != null) {
				entityClass = player.vehicle.getClass();
				player.vehicle.d(tag);
				player.vehicle.die();
			}
			double motionY = player.motY;
			boolean cloudPara = false;
			if (EntityCloudParachute.getCloudBelongingToEntity(player) != null)
				cloudPara = true;
			DimensionBase.usePortal(((BlockAetherPortal) BlockManager.Portal).getDimNumber(), player);
			player.netServerHandler.teleport(new Location(player.world.getWorld(), player.locX, 127, player.locZ, player.yaw, 0));
			if (entityClass != null)
				try {
					Entity riding = (Entity) entityClass.getDeclaredConstructor(net.minecraft.server.World.class).newInstance(player.world);
					riding.e(tag);
					riding.setPositionRotation(player.locX, 127, player.locZ, player.yaw, 0);
					player.world.addEntity(riding);
					player.setPassengerOf(riding);
				} catch (Exception e) {
					System.out.println("Failed to transfer mount.");
				}
			player.motX = player.motZ = 0.0D;
            player.motY = motionY;
            if (cloudPara && EntityCloudParachute.entityHasRoomForCloud(player.world, player)) {
                for(int i = 0; i < 32; i++)
                    EntityCloudParachute.doCloudSmoke(player.world, player);
                player.world.makeSound(player, "cloud", 1.0F, 1.0F / (player.world.random.nextFloat() * 0.1F + 0.95F));
                player.world.addEntity(new EntityCloudParachute(player.world, player, false));
            }
            if(player.world.spawnMonsters == 0)
                player.fallDistance = -64F;
            if(!cloudPara) {
                if(player.inventory.b(ItemManager.CloudParachute.id))
                    if(EntityCloudParachute.entityHasRoomForCloud(player.world, player)) {
                        for(int i = 0; i < 32; i++)
                            EntityCloudParachute.doCloudSmoke(player.world, player);
                        player.world.makeSound(player, "cloud", 1.0F, 1.0F / (player.world.random.nextFloat() * 0.1F + 0.95F));
                            player.world.addEntity(new EntityCloudParachute(player.world, player, false));
                    }
                else
                    for(int i = 0; i < player.inventory.getSize(); i++) {
                        ItemStack itemstack = player.inventory.getItem(i);
                        if(itemstack == null || itemstack.id != ItemManager.CloudParachuteGold.id || !EntityCloudParachute.entityHasRoomForCloud(player.world, player))
                            continue;
                        EntityCloudParachute.doCloudSmoke(player.world, player);
                        player.world.makeSound(player, "cloud", 1.0F, 1.0F / (player.world.random.nextFloat() * 0.1F + 0.95F));
                        player.world.addEntity(new EntityCloudParachute(player.world, player, true));
                        itemstack.damage(1, player);
                        player.inventory.setItem(i, itemstack);
                    }
            }
		}
		if (player.ac()) {
			int playerBlock = player.world.getTypeId(MathHelper.floor(player.locX), MathHelper.floor(player.locY), MathHelper.floor(player.locZ));
            if(player.inventory.armor[0] != null && player.inventory.armor[0].id == ItemManager.PhoenixBoots.id) {
                player.inventory.armor[0].damage(1, player);
                if(playerBlock == Block.STATIONARY_WATER.id) {
                    player.inventory.armor[0].damage(4, player);
                    player.world.setTypeId(MathHelper.floor(player.locX), MathHelper.floor(player.locY), MathHelper.floor(player.locZ), 0);
                }
                if(player.inventory.armor[0] == null || player.inventory.armor[0].count < 1)
                    player.inventory.armor[0] = new ItemStack(ItemManager.ObsidianBoots, 1, 0);
            }
            if(player.inventory.armor[1] != null && player.inventory.armor[1].id == ItemManager.PhoenixLegs.id) {
                player.inventory.armor[1].damage(1, player);
                if(playerBlock == Block.STATIONARY_WATER.id) {
                    player.inventory.armor[1].damage(4, player);
                    player.world.setTypeId(MathHelper.floor(player.locX), MathHelper.floor(player.locY), MathHelper.floor(player.locZ), 0);
                }
                if(player.inventory.armor[1] == null || player.inventory.armor[1].count < 1)
                    player.inventory.armor[1] = new ItemStack(ItemManager.ObsidianLegs, 1, 0);
            }
            if(player.inventory.armor[2] != null && player.inventory.armor[2].id == ItemManager.PhoenixBody.id) {
                player.inventory.armor[2].damage(1, player);
                if(playerBlock == Block.STATIONARY_WATER.id) {
                    player.inventory.armor[2].damage(4, player);
                    player.world.setTypeId(MathHelper.floor(player.locX), MathHelper.floor(player.locY), MathHelper.floor(player.locZ), 0);
                }
                if(player.inventory.armor[2] == null || player.inventory.armor[2].count < 1)
                    player.inventory.armor[2] = new ItemStack(ItemManager.ObsidianBody, 1, 0);
            }
            if(player.inventory.armor[3] != null && player.inventory.armor[3].id == ItemManager.PhoenixHelm.id) {
                player.inventory.armor[3].damage(1, player);
                if(playerBlock == Block.STATIONARY_WATER.id) {
                    player.inventory.armor[3].damage(4, player);
                    player.world.setTypeId(MathHelper.floor(player.locX), MathHelper.floor(player.locY), MathHelper.floor(player.locZ), 0);
                }
                if(player.inventory.armor[3] == null || player.inventory.armor[3].count < 1)
                    player.inventory.armor[3] = new ItemStack(ItemManager.ObsidianHelm, 1, 0);
            }
		}
		if(player.world.spawnMonsters == 0 && player.health >= 20 && player.health < maxHealth && player.ticksLived % 20 == 0)
            player.b(1);
		return false;
	}
	
	@Override
	public boolean writeEntityToNBT(NBTTagCompound tag) {
        NBTTagCompound customData = new NBTTagCompound();
        customData.a("MaxHealth", (byte) maxHealth);
        tag.a("Aether", customData);
        return false;
    }
	
	@Override
	public boolean readEntityFromNBT(NBTTagCompound tag) {
        NBTTagCompound customData = tag.k("Aether");
        maxHealth = customData.c("MaxHealth");
        if(maxHealth < 20)
            maxHealth = 20;
        return false;
    }
	
    public int maxHealth = 20;
}
