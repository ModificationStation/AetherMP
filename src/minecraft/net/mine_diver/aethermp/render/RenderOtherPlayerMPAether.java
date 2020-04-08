package net.mine_diver.aethermp.render;

import org.lwjgl.opengl.GL11;

import net.mine_diver.aethermp.Core;
import net.minecraft.src.AetherItems;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiMainMenu;
import net.minecraft.src.ItemMoreArmor;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModLoader;
import net.minecraft.src.ModelBiped;
import net.minecraft.src.RenderPlayerAether;

import static net.minecraft.src.mod_AetherMp.PackageAccess;

public class RenderOtherPlayerMPAether extends RenderPlayerAether {
	
	public RenderOtherPlayerMPAether() {
		super();
		try {
	        modelCape = (ModelBiped) ModLoader.getPrivateValue(getClass().getSuperclass(), this, "modelCape");
	        modelMisc = (ModelBiped) ModLoader.getPrivateValue(getClass().getSuperclass(), this, "modelMisc");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	protected boolean setEnergyShieldBrightness(EntityPlayer player, int i, float f) {
        if(i != 0)
            return false;
        ItemStack[] inv = Core.getPlayer(player).inv;
        boolean flag = inv != null && inv[3] != null && inv[3].itemID == AetherItems.RepShield.shiftedIndex;
        if(flag) {
            if((player.onGround || player.ridingEntity != null && player.ridingEntity.onGround) && PackageAccess.EntityLiving.getMoveForward(player) == 0.0F && PackageAccess.EntityLiving.getMoveStrafing(player) == 0.0F) {
                loadTexture("/aether/mobs/energyGlow.png");
                GL11.glEnable(2977);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            } else {
                GL11.glEnable(2977);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                loadTexture("/aether/mobs/energyNotGlow.png");
            }
            return true;
        } else
            return false;
    }
	
	@Override
	public void doRenderMisc(EntityPlayer player, double d, double d1, double d2, float f, float f1) {
        GL11.glPushMatrix();
        GL11.glEnable(2884);
        modelMisc.onGround = func_167_c(player, f1);
        modelMisc.isRiding = player.isRiding();
        try {
            float f2 = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * f1;
            float f3 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f1;
            float f4 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f1;
            func_22012_b(player, d, d1, d2);
            float f5 = func_170_d(player, f1);
            rotateCorpse(player, f5, f2, f1);
            float f6 = 0.0625F;
            GL11.glEnable(32826);
            GL11.glScalef(-1F, -1F, 1.0F);
            preRenderCallback(player, f1);
            GL11.glTranslatef(0.0F, -24F * f6 - 0.0078125F, 0.0F);
            float f7 = player.prevLegYaw + (player.legYaw - player.prevLegYaw) * f1;
            float f8 = player.legSwing - player.legYaw * (1.0F - f1);
            if (f7 > 1.0F)
                f7 = 1.0F;
            GL11.glEnable(3008);
            modelMisc.setRotationAngles(f8, f7, f5, f3 - f2, f4, f6);
            float brightness = player.getEntityBrightness(f);
            ItemStack[] inv = Core.getPlayer(player).inv;
            if (inv[0] != null && inv[0].itemID != -1) {
                ItemMoreArmor pendant = (ItemMoreArmor)inv[0].getItem();
                loadTexture(pendant.texture);
                int colour = pendant.getColorFromDamage(0);
                float red = (float)(colour >> 16 & 0xff) / 255F;
                float green = (float)(colour >> 8 & 0xff) / 255F;
                float blue = (float)(colour & 0xff) / 255F;
                if (pendant.colouriseRender)
                    GL11.glColor3f(red * brightness, green * brightness, blue * brightness);
                else
                    GL11.glColor3f(brightness, brightness, brightness);
                modelMisc.bipedBody.render(f6);
            }
            if (inv[1] != null && inv[1].itemID != -1) {
                ItemMoreArmor glove = (ItemMoreArmor)inv[1].getItem();
                loadTexture(glove.texture);
                int colour = glove.getColorFromDamage(0);
                float red = (float)(colour >> 16 & 0xff) / 255F;
                float green = (float)(colour >> 8 & 0xff) / 255F;
                float blue = (float)(colour & 0xff) / 255F;
                if (glove.colouriseRender)
                    GL11.glColor3f(red * brightness, green * brightness, blue * brightness);
                else
                    GL11.glColor3f(brightness, brightness, brightness);
                modelMisc.bipedLeftArm.render(f6);
                modelMisc.bipedRightArm.render(f6);
            }
            GL11.glDisable(3042);
            GL11.glDisable(32826);
        } catch(Exception exception) {
            //exception.printStackTrace();
        }
        GL11.glPopMatrix();
    }
	
	@Override
	public void renderCape(EntityPlayer entityplayer, float f) {
        ItemStack[] inv = Core.getPlayer(entityplayer).inv;
        if (inv[2] != null) {
            ItemStack cape = inv[2];
            if (cape.itemID == AetherItems.RepShield.shiftedIndex || cape.itemID == -1)
                return;
            loadTexture(((ItemMoreArmor)cape.getItem()).texture);
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, 0.0F, 0.125F);
            double d = (entityplayer.field_20066_r + (entityplayer.field_20063_u - entityplayer.field_20066_r) * (double)f) - (entityplayer.prevPosX + (entityplayer.posX - entityplayer.prevPosX) * (double)f);
            double d1 = (entityplayer.field_20065_s + (entityplayer.field_20062_v - entityplayer.field_20065_s) * (double)f) - (entityplayer.prevPosY + (entityplayer.posY - entityplayer.prevPosY) * (double)f);
            double d2 = (entityplayer.field_20064_t + (entityplayer.field_20061_w - entityplayer.field_20064_t) * (double)f) - (entityplayer.prevPosZ + (entityplayer.posZ - entityplayer.prevPosZ) * (double)f);
            float f8 = entityplayer.prevRenderYawOffset + (entityplayer.renderYawOffset - entityplayer.prevRenderYawOffset) * f;
            double d3 = MathHelper.sin((f8 * 3.141593F) / 180F);
            double d4 = -MathHelper.cos((f8 * 3.141593F) / 180F);
            float f9 = (float)d1 * 10F;
            if (f9 < -6F)
                f9 = -6F;
            if (f9 > 32F)
                f9 = 32F;
            float f10 = (float)(d * d3 + d2 * d4) * 100F;
            float f11 = (float)(d * d4 - d2 * d3) * 100F;
            if (f10 < 0.0F)
                f10 = 0.0F;
            float f12 = entityplayer.prevCameraYaw + (entityplayer.cameraYaw - entityplayer.prevCameraYaw) * f;
            f9 += MathHelper.sin((entityplayer.prevDistanceWalkedModified + (entityplayer.distanceWalkedModified - entityplayer.prevDistanceWalkedModified) * f) * 6F) * 32F * f12;
            if (entityplayer.isSneaking())
                f9 += 25F;
            GL11.glRotatef(6F + f10 / 2.0F + f9, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(f11 / 2.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(-f11 / 2.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
            modelCape.renderCloak(0.0625F);
            GL11.glPopMatrix();
        }
    }
	
	@Override
	public boolean invisible(EntityPlayer player) {
        ItemStack[] inv = Core.getPlayer(player).inv;
        if (!player.isSwinging && inv[2] != null && inv[2].itemID == AetherItems.InvisibilityCloak.shiftedIndex)
            return true;
        return GuiMainMenu.mmactive;
    }
	
    private ModelBiped modelCape;
    private ModelBiped modelMisc;
}
