package com.kaseknife95.contraband.core.base.substances;

import com.kaseknife95.contraband.core.util.ColorUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record SubstanceData(
        String strainName,
        float potencyModifier,
        float qualityModifier,
        float purity,
        int color
) {

    public static final Codec<SubstanceData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("strain_name").forGetter(SubstanceData::strainName),

            Codec.FLOAT.fieldOf("potency_modifier").forGetter(SubstanceData::potencyModifier),
            Codec.FLOAT.fieldOf("quality_modifier").forGetter(SubstanceData::qualityModifier),
            Codec.FLOAT.fieldOf("purity").forGetter(SubstanceData::purity),

            Codec.INT.fieldOf("color").forGetter(SubstanceData::color)

    ).apply(instance, SubstanceData::new));

    public static SubstanceData defaultVariant(String variantName, int PrimaryColor, int SecondaryColor) {
        return new SubstanceData(
                variantName,
                1.0F,
                1.0F,
                1.0F,
                ColorUtils.blendColors(PrimaryColor, SecondaryColor, 0.70F)
        );
    }
}
