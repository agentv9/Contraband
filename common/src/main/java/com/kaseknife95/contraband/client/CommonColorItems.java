package com.kaseknife95.contraband.client;

import com.kaseknife95.contraband.items.MyFirstItems;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.function.Supplier;

public final class CommonColorItems {

    private CommonColorItems() {}

    public static List<Supplier<Item>> tintableItems() {
        return List.of(
                MyFirstItems.SHROOM::get,
                MyFirstItems.SHROOM2::get,
                MyFirstItems.SHROOM3::get,
                MyFirstItems.SHROOM4::get
        );
    }
}