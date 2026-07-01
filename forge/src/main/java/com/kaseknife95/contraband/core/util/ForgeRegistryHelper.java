package com.kaseknife95.contraband.core.util;

import com.kaseknife95.contraband.Constants;
import net.minecraft.core.Registry;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ForgeRegistryHelper {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Constants.MOD_ID);

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Constants.MOD_ID);

    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(Registries.MOB_EFFECT, Constants.MOD_ID);

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Constants.MOD_ID);

    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, Constants.MOD_ID);
    @SuppressWarnings("unchecked")
    public static <T> DeferredRegister<T> deferredRegisterFor(Registry<T> objRegistry) {

        if (objRegistry.key().location().equals(ForgeRegistries.Keys.ITEMS.location())) {
            return (DeferredRegister<T>) ITEMS;
        }

        if (objRegistry.key().location().equals(ForgeRegistries.Keys.BLOCKS.location())) {
            return (DeferredRegister<T>) BLOCKS;
        }

        if (objRegistry.key().location().equals(Registries.CREATIVE_MODE_TAB.location())) {
            return (DeferredRegister<T>) CREATIVE_TABS;
        }
        if (objRegistry.key().location().equals(Registries.MOB_EFFECT.location())) {
            return (DeferredRegister<T>) MOB_EFFECTS;
        }

        if (objRegistry.key().location().equals(ForgeRegistries.Keys.BLOCK_ENTITY_TYPES.location())) {
            return (DeferredRegister<T>) BLOCK_ENTITIES;
        }

        if (objRegistry.key().location().equals(Registries.DATA_COMPONENT_TYPE.location())) {
            return (DeferredRegister<T>) DATA_COMPONENTS;
        }
        throw new IllegalArgumentException(
                "No registry linked in Forge module to register type: " + objRegistry.key()
        );
    }
}
