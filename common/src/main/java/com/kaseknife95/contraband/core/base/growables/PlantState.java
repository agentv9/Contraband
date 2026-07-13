package com.kaseknife95.contraband.core.base.growables;

import com.kaseknife95.contraband.core.base.genetics.GeneticsData;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;

import java.util.Objects;

public class PlantState {

    private static final String GENETICS_KEY = "genetics";
    private static final String HYDRATION_KEY = "hydration";
    private static final String NUTRIENTS_KEY = "nutrients";
    private static final String HEALTH_KEY = "health";
    private static final String CLIMATE_STRESS_KEY = "climate_stress";

    private GeneticsData geneticsData;
    private float hydration;
    private float nutrients;
    private float health;
    private float climateStress;

    public PlantState(GeneticsData geneticsData) {
        this(
                geneticsData,
                1.0F,
                1.0F,
                1.0F,
                0.0F
        );
    }

    public PlantState(
            GeneticsData geneticsData,
            float hydration,
            float nutrients,
            float health,
            float climateStress
    ) {
        this.geneticsData = Objects.requireNonNull(
                geneticsData,
                "geneticsData cannot be null"
        );

        this.hydration = clamp01(hydration);
        this.nutrients = clamp01(nutrients);
        this.health = clamp01(health);
        this.climateStress = clamp01(climateStress);
    }

    public GeneticsData getGeneticsData() {
        return geneticsData;
    }

    public void setGeneticsData(GeneticsData geneticsData) {
        this.geneticsData = Objects.requireNonNull(
                geneticsData,
                "geneticsData cannot be null"
        );
    }

    public float getHydration() {
        return hydration;
    }

    public void setHydration(float hydration) {
        this.hydration = clamp01(hydration);
    }

    public float getNutrients() {
        return nutrients;
    }

    public void setNutrients(float nutrients) {
        this.nutrients = clamp01(nutrients);
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = clamp01(health);
    }

    public float getClimateStress() {
        return climateStress;
    }

    public void setClimateStress(float climateStress) {
        this.climateStress = clamp01(climateStress);
    }

    public float getGrowthConditionModifier() {
        return clamp01(
                hydration
                        * nutrients
                        * health
                        * (1.0F - climateStress)
        );
    }

    public PlantState copy() {
        return new PlantState(
                geneticsData,
                hydration,
                nutrients,
                health,
                climateStress
        );
    }

    /*
     * Converts this PlantState into a tag that can be stored
     * inside a BlockEntity's save data.
     */
    public CompoundTag save(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();

        GeneticsData.CODEC
                .encodeStart(
                        registries.createSerializationContext(NbtOps.INSTANCE),
                        geneticsData
                )
                .resultOrPartial(error ->
                        System.err.println(
                                "Failed to save plant genetics: " + error
                        )
                )
                .ifPresent(encoded ->
                        tag.put(GENETICS_KEY, encoded)
                );

        tag.putFloat(HYDRATION_KEY, hydration);
        tag.putFloat(NUTRIENTS_KEY, nutrients);
        tag.putFloat(HEALTH_KEY, health);
        tag.putFloat(CLIMATE_STRESS_KEY, climateStress);

        return tag;
    }

    /*
     * Reconstructs a PlantState from the BlockEntity's saved tag.
     */
    public static PlantState load(
            CompoundTag tag,
            HolderLookup.Provider registries
    ) {
        Tag geneticsTag = tag.get(GENETICS_KEY);

        if (geneticsTag == null) {
            return null;
        }

        GeneticsData genetics = GeneticsData.CODEC
                .parse(
                        registries.createSerializationContext(NbtOps.INSTANCE),
                        geneticsTag
                )
                .resultOrPartial(error ->
                        System.err.println(
                                "Failed to load plant genetics: " + error
                        )
                )
                .orElse(null);

        if (genetics == null) {
            return null;
        }

        float hydration = tag.contains(HYDRATION_KEY)
                ? tag.getFloat(HYDRATION_KEY)
                : 1.0F;

        float nutrients = tag.contains(NUTRIENTS_KEY)
                ? tag.getFloat(NUTRIENTS_KEY)
                : 1.0F;

        float health = tag.contains(HEALTH_KEY)
                ? tag.getFloat(HEALTH_KEY)
                : 1.0F;

        float climateStress = tag.contains(CLIMATE_STRESS_KEY)
                ? tag.getFloat(CLIMATE_STRESS_KEY)
                : 0.0F;

        return new PlantState(
                genetics,
                hydration,
                nutrients,
                health,
                climateStress
        );
    }

    private static float clamp01(float value) {
        return Math.max(0.0F, Math.min(1.0F, value));
    }
}