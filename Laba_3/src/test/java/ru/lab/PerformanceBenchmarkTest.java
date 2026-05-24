package ru.lab;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PerformanceBenchmarkTest {

    private PerformanceBenchmark benchmark;

    @BeforeEach
    void setUp() {
        benchmark = new PerformanceBenchmark(100);
    }

    @Test
    void constructorRejectsNonPositiveIterations() {
        assertThrows(IllegalArgumentException.class, () -> new PerformanceBenchmark(0));
        assertThrows(IllegalArgumentException.class, () -> new PerformanceBenchmark(-1));
    }

    @Test
    void benchmarkAddReturnsPositiveTime() {
        BenchmarkResult r = benchmark.benchmarkAdd("ArrayList");
        assertTrue(r.elapsedMs() >= 0);
        assertEquals("ArrayList", r.collection());
        assertEquals("add", r.method());
        assertEquals(100, r.iterations());
    }

    @Test
    void benchmarkGetReturnsPositiveTime() {
        BenchmarkResult r = benchmark.benchmarkGet("LinkedList");
        assertTrue(r.elapsedMs() >= 0);
        assertEquals("LinkedList", r.collection());
        assertEquals("get", r.method());
    }

    @Test
    void benchmarkRemoveReturnsPositiveTime() {
        BenchmarkResult r = benchmark.benchmarkRemove("ArrayList");
        assertTrue(r.elapsedMs() >= 0);
        assertEquals("remove", r.method());
    }

    @Test
    void runAllReturnsSixResults() {
        List<BenchmarkResult> results = benchmark.runAll();
        assertEquals(6, results.size());
    }

    @Test
    void runAllCoversAllMethodsAndCollections() {
        List<BenchmarkResult> results = benchmark.runAll();
        long arrayListCount = results.stream().filter(r -> r.collection().equals("ArrayList")).count();
        long linkedListCount = results.stream().filter(r -> r.collection().equals("LinkedList")).count();
        assertEquals(3, arrayListCount);
        assertEquals(3, linkedListCount);
    }

    @Test
    void formatTableContainsHeaders() {
        List<BenchmarkResult> results = benchmark.runAll();
        String table = Main.formatTable(results);
        assertTrue(table.contains("Collection"));
        assertTrue(table.contains("Method"));
        assertTrue(table.contains("Time (ms)"));
    }

    @Test
    void formatTableContainsAllCollections() {
        List<BenchmarkResult> results = benchmark.runAll();
        String table = Main.formatTable(results);
        assertTrue(table.contains("ArrayList"));
        assertTrue(table.contains("LinkedList"));
    }
}
