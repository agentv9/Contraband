package com.kaseknife95.psychedelicraft.core.base.genetics;

public record GeneticsData(
        String strainName,
        float potencyModifier,
        float yieldModifier,
        float stability,
        float quality
) {
    public GeneticsData {
        if (strainName == null || strainName.isBlank()) {
            throw new IllegalArgumentException("strainName cannot be null or blank");
        }

        if (stability < 0.0F) {
            throw new IllegalArgumentException("stability cannot be negative");
        }

        if (quality < 0.0F) {
            throw new IllegalArgumentException("quality cannot be negative");
        }
    }

    public static GeneticsData defaultStrain(String strainName) {
        return new GeneticsData(
                strainName,
                1.0F,
                1.0F,
                1.0F,
                1.0F
        );
    }
}