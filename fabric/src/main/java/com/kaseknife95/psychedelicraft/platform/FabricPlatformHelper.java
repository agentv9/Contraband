package com.kaseknife95.psychedelicraft.platform;

import com.kaseknife95.psychedelicraft.Constants;
import com.kaseknife95.psychedelicraft.core.util.DeferredRegistryObject;
import com.kaseknife95.psychedelicraft.core.util.FabricDeferredRegistryObject;
import com.kaseknife95.psychedelicraft.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public <T, U extends T> DeferredRegistryObject<U> register(
            Registry<T> objRegistry,
            String objName,
            Supplier<U> objSupplier
    ) {
        U object = objSupplier.get();

        U registeredObject = Registry.register(
                objRegistry,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, objName),
                object
        );

        return new FabricDeferredRegistryObject<>(registeredObject);
    }

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }
}
