package com.kaseknife95.contraband.core.data.biome_related;

import com.kaseknife95.contraband.core.base.genetics.GeneticsData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

import java.util.List;
import java.util.Locale;

public final class BiomeStrainFactory {

    private BiomeStrainFactory() {}

    public static List<GeneticsData> createBiomeStrains(
            String speciesId,
            ResourceLocation biomeId,
            float temperature,
            float humidity
    ) {
        RandomSource random = createDeterministicRandom(
                speciesId,
                biomeId
        );

        int primaryColor = generatePrimaryColor(
                temperature,
                humidity,
                random
        );

        int secondaryColor = generateSecondaryColor(
                temperature,
                humidity,
                random
        );

        String biomeName = formatBiomeName(biomeId);

        GeneticsData nativeStrain = new GeneticsData(
                speciesId,
                biomeId.getPath() + "_native",
                biomeName + " Native",

                createNativeYield(temperature, humidity),
                1.15F,
                1.00F,

                temperature,
                0.15F,

                humidity,
                0.15F,

                hydrationEfficiencyFor(humidity),
                nutrientEfficiencyFor(humidity),
                1.00F,

                primaryColor,
                secondaryColor
        );

        GeneticsData wildStrain = new GeneticsData(
                speciesId,
                biomeId.getPath() + "_wild",
                biomeName + " Wild",

                createWildYield(temperature, humidity, random),
                0.75F,
                1.10F,

                addVariation(temperature, 0.08F, random),
                0.30F,

                addVariation(humidity, 0.08F, random),
                0.30F,

                clamp(
                        hydrationEfficiencyFor(humidity) + 0.10F,
                        0.50F,
                        1.50F
                ),

                clamp(
                        nutrientEfficiencyFor(humidity)
                                + randomRange(random, -0.10F, 0.10F),
                        0.50F,
                        1.50F
                ),

                clamp(
                        1.05F + randomRange(random, -0.08F, 0.08F),
                        0.50F,
                        1.50F
                ),

                secondaryColor,
                mutateColor(primaryColor, random, 28)
        );

        return List.of(nativeStrain, wildStrain);
    }

    private static float createNativeYield(
            float temperature,
            float humidity
    ) {
        /*
         * Comfortable temperate climates get slightly better baseline yield.
         * Extreme climates sacrifice yield for specialization.
         */
        float temperaturePenalty =
                Math.abs(temperature - 0.8F) * 0.15F;

        float humidityPenalty =
                Math.abs(humidity - 0.5F) * 0.10F;

        return clamp(
                1.05F - temperaturePenalty - humidityPenalty,
                0.75F,
                1.15F
        );
    }

    private static float createWildYield(
            float temperature,
            float humidity,
            RandomSource random
    ) {
        float nativeYield =
                createNativeYield(temperature, humidity);

        return clamp(
                nativeYield
                        + randomRange(random, -0.15F, 0.20F),
                0.70F,
                1.30F
        );
    }

    private static float hydrationEfficiencyFor(float humidity) {
        /*
         * Dry-biome strains conserve water more efficiently.
         *
         * humidity 0.0 -> approximately 1.30
         * humidity 1.0 -> approximately 0.90
         */
        return clamp(
                1.30F - humidity * 0.40F,
                0.75F,
                1.40F
        );
    }

    private static float nutrientEfficiencyFor(float humidity) {
        /*
         * This is deliberately modest for now.
         * Wet environments receive a small nutrient-efficiency benefit.
         */
        return clamp(
                0.95F + humidity * 0.15F,
                0.75F,
                1.25F
        );
    }

    private static int generatePrimaryColor(
            float temperature,
            float humidity,
            RandomSource random
    ) {
        int coldDry = 0x8FAFC4;
        int coldWet = 0x5F8F88;
        int hotDry = 0xC28A45;
        int hotWet = 0x3F8F45;

        int coldColor = blendColors(
                coldDry,
                coldWet,
                clamp01(humidity)
        );

        int hotColor = blendColors(
                hotDry,
                hotWet,
                clamp01(humidity)
        );

        float heat = normalizeTemperature(temperature);

        return mutateColor(
                blendColors(coldColor, hotColor, heat),
                random,
                12
        );
    }

