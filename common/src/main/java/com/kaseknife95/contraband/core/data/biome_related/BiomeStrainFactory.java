package com.kaseknife95.contraband.core.data.biome_related;

import com.kaseknife95.contraband.core.base.genetics.GeneticsData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;

import java.util.List;
import java.util.Locale;

public final class BiomeStrainFactory {

    private static final int NORMAL_COLOR_MUTATION = 18;
    private static final int RARE_COLOR_MUTATION = 40;

    private static final float MIN_TRAIT = 0.10F;
    private static final float MAX_TRAIT = 5.00F;

    private static final float MIN_EFFICIENCY = 0.25F;
    private static final float MAX_EFFICIENCY = 2.00F;

    private BiomeStrainFactory() {}

    /**
     * two deterministic starting strains for a biome:
     *
     * Native:
     * - stable
     * - specialized for the biome
     * - moderate trait variance
     *
     * Wild:
     * - less stable
     * - broader tolerances
     * - stronger trait and color variance
     */
    public static List<GeneticsData> createBiomeStrains(
            String speciesId,
            ResourceLocation biomeId,
            float temperature,
            float humidity
    ) {
        if (speciesId == null || speciesId.isBlank()) {
            throw new IllegalArgumentException(
                    "speciesId cannot be null or blank"
            );
        }

        if (biomeId == null) {
            throw new IllegalArgumentException(
                    "biomeId cannot be null"
            );
        }

        temperature = clamp(temperature, -1.0F, 3.0F);
        humidity = clamp01(humidity);

        RandomSource nativeRandom = createDeterministicRandom(
                speciesId,
                biomeId,
                "native"
        );

        RandomSource wildRandom = createDeterministicRandom(
                speciesId,
                biomeId,
                "wild"
        );

        String biomeName = formatBiomeName(biomeId);

        int nativePrimary = generateBiomePrimaryColor(
                temperature,
                humidity,
                nativeRandom
        );

        int nativeSecondary = generateRelatedSecondaryColor(
                nativePrimary,
                temperature,
                humidity,
                nativeRandom,
                false
        );

        int wildPrimary = generateBiomePrimaryColor(
                temperature,
                humidity,
                wildRandom
        );

        /*
         * Wild strains receive a stronger hue and saturation shift so they
         * do not look like slightly altered copies of the native strain.
         */
        wildPrimary = rotateHue(
                wildPrimary,
                randomSignedRange(wildRandom, 20.0F, 65.0F)
        );

        wildPrimary = adjustSaturation(
                wildPrimary,
                randomRange(wildRandom, 0.80F, 1.55F)
        );

        wildPrimary = mutateColor(
                wildPrimary,
                wildRandom,
                RARE_COLOR_MUTATION
        );

        int wildSecondary = generateRelatedSecondaryColor(
                wildPrimary,
                temperature,
                humidity,
                wildRandom,
                true
        );

        GeneticsData nativeStrain = new GeneticsData(
                speciesId,
                createStrainId(biomeId, "native"),
                biomeName + " Native",

                createNativeYield(temperature, humidity),
                1.15F,
                createNativeQuality(temperature, humidity),

                temperature,
                createNativeTemperatureTolerance(temperature),

                humidity,
                createNativeHumidityTolerance(humidity),

                hydrationEfficiencyFor(humidity),
                nutrientEfficiencyFor(humidity),
                createNativeGrowthSpeed(temperature, humidity),

                nativePrimary,
                nativeSecondary
        );

        GeneticsData wildStrain = new GeneticsData(
                speciesId,
                createStrainId(biomeId, "wild"),
                biomeName + " Wild",

                createWildYield(
                        temperature,
                        humidity,
                        wildRandom
                ),

                clamp(
                        0.70F
                                + randomRange(
                                wildRandom,
                                -0.12F,
                                0.12F
                        ),
                        MIN_TRAIT,
                        MAX_TRAIT
                ),

                clamp(
                        1.10F
                                + randomRange(
                                wildRandom,
                                -0.15F,
                                0.20F
                        ),
                        MIN_TRAIT,
                        MAX_TRAIT
                ),

                clamp(
                        temperature
                                + randomRange(
                                wildRandom,
                                -0.12F,
                                0.12F
                        ),
                        -1.0F,
                        3.0F
                ),

                clamp(
                        createNativeTemperatureTolerance(temperature)
                                + randomRange(
                                wildRandom,
                                0.10F,
                                0.25F
                        ),
                        0.05F,
                        2.0F
                ),

                clamp01(
                        humidity
                                + randomRange(
                                wildRandom,
                                -0.12F,
                                0.12F
                        )
                ),

                clamp(
                        createNativeHumidityTolerance(humidity)
                                + randomRange(
                                wildRandom,
                                0.10F,
                                0.25F
                        ),
                        0.05F,
                        1.0F
                ),

                clamp(
                        hydrationEfficiencyFor(humidity)
                                + randomRange(
                                wildRandom,
                                -0.05F,
                                0.20F
                        ),
                        MIN_EFFICIENCY,
                        MAX_EFFICIENCY
                ),

                clamp(
                        nutrientEfficiencyFor(humidity)
                                + randomRange(
                                wildRandom,
                                -0.15F,
                                0.15F
                        ),
                        MIN_EFFICIENCY,
                        MAX_EFFICIENCY
                ),

                clamp(
                        1.05F
                                + randomRange(
                                wildRandom,
                                -0.15F,
                                0.20F
                        ),
                        MIN_EFFICIENCY,
                        MAX_EFFICIENCY
                ),

                wildPrimary,
                wildSecondary
        );

        return List.of(nativeStrain, wildStrain);
    }

