package net.mine_diver.aethermp.render;

import net.minecraft.src.EntitySentry;
import net.minecraft.src.ModelBase;
import net.minecraft.src.RenderSentry;

public class RenderSentryMp extends RenderSentry
{

    public RenderSentryMp(ModelBase modelbase, float f)
    {
        super(modelbase, f);
    }
    
    @Override
    protected boolean a(EntitySentry sentry, int i, float f)
    {
    	sentry.active = sentry.getDataWatcher().getWatchableObjectInt(17) == 1;
    	return super.a(sentry, i, f);
    }
}
