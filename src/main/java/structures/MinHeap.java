package structures;

import metrics.OperationMetrics;

public class MinHeap {
    private int[] data;
    private int size;
    private final OperationMetrics metrics;

    public MinHeap() {
        data = new int[10];
        size = 0;
        metrics = new OperationMetrics();
    }

    public void insert(int x) {
        if (size == data.length) {
            int[] newData = new int[data.length * 2];

            for (int i = 0; i < data.length; i++) {
                newData[i] = data[i];
                metrics.addStep();
                metrics.addMove();
            }

            data = newData;
        }

        data[size] = x;
        metrics.addMove();

        int index = size;
        size++;

        while (index > 0) {
            int parent = (index - 1) / 2;

            metrics.addStep();
            metrics.addStep();
            metrics.addComparison();

            if (data[parent] <= data[index]) {
                break;
            }

            int temp = data[parent];
            metrics.addStep();

            data[parent] = data[index];
            metrics.addStep();
            metrics.addMove();

            data[index] = temp;
            metrics.addMove();

            index = parent;
        }
    }

    public int peekMin() {
        if (size == 0) {
            throw new IllegalStateException();
        }

        metrics.addStep();
        return data[0];
    }

    public int extractMin() {
        if (size == 0) {
            throw new IllegalStateException();
        }

        int min = data[0];
        metrics.addStep();

        data[0] = data[size - 1];
        metrics.addStep();
        metrics.addMove();

        size--;

        int index = 0;

        while (true) {
            int leftChild = index * 2 + 1;
            int rightChild = index * 2 + 2;
            int smallest = index;

            if (leftChild < size) {
                metrics.addStep();
                metrics.addStep();
                metrics.addComparison();

                if (data[leftChild] < data[smallest]) {
                    smallest = leftChild;
                }
            }

            if (rightChild < size) {
                metrics.addStep();
                metrics.addStep();
                metrics.addComparison();

                if (data[rightChild] < data[smallest]) {
                    smallest = rightChild;
                }
            }

            if (smallest == index) {
                break;
            }

            int temp = data[index];
            metrics.addStep();

            data[index] = data[smallest];
            metrics.addStep();
            metrics.addMove();

            data[smallest] = temp;
            metrics.addMove();

            index = smallest;
        }

        return min;
    }

    public int size() {
        return size;
    }

    public OperationMetrics getMetrics() {
        return metrics;
    }

    public void resetMetrics() {
        metrics.reset();
    }
}