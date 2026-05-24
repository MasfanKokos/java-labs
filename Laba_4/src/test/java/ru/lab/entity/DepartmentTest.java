package ru.lab.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentTest {

    @Test
    void idIsUniqueForEachInstance() {
        Department d1 = new Department("Alpha");
        Department d2 = new Department("Beta");
        assertNotEquals(d1.getId(), d2.getId());
    }

    @Test
    void nameIsStoredCorrectly() {
        Department dept = new Department("Engineering");
        assertEquals("Engineering", dept.getName());
    }

    @Test
    void setNameUpdatesValue() {
        Department dept = new Department("Old");
        dept.setName("New");
        assertEquals("New", dept.getName());
    }

    @Test
    void constructorRejectsNullName() {
        assertThrows(NullPointerException.class, () -> new Department(null));
    }

    @Test
    void toStringContainsNameAndId() {
        Department dept = new Department("HR");
        String str = dept.toString();
        assertTrue(str.contains("HR"));
        assertTrue(str.contains(String.valueOf(dept.getId())));
    }
}
