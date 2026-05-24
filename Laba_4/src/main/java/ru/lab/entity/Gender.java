package ru.lab.entity;

/**
 * Represents a person's biological gender as recorded in the data source.
 */
public enum Gender {
    MALE, FEMALE;

    /**
     * Parses a gender string from CSV (e.g. "Male", "Female").
     *
     * @param value raw string from the CSV file
     * @return corresponding {@code Gender} constant
     * @throws IllegalArgumentException if the value is not recognized
     */
    public static Gender fromString(String value) {
        return switch (value.trim().toLowerCase()) {
            case "male"   -> MALE;
            case "female" -> FEMALE;
            default -> throw new IllegalArgumentException("Unknown gender: " + value);
        };
    }
}
