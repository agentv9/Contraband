package com.kaseknife95.contraband.tab;

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
                                output.accept(Items.CANNABIS_SEED.get());
                                output.accept(Items.DRIED_OPIUM.get());
                                output.accept(Items.OPIUM_BOTTLE.get());

                            })
                            .build()
            );

    public static void loadClass() {}
}
