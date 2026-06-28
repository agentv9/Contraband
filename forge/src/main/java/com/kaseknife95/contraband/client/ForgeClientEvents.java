package com.kaseknife95.contraband.client;

import com.kaseknife95.contraband.Constants;
import com.kaseknife95.contraband.core.base.drugs.DrugBase;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
        modid = Constants.MOD_ID,
        bus = Mod.EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT
)
public final class ForgeClientEvents {

    private ForgeClientEvents() {}

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
