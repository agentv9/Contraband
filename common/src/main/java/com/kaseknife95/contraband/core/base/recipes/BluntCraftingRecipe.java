package com.kaseknife95.contraband.core.base.recipes;

import com.kaseknife95.contraband.core.base.drugs.DrugBase;
import com.kaseknife95.contraband.core.base.drugs.DrugData;
import com.kaseknife95.contraband.core.component.ModDataComponents;
import com.kaseknife95.contraband.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class BluntCraftingRecipe extends CustomRecipe {

    public BluntCraftingRecipe(CraftingBookCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        int budCount = 0;
        int paperCount = 0;

        for (int slot = 0; slot < input.size(); slot++) {
            ItemStack stack = input.getItem(slot);

            if (stack.isEmpty()) {
                continue;
            }

            if (stack.is(ModItems.CANNABIS_BUD.get())) {
                budCount++;
            } else if (stack.is(Items.PAPER)) {
                paperCount++;
            } else {
                return false;
            }
        }

        return budCount == 1 && paperCount == 1;
    }

    @Override
    public ItemStack assemble(
            CraftingInput input,
            HolderLookup.Provider registries
    ) {
        ItemStack budStack = ItemStack.EMPTY;

        for (int slot = 0; slot < input.size(); slot++) {
            ItemStack stack = input.getItem(slot);

            if (stack.is(ModItems.CANNABIS_BUD.get())) {
                budStack = stack;
                break;
            }
        }

        if (budStack.isEmpty()) {
            return ItemStack.EMPTY;
        }

        if (!(budStack.getItem() instanceof DrugBase budItem)) {
            return ItemStack.EMPTY;
        }

        ItemStack output = new ItemStack(ModItems.BLUNT.get());

        if (!(output.getItem() instanceof DrugBase bluntItem)) {
            return ItemStack.EMPTY;
        }

        DrugData budData = budItem.drugData(budStack);
        DrugData bluntBase = bluntItem.baseDrugData();

        DrugData outputData = new DrugData(
                bluntBase.drugId(),
                bluntBase.displayName(),
                bluntBase.drugType(),
                budData.basePotency(),
                budData.baseQuality(),
                budData.geneticsData(),
                budData.substanceData()
        );

        output.set(
                ModDataComponents.DRUG_DATA.get(),
                outputData
        );

        return output;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.BLUNT_CRAFTING.get();
    }
}