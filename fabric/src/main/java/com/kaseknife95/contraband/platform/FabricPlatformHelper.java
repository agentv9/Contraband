package com.kaseknife95.contraband.platform;

import com.kaseknife95.contraband.Constants;
import com.kaseknife95.contraband.core.util.DeferredRegistryObject;
import com.kaseknife95.contraband.core.util.FabricDeferredRegistryObject;
import com.kaseknife95.contraband.platform.services.IPlatformHelper;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.BiFunction;
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
    public <T extends BlockEntity> BlockEntityType<T> createBlockEntityType(
            BiFunction<BlockPos, BlockState, T> factory,
            Block... validBlocks
    ) {
        return FabricBlockEntityTypeBuilder
                .create(factory::apply, validBlocks)
                .build();
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
