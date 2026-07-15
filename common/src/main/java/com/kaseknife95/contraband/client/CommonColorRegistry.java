package com.kaseknife95.contraband.client;

import com.kaseknife95.contraband.block.ModBlocks;
import com.kaseknife95.contraband.core.util.DeferredRegistryObject;
import com.kaseknife95.contraband.item.Items;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.function.Supplier;

public final class CommonColorRegistry {

    private CommonColorRegistry() {}

    public static List<Supplier<Item>> tintableItems() {
        return List.of(
                Items.SHROOM::get,
                Items.CANNABIS_SEED::get,
                Items.CANNABIS_LEAF::get
        );
    }

    public static List<DeferredRegistryObject<Block>> tintableBlocks() {
        return List.of(
                ModBlocks.CANNABIS_CROP::get,
                ModBlocks.CROP_STICKS::get
        );
    }
}