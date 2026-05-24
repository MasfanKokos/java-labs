package ru.lab.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenderTest {

    @Test
    void parseMale() {
        assertEquals(Gender.MALE, Gender.fromString("Male"));
    }

    @Test
    void parseFemale() {
        assertEquals(Gender.FEMALE, Gender.fromString("Female"));
    }

    @Test
    void parseCaseInsensitive() {
        assertEquals(Gender.MALE, Gender.fromString("MALE"));
        assertEquals(Gender.FEMALE, Gender.fromString("female"));
    }

    @Test
    void unknownValueThrows() {
        assertThrows(IllegalArgumentException.class, () -> Gender.fromString("unknown"));
    }
}
