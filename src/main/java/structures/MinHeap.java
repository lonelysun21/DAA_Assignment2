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

    public int size() {
        return size;
    }
}