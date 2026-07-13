package com.kaseknife95.contraband.core.base.breeding;

import com.kaseknife95.contraband.core.base.genetics.GeneticsData;
import net.minecraft.util.RandomSource;

import java.util.Locale;
import java.util.Objects;

public final class GrowableBreeder {

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

        /*
         * Stable parents remain close to their average.
         * Unstable parents receive a wider mutation range.
         */
        float mutationRange =
                lerp(0.15F, 0.025F, clamp01(averageStability));

        float childYield = inheritFloat(
                first.yieldModifier(),
                second.yieldModifier(),
                mutationRange,
                random
        );

        float childStability = inheritFloat(
                first.stability(),
                second.stability(),
                mutationRange * 0.5F,
                random
        );

        float childQuality = inheritFloat(
                first.geneticQuality(),
                second.geneticQuality(),
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
        String strainId = createStrainId(
                first,
                second,
                random
        );

        return new GeneticsData(
                first.speciesId(),
                strainId,
                strainName,
                clamp(childYield, 0.1F, 5.0F),
                clamp(childStability, 0.1F, 5.0F),
                clamp(childQuality, 0.1F, 5.0F),
                primaryColor,
                secondaryColor
        );
    }

    private static float inheritFloat(
            float first,
            float second,
            float mutationRange,
            RandomSource random
    ) {
        float parentBlend = random.nextFloat();
        float inherited = lerp(first, second, parentBlend);

        float mutation =
                (random.nextFloat() * 2.0F - 1.0F)
                        * mutationRange;

        return inherited + mutation;
    }

    private static int inheritColor(
            int first,
            int second,
            RandomSource random
    ) {
        float blend = 0.35F + random.nextFloat() * 0.30F;

        int red = blendChannel(
                (first >> 16) & 0xFF,
                (second >> 16) & 0xFF,
                blend
        );

        int green = blendChannel(
                (first >> 8) & 0xFF,
                (second >> 8) & 0xFF,
                blend
        );

        int blue = blendChannel(
                first & 0xFF,
                second & 0xFF,
                blend
        );

        /*
         * Small test mutation so that breeding results are visually obvious.
         */
        red = mutateChannel(red, random);
        green = mutateChannel(green, random);
        blue = mutateChannel(blue, random);

        return (red << 16) | (green << 8) | blue;
    }

    private static int blendChannel(
            int first,
            int second,
            float blend
    ) {
        return Math.round(lerp(first, second, blend));
    }

    private static int mutateChannel(
            int value,
            RandomSource random
    ) {
        int mutation = random.nextInt(17) - 8;
        return Math.max(0, Math.min(255, value + mutation));
    }

    private static String createStrainName(
            GeneticsData first,
            GeneticsData second
    ) {
        if (first.strainName().equalsIgnoreCase(second.strainName())) {
            return first.strainName() + " Hybrid";
        }

        return first.strainName() + " × " + second.strainName();
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

    private static float clamp01(float value) {
        return clamp(value, 0.0F, 1.0F);
    }

    private static float clamp(
            float value,
            float minimum,
            float maximum
    ) {
        return Math.max(minimum, Math.min(maximum, value));
    }

    private static float lerp(
            float start,
            float end,
            float amount
    ) {
        return start + (end - start) * amount;
    }
}