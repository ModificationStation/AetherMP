package net.mine_diver.aethermp.items;

import net.minecraft.server.Item;

public class ItemInfo {
	
	ItemInfo(Item item) {
		this.item = item;
	}
	
	ItemInfo setMaxDamage(int maxDamage) {
		this.maxDamage = maxDamage;
		return this;
	}
	
	public Item getItem() {
		return item;
	}
	
	public int getMaxDamage() {
		return maxDamage;
	}
	
	private final Item item;
	private int maxDamage;
}
