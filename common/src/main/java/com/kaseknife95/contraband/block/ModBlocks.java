package com.kaseknife95.contraband.block;

import com.kaseknife95.contraband.core.base.cropsticks.CropStickBlock;
import com.kaseknife95.contraband.core.base.growables.GrowableBase;
import com.kaseknife95.contraband.core.data.Growables;
import com.kaseknife95.contraband.core.util.DeferredRegistryObject;
import com.kaseknife95.contraband.item.Items;
import com.kaseknife95.contraband.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ModBlocks {

    public static final DeferredRegistryObject<Block> CANNABIS_CROP =
            Services.PLATFORM.register(
                    BuiltInRegistries.BLOCK,
                    "cannabis_crop",
                    () -> new GrowableBase(
                            BlockBehaviour.Properties.ofFullCopy(
                                    net.minecraft.world.level.block.Blocks.WHEAT
                            ),
                            Growables.CANNABIS_DATA,
                            () -> Items.CANNABIS_SEED.get()
                    )
            );
    public static final DeferredRegistryObject<Block> CROP_STICKS =
            Services.PLATFORM.register(
                    BuiltInRegistries.BLOCK,
                    "crop_sticks",
                    () -> new CropStickBlock(
                            BlockBehaviour.Properties.of()
                                    .noCollission()
                                    .instabreak()
                                    .sound(SoundType.WOOD)
                    )
            );
    public static void loadClass() {}
}
