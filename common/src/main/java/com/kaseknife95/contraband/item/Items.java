package com.kaseknife95.contraband.item;

import com.kaseknife95.contraband.block.ModBlocks;
import com.kaseknife95.contraband.core.base.drugs.DrugBase;
import com.kaseknife95.contraband.core.base.drugs.DrugData;
import com.kaseknife95.contraband.core.base.drugs.DrugType;
import com.kaseknife95.contraband.core.base.genetics.GeneticsData;
import com.kaseknife95.contraband.core.base.products.RawProductBase;
import com.kaseknife95.contraband.core.base.propagation.PropagationBase;
import com.kaseknife95.contraband.core.base.substances.SubstanceData;
import com.kaseknife95.contraband.core.util.DeferredRegistryObject;
import com.kaseknife95.contraband.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

public class Items {

    public static final DeferredRegistryObject<Item> LOGO_ITEM =
            Services.PLATFORM.register(BuiltInRegistries.ITEM, "logo_item",
                    () -> new Item(new Item.Properties()));

    //crop breeding
    public static final DeferredRegistryObject<Item> CROP_STICKS =
            Services.PLATFORM.register(
                    BuiltInRegistries.ITEM,
                    "crop_sticks",
                    () -> new BlockItem(
                            ModBlocks.CROP_STICKS.get(),
                            new Item.Properties()
                    )
            );

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
    public static final DeferredRegistryObject<Item> DRIED_CANNABIS =
            Services.PLATFORM.register(BuiltInRegistries.ITEM, "dried_cannabis",
                    () -> new Item(new Item.Properties()));
    public static final DeferredRegistryObject<Item> DRIED_CANNABIS_PACKAGE =
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

    public static final DeferredRegistryObject<Item> BLUNT =
            Services.PLATFORM.register(BuiltInRegistries.ITEM, "blunt",
                    () -> new DrugBase(new Item.Properties(), new DrugData(
                            "cannabis",
                            "Joint",
                            DrugType.CANNABINOID,
                            1.0F,
                            1.0F,
                            GeneticsData.defaultGenetics("cannabis", 0x6B3FA0, 0x2E7D32),
                            SubstanceData.defaultVariant("cannabis", 0x6B3FA0, 0x2E7D32)
                    )) {

                    });



    public static final DeferredRegistryObject<Item> CANNABIS_LEAF =
            Services.PLATFORM.register(BuiltInRegistries.ITEM, "cannabis_leaf",
                    () -> new RawProductBase(new Item.Properties(), new DrugData(
                            "cannabis",
                            "Cannabis Leaf",
                            DrugType.CANNABINOID,
                            1.0F,
                            1.0F,
                            GeneticsData.defaultGenetics("cannabis", 0x6B3FA0, 0x2E7D32),
                            SubstanceData.defaultVariant("cannabis", 0x6B3FA0, 0x2E7D32)
                    )));

    public static final DeferredRegistryObject<Item> CANNABIS_BUD =
            Services.PLATFORM.register(BuiltInRegistries.ITEM, "cannabis_bud",
                    () -> new RawProductBase(new Item.Properties(), new DrugData(
                            "cannabis",
                            "Cannabis Bud",
                            DrugType.CANNABINOID,
                            1.0F,
                            1.0F,
                            GeneticsData.defaultGenetics("cannabis", 0x6B3FA0, 0x2E7D32),
                            SubstanceData.defaultVariant("cannabis", 0x6B3FA0, 0x2E7D32)
                    )));



    public static final DeferredRegistryObject<Item> CANNABIS_SEED =
            Services.PLATFORM.register(BuiltInRegistries.ITEM, "cannabis_seed",
                    () -> new PropagationBase(
                            ModBlocks.CANNABIS_CROP.get(),
                            new Item.Properties(),
                            GeneticsData.defaultGenetics("cannabis", 0x6B3FA0, 0x2E7D32)
                    ));
    public static void loadClass() {}
}