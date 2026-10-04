package benchmark;

import structures.DynamicArray;
import structures.MinHeap;
import structures.MyLinkedList;

import java.util.Random;

public class BenchmarkRunner {

    public static void main(String[] args) {
        int[] sizes = {100, 1000, 10000, 100000};

        for (int n : sizes) {
            runW1(n);
            runW2(n);
            runW3Head(n);
            runW3Middle(n);
            runW4(n);
        }
    }

    private static void runW1(int n) {
        int[] values = generateData(n);

        DynamicArray array = new DynamicArray();
        MyLinkedList list = new MyLinkedList();

        for (int value : values) {
            array.add(value);
            list.add(value);
        }

        array.resetMetrics();
        list.resetMetrics();

        Random random = new Random(42);

        long startArray = System.nanoTime();

        for (int i = 0; i < 10000; i++) {
            int index = random.nextInt(n);
            array.get(index);
        }

        long endArray = System.nanoTime();

        random = new Random(42);

        long startList = System.nanoTime();

        for (int i = 0; i < 10000; i++) {
            int index = random.nextInt(n);
            list.get(index);
        }

        long endList = System.nanoTime();

        printResult(new BenchmarkResult(
                "W1", "-", "DynamicArray", n,
                (endArray - startArray) / 1_000_000.0,
                array.getMetrics().getSteps(),
                array.getMetrics().getMoves(),
                array.getMetrics().getComparisons()
        ));

        printResult(new BenchmarkResult(
                "W1", "-", "MyLinkedList", n,
                (endList - startList) / 1_000_000.0,
                list.getMetrics().getSteps(),
                list.getMetrics().getMoves(),
                list.getMetrics().getComparisons()
        ));
    }

    private static void runW2(int n) {
        int[] values = generateData(n);

        DynamicArray array = new DynamicArray();
        MyLinkedList list = new MyLinkedList();

        for (int value : values) {
            array.add(value);
            list.add(value);
        }

        int[] queries = new int[1000];
        Random random = new Random(42);

        for (int i = 0; i < 500; i++) {
            queries[i] = values[random.nextInt(n)];
        }

        for (int i = 500; i < 1000; i++) {
            queries[i] = -1 - i;
        }

        array.resetMetrics();
        list.resetMetrics();

        long startArray = System.nanoTime();

        for (int query : queries) {
            array.contains(query);
        }

        long endArray = System.nanoTime();

        long startList = System.nanoTime();

        for (int query : queries) {
            list.contains(query);
        }

        long endList = System.nanoTime();

        printResult(new BenchmarkResult(
                "W2", "-", "DynamicArray", n,
                (endArray - startArray) / 1_000_000.0,
                array.getMetrics().getSteps(),
                array.getMetrics().getMoves(),
                array.getMetrics().getComparisons()
        ));

        printResult(new BenchmarkResult(
                "W2", "-", "MyLinkedList", n,
                (endList - startList) / 1_000_000.0,
                list.getMetrics().getSteps(),
                list.getMetrics().getMoves(),
                list.getMetrics().getComparisons()
        ));
    }

    private static void runW3Head(int n) {
        int[] values = generateData(n);

        DynamicArray array = new DynamicArray();
        MyLinkedList list = new MyLinkedList();

        for (int value : values) {
            array.add(value);
            list.add(value);
        }

        array.resetMetrics();
        list.resetMetrics();

        long startArray = System.nanoTime();

        for (int i = 0; i < 1000; i++) {
            array.add(0, i);
        }

        for (int i = 0; i < 1000; i++) {
            array.remove(0);
        }

        long endArray = System.nanoTime();

        long startList = System.nanoTime();

        for (int i = 0; i < 1000; i++) {
            list.add(0, i);
        }

        for (int i = 0; i < 1000; i++) {
            list.remove(0);
        }

        long endList = System.nanoTime();

        printResult(new BenchmarkResult(
                "W3", "head", "DynamicArray", n,
                (endArray - startArray) / 1_000_000.0,
                array.getMetrics().getSteps(),
                array.getMetrics().getMoves(),
                array.getMetrics().getComparisons()
        ));

        printResult(new BenchmarkResult(
                "W3", "head", "MyLinkedList", n,
                (endList - startList) / 1_000_000.0,
                list.getMetrics().getSteps(),
                list.getMetrics().getMoves(),
                list.getMetrics().getComparisons()
        ));
    }

    private static void runW3Middle(int n) {
        int[] values = generateData(n);

        DynamicArray array = new DynamicArray();
        MyLinkedList list = new MyLinkedList();

        for (int value : values) {
            array.add(value);
            list.add(value);
        }

        array.resetMetrics();
        list.resetMetrics();

        long startArray = System.nanoTime();

        for (int i = 0; i < 1000; i++) {
            array.add(array.size() / 2, i);
        }

        for (int i = 0; i < 1000; i++) {
            array.remove(array.size() / 2);
        }

        long endArray = System.nanoTime();

        long startList = System.nanoTime();

        for (int i = 0; i < 1000; i++) {
            list.add(list.size() / 2, i);
        }

        for (int i = 0; i < 1000; i++) {
            list.remove(list.size() / 2);
        }

        long endList = System.nanoTime();

        printResult(new BenchmarkResult(
                "W3", "middle", "DynamicArray", n,
                (endArray - startArray) / 1_000_000.0,
                array.getMetrics().getSteps(),
                array.getMetrics().getMoves(),
                array.getMetrics().getComparisons()
        ));

        printResult(new BenchmarkResult(
                "W3", "middle", "MyLinkedList", n,
                (endList - startList) / 1_000_000.0,
                list.getMetrics().getSteps(),
                list.getMetrics().getMoves(),
                list.getMetrics().getComparisons()
        ));
    }

    private static void runW4(int n) {
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

        printResult(new BenchmarkResult(
                "W4", "-", "MinHeap", n,
                (end - start) / 1_000_000.0,
                heap.getMetrics().getSteps(),
                heap.getMetrics().getMoves(),
                heap.getMetrics().getComparisons()
        ));
    }

    private static int[] generateData(int n) {
        Random random = new Random(42);
        int[] values = new int[n];

        for (int i = 0; i < n; i++) {
            values[i] = random.nextInt(1_000_000);
        }

        return values;
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
}