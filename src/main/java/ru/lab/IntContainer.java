package ru.lab;

/**
 * Container for storing an arbitrary number of integers.
 * Implemented as a dynamic array without using built-in collections.
 */
public class IntContainer {

    private static final int INITIAL_CAPACITY = 10;

    private int[] data;
    private int size;

    public IntContainer() {
        data = new int[INITIAL_CAPACITY];
        size = 0;
    }

    public void add(int value) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public int get(int index) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void remove(int index) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public int size() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public boolean isEmpty() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
