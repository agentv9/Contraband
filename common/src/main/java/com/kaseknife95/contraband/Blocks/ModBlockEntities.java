package com.kaseknife95.contraband.Blocks;

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
                            ModBlocks.HEMP_CROP.get()
                    )
            );
    public static void loadClass() {}
}
