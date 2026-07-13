package com.kaseknife95.contraband.core.base.genetics;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record GeneticsData(
        String speciesId,
        String strainId,
        String strainName,

        float yieldModifier,
        float stability,
        float geneticQuality,

        float preferredTemperature,
        float temperatureTolerance,
        float preferredHumidity,
        float humidityTolerance,

        float hydrationEfficiency,
        float nutrientEfficiency,
        float growthSpeedModifier,

        int primaryColor,
        int secondaryColor
)  {
    public static final Codec<GeneticsData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("species_id").forGetter(GeneticsData::speciesId),
            Codec.STRING.fieldOf("strain_id").forGetter(GeneticsData::strainId),
            Codec.STRING.fieldOf("strain_name").forGetter(GeneticsData::strainName),

            Codec.FLOAT.fieldOf("yield_modifier").forGetter(GeneticsData::yieldModifier),
            Codec.FLOAT.fieldOf("stability").forGetter(GeneticsData::stability),
            Codec.FLOAT.fieldOf("genetic_quality").forGetter(GeneticsData::geneticQuality),

            Codec.FLOAT.fieldOf("preferred_temperature").forGetter(GeneticsData::preferredTemperature),
            Codec.FLOAT.fieldOf("temperature_tolerance").forGetter(GeneticsData::temperatureTolerance),
            Codec.FLOAT.fieldOf("preferred_humidity").forGetter(GeneticsData::preferredHumidity),
            Codec.FLOAT.fieldOf("humidity_tolerance").forGetter(GeneticsData::humidityTolerance),

            Codec.FLOAT.fieldOf("hydration_efficiency").forGetter(GeneticsData::hydrationEfficiency),
            Codec.FLOAT.fieldOf("nutrition_efficiency").forGetter(GeneticsData::nutrientEfficiency),
            Codec.FLOAT.fieldOf("growth_speed_modifier").forGetter(GeneticsData::growthSpeedModifier),

            Codec.INT.fieldOf("primary_color").forGetter(GeneticsData::primaryColor),
            Codec.INT.fieldOf("secondary_color").forGetter(GeneticsData::secondaryColor)
    ).apply(instance, GeneticsData::new));

    public GeneticsData {
        if (speciesId == null || speciesId.isBlank()) {
            throw new IllegalArgumentException("speciesId cannot be null or blank");
        }
    }

    public static GeneticsData defaultGenetics(String speciesId, int PrimaryColor, int SecondaryColor) {
        return new GeneticsData(
                speciesId,
                "default",
                "Default",
                1.0F,
                1.0F,
                1.0F,
                1.0F,
                1.0F,
                1.0F,
                1.0F,
                1.0F,
                1.0F,
                1.0F,
                PrimaryColor,
                SecondaryColor
        );
    }
}