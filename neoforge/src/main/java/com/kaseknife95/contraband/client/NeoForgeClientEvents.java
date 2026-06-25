package com.kaseknife95.contraband.client;

import com.kaseknife95.contraband.Constants;
import com.kaseknife95.contraband.core.base.drugs.DrugBase;
import com.kaseknife95.contraband.items.MyFirstItems;
import net.minecraft.world.item.ItemStack;
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
        event.register(
                (ItemStack stack, int tintIndex) -> {
                    if (stack.getItem() instanceof DrugBase drugItem) {
                        return drugItem.getTintColor(stack, tintIndex);
                    }

                    return 0xFFFFFF;
                },
                MyFirstItems.SHROOM.get()
        );
    }
}
