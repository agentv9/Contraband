package com.kaseknife95.contraband.core.data.biome_related;

import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public final class VanillaBiomeProfiles {

    private static final Map<ResourceLocation, BiomeClimateProfile> PROFILES =
            new HashMap<>();

    static {
        register("plains", 0.8F, 0.4F);
        register("sunflower_plains", 0.8F, 0.4F);
        register("desert", 2.0F, 0.0F);
        register("swamp", 0.8F, 0.9F);
        register("mangrove_swamp", 0.8F, 0.9F);
        register("jungle", 0.95F, 0.9F);
        register("snowy_plains", 0.0F, 0.5F);
        register("badlands", 2.0F, 0.0F);
    }

    private static void register(
            String biomeId,
            float temperature,
            float humidity
    ) {
        PROFILES.put(
                ResourceLocation.withDefaultNamespace(biomeId),
                new BiomeClimateProfile(temperature, humidity)
        );
    }

    public static BiomeClimateProfile get(ResourceLocation biomeId) {
        return PROFILES.get(biomeId);
    }

    public static Map<ResourceLocation, BiomeClimateProfile> all() {
        return Map.copyOf(PROFILES);
    }

    private VanillaBiomeProfiles() {}
}
