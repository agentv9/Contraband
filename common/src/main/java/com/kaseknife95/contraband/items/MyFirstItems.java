package com.kaseknife95.contraband.items;

import com.kaseknife95.contraband.core.util.DeferredRegistryObject;
import com.kaseknife95.contraband.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

//import static com.kaseknife95.contraband.Blocks.MyFirstBlocks.TEST_BLOCK;

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
                                    )
                    ));
    /*
    public static final DeferredRegistryObject<Item> TEST_BLOCK_ITEM =
            Services.PLATFORM.register(BuiltInRegistries.ITEM, "test_block",
                    () -> new BlockItem(TEST_BLOCK.get(), new Item.Properties()));
    */
    public static void loadClass() {}
}