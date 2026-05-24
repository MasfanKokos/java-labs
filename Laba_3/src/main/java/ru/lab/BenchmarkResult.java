package ru.lab;

/**
 * Holds the result of a single benchmark run.
 *
 * @param collection name of the collection (e.g. "ArrayList")
 * @param method     name of the tested method (e.g. "add")
 * @param iterations number of times the method was called
 * @param elapsedMs  total elapsed time in milliseconds
 */
public record BenchmarkResult(String collection, String method, int iterations, double elapsedMs) {}