    private static int generateSecondaryColor(
            float temperature,
            float humidity,
            RandomSource random
    ) {
        int primary = generatePrimaryColor(
                temperature,
                humidity,
                random
        );

        int accent;

        if (temperature > 1.5F && humidity < 0.3F) {
            // Desert and badlands accents.
            accent = 0xD46A32;
        } else if (temperature < 0.2F) {
            // Snowy and frozen accents.
            accent = 0xD5E6F2;
        } else if (humidity > 0.8F) {
            // Swamp/jungle accents.
            accent = 0x6D7F3B;
        } else {
            // Temperate accent.
            accent = 0x7B5A8E;
        }

        int blended = blendColors(
                primary,
                accent,
                0.45F
        );

        return mutateColor(blended, random, 18);
    }

    public static int blendColors(
            int first,
            int second,
            float secondRatio
    ) {
        float ratio = clamp01(secondRatio);
        float firstRatio = 1.0F - ratio;

        int firstRed = first >> 16 & 0xFF;
        int firstGreen = first >> 8 & 0xFF;
        int firstBlue = first & 0xFF;

        int secondRed = second >> 16 & 0xFF;
        int secondGreen = second >> 8 & 0xFF;
        int secondBlue = second & 0xFF;

        int red = Math.round(
                firstRed * firstRatio + secondRed * ratio
        );

        int green = Math.round(
                firstGreen * firstRatio + secondGreen * ratio
        );

        int blue = Math.round(
                firstBlue * firstRatio + secondBlue * ratio
        );

        return red << 16 | green << 8 | blue;
    }

    private static int mutateColor(
            int color,
            RandomSource random,
            int maximumChange
    ) {
        int red = color >> 16 & 0xFF;
        int green = color >> 8 & 0xFF;
        int blue = color & 0xFF;

        red = mutateColorChannel(
                red,
                random,
                maximumChange
        );

        green = mutateColorChannel(
                green,
                random,
                maximumChange
        );

        blue = mutateColorChannel(
                blue,
                random,
                maximumChange
        );

        return red << 16 | green << 8 | blue;
    }

    private static int mutateColorChannel(
            int channel,
            RandomSource random,
            int maximumChange
    ) {
        int change = random.nextInt(
                maximumChange * 2 + 1
        ) - maximumChange;

        return Mth.clamp(channel + change, 0, 255);
    }

    private static float normalizeTemperature(float temperature) {
        /*
         * Maps typical biome temperatures into approximately 0–1.
         * Values outside the expected range are safely clamped.
         */
        return clamp01((temperature + 0.5F) / 2.5F);
    }

    private static float addVariation(
            float value,
            float variation,
            RandomSource random
    ) {
        return clamp01(
                value + randomRange(
                        random,
                        -variation,
                        variation
                )
        );
    }

    private static float randomRange(
            RandomSource random,
            float minimum,
            float maximum
    ) {
        return minimum
                + random.nextFloat() * (maximum - minimum);
    }

    private static RandomSource createDeterministicRandom(
            String speciesId,
            ResourceLocation biomeId
    ) {
        long seed = 31L * speciesId.hashCode()
                + biomeId.toString().hashCode();

        return RandomSource.create(seed);
    }

    private static String formatBiomeName(
            ResourceLocation biomeId
    ) {
        String[] words = biomeId.getPath().split("_");
        StringBuilder name = new StringBuilder();

        for (String word : words) {
            if (word.isBlank()) {
                continue;
            }

            if (!name.isEmpty()) {
                name.append(' ');
            }

            name.append(
                    Character.toUpperCase(word.charAt(0))
            );

            if (word.length() > 1) {
                name.append(
                        word.substring(1)
                                .toLowerCase(Locale.ROOT)
                );
            }
        }

        return name.toString();
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
}
