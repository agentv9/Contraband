package com.kaseknife95.contraband.core.base.products;

import com.kaseknife95.contraband.core.base.drugs.DrugBase;
import com.kaseknife95.contraband.core.base.drugs.DrugData;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class RawProductBase extends DrugBase {

    public RawProductBase(Properties properties, DrugData baseDrugData) {
        super(properties, baseDrugData);
    }

    @Override
    public Component getName(ItemStack stack) {
        DrugData genetics = this.drugData(stack);

        return Component.literal(
                genetics.geneticsData().strainName() + " " + genetics.displayName().split("\\s+")[1]
        );
    }
}