    private static float createNativeYield(
            float temperature,
            float humidity
    ) {
        float temperaturePenalty =
                Math.abs(temperature - 0.8F) * 0.15F;

        float humidityPenalty =
                Math.abs(humidity - 0.5F) * 0.10F;

        return clamp(
                1.05F
                        - temperaturePenalty
                        - humidityPenalty,
                0.75F,
                1.15F
        );
    }

    private static float createWildYield(
            float temperature,
            float humidity,
            RandomSource random
    ) {
        float nativeYield = createNativeYield(
                temperature,
                humidity
        );

        return clamp(
                nativeYield
                        + randomRange(
                        random,
                        -0.18F,
                        0.25F
                ),
                0.65F,
                1.35F
        );
    }

    private static float createNativeQuality(
            float temperature,
            float humidity
    ) {
        float extremity =
                Math.abs(temperature - 0.8F) * 0.05F
                        + Math.abs(humidity - 0.5F) * 0.05F;

        return clamp(
                1.0F + extremity,
                0.90F,
                1.15F
        );
    }

    private static float createNativeGrowthSpeed(
            float temperature,
            float humidity
    ) {
        float temperaturePenalty =
                Math.abs(temperature - 0.8F) * 0.08F;

        float humidityPenalty =
                Math.abs(humidity - 0.5F) * 0.05F;

        return clamp(
                1.05F
                        - temperaturePenalty
                        - humidityPenalty,
                0.75F,
                1.15F
        );
    }

    private static float createNativeTemperatureTolerance(
            float temperature
    ) {
        /*
         * Extreme-biome strains receive slightly wider tolerances,
         * while temperate strains remain more specialized.
         */
        float extremity =
                Math.abs(temperature - 0.8F);

        return clamp(
                0.14F + extremity * 0.06F,
                0.12F,
                0.30F
        );
    }

    private static float createNativeHumidityTolerance(
            float humidity
    ) {
        float extremity =
                Math.abs(humidity - 0.5F);

        return clamp(
                0.14F + extremity * 0.08F,
                0.12F,
                0.28F
        );
    }

    private static float hydrationEfficiencyFor(float humidity) {
        /*
         * Dry-climate strains conserve water more efficiently.
         */
        return clamp(
                1.30F - humidity * 0.40F,
                0.75F,
                1.40F
        );
    }

    private static float nutrientEfficiencyFor(float humidity) {
        return clamp(
                0.95F + humidity * 0.15F,
                0.75F,
                1.25F
        );
    }

    /**
     * Generates a biome-based starting color, then applies deterministic
     * variation so similar biomes do not all produce identical palettes.
     */
    private static int generateBiomePrimaryColor(
            float temperature,
            float humidity,
            RandomSource random
    ) {
        int coldDry = 0x89AFC4;
        int coldWet = 0x5C908A;

        int temperateDry = 0x8A9A49;
        int temperateWet = 0x43834C;

        int hotDry = 0xC58A3D;
        int hotWet = 0x318A43;

        float normalizedHeat =
                normalizeTemperature(temperature);

        int dryColor;
        int wetColor;

        if (normalizedHeat < 0.5F) {
            float localHeat = normalizedHeat * 2.0F;

            dryColor = blendColors(
                    coldDry,
                    temperateDry,
                    localHeat
            );

            wetColor = blendColors(
                    coldWet,
                    temperateWet,
                    localHeat
            );
        } else {
            float localHeat =
                    (normalizedHeat - 0.5F) * 2.0F;

            dryColor = blendColors(
                    temperateDry,
                    hotDry,
                    localHeat
            );

            wetColor = blendColors(
                    temperateWet,
                    hotWet,
                    localHeat
            );
        }

        int result = blendColors(
                dryColor,
                wetColor,
                humidity
        );

        /*
         * Deterministic parent dominance and hue movement provide more
         * separation between biomes with similar climate values.
         */
        result = rotateHue(
                result,
                randomSignedRange(random, 8.0F, 28.0F)
        );

        result = adjustSaturation(
                result,
                randomRange(random, 0.80F, 1.35F)
        );

        result = adjustBrightness(
                result,
                randomRange(random, 0.82F, 1.18F)
        );

        return mutateColor(
                result,
                random,
                NORMAL_COLOR_MUTATION
        );
    }

