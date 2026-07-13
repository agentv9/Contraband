package com.kaseknife95.contraband.tab;

import com.kaseknife95.contraband.core.base.genetics.GeneticsData;
import com.kaseknife95.contraband.core.base.propagation.PropagationBase;
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

                                output.accept(Items.SHROOM.get());
                                output.accept(Items.COCAINE_LEAF.get());
                                output.accept(Items.COCAINE_SEED.get());
                                output.accept(Items.DRIED_COCAINE.get());
                                output.accept(Items.DRIED_COCAINE_PACKAGE.get());
                                output.accept(Items.DRIED_HEMP.get());
                                output.accept(Items.DRIED_HEMP_PACKAGE.get());
                                output.accept(Items.CANNABIS_LEAF.get());
                                output.accept(Items.HEMP_PACKAGE.get());

                                output.accept(Items.DRIED_OPIUM.get());
                                output.accept(Items.OPIUM_BOTTLE.get());

                            })
                            .build()
            );

    public static final DeferredRegistryObject<CreativeModeTab> CONTRABAND_SEEDS_TAB =
            Services.PLATFORM.register(BuiltInRegistries.CREATIVE_MODE_TAB, "contraband_seeds",
                    () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
                            .title(Component.translatable("itemGroup.contraband.contraband_seeds"))
                            .icon(() -> new ItemStack(Items.CANNABIS_SEED.get()))
                            .displayItems((parameters, output) -> {

                                PropagationBase cannabisSeed =
                                        (PropagationBase) Items.CANNABIS_SEED.get();

                                for (GeneticsData genetics : CannabisStrains.createAll()) {
                                    ItemStack stack = new ItemStack(Items.CANNABIS_SEED.get());
                                    stack.set(ModDataComponents.GENETICS.get(), genetics);
                                    output.accept(stack);
                                }
                            })
                            .build()
            );

    public static void loadClass() {}
}
