package com.kaseknife95.contraband.client;

import com.kaseknife95.contraband.items.Items;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.function.Supplier;

public final class CommonColorRegistry {

    private CommonColorRegistry() {}

    public static List<Supplier<Item>> tintableItems() {
        return List.of(
                Items.SHROOM::get

        );
    }
}