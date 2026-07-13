package com.kaseknife95.contraband.core.base.breeding;

import com.kaseknife95.contraband.core.base.genetics.GeneticsData;
import net.minecraft.util.RandomSource;

import java.util.Locale;
import java.util.Objects;

public final class GrowableBreeder {

    private static final float MIN_GENERAL_MODIFIER = 0.10F;
    private static final float MAX_GENERAL_MODIFIER = 5.00F;

    private static final float MIN_EFFICIENCY = 0.25F;
    private static final float MAX_EFFICIENCY = 2.00F;

    private static final float MIN_TOLERANCE = 0.01F;
    private static final float MAX_TOLERANCE = 2.00F;

    private static final float MIN_HUMIDITY = 0.00F;
    private static final float MAX_HUMIDITY = 1.00F;


    private static final float MIN_TEMPERATURE = -1.00F;
    private static final float MAX_TEMPERATURE = 3.00F;

    private GrowableBreeder() {}

    public static boolean canBreed(
            GeneticsData first,
            GeneticsData second
    ) {
        return first != null
                && second != null
                && first.speciesId().equals(second.speciesId());
    }

    public static GeneticsData cross(
            GeneticsData first,
            GeneticsData second,
            RandomSource random
    ) {
        Objects.requireNonNull(first, "first genetics cannot be null");
        Objects.requireNonNull(second, "second genetics cannot be null");
        Objects.requireNonNull(random, "random cannot be null");

        if (!canBreed(first, second)) {
            throw new IllegalArgumentException(
                    "Cannot breed different species: "
                            + first.speciesId()
                            + " and "
                            + second.speciesId()
            );
        }


        float averageStability =
                (first.stability() + second.stability()) * 0.5F;

        float normalizedStability = clamp01(averageStability);

        float mutationRange = lerp(
                0.15F,
                0.025F,
                normalizedStability
        );

        float childYield = inheritTrait(
                first.yieldModifier(),
                second.yieldModifier(),
                mutationRange,
                random
        );

        float childStability = inheritTrait(
                first.stability(),
                second.stability(),
                mutationRange * 0.50F,
                random
        );

        float childQuality = inheritTrait(
                first.geneticQuality(),
                second.geneticQuality(),
                mutationRange,
                random
        );


        float childPreferredTemperature = inheritTrait(
                first.preferredTemperature(),
                second.preferredTemperature(),
                mutationRange * 0.35F,
                random
        );

        float childTemperatureTolerance = inheritTrait(
                first.temperatureTolerance(),
                second.temperatureTolerance(),
                mutationRange * 0.50F,
                random
        );

        float childPreferredHumidity = inheritTrait(
                first.preferredHumidity(),
                second.preferredHumidity(),
                mutationRange * 0.25F,
                random
        );

        float childHumidityTolerance = inheritTrait(
                first.humidityTolerance(),
                second.humidityTolerance(),
                mutationRange * 0.50F,
                random
        );

        float childHydrationEfficiency = inheritTrait(
                first.hydrationEfficiency(),
                second.hydrationEfficiency(),
                mutationRange,
                random
        );

        float childNutrientEfficiency = inheritTrait(
                first.nutrientEfficiency(),
                second.nutrientEfficiency(),
                mutationRange,
                random
        );

        float childGrowthSpeed = inheritTrait(
                first.growthSpeedModifier(),
                second.growthSpeedModifier(),
                mutationRange,
                random
        );

        int primaryColor = inheritColor(
                first.primaryColor(),
                second.primaryColor(),
                random
        );

        int secondaryColor = inheritColor(
                first.secondaryColor(),
                second.secondaryColor(),
                random
        );

        String strainName = createStrainName(first, second);
        String strainId = createStrainId(first, second, random);

        return new GeneticsData(
                first.speciesId(),
                strainId,
                strainName,

                clamp(
                        childYield,
                        MIN_GENERAL_MODIFIER,
                        MAX_GENERAL_MODIFIER
                ),

                clamp(
                        childStability,
                        MIN_GENERAL_MODIFIER,
                        MAX_GENERAL_MODIFIER
                ),

                clamp(
                        childQuality,
                        MIN_GENERAL_MODIFIER,
                        MAX_GENERAL_MODIFIER
                ),

                clamp(
                        childPreferredTemperature,
                        MIN_TEMPERATURE,
                        MAX_TEMPERATURE
                ),

                clamp(
                        childTemperatureTolerance,
                        MIN_TOLERANCE,
                        MAX_TOLERANCE
                ),

                clamp(
                        childPreferredHumidity,
                        MIN_HUMIDITY,
                        MAX_HUMIDITY
                ),

                clamp(
                        childHumidityTolerance,
                        MIN_TOLERANCE,
                        MAX_TOLERANCE
                ),

                clamp(
                        childHydrationEfficiency,
                        MIN_EFFICIENCY,
                        MAX_EFFICIENCY
                ),

                clamp(
                        childNutrientEfficiency,
                        MIN_EFFICIENCY,
                        MAX_EFFICIENCY
                ),

                clamp(
                        childGrowthSpeed,
                        MIN_EFFICIENCY,
                        MAX_EFFICIENCY
                ),

                primaryColor,
                secondaryColor
        );
    }


