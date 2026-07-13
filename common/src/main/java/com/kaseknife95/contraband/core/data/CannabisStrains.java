package com.kaseknife95.contraband.core.data;

import com.kaseknife95.contraband.core.base.genetics.GeneticsData;
import com.kaseknife95.contraband.core.data.biome_related.BiomeStrainFactory;
import com.kaseknife95.contraband.core.data.biome_related.VanillaBiomeProfiles;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class CannabisStrains {

    private CannabisStrains() {}

    /**
     * Generates two cannabis strains for every biome currently available
     * through the biome registry.
     *
     * This includes vanilla biomes and will also include modded biomes.
     */
    public static List<GeneticsData> createAll() {
        List<GeneticsData> strains = new ArrayList<>();

        VanillaBiomeProfiles.all().forEach((biomeId, profile) ->
                strains.addAll(
                        BiomeStrainFactory.createBiomeStrains(
                                "cannabis",
                                biomeId,
                                profile.temperature(),
                                profile.humidity()
                        )
                )
        );

        return List.copyOf(strains);
    }
}