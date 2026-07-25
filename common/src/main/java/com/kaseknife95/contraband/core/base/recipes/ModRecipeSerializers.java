package com.kaseknife95.contraband.core.base.recipes;


import com.kaseknife95.contraband.core.util.DeferredRegistryObject;
import com.kaseknife95.contraband.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;

public final class ModRecipeSerializers {

    public static final DeferredRegistryObject<RecipeSerializer<BluntCraftingRecipe>>
            BLUNT_CRAFTING =
            Services.PLATFORM.register(
                    BuiltInRegistries.RECIPE_SERIALIZER,
                    "blunt_crafting",
                    () -> new SimpleCraftingRecipeSerializer<>(
                            BluntCraftingRecipe::new
                    )
            );

    private ModRecipeSerializers() {}

    public static void loadClass() {}
}
