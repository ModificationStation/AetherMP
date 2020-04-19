package net.mine_diver.aethermp.player;

import org.bukkit.Location;

import net.mine_diver.aethermp.entities.EntityCloudParachute;
import net.mine_diver.aethermp.inventory.ContainerAether;
import net.mine_diver.aethermp.inventory.InventoryAether;
import net.mine_diver.aethermp.items.ItemManager;
import net.mine_diver.aethermp.util.AetherPoison;
import net.minecraft.server.Block;
import net.minecraft.server.DimensionBase;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.ItemStack;
import net.minecraft.server.MathHelper;
import net.minecraft.server.ModLoaderMp;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.NBTTagList;
import net.minecraft.server.NetServerHandler;
import net.minecraft.server.Packet230ModLoader;
import net.minecraft.server.World;
import net.minecraft.server.mod_AetherMp;

import static net.minecraft.server.mod_AetherMp.PackageAccess;

public class PlayerBaseAether extends PlayerBaseAetherImpl {

	public PlayerBaseAether(EntityPlayer var1) {
		super(var1);
		inv = new InventoryAether(player);
		player.defaultContainer = new ContainerAether(player.inventory, inv, !player.world.isStatic);
		player.activeContainer = player.defaultContainer;
	}
	
	@Override
	public void increaseMaxHP(int i) {
        if(maxHealth <= 40 - i) {
            maxHealth += i;
            player.health += i;
        }
    }
	
