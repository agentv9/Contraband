package com.kaseknife95.contraband.core.base.drugs;

import com.kaseknife95.contraband.core.base.genetics.GeneticsData;
import com.kaseknife95.contraband.core.base.substances.SubstanceData;

public record DrugData(
        String drugId,
        String displayName,
        DrugType drugType,
        float basePotency,
        float baseQuality,
        GeneticsData geneticsData,
        SubstanceData substanceData
) {

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