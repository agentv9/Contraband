package com.kaseknife95.contraband.core.base.growables;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.function.Supplier;

public class GrowableBase extends CropBlock {


    private final int maxAge;
    private final Supplier<? extends ItemLike> seedItem;
    public static final IntegerProperty CROP_AGE = BlockStateProperties.AGE_7;

    public GrowableBase(
            BlockBehaviour.Properties properties,
            int maxAge,
            Supplier<? extends ItemLike> seedItem
    ) {
        super(properties);
        this.maxAge = maxAge;
        this.seedItem = seedItem;
    }

    @Override
    protected IntegerProperty getAgeProperty() {
        return CROP_AGE;
    }

    @Override
    public int getMaxAge() {
        return this.maxAge;
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return this.seedItem.get();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CROP_AGE);
    }
}