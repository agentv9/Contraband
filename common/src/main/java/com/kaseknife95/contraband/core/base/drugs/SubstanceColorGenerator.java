package com.kaseknife95.contraband.core.base.drugs;

import java.awt.Color;

public final class SubstanceColorGenerator {

    private SubstanceColorGenerator() {}

    public static int fromStrainName(String strainName) {

        int hash = Math.abs(strainName.toLowerCase().trim().hashCode());

        float hue = (hash % 360) / 360.0F;

        float saturation = 0.90F;
        float brightness = 1.00F;

        return Color.HSBtoRGB(hue, saturation, brightness) & 0xFFFFFF;
    }
}
