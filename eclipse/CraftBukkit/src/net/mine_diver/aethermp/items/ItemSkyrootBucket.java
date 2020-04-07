package net.mine_diver.aethermp.items;

import org.bukkit.Location;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;

import net.mine_diver.aethermp.craftbukkit.event.CraftAetherEventFactory;
import net.minecraft.server.Block;
import net.minecraft.server.EntityCow;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EnumMovingObjectType;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.Material;
import net.minecraft.server.MathHelper;
import net.minecraft.server.MovingObjectPosition;
import net.minecraft.server.Vec3D;
import net.minecraft.server.World;

public class ItemSkyrootBucket extends Item {

    public ItemSkyrootBucket(int i) {
        super(i);
        a(true);
        maxStackSize = 1;
    }
    
    @Override
    public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman) {
        float f = 1.0F;
        float f1 = entityhuman.lastPitch + (entityhuman.pitch - entityhuman.lastPitch) * f;
        float f2 = entityhuman.lastPitch + (entityhuman.yaw - entityhuman.lastPitch) * f;
        double d = entityhuman.lastX + (entityhuman.locX - entityhuman.lastX) * (double)f;
        double d1 = (entityhuman.lastY + (entityhuman.locY - entityhuman.lastY) * (double)f + 1.6200000000000001D) - (double)entityhuman.height;
        double d2 = entityhuman.lastZ + (entityhuman.locZ - entityhuman.lastZ) * (double)f;
        Vec3D vec3d = Vec3D.create(d, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.01745329F - 3.141593F);
        float f4 = MathHelper.sin(-f2 * 0.01745329F - 3.141593F);
        float f5 = -MathHelper.cos(-f1 * 0.01745329F);
        float f6 = MathHelper.sin(-f1 * 0.01745329F);
        float f7 = f4 * f5;
        float f8 = f6;
        float f9 = f3 * f5;
        double d3 = 5D;
        Vec3D vec3d1 = vec3d.add((double)f7 * d3, (double)f8 * d3, (double)f9 * d3);
        MovingObjectPosition movingobjectposition = world.rayTrace(vec3d, vec3d1, itemstack.getData() == 0);
        
        /*if (itemstack.getData() == 2 && (ModLoader.getMinecraftInstance().objectMouseOver == null || ModLoader.getMinecraftInstance().objectMouseOver.entityHit == null || !(ModLoader.getMinecraftInstance().objectMouseOver.entityHit instanceof EntityAechorPlant)))
            if (AetherPoison.afflictPoison()) {
                itemstack.setItemDamage(0);
                return itemstack;
            }
        else if (itemstack.getData() == 3 && AetherPoison.curePoison()) {
            itemstack.setItemDamage(0);
            return itemstack;
        }*/
        if (movingobjectposition != null && movingobjectposition.type == EnumMovingObjectType.TILE && (itemstack.getData() == 0 || itemstack.getData() == Block.WATER.id)) {
            int i = movingobjectposition.b;
            int j = movingobjectposition.c;
            int k = movingobjectposition.d;
            if (!world.a(entityhuman, i, j, k))
                return itemstack;
            if (itemstack.getData() == 0) {
                if (world.getMaterial(i, j, k) == Material.WATER && world.getData(i, j, k) == 0) {
                    final PlayerBucketFillEvent event = CraftAetherEventFactory.callPlayerBucketFillEvent(entityhuman, i, j, k, -1, itemstack, ItemManager.Bucket);
                    if (event.isCancelled())
                        return itemstack;
                    final CraftItemStack itemInHand = (CraftItemStack)event.getItemStack();
                    final byte data = (byte)((itemInHand.getData() == null) ? Block.WATER.id : itemInHand.getData().getData());
                    world.setTypeId(i, j, k, 0);
                    return new ItemStack(itemInHand.getTypeId(), itemInHand.getAmount(), data);
                }
            } else {
            	final int clickedX = i;
                final int clickedY = j;
                final int clickedZ = k;
                if (itemstack.getData() <= 3 && itemstack.getData() != 0)
                    return new ItemStack(ItemManager.Bucket);
                if (movingobjectposition.face == 0)
                    j--;
                if (movingobjectposition.face == 1)
                    j++;
                if (movingobjectposition.face == 2)
                    k--;
                if (movingobjectposition.face == 3)
                    k++;
                if (movingobjectposition.face == 4)
                    i--;
                if (movingobjectposition.face == 5)
                    i++;
                if (world.isEmpty(i, j, k) || !world.getMaterial(i, j, k).isBuildable()) {
                	final PlayerBucketEmptyEvent event3 = CraftAetherEventFactory.callPlayerBucketEmptyEvent(entityhuman, clickedX, clickedY, clickedZ, movingobjectposition.face, itemstack);
                    if (event3.isCancelled())
                        return itemstack;
                    if (world.worldProvider.d && itemstack.getData() == Block.WATER.id) {
                        world.makeSound(d + 0.5D, d1 + 0.5D, d2 + 0.5D, "random.fizz", 0.5F, 2.6F + (world.random.nextFloat() - world.random.nextFloat()) * 0.8F);
                        for(int l = 0; l < 8; l++)
                            world.a("largesmoke", (double)i + Math.random(), (double)j + Math.random(), (double)k + Math.random(), 0.0D, 0.0D, 0.0D);
                    } else
                        world.setTypeIdAndData(i, j, k, itemstack.getData(), 0);
                    final CraftItemStack itemInHand2 = (CraftItemStack)event3.getItemStack();
                    final byte data2 = (byte) ((itemInHand2.getData() == null) ? 0 : itemInHand2.getData().getData());
                    return new ItemStack(itemInHand2.getTypeId(), itemInHand2.getAmount(), data2);
                }
            }
        } else if (itemstack.getData() == 0 && movingobjectposition != null && (movingobjectposition.entity instanceof EntityCow/* || movingobjectposition.entity instanceof EntityCowFlying*/)) {
        	System.out.println("Milked");
            final Location loc = movingobjectposition.entity.getBukkitEntity().getLocation();
            final PlayerBucketFillEvent event4 = CraftAetherEventFactory.callPlayerBucketFillEvent(entityhuman, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), -1, itemstack, ItemManager.Bucket);
            if (event4.isCancelled())
                return itemstack;
            final CraftItemStack itemInHand3 = (CraftItemStack) event4.getItemStack();
            final byte data3 = (byte) ((itemInHand3.getData() == null) ? 1 : itemInHand3.getData().getData());
            return new ItemStack(itemInHand3.getTypeId(), itemInHand3.getAmount(), data3);
        }
        return itemstack;
    }

}
