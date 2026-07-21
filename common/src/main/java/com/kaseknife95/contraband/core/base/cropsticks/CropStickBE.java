package com.kaseknife95.contraband.core.base.cropsticks;

import com.kaseknife95.contraband.block.ModBlockEntities;
import com.kaseknife95.contraband.core.base.breeding.GrowableBreeder;
import com.kaseknife95.contraband.core.base.drugs.DrugBase;
import com.kaseknife95.contraband.core.base.drugs.DrugData;
import com.kaseknife95.contraband.core.base.genetics.GeneticsData;
import com.kaseknife95.contraband.core.base.growables.GrowableBE;
import com.kaseknife95.contraband.core.base.growables.GrowableBase;
import com.kaseknife95.contraband.core.base.growables.GrowableDefinition;
import com.kaseknife95.contraband.core.base.growables.PlantState;
import com.kaseknife95.contraband.core.base.propagation.PropagationBase;
import com.kaseknife95.contraband.core.component.ModDataComponents;
import com.kaseknife95.contraband.core.data.Growables;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class CropStickBE extends BlockEntity {

    private static final String MODE_KEY = "mode";
    private static final String PLANT_STATE_KEY = "plant_state";
    private static final String AGE_KEY = "age";
    private static final String BREEDING_PROGRESS_KEY = "breeding_progress";
    private static final String TICK_COUNTER_KEY = "tick_counter";

    public static final int MAX_AGE = 7;

    /*
     * Five seconds with two valid parents.
     */
    private static final int REQUIRED_BREEDING_PROGRESS = 100;

    /*
     * Attempt growth every five seconds.
     */
    private static final int GROWTH_INTERVAL = 100;

    private CropStickMode mode = CropStickMode.CROSSBREEDING;
    private PlantState plantState;

    private int age;
    private int breedingProgress;
    private int tickCounter;

    public CropStickBE(
            BlockPos pos,
            BlockState state
    ) {
        super(ModBlockEntities.CROP_STICK.get(), pos, state);
    }

    public CropStickMode getMode() {
        return this.mode;
    }

    public PlantState getPlantState() {
        return this.plantState;
    }

    public GeneticsData getGeneticsData() {
        return this.plantState != null
                ? this.plantState.getGeneticsData()
                : null;
    }

    public int getAge() {
        return this.age;
    }

    public int getBreedingProgress() {
        return this.breedingProgress;
    }

    public boolean hasPlant() {
        return this.plantState != null;
    }

    public boolean isMature() {
        return hasPlant() && this.age >= MAX_AGE;
    }

    public boolean canAcceptPlant() {
        return !hasPlant();
    }

    public void setPlant(
            GeneticsData genetics,
            int age
    ) {
        if (genetics == null) {
            throw new IllegalArgumentException(
                    "genetics cannot be null"
            );
        }

        this.plantState = new PlantState(genetics);
        this.age = clampAge(age);
        this.mode = CropStickMode.SINGLE_CROP;
        this.breedingProgress = 0;

        setChangedAndSync();
    }

    public void clearPlant() {
        this.plantState = null;
        this.age = 0;
        this.breedingProgress = 0;
        this.mode = CropStickMode.EMPTY;

        setChangedAndSync();
    }

    public void enableCrossbreeding() {
        if (hasPlant()) {
            return;
        }

        this.mode = CropStickMode.CROSSBREEDING;
        this.breedingProgress = 0;

        setChangedAndSync();
    }

    public void harvestPlant(Player player) {
        if (!hasPlant()) {
            return;
        }

        if (!isMature()) {
            return;
        }

        Level level = getLevel();

        if (level == null || level.isClientSide()) {
            return;
        }

        GeneticsData genetics = getGeneticsData();

        if (genetics == null) {
            clearPlant();
            return;
        }

        GrowableDefinition definition =
                Growables.require(genetics.speciesId());

        dropHybridSeeds(level, definition, genetics);
        dropRawProduct(level, definition, genetics);

        clearPlant();
    }

    private void dropHybridSeeds(
            Level level,
            GrowableDefinition definition,
            GeneticsData genetics
    ) {
        PropagationBase propagation =
                definition.propagationItem().get();

        ItemStack seedStack = new ItemStack(propagation, 2);

        propagation.setGeneticsOnSeed(
                seedStack,
                genetics
        );

        Block.popResource(
                level,
                this.worldPosition,
                seedStack
        );
    }

    private void dropRawProduct(
            Level level,
            GrowableDefinition definition,
            GeneticsData genetics
    ) {
        DrugBase rawProduct =
                definition.rawProduct().get();

        DrugBase rawProduct2 = definition.rawProduct2().get();

        ItemStack productStack =
                new ItemStack(rawProduct);

        ItemStack productStack2 =
                new ItemStack(rawProduct2);

        DrugData baseData =
                rawProduct.baseDrugData();

        DrugData harvestedData =
                new DrugData(
                        baseData.drugId(),
                        baseData.displayName(),
                        baseData.drugType(),
                        baseData.basePotency(),
                        baseData.baseQuality(),
                        genetics,
                        baseData.substanceData()
                );

        productStack.set(
                ModDataComponents.DRUG_DATA.get(),
                harvestedData
        );

        Block.popResource(
                level,
                this.worldPosition,
                productStack
        );

        productStack2.set(
                ModDataComponents.DRUG_DATA.get(),
                harvestedData
        );

        Block.popResource(
                level,
                this.worldPosition,
                productStack2
        );
    }

    public static void serverTick(
            ServerLevel level,
            BlockPos pos,
            BlockState state,
            CropStickBE blockEntity
    ) {
        blockEntity.tickCounter++;

        switch (blockEntity.mode) {
            case EMPTY -> {
                // Intentionally inactive.
            }

            case SINGLE_CROP ->
                    blockEntity.tickPlantGrowth(level);

            case CROSSBREEDING ->
                    blockEntity.tickBreeding(level);
        }
    }

    private void tickPlantGrowth(ServerLevel level) {
        if (!hasPlant() || this.age >= MAX_AGE) {
            return;
        }

        if (this.tickCounter % GROWTH_INTERVAL != 0) {
            return;
        }

        float conditionModifier =
                this.plantState.getGrowthConditionModifier();

        float growthChance = 0.55F * conditionModifier;

        if (level.random.nextFloat() <= growthChance) {
            this.age++;
            setChangedAndSync();
        }
    }

    private void tickBreeding(ServerLevel level) {
        if (hasPlant()) {
            return;
        }

        List<GeneticsData> parents = findValidParents(level);

        if (parents.size() < 2) {
            if (this.breedingProgress != 0) {
                this.breedingProgress = 0;
                setChangedAndSync();
            }

            return;
        }

        GeneticsData first = parents.get(0);
        GeneticsData second = parents.get(1);

        if (!GrowableBreeder.canBreed(first, second)) {
            this.breedingProgress = 0;
            return;
        }

        this.breedingProgress++;

        if (this.breedingProgress < REQUIRED_BREEDING_PROGRESS) {
            if (this.breedingProgress % 20 == 0) {
                setChangedAndSync();
            }

            return;
        }

        GeneticsData child = GrowableBreeder.cross(
                first,
                second,
                level.random
        );

        setPlant(child, 0);
    }

    private List<GeneticsData> findValidParents(ServerLevel level) {
        List<GeneticsData> parents = new ArrayList<>(2);

        /*
         * Cardinal neighbors first.
         */
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            addParentAt(
                    level,
                    this.worldPosition.relative(direction),
                    parents
            );

            if (parents.size() >= 2) {
                return parents;
            }
        }

        /*
         * Diagonal neighbors allow a traditional crop-stick arrangement.
         */
        int[] offsets = {-1, 1};

        for (int x : offsets) {
            for (int z : offsets) {
                addParentAt(
                        level,
                        this.worldPosition.offset(x, 0, z),
                        parents
                );

                if (parents.size() >= 2) {
                    return parents;
                }
            }
        }

        return parents;
    }

    private void addParentAt(
            ServerLevel level,
            BlockPos parentPos,
            List<GeneticsData> parents
    ) {
        BlockState parentState = level.getBlockState(parentPos);

        if (!(parentState.getBlock() instanceof GrowableBase growable)) {
            return;
        }

        if (growable.getAge(parentState) < growable.getMaxAge()) {
            return;
        }

        if (!(level.getBlockEntity(parentPos) instanceof GrowableBE growableBE)) {
            return;
        }

        GeneticsData genetics = growableBE.getGeneticsData();

        if (genetics == null) {
            return;
        }

        if (!parents.isEmpty()
                && !parents.getFirst().speciesId().equals(genetics.speciesId())) {
            return;
        }

        parents.add(genetics);
    }

    private void setChangedAndSync() {
        setChanged();

        Level level = getLevel();

        if (level == null) {
            return;
        }

        BlockState state = getBlockState();

        level.sendBlockUpdated(
                this.worldPosition,
                state,
                state,
                Block.UPDATE_ALL_IMMEDIATE
        );
    }


    @Override
    protected void saveAdditional(
            CompoundTag tag,
            HolderLookup.Provider registries
    ) {
        super.saveAdditional(tag, registries);

        tag.putString("mode", mode.name());
        tag.putInt("age", age);
        tag.putInt("breeding_progress", breedingProgress);
        tag.putInt(TICK_COUNTER_KEY, this.tickCounter);

        if (plantState != null) {
            tag.put(
                    PLANT_STATE_KEY,
                    plantState.save(registries)
            );
        }
    }

    @Override
    protected void loadAdditional(
            CompoundTag tag,
            HolderLookup.Provider registries
    ) {
        super.loadAdditional(tag, registries);

        mode = CropStickMode.byName(
                tag.getString("mode")
        );

        age = tag.getInt("age");
        breedingProgress = tag.getInt("breeding_progress");
        this.tickCounter = tag.getInt(TICK_COUNTER_KEY);
        if (tag.contains(PLANT_STATE_KEY, Tag.TAG_COMPOUND)) {
            plantState = PlantState.load(
                    tag.getCompound(PLANT_STATE_KEY),
                    registries
            );
        } else {
            plantState = null;
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

    private static int clampAge(int age) {
        return Math.max(0, Math.min(MAX_AGE, age));
    }
}