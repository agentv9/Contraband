package com.kaseknife95.psychedelicraft.items;

import com.kaseknife95.psychedelicraft.core.util.DeferredRegistryObject;
import com.kaseknife95.psychedelicraft.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

import static com.kaseknife95.psychedelicraft.Blocks.MyFirstBlocks.MY_FIRST_BLOCK;

public class MyFirstItems {

    public static final DeferredRegistryObject<Item> ICON_ITEM =
            Services.PLATFORM.register(BuiltInRegistries.ITEM, "icon_item",
                    () -> new Item(new Item.Properties()));

    public static final DeferredRegistryObject<Item> SHROOM =
            Services.PLATFORM.register(BuiltInRegistries.ITEM, "psilocybin_mushroom",
                    () -> new Shroom(
                            new Item.Properties()
                                    .food(new FoodProperties.Builder()
                                            .nutrition(2)
                                            .saturationModifier(0.2f)
                                            .alwaysEdible()
                                            .build()
                                    )
                    ));

    public static final DeferredRegistryObject<Item> MY_FIRST_BLOCK_ITEM =
            Services.PLATFORM.register(BuiltInRegistries.ITEM, "my_first_block",
                    () -> new BlockItem(MY_FIRST_BLOCK.get(), new Item.Properties()));

    public static void loadClass() {}
}