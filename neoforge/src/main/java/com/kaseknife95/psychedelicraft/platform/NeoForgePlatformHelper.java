package com.kaseknife95.psychedelicraft.platform;

import com.kaseknife95.psychedelicraft.core.util.DeferredRegistryObject;
import com.kaseknife95.psychedelicraft.core.util.NeoForgeDeferredRegistryObject;
import com.kaseknife95.psychedelicraft.core.util.NeoForgeRegistryHelper;
import com.kaseknife95.psychedelicraft.platform.services.IPlatformHelper;
import net.minecraft.core.Registry;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class NeoForgePlatformHelper implements IPlatformHelper {

    @Override
    public <T, U extends T> DeferredRegistryObject<U> register(
            Registry<T> objRegistry,
            String objName,
            Supplier<U> objSupplier
    ) {
        DeferredRegister<T> registry = NeoForgeRegistryHelper.deferredRegisterFor(objRegistry);
        return new NeoForgeDeferredRegistryObject<>(registry.register(objName, objSupplier));
    }

    @Override
    public String getPlatformName() {
        return "NeoForge";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
    }
}
