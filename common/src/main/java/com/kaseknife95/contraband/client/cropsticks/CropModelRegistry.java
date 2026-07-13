package com.kaseknife95.contraband.client.cropsticks;

import com.kaseknife95.contraband.core.base.growables.GrowableBase;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public final class CropModelRegistry {

    private static final Map<
            String,
            Supplier<? extends GrowableBase>
            > CROPS = new HashMap<>();

    private CropModelRegistry() {}

    public static void register(
            String speciesId,
            Supplier<? extends GrowableBase> cropSupplier
    ) {
        Objects.requireNonNull(
                speciesId,
                "speciesId cannot be null"
        );

        Objects.requireNonNull(
                cropSupplier,
                "cropSupplier cannot be null"
        );

        CROPS.put(speciesId, cropSupplier);
    }

    public static BlockState getPlantState(
            String speciesId,
            int age
    ) {
        Supplier<? extends GrowableBase> supplier =
                CROPS.get(speciesId);

        if (supplier == null) {
            return null;
        }

        GrowableBase crop = supplier.get();

        if (crop == null) {
            return null;
        }

        int safeAge = Math.max(
                0,
                Math.min(crop.getMaxAge(), age)
        );

        return crop.getStateForAge(safeAge);
    }

    public static boolean contains(String speciesId) {
        return CROPS.containsKey(speciesId);
    }

    public static void clear() {
        CROPS.clear();
    }
}