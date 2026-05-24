package ru.lab;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Benchmarks {@link ArrayList} vs {@link LinkedList} for add, get, and remove operations.
 *
 * <p>Each method is called {@code iterations} times on a fresh list instance,
 * and the elapsed wall-clock time is recorded in milliseconds.</p>
 */
public class PerformanceBenchmark {

    private final int iterations;
    private final Random random;

    /**
     * Creates a benchmark with the given iteration count.
     *
     * @param iterations number of times each operation is performed; must be positive
     */
    public PerformanceBenchmark(int iterations) {
        if (iterations <= 0) throw new IllegalArgumentException("iterations must be positive");
        this.iterations = iterations;
        this.random = new Random(42);
    }

    /**
     * Runs all benchmarks (add, get, remove) for both ArrayList and LinkedList.
     *
     * @return list of results in the order: add, get, remove for each collection
     */
    public List<BenchmarkResult> runAll() {
        List<BenchmarkResult> results = new ArrayList<>();

        for (String name : List.of("ArrayList", "LinkedList")) {
            results.add(benchmarkAdd(name));
            results.add(benchmarkGet(name));
            results.add(benchmarkRemove(name));
        }

        return results;
    }

    /**
     * Benchmarks {@code add(element)} — appends elements to the end of the list.
     *
     * @param collectionName display name of the collection
     * @return benchmark result
     */
    public BenchmarkResult benchmarkAdd(String collectionName) {
        List<Integer> list = createList(collectionName);
        long start = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            list.add(i);
        }
        return result(collectionName, "add", start);
    }

    /**
     * Benchmarks {@code get(index)} — retrieves elements at random indices.
     * The list is pre-populated with {@code iterations} elements before timing starts.
     *
     * @param collectionName display name of the collection
     * @return benchmark result
     */
    public BenchmarkResult benchmarkGet(String collectionName) {
        List<Integer> list = populate(createList(collectionName));
        random.setSeed(42);
        long start = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            list.get(random.nextInt(iterations));
        }
        return result(collectionName, "get", start);
    }

    /**
     * Benchmarks {@code remove(index)} — removes elements from the middle of the list.
     * The list is pre-populated with {@code iterations} elements before timing starts.
     *
     * @param collectionName display name of the collection
     * @return benchmark result
     */
    public BenchmarkResult benchmarkRemove(String collectionName) {
        List<Integer> list = populate(createList(collectionName));
        long start = System.nanoTime();
        while (!list.isEmpty()) {
            list.remove(list.size() / 2);
        }
        return result(collectionName, "remove", start);
    }

    private List<Integer> createList(String name) {
        return name.equals("ArrayList") ? new ArrayList<>() : new LinkedList<>();
    }

    private List<Integer> populate(List<Integer> list) {
        for (int i = 0; i < iterations; i++) list.add(i);
        return list;
    }

    private BenchmarkResult result(String name, String method, long startNano) {
        double ms = (System.nanoTime() - startNano) / 1_000_000.0;
        return new BenchmarkResult(name, method, iterations, ms);
    }
}
