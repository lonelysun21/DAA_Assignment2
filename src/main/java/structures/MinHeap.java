package structures;

public class MinHeap {
    private int[] data;
    private int size;

    public MinHeap() {
        data = new int[10];
        size = 0;
    }

    public void insert(int x) {
        if (size == data.length) {
            int[] newData = new int[data.length * 2];

            for (int i = 0; i < data.length; i++) {
                newData[i] = data[i];
            }

            data = newData;
        }

        data[size] = x;
        int index = size;
        size++;

        while (index > 0) {
            int parent = (index - 1) / 2;

            if (data[parent] <= data[index]) {
                break;
            }

            int temp = data[parent];
            data[parent] = data[index];
            data[index] = temp;

            index = parent;
        }
    }

    public int peekMin() {
        if (size == 0) {
            throw new IllegalStateException();
        }

        return data[0];
    }

    public int extractMin() {
        if (size == 0) {
            throw new IllegalStateException();
        }

        int min = data[0];

        data[0] = data[size - 1];
        size--;

        int index = 0;

        while (true) {
            int leftChild = index * 2 + 1;
            int rightChild = index * 2 + 2;
            int smallest = index;

            if (leftChild < size && data[leftChild] < data[smallest]) {
                smallest = leftChild;
            }

            if (rightChild < size && data[rightChild] < data[smallest]) {
                smallest = rightChild;
            }

            if (smallest == index) {
                break;
            }

            int temp = data[index];
            data[index] = data[smallest];
            data[smallest] = temp;

            index = smallest;
        }

        return min;
    }

    public int size() {
        return size;
    }
}