    private static int generateRelatedSecondaryColor(
            int primaryColor,
            float temperature,
            float humidity,
            RandomSource random,
            boolean wild
    ) {
        float minimumHueShift = wild ? 40.0F : 22.0F;
        float maximumHueShift = wild ? 115.0F : 65.0F;

        int secondary = rotateHue(
                primaryColor,
                randomSignedRange(
                        random,
                        minimumHueShift,
                        maximumHueShift
                )
        );

        /*
         * Different climates influence accent brightness.
         */
        if (temperature < 0.2F) {
            secondary = blendColors(
                    secondary,
                    0xD7ECF4,
                    0.35F
            );
        } else if (temperature > 1.5F && humidity < 0.30F) {
            secondary = blendColors(
                    secondary,
                    0xD7632E,
                    0.35F
            );
        } else if (humidity > 0.75F) {
            secondary = blendColors(
                    secondary,
                    0x72639A,
                    0.30F
            );
        }

        secondary = adjustSaturation(
                secondary,
                wild
                        ? randomRange(random, 0.90F, 1.60F)
                        : randomRange(random, 0.75F, 1.35F)
        );

        secondary = adjustBrightness(
                secondary,
                randomRange(random, 0.78F, 1.22F)
        );

        secondary = mutateColor(
                secondary,
                random,
                wild
                        ? RARE_COLOR_MUTATION
                        : NORMAL_COLOR_MUTATION
        );

        /*
         * Ensure both seed tint layers remain visibly distinct.
         */
        if (colorDistance(primaryColor, secondary) < 65.0F) {
            secondary = rotateHue(
                    secondary,
                    random.nextBoolean()
                            ? 55.0F
                            : -55.0F
            );

            secondary = adjustSaturation(
                    secondary,
                    1.20F
            );
        }

        return secondary;
    }

    public static int blendColors(
            int first,
            int second,
            float secondRatio
    ) {
        float ratio = clamp01(secondRatio);
        float firstRatio = 1.0F - ratio;

        int red = Math.round(
                getRed(first) * firstRatio
                        + getRed(second) * ratio
        );

        int green = Math.round(
                getGreen(first) * firstRatio
                        + getGreen(second) * ratio
        );

        int blue = Math.round(
                getBlue(first) * firstRatio
                        + getBlue(second) * ratio
        );

        return packColor(red, green, blue);
    }

    private static int mutateColor(
            int color,
            RandomSource random,
            int maximumChange
    ) {
        return packColor(
                mutateColorChannel(
                        getRed(color),
                        random,
                        maximumChange
                ),
                mutateColorChannel(
                        getGreen(color),
                        random,
                        maximumChange
                ),
                mutateColorChannel(
                        getBlue(color),
                        random,
                        maximumChange
                )
        );
    }

    private static int mutateColorChannel(
            int channel,
            RandomSource random,
            int maximumChange
    ) {
        int change = random.nextInt(
                maximumChange * 2 + 1
        ) - maximumChange;

        return clampColorChannel(channel + change);
    }

    private static int rotateHue(
            int color,
            float degrees
    ) {
        float[] hsv = rgbToHsv(
                getRed(color),
                getGreen(color),
                getBlue(color)
        );

        hsv[0] = wrap01(
                hsv[0] + degrees / 360.0F
        );

        return hsvToRgb(
                hsv[0],
                hsv[1],
                hsv[2]
        );
    }

    private static int adjustSaturation(
            int color,
            float multiplier
    ) {
        float[] hsv = rgbToHsv(
                getRed(color),
                getGreen(color),
                getBlue(color)
        );

        hsv[1] = clamp01(
                hsv[1] * multiplier
        );

        return hsvToRgb(
                hsv[0],
                hsv[1],
                hsv[2]
        );
    }

    private static int adjustBrightness(
            int color,
            float multiplier
    ) {
        float[] hsv = rgbToHsv(
                getRed(color),
                getGreen(color),
                getBlue(color)
        );

        hsv[2] = clamp01(
                hsv[2] * multiplier
        );

        return hsvToRgb(
                hsv[0],
                hsv[1],
                hsv[2]
        );
    }

