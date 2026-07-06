package com.kaseknife95.contraband.core.base.growables;

import com.kaseknife95.contraband.Blocks.ModBlockEntities;
import com.kaseknife95.contraband.core.base.genetics.GeneticsData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class GrowableBE extends BlockEntity {

    private static final String GENETICS_KEY = "genetics";

    private GeneticsData geneticsData;

    public static GrowableBE create(BlockPos pos, BlockState state) {
        return new GrowableBE(pos, state);
    }

    public GrowableBE(BlockPos pos, BlockState state) {
        this(ModBlockEntities.GROWABLE.get(), pos, state);
    }

    public GrowableBE(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public GeneticsData getGeneticsData() {
        return geneticsData;
    }

    public void setGeneticsData(GeneticsData geneticsData) {
        this.geneticsData = geneticsData;
        setChanged();

        if (level != null && !level.isClientSide) {
            BlockState state = getBlockState();
            level.sendBlockUpdated(worldPosition, state, state, Block.UPDATE_ALL_IMMEDIATE);
        }
    }



    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        if (geneticsData != null) {
            GeneticsData.CODEC
                    .encodeStart(registries.createSerializationContext(NbtOps.INSTANCE), geneticsData)
                    .result()
                    .ifPresent(encoded -> tag.put(GENETICS_KEY, encoded));
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        if (tag.contains(GENETICS_KEY)) {
            this.geneticsData = GeneticsData.CODEC
                    .parse(registries.createSerializationContext(NbtOps.INSTANCE), tag.get(GENETICS_KEY))
                    .result()
                    .orElse(null);

            if (level != null && level.isClientSide) {
                com.kaseknife95.contraband.client.ClientRenderRefresh.refresh(worldPosition);
            }
        }
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        saveAdditional(tag, registries);
        return tag;
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

}