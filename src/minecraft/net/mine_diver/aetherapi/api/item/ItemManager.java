package net.mine_diver.aetherapi.api.item;

public interface ItemManager {
	
	ItemManager INSTANCE = new ItemManager() {
		
		public ItemManager handler;
		
		@Override
		public void setHandler(ItemManager handler) {
			this.handler = handler;
		}

		@Override
		public void overrideItem(ItemType item) {
			if (handler != null)
				handler.overrideItem(item);
		}
	};
	
	void setHandler(ItemManager handler);
	
	void overrideItem(ItemType item);
}
