package com.kaseknife95.contraband.core.base.growables;

import com.kaseknife95.contraband.core.base.genetics.GeneticsData;
import com.kaseknife95.contraband.core.base.propagation.PropagationBase;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.List;
import java.util.function.Supplier;

public class GrowableBase extends CropBlock implements EntityBlock {


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

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new GrowableBE(pos, state);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        List<ItemStack> drops = super.getDrops(state, params);

        BlockEntity blockEntity = params.getOptionalParameter(LootContextParams.BLOCK_ENTITY);

        if (!(blockEntity instanceof GrowableBE growableBE)) {
            return drops;
        }

        GeneticsData parentGenetics = growableBE.getGeneticsData();

        if (parentGenetics == null) {
            return drops;
        }

        ServerLevel level = params.getLevel();

        for (ItemStack stack : drops) {
            if (stack.getItem() instanceof PropagationBase propagation) {
                GeneticsData childGenetics =
                        propagation.mutateGeneticsForTesting(parentGenetics, level.random);

                propagation.setGeneticsOnSeed(stack, childGenetics);
                stack.setCount(2);
            }
        }

        return drops;
    }
}