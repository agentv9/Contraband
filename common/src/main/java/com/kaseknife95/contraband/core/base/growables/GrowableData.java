package com.kaseknife95.contraband.core.base.growables;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record GrowableData(
        String speciesId,

        int maxAge,
        int maxHeight,

        float baseGrowthChance,

        float hydrationCapacity,
        float hydrationDrain,

        float nutrientCapacity,
        float nutrientDrain,

        float idealTemperature,
        float temperatureTolerance,

        int minimumLight,
        int maximumLight,

        boolean acceptsBonemeal,
        boolean regrows
) {

    public static final Codec<GrowableData> CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    Codec.STRING
                            .fieldOf("species_id")
                            .forGetter(GrowableData::speciesId),

                    Codec.intRange(1, 7)
                            .fieldOf("max_age")
                            .forGetter(GrowableData::maxAge),

                    Codec.intRange(1, 3)
                            .optionalFieldOf("max_height", 1)
                            .forGetter(GrowableData::maxHeight),

                    Codec.floatRange(0.0F, 1.0F)
                            .optionalFieldOf("base_growth_chance", 1.0F)
                            .forGetter(GrowableData::baseGrowthChance),

                    Codec.FLOAT
                            .optionalFieldOf("hydration_capacity", 1.0F)
                            .forGetter(GrowableData::hydrationCapacity),

                    Codec.FLOAT
                            .optionalFieldOf("hydration_drain", 0.0F)
                            .forGetter(GrowableData::hydrationDrain),

                    Codec.FLOAT
                            .optionalFieldOf("nutrient_capacity", 1.0F)
                            .forGetter(GrowableData::nutrientCapacity),

                    Codec.FLOAT
                            .optionalFieldOf("nutrient_drain", 0.0F)
                            .forGetter(GrowableData::nutrientDrain),

                    Codec.FLOAT
                            .optionalFieldOf("ideal_temperature", 0.8F)
                            .forGetter(GrowableData::idealTemperature),

                    Codec.FLOAT
                            .optionalFieldOf("temperature_tolerance", 0.3F)
                            .forGetter(GrowableData::temperatureTolerance),

                    Codec.intRange(0, 15)
                            .optionalFieldOf("minimum_light", 9)
                            .forGetter(GrowableData::minimumLight),

                    Codec.intRange(0, 15)
                            .optionalFieldOf("maximum_light", 15)
                            .forGetter(GrowableData::maximumLight),

                    Codec.BOOL
                            .optionalFieldOf("accepts_bonemeal", true)
                            .forGetter(GrowableData::acceptsBonemeal),

                    Codec.BOOL
                            .optionalFieldOf("regrows", false)
                            .forGetter(GrowableData::regrows)
            ).apply(instance, GrowableData::new));

    public GrowableData {
        if (speciesId == null || speciesId.isBlank()) {
            throw new IllegalArgumentException(
                    "speciesId cannot be null or blank"
            );
        }

        if (maxAge < 1 || maxAge > 7) {
            throw new IllegalArgumentException(
                    "maxAge must be between 1 and 7 while using AGE_7"
            );
        }

        if (maxHeight < 1) {
            throw new IllegalArgumentException(
                    "maxHeight must be at least 1"
            );
        }

        if (baseGrowthChance < 0.0F || baseGrowthChance > 1.0F) {
            throw new IllegalArgumentException(
                    "baseGrowthChance must be between 0 and 1"
            );
        }

        if (hydrationCapacity < 0.0F) {
            throw new IllegalArgumentException(
                    "hydrationCapacity cannot be negative"
            );
        }

        if (hydrationDrain < 0.0F) {
            throw new IllegalArgumentException(
                    "hydrationDrain cannot be negative"
            );
        }

        if (nutrientCapacity < 0.0F) {
            throw new IllegalArgumentException(
                    "nutrientCapacity cannot be negative"
            );
        }

        if (nutrientDrain < 0.0F) {
            throw new IllegalArgumentException(
                    "nutrientDrain cannot be negative"
            );
        }

        if (temperatureTolerance < 0.0F) {
            throw new IllegalArgumentException(
                    "temperatureTolerance cannot be negative"
            );
        }

        if (minimumLight < 0 || minimumLight > 15) {
            throw new IllegalArgumentException(
                    "minimumLight must be between 0 and 15"
            );
        }

        if (maximumLight < 0 || maximumLight > 15) {
            throw new IllegalArgumentException(
                    "maximumLight must be between 0 and 15"
            );
        }

        if (minimumLight > maximumLight) {
            throw new IllegalArgumentException(
                    "minimumLight cannot be greater than maximumLight"
            );
        }
    }

    public static GrowableData basicCrop(String speciesId) {
        return new GrowableData(
                speciesId,
                7,
                1,
                1.0F,
                1.0F,
                0.0F,
                1.0F,
                0.0F,
                0.8F,
                0.3F,
                9,
                15,
                true,
                false
        );
    }
}