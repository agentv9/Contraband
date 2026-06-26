package com.kaseknife95.contraband.CreativeTabs;

import com.kaseknife95.contraband.core.util.DeferredRegistryObject;
import com.kaseknife95.contraband.items.MyFirstItems;
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
                            .icon(() -> new ItemStack(MyFirstItems.LOGO_ITEM.get()))
                            .displayItems((parameters, output) -> {

                                output.accept(MyFirstItems.SHROOM.get());
                                output.accept(MyFirstItems.SHROOM2.get());
                                output.accept(MyFirstItems.SHROOM3.get());
                                output.accept(MyFirstItems.SHROOM4.get());

                            })
                            .build()
            );

    public static void loadClass() {}
}
