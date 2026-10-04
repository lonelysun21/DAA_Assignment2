package benchmark;

import structures.DynamicArray;
import structures.MinHeap;
import structures.MyLinkedList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class BenchmarkRunner {

    public static void main(String[] args) {
        int[] sizes = {100, 1000, 10000, 100000};
        BenchmarkResult[] results = new BenchmarkResult[36];
        int index = 0;

        for (int n : sizes) {
            results[index++] = runRepeated(() -> measureW1Array(n));
            results[index++] = runRepeated(() -> measureW1List(n));

            results[index++] = runRepeated(() -> measureW2Array(n));
            results[index++] = runRepeated(() -> measureW2List(n));

            results[index++] = runRepeated(() -> measureW3HeadArray(n));
            results[index++] = runRepeated(() -> measureW3HeadList(n));

            results[index++] = runRepeated(() -> measureW3MiddleArray(n));
            results[index++] = runRepeated(() -> measureW3MiddleList(n));

            results[index++] = runRepeated(() -> measureW4Heap(n));
        }

        writeCsv(results);
    }

    private static BenchmarkResult measureW1Array(int n) {
        int[] values = generateData(n);
        DynamicArray array = new DynamicArray();

        for (int value : values) {
            array.add(value);
        }

        array.resetMetrics();

        Random random = new Random(42);

        long start = System.nanoTime();

        for (int i = 0; i < 10000; i++) {
            array.get(random.nextInt(n));
        }

        long end = System.nanoTime();

        return new BenchmarkResult(
                "W1", "-", "DynamicArray", n,
                (end - start) / 1_000_000.0,
                array.getMetrics().getSteps(),
                array.getMetrics().getMoves(),
                array.getMetrics().getComparisons()
        );
    }

    private static BenchmarkResult measureW1List(int n) {
        int[] values = generateData(n);
        MyLinkedList list = new MyLinkedList();

        fillList(list, values);
        list.resetMetrics();

        Random random = new Random(42);

        long start = System.nanoTime();

        for (int i = 0; i < 10000; i++) {
            list.get(random.nextInt(n));
        }

        long end = System.nanoTime();

        return new BenchmarkResult(
                "W1", "-", "MyLinkedList", n,
                (end - start) / 1_000_000.0,
                list.getMetrics().getSteps(),
                list.getMetrics().getMoves(),
                list.getMetrics().getComparisons()
        );
    }

    private static BenchmarkResult measureW2Array(int n) {
        int[] values = generateData(n);
        int[] queries = generateQueries(values);

        DynamicArray array = new DynamicArray();

        for (int value : values) {
            array.add(value);
        }

        array.resetMetrics();

        long start = System.nanoTime();

        for (int query : queries) {
            array.contains(query);
        }

        long end = System.nanoTime();

        return new BenchmarkResult(
                "W2", "-", "DynamicArray", n,
                (end - start) / 1_000_000.0,
                array.getMetrics().getSteps(),
                array.getMetrics().getMoves(),
                array.getMetrics().getComparisons()
        );
    }

    private static BenchmarkResult measureW2List(int n) {
        int[] values = generateData(n);
        int[] queries = generateQueries(values);

        MyLinkedList list = new MyLinkedList();

        fillList(list, values);
        list.resetMetrics();

        long start = System.nanoTime();

        for (int query : queries) {
            list.contains(query);
        }

        long end = System.nanoTime();

        return new BenchmarkResult(
                "W2", "-", "MyLinkedList", n,
                (end - start) / 1_000_000.0,
                list.getMetrics().getSteps(),
                list.getMetrics().getMoves(),
                list.getMetrics().getComparisons()
        );
    }

    private static BenchmarkResult measureW3HeadArray(int n) {
        int[] values = generateData(n);
        DynamicArray array = new DynamicArray();

        for (int value : values) {
            array.add(value);
        }

        array.resetMetrics();

        long start = System.nanoTime();

        for (int i = 0; i < 1000; i++) {
            array.add(0, i);
        }

        for (int i = 0; i < 1000; i++) {
            array.remove(0);
        }

        long end = System.nanoTime();

        return new BenchmarkResult(
                "W3", "head", "DynamicArray", n,
                (end - start) / 1_000_000.0,
                array.getMetrics().getSteps(),
                array.getMetrics().getMoves(),
                array.getMetrics().getComparisons()
        );
    }

    private static BenchmarkResult measureW3HeadList(int n) {
        int[] values = generateData(n);
        MyLinkedList list = new MyLinkedList();

        fillList(list, values);
        list.resetMetrics();

        long start = System.nanoTime();

        for (int i = 0; i < 1000; i++) {
            list.add(0, i);
        }

        for (int i = 0; i < 1000; i++) {
            list.remove(0);
        }

        long end = System.nanoTime();

        return new BenchmarkResult(
                "W3", "head", "MyLinkedList", n,
                (end - start) / 1_000_000.0,
                list.getMetrics().getSteps(),
                list.getMetrics().getMoves(),
                list.getMetrics().getComparisons()
        );
    }

    private static BenchmarkResult measureW3MiddleArray(int n) {
        int[] values = generateData(n);
        DynamicArray array = new DynamicArray();

        for (int value : values) {
            array.add(value);
        }

        array.resetMetrics();

        int middle = n / 2;

        long start = System.nanoTime();

        for (int i = 0; i < 1000; i++) {
            array.add(middle, i);
        }

        for (int i = 0; i < 1000; i++) {
            array.remove(middle);
        }

        long end = System.nanoTime();

        return new BenchmarkResult(
                "W3", "middle", "DynamicArray", n,
                (end - start) / 1_000_000.0,
                array.getMetrics().getSteps(),
                array.getMetrics().getMoves(),
                array.getMetrics().getComparisons()
        );
    }

    private static BenchmarkResult measureW3MiddleList(int n) {
        int[] values = generateData(n);
        MyLinkedList list = new MyLinkedList();

        fillList(list, values);
        list.resetMetrics();

        int middle = n / 2;

        long start = System.nanoTime();

        for (int i = 0; i < 1000; i++) {
            list.add(middle, i);
        }

        for (int i = 0; i < 1000; i++) {
            list.remove(middle);
        }

        long end = System.nanoTime();

        return new BenchmarkResult(
                "W3", "middle", "MyLinkedList", n,
                (end - start) / 1_000_000.0,
                list.getMetrics().getSteps(),
                list.getMetrics().getMoves(),
                list.getMetrics().getComparisons()
        );
    }

    private static BenchmarkResult measureW4Heap(int n) {
        int[] values = generateData(n);
        MinHeap heap = new MinHeap();

        heap.resetMetrics();

        long start = System.nanoTime();

        for (int value : values) {
            heap.insert(value);
        }

        int previous = Integer.MIN_VALUE;

        for (int i = 0; i < n; i++) {
            int current = heap.extractMin();

            if (current < previous) {
                throw new IllegalStateException();
            }

            previous = current;
        }

        long end = System.nanoTime();

        return new BenchmarkResult(
                "W4", "-", "MinHeap", n,
                (end - start) / 1_000_000.0,
                heap.getMetrics().getSteps(),
                heap.getMetrics().getMoves(),
                heap.getMetrics().getComparisons()
        );
    }

    private static BenchmarkResult runRepeated(BenchmarkTask task) {
        task.run();

        BenchmarkResult[] results = new BenchmarkResult[5];

        for (int i = 0; i < 5; i++) {
            results[i] = task.run();
        }

        BenchmarkResult median = findMedian(results);
        printResult(median);

        return median;
    }

    private static BenchmarkResult findMedian(BenchmarkResult[] results) {
        for (int i = 1; i < results.length; i++) {
            BenchmarkResult current = results[i];
            int j = i - 1;

            while (j >= 0 && results[j].getTimeMs() > current.getTimeMs()) {
                results[j + 1] = results[j];
                j--;
            }

            results[j + 1] = current;
        }

        return results[results.length / 2];
    }

    private static int[] generateData(int n) {
        Random random = new Random(42);
        int[] values = new int[n];

        for (int i = 0; i < n; i++) {
            values[i] = random.nextInt(1_000_000);
        }

        return values;
    }

    private static int[] generateQueries(int[] values) {
        int[] queries = new int[1000];
        Random random = new Random(42);

        for (int i = 0; i < 500; i++) {
            queries[i] = values[random.nextInt(values.length)];
        }

        for (int i = 500; i < 1000; i++) {
            queries[i] = -1 - i;
        }

        return queries;
    }

    private static void fillList(MyLinkedList list, int[] values) {
        for (int i = values.length - 1; i >= 0; i--) {
            list.add(0, values[i]);
        }
    }

    private static void writeCsv(BenchmarkResult[] results) {
        File directory = new File("results");

        if (!directory.exists()) {
            directory.mkdirs();
        }

        File file = new File(directory, "results.csv");

        try (FileWriter writer = new FileWriter(file)) {
            writer.write("workload,variant,structure,n,time_ms,steps,moves,comparisons\n");

            for (BenchmarkResult result : results) {
                writer.write(result.toCsvRow());
                writer.write("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void printResult(BenchmarkResult result) {
        System.out.println(
                result.getWorkload() + " " +
                        result.getVariant() + " " +
                        result.getStructure() + " n=" +
                        result.getN() + " time=" +
                        result.getTimeMs() + " ms steps=" +
                        result.getSteps() + " moves=" +
                        result.getMoves() + " comparisons=" +
                        result.getComparisons()
        );
    }

    @FunctionalInterface
    private interface BenchmarkTask {
        BenchmarkResult run();
    }
}