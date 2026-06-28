package com.kaseknife95.contraband.client;

import com.kaseknife95.contraband.core.base.drugs.DrugBase;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;

public class FabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        CommonColorRegistry.tintableItems().forEach(item ->
                ColorProviderRegistry.ITEM.register(
                        (stack, tintIndex) -> {
                            if (stack.getItem() instanceof DrugBase drugItem) {
                                return drugItem.getTintColor(stack, tintIndex);
                            }

                            return 0xFFFFFFFF;
                        },
                        item.get()
                )
        );
    }
}
