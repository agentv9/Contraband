package com.kaseknife95.contraband.Blocks;

import com.kaseknife95.contraband.core.base.growables.GrowableBase;
import com.kaseknife95.contraband.core.util.DeferredRegistryObject;
import com.kaseknife95.contraband.items.Items;
import com.kaseknife95.contraband.items.Shroom;
import com.kaseknife95.contraband.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.food.FoodProperties;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class Blocks {

    public static final DeferredRegistryObject<Block> HEMP_CROP =
            Services.PLATFORM.register(BuiltInRegistries.BLOCK, "hemp_crop",
                    () -> new GrowableBase(
                            BlockBehaviour.Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.WHEAT),
                            7,
                            () -> Items.HEMP_SEED.get()
                    ));

    public static void loadClass() {}
}
