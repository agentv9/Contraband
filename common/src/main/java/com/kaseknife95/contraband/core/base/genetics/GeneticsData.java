package com.kaseknife95.contraband.core.base.genetics;

public record GeneticsData(
        String speciesId,
        float yieldModifier,
        float stability,
        float geneticQuality
) {
    public GeneticsData {
        if (speciesId == null || speciesId.isBlank()) {
            throw new IllegalArgumentException("speciesId cannot be null or blank");
        }
    }

    public static GeneticsData defaultGenetics(String speciesId) {
        return new GeneticsData(
                speciesId,
                1.0F,
                1.0F,
                1.0F
        );
    }
}