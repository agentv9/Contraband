package com.kaseknife95.contraband.platform;

import com.kaseknife95.contraband.core.util.DeferredRegistryObject;
import com.kaseknife95.contraband.core.util.NeoForgeDeferredRegistryObject;
import com.kaseknife95.contraband.core.util.NeoForgeRegistryHelper;
import com.kaseknife95.contraband.platform.services.IPlatformHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.BiFunction;
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
    public <T extends BlockEntity> BlockEntityType<T> createBlockEntityType(
            BiFunction<BlockPos, BlockState, T> factory,
            Block... validBlocks
    ) {
        return BlockEntityType.Builder.of(
                factory::apply,
                validBlocks
        ).build(null);
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
