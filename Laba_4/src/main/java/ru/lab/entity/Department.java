package ru.lab.entity;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents a company department.
 *
 * <p>The {@link #id} is assigned automatically by an internal counter when
 * a new {@code Department} instance is created.</p>
 */
public class Department {

    private static final AtomicInteger ID_COUNTER = new AtomicInteger(1);

    private final int id;
    private String name;

    /**
     * Creates a new department with an auto-generated ID.
     *
     * @param name human-readable department name, must not be {@code null}
     */
    public Department(String name) {
        this.id = ID_COUNTER.getAndIncrement();
        this.name = Objects.requireNonNull(name, "name must not be null");
    }

    /**
     * Returns the auto-generated department ID.
     *
     * @return unique department identifier
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the department name.
     *
     * @return department name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the department name.
     *
     * @param name new department name, must not be {@code null}
     */
    public void setName(String name) {
        this.name = Objects.requireNonNull(name, "name must not be null");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department that)) return false;
        return id == that.id && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Department{id=" + id + ", name='" + name + "'}";
    }
}
