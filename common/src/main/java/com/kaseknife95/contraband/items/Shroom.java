package com.kaseknife95.contraband.items;

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
                        GeneticsData.defaultGenetics("mushroom"),
                        SubstanceData.defaultVariant(variantName)
                )
        );
    }
}
