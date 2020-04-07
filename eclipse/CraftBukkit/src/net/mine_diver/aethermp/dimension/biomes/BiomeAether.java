package net.mine_diver.aethermp.dimension.biomes;

import java.util.Random;

import net.mine_diver.aethermp.dimension.world.generation.AetherGenGoldenOak;
import net.mine_diver.aethermp.dimension.world.generation.AetherGenSkyroot;
import net.mine_diver.aethermp.entities.EntitySheepuff;
import net.mine_diver.aethermp.entities.EntityZephyr;
import net.minecraft.server.BiomeBase;
import net.minecraft.server.BiomeMeta;
import net.minecraft.server.WorldGenerator;
import net.minecraft.server.mod_AetherMp;

public class BiomeAether extends BiomeBase {

    @SuppressWarnings("unchecked")
	public BiomeAether() {
        s.clear();
        t.clear();
        u.clear();
        /*if(mod_AetherMp.raritySwet != 0)
            t.add(new BiomeMeta(EntitySwet.class, mod_AetherMp.raritySwet));
        if(mod_AetherMp.rarityAechorPlant != 0)
            t.add(new BiomeMeta(EntityAechorPlant.class, mod_AetherMp.rarityAechorPlant));
        if(mod_AetherMp.rarityCockatrice != 0)
            s.add(new BiomeMeta(EntityCockatrice.class, mod_AetherMp.rarityCockatrice));
        if(mod_AetherMp.rarityAerwhale != 0)
            s.add(new BiomeMeta(EntityAerwhale.class, mod_AetherMp.rarityAerwhale));*/
        if(mod_AetherMp.rarityZephyr != 0)
            s.add(new BiomeMeta(EntityZephyr.class, mod_AetherMp.rarityZephyr));
        if(mod_AetherMp.raritySheepuff != 0)
            t.add(new BiomeMeta(EntitySheepuff.class, mod_AetherMp.raritySheepuff));
        /*if(mod_AetherMp.rarityPhyg != 0)
            t.add(new BiomeMeta(EntityPhyg.class, mod_AetherMp.rarityPhyg));
        if(mod_AetherMp.rarityMoa != 0)
            t.add(new BiomeMeta(EntityMoa.class, mod_AetherMp.rarityMoa));
        if(mod_AetherMp.rarityFlyingCow != 0)
            t.add(new BiomeMeta(EntityFlyingCow.class, mod_AetherMp.rarityFlyingCow));
        if(mod_AetherMp.rarityWhirlwind != 0)
            t.add(new BiomeMeta(Whirly.class, mod_AetherMp.rarityWhirlwind));
        if(mod_AetherMp.rarityAerbunny != 0)
            t.add(new BiomeMeta(EntityAerbunny.class, mod_AetherMp.rarityAerbunny));*/
        me = this;
    }
    
    @Override
    public WorldGenerator a(Random random) {
        if(random.nextInt(100) == 0)
            return new AetherGenGoldenOak();
        else
            return new AetherGenSkyroot();
    }

    public static BiomeAether me = null;
}