    private static float[] rgbToHsv(
            int red,
            int green,
            int blue
    ) {
        float r = red / 255.0F;
        float g = green / 255.0F;
        float b = blue / 255.0F;

        float maximum = Math.max(
                r,
                Math.max(g, b)
        );

        float minimum = Math.min(
                r,
                Math.min(g, b)
        );

        float difference = maximum - minimum;

        float hue;

        if (difference == 0.0F) {
            hue = 0.0F;
        } else if (maximum == r) {
            hue = ((g - b) / difference) % 6.0F;
        } else if (maximum == g) {
            hue = ((b - r) / difference) + 2.0F;
        } else {
            hue = ((r - g) / difference) + 4.0F;
        }

        hue = wrap01(hue / 6.0F);

        float saturation =
                maximum == 0.0F
                        ? 0.0F
                        : difference / maximum;

        return new float[] {
                hue,
                saturation,
                maximum
        };
    }

    private static int hsvToRgb(
            float hue,
            float saturation,
            float value
    ) {
        hue = wrap01(hue);
        saturation = clamp01(saturation);
        value = clamp01(value);

        float scaledHue = hue * 6.0F;
        int sector = (int) Math.floor(scaledHue);
        float fraction = scaledHue - sector;

        float p = value * (1.0F - saturation);
        float q = value * (1.0F - fraction * saturation);
        float t = value
                * (1.0F - (1.0F - fraction) * saturation);

        float red;
        float green;
        float blue;

        switch (sector % 6) {
            case 0 -> {
                red = value;
                green = t;
                blue = p;
            }

            case 1 -> {
                red = q;
                green = value;
                blue = p;
            }

            case 2 -> {
                red = p;
                green = value;
                blue = t;
            }

            case 3 -> {
                red = p;
                green = q;
                blue = value;
            }

            case 4 -> {
                red = t;
                green = p;
                blue = value;
            }

            default -> {
                red = value;
                green = p;
                blue = q;
            }
        }

        return packColor(
                Math.round(red * 255.0F),
                Math.round(green * 255.0F),
                Math.round(blue * 255.0F)
        );
    }

    private static float colorDistance(
            int first,
            int second
    ) {
        int redDifference =
                getRed(first) - getRed(second);

        int greenDifference =
                getGreen(first) - getGreen(second);

        int blueDifference =
                getBlue(first) - getBlue(second);

        return (float) Math.sqrt(
                redDifference * redDifference
                        + greenDifference * greenDifference
                        + blueDifference * blueDifference
        );
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
        return (clampColorChannel(red) << 16)
                | (clampColorChannel(green) << 8)
                | clampColorChannel(blue);
    }

    private static int clampColorChannel(int value) {
        return Math.max(
                0,
                Math.min(255, value)
        );
    }

    private static float normalizeTemperature(
            float temperature
    ) {
        /*
         * Maps the expected -0.5 to 2.0 biome range approximately
         * into 0.0 to 1.0.
         */
        return clamp01(
                (temperature + 0.5F) / 2.5F
        );
    }

    private static RandomSource createDeterministicRandom(
            String speciesId,
            ResourceLocation biomeId,
            String variant
    ) {
        long seed = 17L;

        seed = 31L * seed + speciesId.hashCode();
        seed = 31L * seed + biomeId.toString().hashCode();
        seed = 31L * seed + variant.hashCode();

        return RandomSource.create(seed);
    }

    private static String createStrainId(
            ResourceLocation biomeId,
            String variant
    ) {
        String namespacePrefix =
                biomeId.getNamespace().equals("minecraft")
                        ? ""
                        : biomeId.getNamespace() + "_";

        return sanitizeId(
                namespacePrefix
                        + biomeId.getPath()
                        + "_"
                        + variant
        );
    }

    private static String formatBiomeName(
            ResourceLocation biomeId
    ) {
        String[] words =
                biomeId.getPath().split("_");

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

    private static String sanitizeId(String value) {
        return value
                .toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9_]+", "_")
                .replaceAll("_+", "_")
                .replaceAll("^_|_$", "");
    }

    private static float randomSignedRange(
            RandomSource random,
            float minimumMagnitude,
            float maximumMagnitude
    ) {
        float magnitude = randomRange(
                random,
                minimumMagnitude,
                maximumMagnitude
        );

        return random.nextBoolean()
                ? magnitude
                : -magnitude;
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

    private static float wrap01(float value) {
        value %= 1.0F;

        if (value < 0.0F) {
            value += 1.0F;
        }

        return value;
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