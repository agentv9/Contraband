package com.kaseknife95.psychedelicraft.items;

import com.kaseknife95.psychedelicraft.core.base.drugs.DrugBase;
import com.kaseknife95.psychedelicraft.core.base.drugs.DrugData;
import com.kaseknife95.psychedelicraft.core.base.drugs.DrugType;
import com.kaseknife95.psychedelicraft.core.base.genetics.GeneticsData;

// common
public class Shroom extends DrugBase {

    public Shroom(Properties properties) {
        super(
                properties,
                new DrugData(
                        "psilocybin_mushroom",
                        "Psilocybin Mushroom",
                        DrugType.HALLUCINOGEN,
                        1.0F,
                        1.0F
                ),
                GeneticsData.defaultStrain("Golden Teacher")
        );
    }
}
