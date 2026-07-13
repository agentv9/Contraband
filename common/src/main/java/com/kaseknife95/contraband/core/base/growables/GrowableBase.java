package com.kaseknife95.contraband.core.base.growables;

import com.kaseknife95.contraband.core.base.drugs.DrugBase;
import com.kaseknife95.contraband.core.base.drugs.DrugData;
import com.kaseknife95.contraband.core.base.genetics.GeneticsData;
import com.kaseknife95.contraband.core.base.propagation.PropagationBase;
import com.kaseknife95.contraband.core.component.ModDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
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
import java.util.Objects;
import java.util.function.Supplier;

public class GrowableBase extends CropBlock implements EntityBlock {

    public static final IntegerProperty CROP_AGE =
            BlockStateProperties.AGE_7;

    private final GrowableData growableData;
    private final Supplier<? extends ItemLike> seedItem;

    public GrowableBase(
            BlockBehaviour.Properties properties,
            GrowableData growableData,
            Supplier<? extends ItemLike> seedItem
    ) {
        super(properties);

        this.growableData = Objects.requireNonNull(
                growableData,
                "growableData cannot be null"
        );

        this.seedItem = Objects.requireNonNull(
                seedItem,
                "seedItem cannot be null"
        );

        this.registerDefaultState(
                this.stateDefinition.any().setValue(CROP_AGE, 0)
        );
    }

    public GrowableData growableData() {
        return this.growableData;
    }

    @Override
    protected IntegerProperty getAgeProperty() {
        return CROP_AGE;
    }

    @Override
    public int getMaxAge() {
        return this.growableData.maxAge();
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return this.seedItem.get();
    }

    @Override
    protected void createBlockStateDefinition(
            StateDefinition.Builder<Block, BlockState> builder
    ) {
        builder.add(CROP_AGE);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new GrowableBE(pos, state);
    }

    public static int getTintColor(
            BlockState state,
            BlockAndTintGetter level,
            BlockPos pos,
            int tintIndex
    ) {
        if (level == null || pos == null) {
            return 0xFFFFFF;
        }

        BlockEntity blockEntity = level.getBlockEntity(pos);

        if (!(blockEntity instanceof GrowableBE growableBE)) {
            return 0xFFFFFF;
        }

        GeneticsData genetics = growableBE.getGeneticsData();

        if (genetics == null) {
            return 0xFFFFFF;
        }

        return switch (tintIndex) {
            case 1 -> genetics.primaryColor();
            case 2 -> genetics.secondaryColor();
            default -> 0xFFFFFF;
        };
    }

    @Override
    public List<ItemStack> getDrops(
            BlockState state,
            LootParams.Builder params
    ) {
        List<ItemStack> drops = super.getDrops(state, params);

        BlockEntity blockEntity =
                params.getOptionalParameter(
                        LootContextParams.BLOCK_ENTITY
                );

        if (!(blockEntity instanceof GrowableBE growableBE)) {
            return drops;
        }

        GeneticsData parentGenetics =
                growableBE.getGeneticsData();

        if (parentGenetics == null) {
            return drops;
        }

        for (ItemStack stack : drops) {
            applyGeneticsToDrop(stack, parentGenetics);
        }

        return drops;
    }

    private void applyGeneticsToDrop(
            ItemStack stack,
            GeneticsData parentGenetics
    ) {
        if (stack.getItem() instanceof PropagationBase propagation) {
            propagation.setGeneticsOnSeed(
                    stack,
                    parentGenetics
            );

            stack.setCount(2);
            return;
        }

        if (stack.getItem() instanceof DrugBase drugItem) {
            DrugData baseData = drugItem.baseDrugData();

            DrugData harvestedData = new DrugData(
                    baseData.drugId(),
                    baseData.displayName(),
                    baseData.drugType(),
                    baseData.basePotency(),
                    baseData.baseQuality(),
                    parentGenetics,
                    baseData.substanceData()
            );

            stack.set(
                    ModDataComponents.DRUG_DATA.get(),
                    harvestedData
            );
        }
    }
}