	@Override
	public boolean setEntityDead() {
		wasDead = true;
		return false;
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
	public boolean moveEntity(double x, double y, double z) {
		if (new Exception().getStackTrace()[3].getClassName().equals(NetServerHandler.class.getName()))
			if (wasDead || maxHealth != previousMaxHealth) {
				Packet230ModLoader packet = new Packet230ModLoader();
				packet.packetType = 6;
				packet.dataInt = new int[] {maxHealth};
				ModLoaderMp.SendPacketTo(ModLoaderMp.GetModInstance(mod_AetherMp.class), player, packet);
				wasDead = false;
				previousMaxHealth = maxHealth;
			}
		return false;
	}
	
	@Override
	public ItemStack getMoreArmorEquipment(int i) {
		if (i == 0)
			return inv.slots[0];
		if (i == 1)
			return inv.slots[6];
		if (i == 2)
			return inv.slots[1];
		if (inv.slots[2] != null && inv.slots[2].id == ItemManager.RepShield.id)
			//TODO: Make shield go off only when there's no manual input from client
			return ((player.onGround || player.vehicle != null && player.vehicle.onGround) && Math.abs(prevLocX - player.locX) < 0.01 && Math.abs(prevLocZ - player.locZ) < 0.01) ? new ItemStack(inv.slots[2].id, inv.slots[2].count, 1) : new ItemStack(inv.slots[2].id, inv.slots[2].count, 0);
		return inv.slots[2];
    }
	
	@Override
	public int getMoreArmorAmount() {
		return 4;
	}
	
	@Override
	public void writeCustomData(NBTTagCompound customData) {
        customData.a("MaxHealth", (byte) maxHealth);
        customData.a("Inventory", inv.writeToNBT(new NBTTagList()));
    }
	
	@Override
	public void readCustomData(NBTTagCompound customData) {
        maxHealth = customData.c("MaxHealth");
        if(maxHealth < 20)
            maxHealth = 20;
        NBTTagList nbttaglist = customData.l("Inventory");
        inv.readFromNBT(nbttaglist);
    }
	
	@Override
	public boolean onDeath(Entity entity) {
		for (int i = 0; i < inv.slots.length; i++)
			inv.slots[i] = null;
		sendInv();
		return false;
	}
	
	@Override
	public boolean isInWater(boolean inWater) {
        return inWater && (!wearingNeptuneArmor() || player.getJumping());
    }
	
	@Override
	public float getCurrentPlayerStrVsBlock(Block block, float f) {
        if(inv.slots[0] != null && inv.slots[0].id == ItemManager.ZanitePendant.id)
            f *= 1.0F + (float)inv.slots[0].getData() / ((float)inv.slots[0].i() * 3F);
        if(inv.slots[4] != null && inv.slots[4].id == ItemManager.ZaniteRing.id)
            f *= 1.0F + (float)inv.slots[4].getData() / ((float)inv.slots[4].i() * 3F);
        if(inv.slots[5] != null && inv.slots[5].id == ItemManager.ZaniteRing.id)
            f *= 1.0F + (float)inv.slots[5].getData() / ((float)inv.slots[5].i() * 3F);
        return f;
    }
	
	private boolean wearingNeptuneArmor() {
        return player.inventory.armor[3] != null && player.inventory.armor[3].id == ItemManager.NeptuneHelmet.id && player.inventory.armor[2] != null && player.inventory.armor[2].id == ItemManager.NeptuneChestplate.id && player.inventory.armor[1] != null && player.inventory.armor[1].id == ItemManager.NeptuneLeggings.id && player.inventory.armor[0] != null && player.inventory.armor[0].id == ItemManager.NeptuneBoots.id && inv.slots[6] != null && inv.slots[6].id == ItemManager.NeptuneGlove.id;
    }
	
	@Override
	public void updatePoison() {
		if(poisonWorld != player.world || player.dead || player.health <= 0) {
            poisonWorld = player.world;
            poisonTime = 0;
            return;
        }
        if(poisonWorld == null)
            return;
        if(poisonTime < 0) {
            poisonTime++;
            return;
        }
        if(poisonTime == 0)
            return;
        long time = player.world.getTime();
        int mod = poisonTime % 50;
        if(clock != time) {
            AetherPoison.distractEntity(player);
            if(mod == 0)
                player.damageEntity(null, 1);
            poisonTime--;
            clock = time;
        }
	}
	
	@Override
	public boolean afflictPoison() {
        if(poisonTime < 0)
            return false;
        else {
            poisonTime = 500;
            poisonWorld = player.world;
            return true;
        }
    }
	
	@Override
    public boolean curePoison() {
        if(poisonTime == -500)
            return false;
        else {
            poisonTime = -500;
            poisonWorld = player.world;
            return true;
        }
    }
	
	@Override
	public void onFallFromAether() {
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
		DimensionBase.usePortal(player.dimension, player);
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
	
	@Override
	public ItemStack[] getBonus() {
		return entranceBonus;
	}
	
	@Override
	public void doAccessoriesPhysics() {
		if(player.inventory.armor[3] != null && player.inventory.armor[3].id == ItemManager.PhoenixHelm.id && player.inventory.armor[2] != null && player.inventory.armor[2].id == ItemManager.PhoenixBody.id && player.inventory.armor[1] != null && player.inventory.armor[1].id == ItemManager.PhoenixLegs.id && player.inventory.armor[0] != null && player.inventory.armor[0].id == ItemManager.PhoenixBoots.id && inv.slots[6] != null && inv.slots[6].id == ItemManager.PhoenixGlove.id) {
            PackageAccess.Entity.setIsImmuneToFire(player, true);
            player.fireTicks = 0;
            PackageAccess.Entity.setEntityFlag(player, 0, false);
            player.world.a("flame", player.locX + player.getRandom().nextGaussian() / 5D, (player.locY - 0.5D) + player.getRandom().nextGaussian() / 5D, player.locZ + player.getRandom().nextGaussian() / 3D, 0.0D, 0.0D, 0.0D);
        } else
            PackageAccess.Entity.setIsImmuneToFire(player, false);
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
            if(inv.slots[6] != null && inv.slots[6].id == ItemManager.PhoenixGlove.id) {
                inv.slots[6].damage(1, player);
                if(playerBlock == Block.STATIONARY_WATER.id) {
                    inv.slots[6].damage(4, player);
                    player.world.setTypeId(MathHelper.floor(player.locX), MathHelper.floor(player.locY), MathHelper.floor(player.locZ), 0);
                }
                if(inv.slots[6] == null || inv.slots[6].count < 1)
                    inv.slots[6] = new ItemStack(ItemManager.ObsidianGlove, 1, 0);
            }
		}
		if(player.inventory.armor[3] != null && player.inventory.armor[3].id == ItemManager.GravititeHelmet.id && player.inventory.armor[2] != null && player.inventory.armor[2].id == ItemManager.GravititeBodyplate.id && player.inventory.armor[1] != null && player.inventory.armor[1].id == ItemManager.GravititePlatelegs.id && player.inventory.armor[0] != null && player.inventory.armor[0].id == ItemManager.GravititeBoots.id && inv.slots[6] != null && inv.slots[6].id == ItemManager.GravititeGlove.id) {
            if(player.getJumping() && !jumpBoosted) {
                player.motY = 1.0D;
                jumpBoosted = true;
            }
            player.fallDistance = -1F;
        }
		if(!player.getJumping() && player.onGround)
            jumpBoosted = false;
		if(inv.slots[3] != null && inv.slots[3].id == ItemManager.IronBubble.id || inv.slots[7] != null && inv.slots[7].id == ItemManager.IronBubble.id)
            player.airTicks = 20;
		if(player.world.spawnMonsters == 0 && player.health >= 20 && player.health < maxHealth && player.ticksLived % 20 == 0)
            player.b(1);
		if(inv.slots[0] != null && inv.slots[0].id == ItemManager.IcePendant.id || inv.slots[4] != null && inv.slots[4].id == ItemManager.IceRing.id || inv.slots[5] != null && inv.slots[5].id == ItemManager.IceRing.id) {
            int i = MathHelper.floor(player.locX);
            int j = MathHelper.floor(player.boundingBox.b);
            int k = MathHelper.floor(player.locZ);
            for(int l = i - 1; l <= i + 1; l++)
                for(int i1 = j - 1; i1 <= j + 1; i1++)
                    for(int j1 = k - 1; j1 <= k + 1; j1++) {
                        if(player.world.getTypeId(l, i1, j1) == 8) {
                        	player.world.setTypeId(l, i1, j1, 79);
                            continue;
                        }
                        if(player.world.getTypeId(l, i1, j1) == 9) {
                        	player.world.setTypeId(l, i1, j1, 79);
                            continue;
                        }
                        if(player.world.getTypeId(l, i1, j1) == 10) {
                        	player.world.setTypeId(l, i1, j1, 49);
                            continue;
                        }
                        if(player.world.getTypeId(l, i1, j1) == 11)
                        	player.world.setTypeId(l, i1, j1, 49);
                    }
        }
		if(inv.slots[3] != null && inv.slots[3].id == ItemManager.GoldenFeather.id || inv.slots[7] != null && inv.slots[7].id == ItemManager.GoldenFeather.id) {
            if(!player.onGround && player.motY < 0.0D && !PackageAccess.Entity.getInWater(player))
                player.motY *= 0.59999999999999998D;
            player.fallDistance = -1F;
        }
		if(inv.slots[1] != null && inv.slots[1].id == ItemManager.AgilityCape.id)
            player.bs = 1.0F;
        else
            player.bs = 0.5F;
		if(ticks % 200 == 0 && player.health < maxHealth && (inv.slots[3] != null && inv.slots[3].id == ItemManager.RegenerationStone.id || inv.slots[7] != null && inv.slots[7].id == ItemManager.RegenerationStone.id))
            player.b(1);
		if(player.ticksLived % 400 == 0) {
            if(inv.slots[0] != null && inv.slots[0].id == ItemManager.ZanitePendant.id) {
                inv.slots[0].damage(1, player);
                if(inv.slots[0].count < 1)
                    inv.slots[0] = null;
            }
            if(inv.slots[4] != null && inv.slots[4].id == ItemManager.ZaniteRing.id) {
                inv.slots[4].damage(1, player);
                if(inv.slots[4].count < 1)
                    inv.slots[4] = null;
            }
            if(inv.slots[5] != null && inv.slots[5].id == ItemManager.ZaniteRing.id) {
                inv.slots[5].damage(1, player);
                if(inv.slots[5].count < 1)
                    inv.slots[5] = null;
            }
            if(inv.slots[0] != null && inv.slots[0].id == ItemManager.IcePendant.id) {
                inv.slots[0].damage(1, player);
                if(inv.slots[0].count < 1)
                    inv.slots[0] = null;
            }
            if(inv.slots[4] != null && inv.slots[4].id == ItemManager.IceRing.id) {
                inv.slots[4].damage(1, player);
                if(inv.slots[4].count < 1)
                    inv.slots[4] = null;
            }
            if(inv.slots[5] != null && inv.slots[5].id == ItemManager.IceRing.id) {
                inv.slots[5].damage(1, player);
                if(inv.slots[5].count < 1)
                    inv.slots[5] = null;
            }
        }
		ticks++;
	}
	
    public int maxHealth = 20;
    public int previousMaxHealth = 20;
    public boolean wasDead = false;
    public InventoryAether inv;
    private boolean jumpBoosted;
    private int ticks = 0;
    public World poisonWorld;
    public int poisonTime;
    public long clock;
    public static ItemStack[] entranceBonus = new ItemStack[] {new ItemStack(ItemManager.LoreBook, 1, 2), new ItemStack(ItemManager.CloudParachute, 1)};
}
