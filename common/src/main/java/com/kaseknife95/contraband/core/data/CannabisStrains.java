package com.kaseknife95.contraband.core.data;

import com.kaseknife95.contraband.core.base.genetics.GeneticsData;
import com.kaseknife95.contraband.core.data.biome_related.BiomeStrainFactory;
import com.kaseknife95.contraband.core.data.biome_related.VanillaBiomeProfiles;

import java.util.ArrayList;

import java.util.List;

public final class CannabisStrains {

    private CannabisStrains() {}

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