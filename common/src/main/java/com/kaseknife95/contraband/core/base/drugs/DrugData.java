package com.kaseknife95.contraband.core.base.drugs;

import com.kaseknife95.contraband.core.base.genetics.GeneticsData;
import com.kaseknife95.contraband.core.base.substances.SubstanceData;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record DrugData(
        String drugId,
        String displayName,
        DrugType drugType,
        float basePotency,
        float baseQuality,
        GeneticsData geneticsData,
        SubstanceData substanceData
) {

    public static final Codec<DrugData> CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    Codec.STRING.fieldOf("drug_id").forGetter(DrugData::drugId),
                    Codec.STRING.fieldOf("display_name").forGetter(DrugData::displayName),

                    DrugType.CODEC.fieldOf("drug_type").forGetter(DrugData::drugType),

                    Codec.FLOAT.fieldOf("base_potency").forGetter(DrugData::basePotency),
                    Codec.FLOAT.fieldOf("base_quality").forGetter(DrugData::baseQuality),

                    GeneticsData.CODEC.fieldOf("genetics_data").forGetter(DrugData::geneticsData),

                    SubstanceData.CODEC.fieldOf("substance_data").forGetter(DrugData::substanceData)

            ).apply(instance, DrugData::new));

    public int drugColor() {
        return substanceData != null ? substanceData.color() : 0xFFFFFF;
    }

    public float effectivePotency() {
        return basePotency * (substanceData != null ? substanceData.potencyModifier() : 1.0F);
    }

    public float effectiveQuality() {
        return baseQuality * (substanceData != null ? substanceData.qualityModifier() : 1.0F);
    }

    public boolean hasGenetics() {
        return geneticsData != null;
    }

    public boolean hasSubstanceData() {
        return substanceData != null;
    }


}