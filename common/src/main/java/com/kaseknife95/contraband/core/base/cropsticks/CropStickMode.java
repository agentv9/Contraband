package com.kaseknife95.contraband.core.base.cropsticks;

public enum CropStickMode {

    /**
     * Contains no plant and will not automatically breed.
     */
    EMPTY,

    /**
     * Contains a planted or crossbred crop.
     */
    SINGLE_CROP,

    /**
     * Searches for two mature compatible neighboring plants.
     */
    CROSSBREEDING;

    public static CropStickMode byName(String name) {
        if (name == null || name.isBlank()) {
            return EMPTY;
        }

        for (CropStickMode mode : values()) {
            if (mode.name().equalsIgnoreCase(name)) {
                return mode;
            }
        }

        return EMPTY;
    }
}