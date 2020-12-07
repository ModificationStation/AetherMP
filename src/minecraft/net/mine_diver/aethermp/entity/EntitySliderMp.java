package net.mine_diver.aethermp.entity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.src.EntitySlider;
import net.minecraft.src.World;

public class EntitySliderMp extends EntitySlider {
	
	public EntitySliderMp(World world, double x, double y, double z) {
		super(world);
		setPosition(x, y, z);
	}
	
	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(16, (byte) 0);
		dataWatcher.addObject(17, 0);
		dataWatcher.addObject(18, "");
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		chatTime = 1;
		texture = TEXTURES.get(dataWatcher.getWatchableObjectByte(16));
	}
	
	@Override
	public int getBossHP() {
		health = dataWatcher.getWatchableObjectInt(17);
		return super.getBossHP();
	}
	
	@Override
	public String getBossTitle() {
		bossName = dataWatcher.getWatchableObjectString(18);
		return super.getBossTitle();
	}
	
	public static final List<String> TEXTURES = new ArrayList<>();
	static {
		TEXTURES.add("/aether/mobs/sliderSleep.png");
		TEXTURES.add("/aether/mobs/sliderAwake.png");
		TEXTURES.add("/aether/mobs/sliderAwake_red.png");
	}
}
