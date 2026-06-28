package com.kaseknife95.contraband.items;

import com.kaseknife95.contraband.Blocks.Blocks;
import com.kaseknife95.contraband.core.util.DeferredRegistryObject;
import com.kaseknife95.contraband.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;

public class Items {

    public static final DeferredRegistryObject<Item> LOGO_ITEM =
            Services.PLATFORM.register(BuiltInRegistries.ITEM, "logo_item",
                    () -> new Item(new Item.Properties()));

    //Growing stuff
    public static final DeferredRegistryObject<Item> COCAINE_LEAF =
            Services.PLATFORM.register(BuiltInRegistries.ITEM, "cocaine_leaf",
                    () -> new Item(new Item.Properties()));
    public static final DeferredRegistryObject<Item> COCAINE_SEED =
            Services.PLATFORM.register(BuiltInRegistries.ITEM, "cocaine_seed",
                    () -> new Item(new Item.Properties()));
    public static final DeferredRegistryObject<Item> DRIED_COCAINE =
            Services.PLATFORM.register(BuiltInRegistries.ITEM, "dried_cocaine",
                    () -> new Item(new Item.Properties()));
    public static final DeferredRegistryObject<Item> DRIED_COCAINE_PACKAGE =
            Services.PLATFORM.register(BuiltInRegistries.ITEM, "dried_cocaine_package",
                    () -> new Item(new Item.Properties()));
    public static final DeferredRegistryObject<Item> DRIED_HEMP =
            Services.PLATFORM.register(BuiltInRegistries.ITEM, "dried_hemp",
                    () -> new Item(new Item.Properties()));
    public static final DeferredRegistryObject<Item> DRIED_HEMP_PACKAGE =
            Services.PLATFORM.register(BuiltInRegistries.ITEM, "dried_hemp_package",
                    () -> new Item(new Item.Properties()));
    public static final DeferredRegistryObject<Item> HEMP =
            Services.PLATFORM.register(BuiltInRegistries.ITEM, "hemp",
                    () -> new Item(new Item.Properties()));
    public static final DeferredRegistryObject<Item> HEMP_PACKAGE =
            Services.PLATFORM.register(BuiltInRegistries.ITEM, "hemp_package",
                    () -> new Item(new Item.Properties()));

    public static final DeferredRegistryObject<Item> DRIED_OPIUM =
            Services.PLATFORM.register(BuiltInRegistries.ITEM, "dried_opium",
                    () -> new Item(new Item.Properties()));
    public static final DeferredRegistryObject<Item> OPIUM_BOTTLE =
            Services.PLATFORM.register(BuiltInRegistries.ITEM, "opium_bottle",
                    () -> new Item(new Item.Properties()));
    //Drugs
    public static final DeferredRegistryObject<Item> SHROOM =
            Services.PLATFORM.register(BuiltInRegistries.ITEM, "shroom",
                    () -> new Shroom(
                            new Item.Properties()
                                    .food(new FoodProperties.Builder()
                                            .nutrition(2)
                                            .saturationModifier(0.2f)
                                            .alwaysEdible()
                                            .build()
                                    ), "Golden Teacher"
                    ));

    public static final DeferredRegistryObject<Item> HEMP_SEED =
            Services.PLATFORM.register(BuiltInRegistries.ITEM, "hemp_seed",
                    () -> new ItemNameBlockItem(
                            Blocks.HEMP_CROP.get(),
                            new Item.Properties()
                    ));
    public static void loadClass() {}
}