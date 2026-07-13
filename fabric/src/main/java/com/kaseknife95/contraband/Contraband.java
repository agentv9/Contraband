package com.kaseknife95.contraband;

import com.kaseknife95.contraband.core.base.cropsticks.CropStickBlock;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;

public class Contraband implements ModInitializer {

    @Override
    public void onInitialize() {
        PlayerBlockBreakEvents.BEFORE.register(
                (level, player, pos, state, blockEntity) -> {
                    if (!(state.getBlock() instanceof CropStickBlock cropSticks)) {
                        return true;
                    }

                    boolean harvested = cropSticks.harvestPlant(
                            level,
                            pos,
                            player
                    );

                    // true allows normal breaking.
                    // false cancels breaking.
                    return !harvested;
                }
        );
        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.

        // Use Fabric to bootstrap the Common mod.
        Constants.LOG.info("Hello Fabric world!");
        CommonClass.init();
    }
}
