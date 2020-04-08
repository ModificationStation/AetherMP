package net.mine_diver.aethermp.render;

import java.util.Map;

import net.mine_diver.aethermp.entities.EntitySentryMp;
import net.mine_diver.aethermp.render.RenderType.RegType;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityOtherPlayerMP;
import net.minecraft.src.ModelSlime;
import net.minecraft.src.Render;

public class RenderManager {
	
	public static void registerRenderers(Map<Class<? extends Entity>, Render> entityRenderers) {
		for (RenderType render : aetherRenders)
			switch (render.getRegType()) {
				case ADD: {
					entityRenderers.put(render.getEntity(), render.getRender());
					break;
				}
				case REPLACE: {
					entityRenderers.replace(render.getEntity(), render.getRender());
					break;
				}
			}
	}
	
	public static RenderType[] aetherRenders = new RenderType[] {
			new RenderType(EntitySentryMp.class, new RenderSentryMp(new ModelSlime(0), 0.2F), RegType.ADD),
			new RenderType(EntityOtherPlayerMP.class, new RenderOtherPlayerMPAether(), RegType.ADD)
	};
}
