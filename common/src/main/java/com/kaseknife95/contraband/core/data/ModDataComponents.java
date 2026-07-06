package com.kaseknife95.contraband.core.data;

import com.kaseknife95.contraband.core.base.drugs.DrugData;
import com.kaseknife95.contraband.core.base.genetics.GeneticsData;
import com.kaseknife95.contraband.core.base.substances.SubstanceData;
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

    public static final DeferredRegistryObject<DataComponentType<DrugData>> DRUG_DATA =
            Services.PLATFORM.register(
                    BuiltInRegistries.DATA_COMPONENT_TYPE,
                    "drug_data",
                    () -> DataComponentType.<DrugData>builder()
                            .persistent(DrugData.CODEC)
                            .build()
            );

    public static final DeferredRegistryObject<DataComponentType<SubstanceData>> SUBSTANCE_DATA =
            Services.PLATFORM.register(
                    BuiltInRegistries.DATA_COMPONENT_TYPE,
                    "substance_data",
                    () -> DataComponentType.<SubstanceData>builder()
                            .persistent(SubstanceData.CODEC)
                            .build()
            );

    public static void loadClass() {}
}