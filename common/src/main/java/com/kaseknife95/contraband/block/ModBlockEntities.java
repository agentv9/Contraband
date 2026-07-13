package com.kaseknife95.contraband.block;

import com.kaseknife95.contraband.core.base.cropsticks.CropStickBE;
import com.kaseknife95.contraband.core.base.growables.GrowableBE;
import com.kaseknife95.contraband.core.util.DeferredRegistryObject;
import com.kaseknife95.contraband.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntities {
    public static final DeferredRegistryObject<BlockEntityType<GrowableBE>> GROWABLE =
            Services.PLATFORM.register(
                    BuiltInRegistries.BLOCK_ENTITY_TYPE,
                    "growable",
                    () -> Services.PLATFORM.createBlockEntityType(
                            GrowableBE::new,
                            ModBlocks.CANNABIS_CROP.get()
                    )
            );
    public static final DeferredRegistryObject<BlockEntityType<CropStickBE>>
            CROP_STICK =
            Services.PLATFORM.register(
                    BuiltInRegistries.BLOCK_ENTITY_TYPE,
                    "crop_stick",
                    () -> Services.PLATFORM.createBlockEntityType(
                            CropStickBE::new,
                            ModBlocks.CROP_STICKS.get()
                    )
            );
    public static void loadClass() {}
}
