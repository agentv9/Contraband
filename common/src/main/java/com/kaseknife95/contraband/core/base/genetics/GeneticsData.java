package com.kaseknife95.contraband.core.base.genetics;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record GeneticsData(
        String speciesId,
        float yieldModifier,
        float stability,
        float geneticQuality
) {
    public static final Codec<GeneticsData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("species_id").forGetter(GeneticsData::speciesId),
            Codec.FLOAT.fieldOf("yield_modifier").forGetter(GeneticsData::yieldModifier),
            Codec.FLOAT.fieldOf("stability").forGetter(GeneticsData::stability),
            Codec.FLOAT.fieldOf("genetic_quality").forGetter(GeneticsData::geneticQuality)
    ).apply(instance, GeneticsData::new));

    public GeneticsData {
        if (speciesId == null || speciesId.isBlank()) {
            throw new IllegalArgumentException("speciesId cannot be null or blank");
        }
    }

    public static GeneticsData defaultGenetics(String speciesId) {
        return new GeneticsData(speciesId, 1.0F, 1.0F, 1.0F);
    }
}