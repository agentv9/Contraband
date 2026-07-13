package com.kaseknife95.contraband.core.base.growables;

import com.kaseknife95.contraband.core.base.drugs.DrugBase;
import com.kaseknife95.contraband.core.base.propagation.PropagationBase;

import java.util.Objects;
import java.util.function.Supplier;

public record GrowableDefinition(
        GrowableData data,
        Supplier<? extends GrowableBase> crop,
        Supplier<? extends PropagationBase> propagationItem,
        Supplier<? extends DrugBase> rawProduct
) {
    public GrowableDefinition {
        Objects.requireNonNull(data, "data cannot be null");
        Objects.requireNonNull(crop, "crop cannot be null");
        Objects.requireNonNull(propagationItem, "propagationItem cannot be null");
        Objects.requireNonNull(rawProduct, "rawProduct cannot be null");
    }

    public String speciesId() {
        return data.speciesId();
    }
}