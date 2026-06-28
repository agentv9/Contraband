package com.kaseknife95.contraband.client;

import com.kaseknife95.contraband.Constants;
import com.kaseknife95.contraband.core.base.drugs.DrugBase;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@EventBusSubscriber(
        modid = Constants.MOD_ID,
        bus = EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT
)
public final class NeoForgeClientEvents {

    private NeoForgeClientEvents() {}

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        CommonColorRegistry.tintableItems().forEach(item ->
                event.register(
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
