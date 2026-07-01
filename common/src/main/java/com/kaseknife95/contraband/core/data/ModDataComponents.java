package com.kaseknife95.contraband.core.data;

import com.kaseknife95.contraband.core.base.genetics.GeneticsData;
import com.kaseknife95.contraband.core.util.DeferredRegistryObject;
import com.kaseknife95.contraband.platform.Services;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;

public class ModDataComponents {

    public static final DeferredRegistryObject<DataComponentType<GeneticsData>> GENETICS =
            Services.PLATFORM.register(
                    BuiltInRegistries.DATA_COMPONENT_TYPE,
                    "genetics",
                    () -> DataComponentType.<GeneticsData>builder()
                            .persistent(GeneticsData.CODEC)
                            .build()
            );

    public static void loadClass() {}
}