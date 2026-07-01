package com.kaseknife95.contraband.core.util;

import com.kaseknife95.contraband.Constants;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NeoForgeRegistryHelper {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(BuiltInRegistries.ITEM, Constants.MOD_ID);

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(BuiltInRegistries.BLOCK, Constants.MOD_ID);

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, Constants.MOD_ID);

    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, Constants.MOD_ID);

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPE =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Constants.MOD_ID);

    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPE  =
            DeferredRegister.create(BuiltInRegistries.DATA_COMPONENT_TYPE, Constants.MOD_ID);

    @SuppressWarnings("unchecked")
    public static <T> DeferredRegister<T> deferredRegisterFor(Registry<T> objRegistry) {
        if (objRegistry.key().location().equals(BuiltInRegistries.ITEM.key().location())) {
            return (DeferredRegister<T>) ITEMS;
        }

        if (objRegistry.key().location().equals(BuiltInRegistries.BLOCK.key().location())) {
            return (DeferredRegister<T>) BLOCKS;
        }

        if (objRegistry.key().location().equals(BuiltInRegistries.CREATIVE_MODE_TAB.key().location())) {
            return (DeferredRegister<T>) CREATIVE_TABS;
        }
        if (objRegistry.key().location().equals(BuiltInRegistries.MOB_EFFECT.key().location())) {
            return (DeferredRegister<T>) MOB_EFFECTS;
        }
        if (objRegistry.key().location().equals(BuiltInRegistries.BLOCK_ENTITY_TYPE.key().location())) {
            return (DeferredRegister<T>) BLOCK_ENTITY_TYPE;
        }
        if (objRegistry.key().location().equals(BuiltInRegistries.DATA_COMPONENT_TYPE.key().location())) {
            return (DeferredRegister<T>) DATA_COMPONENT_TYPE;
        }
        throw new IllegalArgumentException("No NeoForge registry linked for: " + objRegistry.key());
    }
}