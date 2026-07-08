package com.kaseknife95.contraband.items;

import com.kaseknife95.contraband.Blocks.ModBlocks;
import com.kaseknife95.contraband.core.base.drugs.DrugData;
import com.kaseknife95.contraband.core.base.drugs.DrugType;
import com.kaseknife95.contraband.core.base.genetics.GeneticsData;
import com.kaseknife95.contraband.core.base.products.RawProductBase;
import com.kaseknife95.contraband.core.base.propagation.PropagationBase;
import com.kaseknife95.contraband.core.base.substances.SubstanceData;
import com.kaseknife95.contraband.core.util.DeferredRegistryObject;
import com.kaseknife95.contraband.items.drugs.Shroom;
import com.kaseknife95.contraband.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

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
            Services.PLATFORM.register(BuiltInRegistries.ITEM, "dried_cannabis",
                    () -> new Item(new Item.Properties()));
    public static final DeferredRegistryObject<Item> DRIED_HEMP_PACKAGE =
            Services.PLATFORM.register(BuiltInRegistries.ITEM, "dried_cannabis_package",
                    () -> new Item(new Item.Properties()));

    public static final DeferredRegistryObject<Item> HEMP_PACKAGE =
            Services.PLATFORM.register(BuiltInRegistries.ITEM, "cannabis_package",
                    () -> new Item(new Item.Properties()));

    public static final DeferredRegistryObject<Item> DRIED_OPIUM =
            Services.PLATFORM.register(BuiltInRegistries.ITEM, "dried_opium",
                    () -> new Item(new Item.Properties()));
    public static final DeferredRegistryObject<Item> OPIUM_BOTTLE =
            Services.PLATFORM.register(BuiltInRegistries.ITEM, "opium_bottle",
                    () -> new Item(new Item.Properties()));
    //Drugs

    public static final DeferredRegistryObject<Item> HEMP =
            Services.PLATFORM.register(BuiltInRegistries.ITEM, "cannabis_leaf",
                    () -> new RawProductBase(new Item.Properties(), new DrugData(
                            "cannabis",
                            "Cannabis Leaf",
                            DrugType.CANNABINOID,
                            1.0F,
                            1.0F,
                            GeneticsData.defaultGenetics("cannabis", 0x6B3FA0, 0x2E7D32),
                            SubstanceData.defaultVariant("OG Kush", 0x6B3FA0, 0x2E7D32)
                    )));



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

    public static final DeferredRegistryObject<Item> CANNABIS_SEED =
            Services.PLATFORM.register(BuiltInRegistries.ITEM, "cannabis_seed",
                    () -> new PropagationBase(
                            ModBlocks.CANNABIS_CROP.get(),
                            new Item.Properties(),
                            GeneticsData.defaultGenetics("cannabis", 0x6B3FA0, 0x2E7D32)
                    ));
    public static void loadClass() {}
}