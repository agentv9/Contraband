package com.kaseknife95.psychedelicraft.core.base.drugs;

public enum DrugType {

    STIMULANT(
            "Stimulant",
            "Increases alertness, focus, and energy."
    ),

    DEPRESSANT(
            "Depressant",
            "Reduces activity of the central nervous system."
    ),

    HALLUCINOGEN(
            "Hallucinogen",
            "Alters perception, mood, and sensory processing."
    ),

    DISSOCIATIVE(
            "Dissociative",
            "Produces detachment from surroundings and self."
    ),

    OPIOID(
            "Opioid",
            "Produces pain relief and sedation."
    ),

    CANNABINOID(
            "Cannabinoid",
            "Interacts with the endocannabinoid system."
    );

    private final String displayName;
    private final String description;

    DrugType(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String displayName() {
        return displayName;
    }

    public String description() {
        return description;
    }
}