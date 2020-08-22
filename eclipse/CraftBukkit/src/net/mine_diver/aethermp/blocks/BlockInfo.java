package net.mine_diver.aethermp.blocks;

import net.minecraft.server.Block;
import net.minecraft.server.ItemBlock;
import net.minecraft.server.TileEntity;

public class BlockInfo {
	
	public BlockInfo(Block block) {
		this.block = block;
	}
	
	public BlockInfo setHardness(float hardness) {
		this.hardness = hardness;
		return this;
	}
	
	public BlockInfo setResistance(float resistance) {
		this.resistance = resistance;
		return this;
	}
	
	public BlockInfo setLightValue(float lightValue) {
		this.lightValue = lightValue;
		return this;
	}
	
	public BlockInfo setLightOpacity(int lightOpacity) {
		this.lightOpacity = lightOpacity;
		return this;
	}
	
	public BlockInfo setBlockItem(Class<? extends ItemBlock> blockItem) {
		this.blockItem = blockItem;
		return this;
	}
	
	public BlockInfo setTileEntity(Class<? extends TileEntity> tileEntity) {
		this.tileEntity = tileEntity;
		return this;
	}
	
	public Block getBlock() {
		return block;
	}
	
	public float getHardness() {
		return hardness;
	}
	
	public float getResistance() {
		return resistance;
	}
	
	public float getLightValue() {
		return lightValue;
	}
	
	public int getLightOpacity() {
		return lightOpacity;
	}
	
	public Class<? extends ItemBlock> getBlockItem() {
		return blockItem;
	}
	
	public Class<? extends TileEntity> getTileEntity() {
		return tileEntity;
	}
	
	private final Block block;
	private float hardness;
	private float resistance;
	private float lightValue;
	private int lightOpacity;
	private Class<? extends ItemBlock> blockItem;
	private Class<? extends TileEntity> tileEntity;
}
