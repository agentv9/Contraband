package com.kaseknife95.contraband.items.drugs;

import com.kaseknife95.contraband.core.base.drugs.DrugBase;
import com.kaseknife95.contraband.core.base.drugs.DrugData;
import com.kaseknife95.contraband.core.base.drugs.DrugType;
import com.kaseknife95.contraband.core.base.genetics.GeneticsData;
import com.kaseknife95.contraband.core.base.substances.SubstanceData;

public class Shroom extends DrugBase {

    public Shroom(Properties properties, String variantName) {
        super(
                properties,
                new DrugData(
                        "psilocybin_mushroom",
                        "Psilocybin Mushroom",
                        DrugType.HALLUCINOGEN,
                        1.0F,
                        1.0F,
                        GeneticsData.defaultGenetics("mushroom", 0x6B3FA0, 0x2E7D32),
                        SubstanceData.defaultVariant(variantName, 0x6B3FA0, 0x2E7D32)
                )
        );
    }
}
