package net.mine_diver.aethermp.render;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.RenderPlayerAether;

public class RenderPlayerAetherMp extends RenderPlayerAether {
	
	@Override
	public boolean invisible(EntityPlayer player)
    {
		if (player.worldObj.multiplayerWorld)
			return false;
        return super.invisible(player);
    }
}
