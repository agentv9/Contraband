package com.kaseknife95.contraband.core.data;


import com.kaseknife95.contraband.block.ModBlocks;
import com.kaseknife95.contraband.core.base.drugs.DrugBase;
import com.kaseknife95.contraband.core.base.growables.GrowableBase;
import com.kaseknife95.contraband.core.base.growables.GrowableData;
import com.kaseknife95.contraband.core.base.growables.GrowableDefinition;
import com.kaseknife95.contraband.core.base.propagation.PropagationBase;
import com.kaseknife95.contraband.item.Items;


import java.util.Map;

public final class Growables {

    public static final GrowableData CANNABIS_DATA =
            new GrowableData(
                    "cannabis",
                    7,
                    1,
                    0.85F,
                    1.0F,
                    0.015F,
                    1.0F,
                    0.01F,
                    0.8F,
                    0.25F,
                    9,
                    15,
                    true,
                    false
            );

    public static final GrowableDefinition CANNABIS =
            new GrowableDefinition(
                    CANNABIS_DATA,
                    () -> (GrowableBase) ModBlocks.CANNABIS_CROP.get(),
                    () -> (PropagationBase) Items.CANNABIS_SEED.get(),
                    () -> (DrugBase) Items.CANNABIS_LEAF.get(),
                    () -> (DrugBase) Items.CANNABIS_BUD.get()
            );

    private static final Map<String, GrowableDefinition> DEFINITIONS =
            Map.of(
                    CANNABIS.speciesId(),
                    CANNABIS
            );

    private Growables() {}

    public static GrowableDefinition get(String speciesId) {
        return DEFINITIONS.get(speciesId);
    }

    public static GrowableDefinition require(String speciesId) {
        GrowableDefinition definition = get(speciesId);

        if (definition == null) {
            throw new IllegalArgumentException(
                    "Unknown growable species: " + speciesId
            );
        }

        return definition;
    }
}