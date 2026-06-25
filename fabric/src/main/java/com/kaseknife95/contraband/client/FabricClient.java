package com.kaseknife95.contraband.client;

import com.kaseknife95.contraband.core.base.drugs.DrugBase;
import com.kaseknife95.contraband.items.MyFirstItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.level.ItemLike;

public class FabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ColorProviderRegistry.ITEM.register(
                (ItemColor) (stack, tintIndex) -> {
                    if (stack.getItem() instanceof DrugBase drugItem) {
                        return drugItem.getTintColor(stack, tintIndex);
                    }

                    return 0xFFFFFF;
                },
                MyFirstItems.SHROOM.get()
        );
    }
}
