package com.kaseknife95.contraband.client;

import com.kaseknife95.contraband.Constants;
import com.kaseknife95.contraband.core.base.drugs.DrugBase;
import com.kaseknife95.contraband.core.base.growables.GrowableBase;
import com.kaseknife95.contraband.core.base.propagation.PropagationBase;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
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

                            if (stack.getItem() instanceof PropagationBase seedItem) {
                                return seedItem.getTintColor(stack, tintIndex);
                            }

                            return 0xFFFFFFFF;
                        },
                        item.get()
                )
        );
    }

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        CommonColorRegistry.tintableBlocks().forEach(block ->
                event.register(
                        GrowableBase::getTintColor,
                        block.get()
                ));
    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() ->
                ClientRenderRefresh.init(pos -> {
                    Minecraft minecraft = Minecraft.getInstance();

                    if (minecraft.levelRenderer != null) {
                        minecraft.levelRenderer.setBlocksDirty(
                                pos.getX(), pos.getY(), pos.getZ(),
                                pos.getX(), pos.getY(), pos.getZ()
                        );
                    }
                })
        );
    }
}
