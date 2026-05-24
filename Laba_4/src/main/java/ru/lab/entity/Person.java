package ru.lab.entity;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents an employee record loaded from a CSV data source.
 *
 * <p>Each person belongs to exactly one {@link Department} and carries
 * personal attributes: name, gender, salary, and date of birth.</p>
 */
public class Person {

    private final int id;
    private String name;
    private Gender gender;
    private Department department;
    private double salary;
    private LocalDate birthDate;

    /**
     * Constructs a fully initialised {@code Person}.
     *
     * @param id         unique person identifier from the data source
     * @param name       full name, must not be {@code null}
     * @param gender     person's gender, must not be {@code null}
     * @param department department the person belongs to, must not be {@code null}
     * @param salary     monthly salary (non-negative)
     * @param birthDate  date of birth, must not be {@code null}
     */
    public Person(int id, String name, Gender gender,
                  Department department, double salary, LocalDate birthDate) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.gender = Objects.requireNonNull(gender, "gender must not be null");
        this.department = Objects.requireNonNull(department, "department must not be null");
        this.salary = salary;
        this.birthDate = Objects.requireNonNull(birthDate, "birthDate must not be null");
    }

    /** @return person ID from the source data */
    public int getId() { return id; }

    /** @return full name */
    public String getName() { return name; }

    /** @param name new name, must not be {@code null} */
    public void setName(String name) {
        this.name = Objects.requireNonNull(name, "name must not be null");
    }

    /** @return gender */
    public Gender getGender() { return gender; }

    /** @param gender new gender, must not be {@code null} */
    public void setGender(Gender gender) {
        this.gender = Objects.requireNonNull(gender, "gender must not be null");
    }

    /** @return the department this person belongs to */
    public Department getDepartment() { return department; }

    /** @param department new department, must not be {@code null} */
    public void setDepartment(Department department) {
        this.department = Objects.requireNonNull(department, "department must not be null");
    }

    /** @return monthly salary */
    public double getSalary() { return salary; }

    /** @param salary new salary */
    public void setSalary(double salary) { this.salary = salary; }

    /** @return date of birth */
    public LocalDate getBirthDate() { return birthDate; }

    /** @param birthDate new date of birth, must not be {@code null} */
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = Objects.requireNonNull(birthDate, "birthDate must not be null");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person that)) return false;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", department=" + department +
                ", salary=" + salary +
                ", birthDate=" + birthDate +
                '}';
    }
}
