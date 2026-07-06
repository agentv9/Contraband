package com.kaseknife95.contraband.client;

import com.kaseknife95.contraband.Constants;
import com.kaseknife95.contraband.client.ClientRenderRefresh;
import com.kaseknife95.contraband.core.base.drugs.DrugBase;
import com.kaseknife95.contraband.core.base.growables.GrowableBase;
import com.kaseknife95.contraband.core.base.propagation.PropagationBase;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

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
                )
        );
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
