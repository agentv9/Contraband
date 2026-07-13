package com.kaseknife95.contraband.core.base.cropsticks;

import com.kaseknife95.contraband.block.ModBlockEntities;
import com.kaseknife95.contraband.core.base.propagation.PropagationBase;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class CropStickBlock extends Block implements EntityBlock {

    private static final VoxelShape SHAPE =
            Block.box(2.0D, 0.0D, 2.0D, 14.0D, 15.0D, 14.0D);

    public CropStickBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(
            BlockState state,
            BlockGetter level,
            BlockPos pos,
            CollisionContext context
    ) {
        return SHAPE;
    }

    @Override
    public BlockEntity newBlockEntity(
            BlockPos pos,
            BlockState state
    ) {
        return new CropStickBE(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
            Level level,
            BlockState state,
            BlockEntityType<T> type
    ) {
        if (level.isClientSide()) {
            return null;
        }

        if (type != ModBlockEntities.CROP_STICK.get()) {
            return null;
        }

        return (tickerLevel, tickerPos, tickerState, blockEntity) ->
                CropStickBE.serverTick(
                        (ServerLevel) tickerLevel,
                        tickerPos,
                        tickerState,
                        (CropStickBE) blockEntity
                );
    }

    @Override
    protected ItemInteractionResult useItemOn(
            ItemStack stack,
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            InteractionHand hand,
            BlockHitResult hitResult
    ) {
        if (!(stack.getItem() instanceof PropagationBase propagation)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        if (!(level.getBlockEntity(pos) instanceof CropStickBE cropStickBE)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        if (!cropStickBE.canAcceptPlant()) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        if (!level.isClientSide()) {
            cropStickBE.setPlant(
                    propagation.geneticsData(stack),
                    0
            );

            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }

        return ItemInteractionResult.sidedSuccess(level.isClientSide());
    }

    /**
     * Called by your loader-specific block-break event.
     *
     * @return true when a plant was harvested and block destruction
     * should be cancelled.
     */
    public boolean harvestPlant(
            Level level,
            BlockPos pos,
            Player player
    ) {
        if (!(level.getBlockEntity(pos) instanceof CropStickBE cropStickBE)) {
            return false;
        }

        if (!cropStickBE.hasPlant()) {
            return false;
        }

        if (!level.isClientSide()) {
            cropStickBE.harvestPlant(player);
        }

        return true;
    }
}