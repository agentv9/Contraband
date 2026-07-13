package com.kaseknife95.contraband.client.cropsticks;

import com.kaseknife95.contraband.block.ModBlocks;
import com.kaseknife95.contraband.core.base.growables.GrowableBase;

public class CropModels {
    public static void loadClass() {
        CropModelRegistry.register(
                "cannabis",
                () -> (GrowableBase) ModBlocks.CANNABIS_CROP.get()
        );
    }
}
