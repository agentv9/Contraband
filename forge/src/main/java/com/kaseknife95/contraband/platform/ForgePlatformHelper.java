package com.kaseknife95.contraband.platform;

import com.kaseknife95.contraband.core.util.DeferredRegistryObject;
import com.kaseknife95.contraband.core.util.ForgeDeferredRegistryObject;
import com.kaseknife95.contraband.core.util.ForgeRegistryHelper;
import com.kaseknife95.contraband.platform.services.IPlatformHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.registries.DeferredRegister;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public class ForgePlatformHelper implements IPlatformHelper {

    public <T, U extends T> DeferredRegistryObject<U> register(Registry<T> objRegistry, String objName, Supplier<U> objSupplier) {
        DeferredRegister<T> registry = ForgeRegistryHelper.deferredRegisterFor(objRegistry);
        return new ForgeDeferredRegistryObject<>(registry.register(objName, objSupplier));
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
        return "Forge";
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
