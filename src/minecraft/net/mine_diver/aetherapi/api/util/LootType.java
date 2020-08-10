package net.mine_diver.aetherapi.api.util;

public enum LootType {
	
	BRONZE_NORMAL(14, 3, 6),
	BRONZE(7, 3, 6),
	SILVER_NORMAL(15, 3, 6),
	SILVER(9, 3, 6),
	GOLD(8, 3, 6);
	
	private LootType(int defaultRandomLootAmount, int guaranteedLootPerChest, int maximumLootPerChest) {
		this.defaultRandomLootAmount = defaultRandomLootAmount;
		this.guaranteedLootPerChest = guaranteedLootPerChest;
		this.maximumLootPerChest = maximumLootPerChest;
	}
	
	public int getDefaultRandomLootAmount() {
		return this.defaultRandomLootAmount;
	}
	
	public int getGuaranteedLootPerChest() {
		return this.guaranteedLootPerChest;
	}
	
	public int getMaximumLootPerChest() {
		return this.maximumLootPerChest;
	}
	
	public void setGuaranteedLootPerChest(int guaranteedLootPerChest) {
		this.guaranteedLootPerChest = guaranteedLootPerChest;
	}
	
	public void setMaximumLootPerChest(int maximumLootPerChest) {
		this.maximumLootPerChest = maximumLootPerChest;
	}
	
	private final int defaultRandomLootAmount;
	private int guaranteedLootPerChest;
	private int maximumLootPerChest;
}
