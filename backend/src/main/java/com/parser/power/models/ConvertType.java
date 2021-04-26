package com.parser.power.models;

public enum ConvertType {
    YAML("application/x-yaml"),
    XML("text/xml"),
    CSV("text/csv"),
    JSON("application/json");

    private final String type;

    ConvertType(String type) {
        this.type = type;
    }

    public static ConvertType fromString(String text) {
        for (ConvertType b : ConvertType.values()) {
            if (b.type.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }


    @Override
    public String toString() {
        return type;
    }

}
