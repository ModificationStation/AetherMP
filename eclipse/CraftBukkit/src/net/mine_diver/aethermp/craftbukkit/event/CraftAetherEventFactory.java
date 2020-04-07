package net.mine_diver.aethermp.craftbukkit.event;

import java.lang.reflect.Method;

import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerEvent;

import net.mine_diver.aethermp.items.ItemManager;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;

public class CraftAetherEventFactory extends CraftEventFactory {
	
	public static PlayerBucketEmptyEvent callPlayerBucketEmptyEvent(final EntityHuman who, final int clickedX, final int clickedY, final int clickedZ, final int clickedFace, final ItemStack itemInHand) {
        return (PlayerBucketEmptyEvent) getPlayerBucketEvent(Event.Type.PLAYER_BUCKET_EMPTY, who, clickedX, clickedY, clickedZ, clickedFace, itemInHand, ItemManager.Bucket);
    }
    
    public static PlayerBucketFillEvent callPlayerBucketFillEvent(final EntityHuman who, final int clickedX, final int clickedY, final int clickedZ, final int clickedFace, final ItemStack itemInHand, final Item bucket) {
        return (PlayerBucketFillEvent) getPlayerBucketEvent(Event.Type.PLAYER_BUCKET_FILL, who, clickedX, clickedY, clickedZ, clickedFace, itemInHand, bucket);
    }
    
    private static PlayerEvent getPlayerBucketEvent(final Event.Type type, final EntityHuman who, final int clickedX, final int clickedY, final int clickedZ, final int clickedFace, final ItemStack itemstack, final Item item) {
        try {
			return (PlayerEvent) getPlayerBucketEventMethod.invoke(null, type, who, clickedX, clickedY, clickedZ, clickedFace, itemstack, item);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
    
    private static final Method getPlayerBucketEventMethod;
    
    static {
    	Method method = null;
    	try {
			method = new Object(){}.getClass().getEnclosingClass().getSuperclass().getDeclaredMethod("getPlayerBucketEvent", Event.Type.class, EntityHuman.class, int.class, int.class, int.class, int.class, ItemStack.class, Item.class);
			method.setAccessible(true);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    	getPlayerBucketEventMethod = method;
    }
}
