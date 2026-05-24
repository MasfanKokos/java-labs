package ru.lab;

import java.util.List;

/**
 * Entry point: runs the benchmark and prints a formatted results table.
 */
public class Main {

    private static final int ITERATIONS = 10_000;

    public static void main(String[] args) {
        System.out.println("Benchmarking ArrayList vs LinkedList (" + ITERATIONS + " iterations each)\n");

        PerformanceBenchmark benchmark = new PerformanceBenchmark(ITERATIONS);
        List<BenchmarkResult> results = benchmark.runAll();

        System.out.println(formatTable(results));
    }

    /**
     * Formats benchmark results into a human-readable table.
     *
     * @param results list of benchmark results
     * @return formatted table string
     */
    public static String formatTable(List<BenchmarkResult> results) {
        String separator = "+------------+--------+------------+-------------+";
        String header    = "| Collection | Method | Iterations |    Time (ms)|";

        StringBuilder sb = new StringBuilder();
        sb.append(separator).append("\n");
        sb.append(header).append("\n");
        sb.append(separator).append("\n");

        for (BenchmarkResult r : results) {
            sb.append(String.format("| %-10s | %-6s | %10d | %11.3f |%n",
                    r.collection(), r.method(), r.iterations(), r.elapsedMs()));
        }

        sb.append(separator);
        return sb.toString();
    }
}
