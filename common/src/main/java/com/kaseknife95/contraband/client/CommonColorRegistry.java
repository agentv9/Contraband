package com.kaseknife95.contraband.client;

import com.kaseknife95.contraband.block.ModBlocks;
import com.kaseknife95.contraband.core.util.DeferredRegistryObject;
import com.kaseknife95.contraband.item.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.List;

public final class CommonColorRegistry {

    private CommonColorRegistry() {}

    public static List<DeferredRegistryObject<Item>> tintableItems() {
        return List.of(
                ModItems.CANNABIS_SEED::get,
                ModItems.CANNABIS_LEAF::get,
                ModItems.CANNABIS_BUD::get,
                ModItems.BLUNT::get
        );
    }

    public static List<DeferredRegistryObject<Block>> tintableBlocks() {
        return List.of(
                ModBlocks.CANNABIS_CROP::get,
                ModBlocks.CROP_STICKS::get
        );
    }
}