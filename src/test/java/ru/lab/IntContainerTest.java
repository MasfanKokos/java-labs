package ru.lab;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntContainerTest {

    private IntContainer container;

    @BeforeEach
    void setUp() {
        container = new IntContainer();
    }

    @Test
    void newContainerIsEmpty() {
        assertTrue(container.isEmpty());
        assertEquals(0, container.size());
    }

    @Test
    void addIncreasesSize() {
        container.add(10);
        container.add(20);
        container.add(30);
        assertEquals(3, container.size());
        assertFalse(container.isEmpty());
    }

    @Test
    void getReturnsCorrectElement() {
        container.add(42);
        container.add(7);
        assertEquals(42, container.get(0));
        assertEquals(7, container.get(1));
    }

    @Test
    void getThrowsOnInvalidIndex() {
        container.add(1);
        assertThrows(IndexOutOfBoundsException.class, () -> container.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> container.get(1));
    }

    @Test
    void removeShiftsElements() {
        container.add(1);
        container.add(2);
        container.add(3);
        container.remove(1);
        assertEquals(2, container.size());
        assertEquals(1, container.get(0));
        assertEquals(3, container.get(1));
    }

    @Test
    void removeThrowsOnInvalidIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> container.remove(0));
        container.add(5);
        assertThrows(IndexOutOfBoundsException.class, () -> container.remove(1));
    }

    @Test
    void addBeyondInitialCapacity() {
        for (int i = 0; i < 25; i++) {
            container.add(i);
        }
        assertEquals(25, container.size());
        assertEquals(0, container.get(0));
        assertEquals(24, container.get(24));
    }

    @Test
    void addAfterRemoveWorksCorrectly() {
        container.add(10);
        container.add(20);
        container.remove(0);
        container.add(30);
        assertEquals(2, container.size());
        assertEquals(20, container.get(0));
        assertEquals(30, container.get(1));
    }
}
