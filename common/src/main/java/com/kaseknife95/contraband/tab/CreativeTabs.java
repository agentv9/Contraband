package com.kaseknife95.contraband.tab;

import com.kaseknife95.contraband.core.base.drugs.DrugBase;
import com.kaseknife95.contraband.core.base.drugs.DrugData;
import com.kaseknife95.contraband.core.base.genetics.GeneticsData;
import com.kaseknife95.contraband.core.component.ModDataComponents;
import com.kaseknife95.contraband.core.data.CannabisStrains;
import com.kaseknife95.contraband.core.util.DeferredRegistryObject;
import com.kaseknife95.contraband.item.Items;
import com.kaseknife95.contraband.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class CreativeTabs {
    public static final DeferredRegistryObject<CreativeModeTab> CONTRABAND_TAB =
            Services.PLATFORM.register(BuiltInRegistries.CREATIVE_MODE_TAB, "contraband",
                    () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
                            .title(Component.translatable("itemGroup.contraband.contraband"))
                            .icon(() -> new ItemStack(Items.LOGO_ITEM.get()))
                            .displayItems((parameters, output) -> {
                                output.accept(Items.COCAINE_LEAF.get());
                                output.accept(Items.COCAINE_SEED.get());
                                output.accept(Items.DRIED_COCAINE.get());
                                output.accept(Items.DRIED_COCAINE_PACKAGE.get());
                                output.accept(Items.DRIED_CANNABIS.get());
                                output.accept(Items.DRIED_CANNABIS_PACKAGE.get());


                                output.accept(Items.HEMP_PACKAGE.get());
                                output.accept(Items.DRIED_OPIUM.get());
                                output.accept(Items.OPIUM_BOTTLE.get());


                                output.accept(Items.CROP_STICKS.get());
                            })
                            .build()
            );

    public static final DeferredRegistryObject<CreativeModeTab> CONTRABAND_GROWABLES_TAB =
            Services.PLATFORM.register(BuiltInRegistries.CREATIVE_MODE_TAB, "contraband_growables",
                    () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
                            .title(Component.translatable("itemGroup.contraband.contraband_growables"))
                            .icon(() -> new ItemStack(Items.CANNABIS_SEED.get()))
                            .displayItems((parameters, output) -> {

                                for (GeneticsData genetics : CannabisStrains.createAll()) {
                                    ItemStack seedStack =
                                            new ItemStack(Items.CANNABIS_SEED.get());

                                    seedStack.set(
                                            ModDataComponents.GENETICS.get(),
                                            genetics
                                    );

                                    DrugBase budItem =
                                            (DrugBase) Items.CANNABIS_BUD.get();

                                    DrugBase leafItem =
                                            (DrugBase) Items.CANNABIS_LEAF.get();

                                    DrugBase jointItem =
                                            (DrugBase) Items.BLUNT.get();

                                    ItemStack budStack =
                                            createDrugStackFromGenetics(budItem, genetics);

                                    ItemStack leafStack =
                                            createDrugStackFromGenetics(leafItem, genetics);

                                    ItemStack jointStack =
                                            createDrugStackFromGenetics(jointItem, genetics);

                                    // Each strain stays grouped:
                                    // seed → bud → leaf
                                    output.accept(seedStack);
                                    output.accept(budStack);
                                    output.accept(leafStack);
                                    output.accept(jointStack);
                                }

                            })
                            .build()
            );

    public static void loadClass() {}

    private static ItemStack createDrugStackFromGenetics(
            DrugBase item,
            GeneticsData genetics
    ) {
        ItemStack stack = new ItemStack(item);

        DrugData baseData = item.baseDrugData();

        DrugData customData = new DrugData(
                baseData.drugId(),
                baseData.displayName(),
                baseData.drugType(),
                baseData.basePotency(),
                baseData.baseQuality(),
                genetics,
                baseData.substanceData()
        );

        stack.set(
                ModDataComponents.DRUG_DATA.get(),
                customData
        );

        return stack;
    }
}
