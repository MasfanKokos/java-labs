package ru.lab;

/**
 * Container for storing an arbitrary number of integers.
 *
 * <p>Implemented as a dynamic array: when capacity is exhausted,
 * the internal array is doubled in size. Built-in collections are not used.</p>
 */
public class IntContainer {

    private static final int INITIAL_CAPACITY = 10;

    private int[] data;
    private int size;

    /**
     * Creates an empty container with initial capacity of 10.
     */
    public IntContainer() {
        data = new int[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Appends the given value to the end of the container.
     * The internal array is doubled if capacity is exhausted.
     *
     * @param value the integer to add
     */
    public void add(int value) {
        if (size == data.length) {
            int[] enlarged = new int[data.length * 2];
            System.arraycopy(data, 0, enlarged, 0, size);
            data = enlarged;
        }
        data[size++] = value;
    }

    /**
     * Returns the element at the specified position.
     *
     * @param index zero-based index of the element
     * @return the integer at the given index
     * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index >= size()}
     */
    public int get(int index) {
        checkIndex(index);
        return data[index];
    }

    /**
     * Removes the element at the specified position.
     * Elements after it are shifted one position to the left.
     *
     * @param index zero-based index of the element to remove
     * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index >= size()}
     */
    public void remove(int index) {
        checkIndex(index);
        System.arraycopy(data, index + 1, data, index, size - index - 1);
        size--;
    }

    /**
     * Returns the number of elements currently stored in the container.
     *
     * @return element count
     */
    public int size() {
        return size;
    }

    /**
     * Returns {@code true} if the container contains no elements.
     *
     * @return {@code true} if empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns {@code true} if the container contains at least one element
     * equal to {@code value}.
     *
     * @param value the integer to search for
     * @return {@code true} if found
     */
    public boolean contains(int value) {
        for (int i = 0; i < size; i++) {
            if (data[i] == value) return true;
        }
        return false;
    }

    /**
     * Returns a string representation of the container, e.g. {@code [1, 2, 3]}.
     *
     * @return string representation
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(data[i]);
            if (i < size - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Index " + index + " is out of bounds for size " + size);
        }
    }
}
