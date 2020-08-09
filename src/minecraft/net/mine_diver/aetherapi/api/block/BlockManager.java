package net.mine_diver.aetherapi.api.block;

public interface BlockManager {
	
	BlockManager INSTANCE = new BlockManager() {
		
		public BlockManager handler;
		
		@Override
		public void setHandler(BlockManager handler) {
			this.handler = handler;
		}

		@Override
		public void overrideBlock(BlockType block) {
			if (handler != null)
				handler.overrideBlock(block);
		}
	};
	
	void setHandler(BlockManager handler);
	
	void overrideBlock(BlockType block);
}
