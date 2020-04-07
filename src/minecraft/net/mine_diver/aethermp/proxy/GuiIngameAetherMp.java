package net.mine_diver.aethermp.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiIngameAether;

public class GuiIngameAetherMp extends GuiIngameAether {

	public GuiIngameAetherMp(Minecraft minecraft) {
		super(minecraft);
	}
	
	@Override
	public void setRecordPlayingMessage(String s) {
        super.setRecordPlayingMessage(s.equals("C418 - AetherTune") ? "Noisestorm - AetherTune" : s);
    }
}
