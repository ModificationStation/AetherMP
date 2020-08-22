package net.mine_diver.aethermp.items;

import net.minecraft.server.Item;

public class ItemInfo {
	
	public ItemInfo(Item item) {
		this.item = item;
	}
	
	public ItemInfo setMaxDamage(int maxDamage) {
		this.maxDamage = maxDamage;
		return this;
	}
	
	public ItemInfo setHasSubtypes(boolean hasSubtypes) {
		this.hasSubtypes = hasSubtypes;
		return this;
	}
	
	public Item getItem() {
		return item;
	}
	
	public int getMaxDamage() {
		return maxDamage;
	}
	
	public boolean getHasSubtypes() {
		return hasSubtypes;
	}
	
	private final Item item;
	private int maxDamage;
	private boolean hasSubtypes = false;
}
