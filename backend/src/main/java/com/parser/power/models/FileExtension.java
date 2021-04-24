package com.parser.power.models;

public enum FileExtension {
    YAML("application/x-yaml"),
    XML("text/xml"),
    CSV("text/csv"),
    JSON("application/json");

    private final String type;

    private FileExtension(String type) {
        this.type = type;
    }

    public static FileExtension fromString(String text) {
        for (FileExtension b : FileExtension.values()) {
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
