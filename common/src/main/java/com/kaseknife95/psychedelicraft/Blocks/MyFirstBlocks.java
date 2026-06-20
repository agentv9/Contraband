package com.kaseknife95.psychedelicraft.Blocks;

import com.kaseknife95.psychedelicraft.core.util.DeferredRegistryObject;
import com.kaseknife95.psychedelicraft.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class MyFirstBlocks {
    public static final DeferredRegistryObject<Block> TEST_BLOCK =
            Services.PLATFORM.register(BuiltInRegistries.BLOCK, "test_block",
                    () -> new Block(BlockBehaviour.Properties.of()
                            .strength(1.5f, 6.0f)
                            .sound(SoundType.STONE)
                    ));

    public static void loadClass() {}
}
