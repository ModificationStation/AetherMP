package net.mine_diver.aethermp.entity;

import net.minecraft.src.EntitySlider;
import net.minecraft.src.World;

public class EntitySliderMp extends EntitySlider {

	public EntitySliderMp(World world, double x, double y, double z) {
		super(world);
		setPosition(x, y, z);
		dataWatcher.addObject(18, bossName);
	}
	
	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(16, (byte) 0);
		dataWatcher.addObject(17, health);
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		chatTime = 1;
		switch(dataWatcher.getWatchableObjectByte(16)) {
		case 0:
			texture = "/aether/mobs/sliderSleep.png";
			break;
		case 1:
			texture = "/aether/mobs/sliderAwake.png";
			break;
		case 2:
			texture = "/aether/mobs/sliderAwake_red.png";
			break;
		}
	}
	
	@Override
	public int getBossHP() {
		return dataWatcher.getWatchableObjectInt(17);
	}
	
	@Override
	public String getBossTitle() {
		bossName = dataWatcher.getWatchableObjectString(18);
		return super.getBossTitle();
	}
}