    private static float inheritTrait(
            float first,
            float second,
            float mutationRange,
            RandomSource random
    ) {
        float parentBlend = random.nextFloat();

        float inherited = lerp(
                first,
                second,
                parentBlend
        );

        float mutation =
                randomRange(
                        random,
                        -mutationRange,
                        mutationRange
                );

        return inherited + mutation;
    }

    private static int inheritColor(
            int first,
            int second,
            RandomSource random
    ) {

        float blend = randomRange(
                random,
                0.35F,
                0.65F
        );

        int red = blendChannel(
                getRed(first),
                getRed(second),
                blend
        );

        int green = blendChannel(
                getGreen(first),
                getGreen(second),
                blend
        );

        int blue = blendChannel(
                getBlue(first),
                getBlue(second),
                blend
        );

        red = mutateChannel(red, random);
        green = mutateChannel(green, random);
        blue = mutateChannel(blue, random);

        return packColor(red, green, blue);
    }

    private static int getRed(int color) {
        return (color >> 16) & 0xFF;
    }

    private static int getGreen(int color) {
        return (color >> 8) & 0xFF;
    }

    private static int getBlue(int color) {
        return color & 0xFF;
    }

    private static int packColor(
            int red,
            int green,
            int blue
    ) {
        return (red << 16)
                | (green << 8)
                | blue;
    }

    private static int blendChannel(
            int first,
            int second,
            float blend
    ) {
        return Math.round(
                lerp(first, second, blend)
        );
    }

    private static int mutateChannel(
            int value,
            RandomSource random
    ) {
        int mutation = random.nextInt(17) - 8;

        return Math.max(
                0,
                Math.min(255, value + mutation)
        );
    }

    private static String createStrainName(
            GeneticsData first,
            GeneticsData second
    ) {
        if (first.strainName().equalsIgnoreCase(second.strainName())) {
            return first.strainName() + " Hybrid";
        }

        return first.strainName()
                + " × "
                + second.strainName();
    }

    private static String createStrainId(
            GeneticsData first,
            GeneticsData second,
            RandomSource random
    ) {
        String base =
                first.strainId()
                        + "_x_"
                        + second.strainId()
                        + "_"
                        + Integer.toUnsignedString(
                        random.nextInt(),
                        36
                );

        return sanitizeId(base);
    }

    private static String sanitizeId(String value) {
        return value
                .toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9_]+", "_")
                .replaceAll("_+", "_")
                .replaceAll("^_|_$", "");
    }

    private static float randomRange(
            RandomSource random,
            float minimum,
            float maximum
    ) {
        return minimum
                + random.nextFloat()
                * (maximum - minimum);
    }

    private static float clamp01(float value) {
        return clamp(value, 0.0F, 1.0F);
    }

    private static float clamp(
            float value,
            float minimum,
            float maximum
    ) {
        return Math.max(
                minimum,
                Math.min(maximum, value)
        );
    }

    private static float lerp(
            float start,
            float end,
            float amount
    ) {
        return start + (end - start) * amount;
    }
}