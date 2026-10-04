package structures;

import metrics.OperationMetrics;

public class DynamicArray {
    private int[] data;
    private int size;
    private final OperationMetrics metrics;

    public DynamicArray() {
        data = new int[10];
        size = 0;
        metrics = new OperationMetrics();
    }

    public void add(int x) {
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
        size++;
    }

    public void add(int index, int x) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        if (size == data.length) {
            int[] newData = new int[data.length * 2];

            for (int i = 0; i < data.length; i++) {
                newData[i] = data[i];
                metrics.addStep();
                metrics.addMove();
            }

            data = newData;
        }

        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
            metrics.addStep();
            metrics.addMove();
        }

        data[index] = x;
        metrics.addMove();
        size++;
    }

    public int remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        int removedValue = data[index];
        metrics.addStep();

        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
            metrics.addStep();
            metrics.addMove();
        }

        size--;
        return removedValue;
    }

    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        metrics.addStep();
        return data[index];
    }

    public boolean contains(int x) {
        for (int i = 0; i < size; i++) {
            metrics.addStep();
            metrics.addComparison();

            if (data[i] == x) {
                return true;
            }
        }

        return false;
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