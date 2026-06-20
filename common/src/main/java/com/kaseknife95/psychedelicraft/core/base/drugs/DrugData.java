package com.kaseknife95.psychedelicraft.core.base.drugs;

public record DrugData(
        String drugId,
        String displayName,
        DrugType drugType,
        float basePotency,
        float baseQuality
) {
    public DrugData {
        if (drugId == null || drugId.isBlank()) {
            throw new IllegalArgumentException("drugId cannot be null or blank");
        }

        if (displayName == null || displayName.isBlank()) {
            throw new IllegalArgumentException("displayName cannot be null or blank");
        }

        if (drugType == null) {
            throw new IllegalArgumentException("drugType cannot be null");
        }

        if (basePotency < 0.0F) {
            throw new IllegalArgumentException("basePotency cannot be negative");
        }

        if (baseQuality < 0.0F) {
            throw new IllegalArgumentException("baseQuality cannot be negative");
        }
    }
}