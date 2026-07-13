package com.kaseknife95.contraband.core.base.growables;

import com.kaseknife95.contraband.block.ModBlockEntities;
import com.kaseknife95.contraband.core.base.genetics.GeneticsData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class GrowableBE extends BlockEntity {

    private static final String PLANT_STATE_KEY = "plant_state";

    private PlantState plantState;

    public GrowableBE(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GROWABLE.get(), pos, state);
    }

    public PlantState getPlantState() {
        return this.plantState;
    }

    public void setPlantState(PlantState plantState) {
        this.plantState = plantState;
        setChangedAndSync();
    }

    public GeneticsData getGeneticsData() {
        return this.plantState != null
                ? this.plantState.getGeneticsData()
                : null;
    }

    public void setGeneticsData(GeneticsData geneticsData) {
        if (geneticsData == null) {
            this.plantState = null;
        } else if (this.plantState == null) {
            this.plantState = new PlantState(geneticsData);
        } else {
            this.plantState.setGeneticsData(geneticsData);
        }

        setChangedAndSync();
    }

    public float getHydration() {
        return this.plantState != null
                ? this.plantState.getHydration()
                : 0.0F;
    }

    public void setHydration(float hydration) {
        if (this.plantState == null) {
            return;
        }

        this.plantState.setHydration(hydration);
        setChangedAndSync();
    }

    public float getNutrients() {
        return this.plantState != null
                ? this.plantState.getNutrients()
                : 0.0F;
    }

    public void setNutrients(float nutrients) {
        if (this.plantState == null) {
            return;
        }

        this.plantState.setNutrients(nutrients);
        setChangedAndSync();
    }

    public float getHealth() {
        return this.plantState != null
                ? this.plantState.getHealth()
                : 0.0F;
    }

    public void setHealth(float health) {
        if (this.plantState == null) {
            return;
        }

        this.plantState.setHealth(health);
        setChangedAndSync();
    }

    public float getClimateStress() {
        return this.plantState != null
                ? this.plantState.getClimateStress()
                : 0.0F;
    }

    public void setClimateStress(float climateStress) {
        if (this.plantState == null) {
            return;
        }

        this.plantState.setClimateStress(climateStress);
        setChangedAndSync();
    }

    public float getGrowthConditionModifier() {
        return this.plantState != null
                ? this.plantState.getGrowthConditionModifier()
                : 0.0F;
    }

    @Override
    protected void saveAdditional(
            CompoundTag tag,
            HolderLookup.Provider registries
    ) {
        super.saveAdditional(tag, registries);

        if (this.plantState != null) {
            tag.put(
                    PLANT_STATE_KEY,
                    this.plantState.save(registries)
            );
        }
    }

    @Override
    protected void loadAdditional(
            CompoundTag tag,
            HolderLookup.Provider registries
    ) {
        super.loadAdditional(tag, registries);

        if (tag.contains(PLANT_STATE_KEY, Tag.TAG_COMPOUND)) {
            this.plantState = PlantState.load(
                    tag.getCompound(PLANT_STATE_KEY),
                    registries
            );
        } else {
            this.plantState = null;
        }
    }

    @Override
    public CompoundTag getUpdateTag(
            HolderLookup.Provider registries
    ) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    private void setChangedAndSync() {
        setChanged();

        Level level = getLevel();

        if (level == null || level.isClientSide()) {
            return;
        }

        BlockState state = getBlockState();

        level.sendBlockUpdated(
                this.worldPosition,
                state,
                state,
                Block.UPDATE_ALL
        );
    }
}