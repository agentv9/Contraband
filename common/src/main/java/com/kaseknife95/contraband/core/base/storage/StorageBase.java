package com.kaseknife95.contraband.core.base.storage;

import com.kaseknife95.contraband.core.base.drugs.DrugData;
import net.minecraft.world.item.Item;

public class StorageBase extends Item {

    private DrugData drugData;

    public StorageBase(Properties pProperties, DrugData drugData) {
        super(pProperties);

        this.drugData = drugData;
    }
}
