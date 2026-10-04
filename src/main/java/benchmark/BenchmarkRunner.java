package benchmark;

import structures.DynamicArray;
import structures.MyLinkedList;

import java.util.Random;

public class BenchmarkRunner {

    public static void main(String[] args) {
        int[] sizes = {100, 1000, 10000, 100000};

        for (int n : sizes) {
            runW1(n);
            runW2(n);
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

        BenchmarkResult arrayResult = new BenchmarkResult(
                "W1", "-", "DynamicArray", n,
                (endArray - startArray) / 1_000_000.0,
                array.getMetrics().getSteps(),
                array.getMetrics().getMoves(),
                array.getMetrics().getComparisons()
        );

        BenchmarkResult listResult = new BenchmarkResult(
                "W1", "-", "MyLinkedList", n,
                (endList - startList) / 1_000_000.0,
                list.getMetrics().getSteps(),
                list.getMetrics().getMoves(),
                list.getMetrics().getComparisons()
        );

        printResult(arrayResult);
        printResult(listResult);
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

        BenchmarkResult arrayResult = new BenchmarkResult(
                "W2", "-", "DynamicArray", n,
                (endArray - startArray) / 1_000_000.0,
                array.getMetrics().getSteps(),
                array.getMetrics().getMoves(),
                array.getMetrics().getComparisons()
        );

        BenchmarkResult listResult = new BenchmarkResult(
                "W2", "-", "MyLinkedList", n,
                (endList - startList) / 1_000_000.0,
                list.getMetrics().getSteps(),
                list.getMetrics().getMoves(),
                list.getMetrics().getComparisons()
        );

        printResult(arrayResult);
        printResult(listResult);
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
                        result.getStructure() + " n=" +
                        result.getN() + " time=" +
                        result.getTimeMs() + " ms steps=" +
                        result.getSteps() + " moves=" +
                        result.getMoves() + " comparisons=" +
                        result.getComparisons()
        );
    }
}