package com.kaseknife95.contraband.core.util;

public class ColorUtils {
    public static int blendColors(int colorA, int colorB, float ratioA) {
        ratioA = Math.max(0.0F, Math.min(1.0F, ratioA));

        float ratioB = 1.0F - ratioA;

        int r = (int)(((colorA >> 16) & 0xFF) * ratioA + ((colorB >> 16) & 0xFF) * ratioB);
        int g = (int)(((colorA >> 8) & 0xFF) * ratioA + ((colorB >> 8) & 0xFF) * ratioB);
        int b = (int)((colorA & 0xFF) * ratioA + (colorB & 0xFF) * ratioB);

        return (r << 16) | (g << 8) | b;
    }
}
