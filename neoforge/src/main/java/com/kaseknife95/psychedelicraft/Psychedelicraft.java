package com.kaseknife95.psychedelicraft;


import com.kaseknife95.psychedelicraft.core.util.NeoForgeRegistryHelper;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class Psychedelicraft {

    public Psychedelicraft(IEventBus eventBus) {
        // This method is invoked by the NeoForge mod loader when it is ready
        // to load your mod. You can access NeoForge and Common code in this
        // project.

        // Use NeoForge to bootstrap the Common mod.
        NeoForgeRegistryHelper.MOB_EFFECTS.register(eventBus);
        NeoForgeRegistryHelper.ITEMS.register(eventBus);
        NeoForgeRegistryHelper.BLOCKS.register(eventBus);
        NeoForgeRegistryHelper.CREATIVE_TABS.register(eventBus);

        Constants.LOG.info("Hello NeoForge world!");
        CommonClass.init();
    }
}
