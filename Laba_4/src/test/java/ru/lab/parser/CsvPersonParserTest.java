package ru.lab.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.lab.entity.Gender;
import ru.lab.entity.Person;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvPersonParserTest {

    private CsvPersonParser parser;

    @BeforeEach
    void setUp() {
        parser = new CsvPersonParser();
    }

    @Test
    void parsesCorrectNumberOfPersons() throws Exception {
        List<Person> persons = parser.parse("test_persons.csv");
        assertEquals(3, persons.size());
    }

    @Test
    void firstPersonFieldsAreCorrect() throws Exception {
        Person alice = parser.parse("test_persons.csv").get(0);

        assertEquals(1, alice.getId());
        assertEquals("Alice", alice.getName());
        assertEquals(Gender.FEMALE, alice.getGender());
        assertEquals(LocalDate.of(1990, 1, 1), alice.getBirthDate());
        assertEquals("A", alice.getDepartment().getName());
        assertEquals(5000.0, alice.getSalary(), 0.001);
    }

    @Test
    void sameDivisionSharesDepartmentInstance() throws Exception {
        List<Person> persons = parser.parse("test_persons.csv");

        // Alice (index 0) and Carol (index 2) are both in division A
        Person alice = persons.get(0);
        Person carol = persons.get(2);

        assertSame(alice.getDepartment(), carol.getDepartment(),
                "Persons in the same division must share a Department instance");
    }

    @Test
    void differentDivisionsProduceDifferentDepartments() throws Exception {
        List<Person> persons = parser.parse("test_persons.csv");

        Person alice = persons.get(0); // division A
        Person bob   = persons.get(1); // division B

        assertNotSame(alice.getDepartment(), bob.getDepartment());
        assertNotEquals(alice.getDepartment().getName(), bob.getDepartment().getName());
    }

    @Test
    void maleGenderParsedCorrectly() throws Exception {
        Person bob = parser.parse("test_persons.csv").get(1);
        assertEquals(Gender.MALE, bob.getGender());
    }

    @Test
    void missingResourceThrowsIOException() {
        assertThrows(Exception.class, () -> parser.parse("nonexistent.csv"));
    }
}
