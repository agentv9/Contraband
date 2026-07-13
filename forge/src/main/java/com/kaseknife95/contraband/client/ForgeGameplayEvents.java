package com.kaseknife95.contraband.client;

import com.kaseknife95.contraband.Constants;
import com.kaseknife95.contraband.core.base.cropsticks.CropStickBlock;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
        modid = Constants.MOD_ID,
        bus = Mod.EventBusSubscriber.Bus.FORGE
)
public final class ForgeGameplayEvents {

    private ForgeGameplayEvents() {}

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