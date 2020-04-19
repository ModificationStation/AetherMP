package net.mine_diver.aethermp.util;

import net.minecraft.client.Minecraft;
import net.minecraft.src.AetherPoison;
import net.minecraft.src.Entity;

public class AetherPoisonMp extends AetherPoison {
	
	public static void distractEntity(Entity ent) {
        double gauss = mc.theWorld.rand.nextGaussian();
        double newRotD = rotDFac * gauss;
        rotD = rotTaper * newRotD + (1.0D - rotTaper) * rotD;
        ent.rotationYaw += rotD;
        ent.rotationPitch += rotD;
    }
	
	public static void tickRender(Minecraft game) {
        if(world != game.theWorld || game.thePlayer != null && (game.thePlayer.isDead || game.thePlayer.health <= 0)) {
            world = game.theWorld;
            poisonTime = 0;
            return;
        }
        if(world == null)
            return;
        if(poisonTime < 0) {
            poisonTime++;
            displayCureEffect();
            return;
        }
        if(poisonTime == 0)
            return;
        long time = mc.theWorld.getWorldTime();
        int mod = poisonTime % 50;
        if(clock != time) {
            distractEntity(game.thePlayer);
            poisonTime--;
            clock = time;
        }
        displayPoisonEffect(mod);
    }
}
