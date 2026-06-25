package com.kaseknife95.contraband.core.base.substances;

import com.kaseknife95.contraband.core.base.drugs.SubstanceColorGenerator;

public record SubstanceData(
        String strainName,
        float potencyModifier,
        float qualityModifier,
        float purity,
        int color
) {
    public static SubstanceData defaultVariant(String variantName) {
        return new SubstanceData(
                variantName,
                1.0F,
                1.0F,
                1.0F,
                SubstanceColorGenerator.fromStrainName(variantName)
        );
    }
}
