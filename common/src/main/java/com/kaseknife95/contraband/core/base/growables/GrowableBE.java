package com.kaseknife95.contraband.core.base.growables;

import com.kaseknife95.contraband.Blocks.ModBlockEntities;
import com.kaseknife95.contraband.core.base.genetics.GeneticsData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class GrowableBE extends BlockEntity {

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
    }


}
