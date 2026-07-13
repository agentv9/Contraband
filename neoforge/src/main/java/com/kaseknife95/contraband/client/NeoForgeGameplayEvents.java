package com.kaseknife95.contraband.client;

import com.kaseknife95.contraband.Constants;
import com.kaseknife95.contraband.core.base.cropsticks.CropStickBlock;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;

@EventBusSubscriber(
        modid = Constants.MOD_ID,
        bus = EventBusSubscriber.Bus.GAME
)
public final class NeoForgeGameplayEvents {

    private NeoForgeGameplayEvents() {}

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (!(event.getState().getBlock() instanceof CropStickBlock cropSticks)) {
            return;
        }

        if (!(event.getLevel() instanceof Level level)) {
            return;
        }

        if (cropSticks.harvestPlant(
                level,
                event.getPos(),
                event.getPlayer()
        )) {
            event.setCanceled(true);
        }
    }
}