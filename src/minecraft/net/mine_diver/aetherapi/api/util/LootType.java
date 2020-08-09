package net.mine_diver.aetherapi.api.util;

public enum LootType {
	
	BRONZE_NORMAL(14),
	BRONZE(7),
	SILVER_NORMAL(15),
	SILVER(9),
	GOLD(8);
	
	private LootType(int standardLootAmount) {
		this.standardLootAmount = standardLootAmount;
	}
	
	public int getStandardLootAmount() {
		return standardLootAmount;
	}
	
	private final int standardLootAmount;
}
