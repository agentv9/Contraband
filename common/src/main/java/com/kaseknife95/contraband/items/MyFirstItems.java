package com.kaseknife95.contraband.items;

import com.kaseknife95.contraband.core.util.DeferredRegistryObject;
import com.kaseknife95.contraband.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class MyFirstItems {

    public static final DeferredRegistryObject<Item> LOGO_ITEM =
            Services.PLATFORM.register(BuiltInRegistries.ITEM, "logo_item",
                    () -> new Item(new Item.Properties()));

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
    public static final DeferredRegistryObject<Item> SHROOM2 =
            Services.PLATFORM.register(BuiltInRegistries.ITEM, "shroom2",
                    () -> new Shroom(
                            new Item.Properties()
                                    .food(new FoodProperties.Builder()
                                            .nutrition(2)
                                            .saturationModifier(0.2f)
                                            .alwaysEdible()
                                            .build()
                                    ), "Blue Meanie"
                    ));
    public static final DeferredRegistryObject<Item> SHROOM3 =
            Services.PLATFORM.register(BuiltInRegistries.ITEM, "shroom3",
                    () -> new Shroom(
                            new Item.Properties()
                                    .food(new FoodProperties.Builder()
                                            .nutrition(2)
                                            .saturationModifier(0.2f)
                                            .alwaysEdible()
                                            .build()
                                    ), "Spore Crack"
                    ));
    public static final DeferredRegistryObject<Item> SHROOM4 =
            Services.PLATFORM.register(BuiltInRegistries.ITEM, "shroom4",
                    () -> new Shroom(
                            new Item.Properties()
                                    .food(new FoodProperties.Builder()
                                            .nutrition(2)
                                            .saturationModifier(0.2f)
                                            .alwaysEdible()
                                            .build()
                                    ), "Albino A+"
                    ));
    public static void loadClass() {}
}