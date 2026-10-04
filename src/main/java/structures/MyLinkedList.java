package structures;

import metrics.OperationMetrics;

public class MyLinkedList {
    private Node head;
    private int size;
    private final OperationMetrics metrics;

    private static class Node {
        int value;
        Node next;

        Node(int value) {
            this.value = value;
        }
    }

    public MyLinkedList() {
        head = null;
        size = 0;
        metrics = new OperationMetrics();
    }

    public void add(int x) {
        Node newNode = new Node(x);

        if (head == null) {
            head = newNode;
            metrics.addMove();
            size++;
            return;
        }

        Node current = head;

        while (current.next != null) {
            current = current.next;
            metrics.addStep();
        }

        current.next = newNode;
        metrics.addMove();
        size++;
    }

    public void add(int index, int x) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        Node newNode = new Node(x);

        if (index == 0) {
            newNode.next = head;
            metrics.addMove();

            head = newNode;
            metrics.addMove();

            size++;
            return;
        }

        Node current = head;

        for (int i = 0; i < index - 1; i++) {
            current = current.next;
            metrics.addStep();
        }

        newNode.next = current.next;
        metrics.addMove();

        current.next = newNode;
        metrics.addMove();

        size++;
    }

    public int remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        if (index == 0) {
            int removedValue = head.value;

            head = head.next;
            metrics.addMove();

            size--;
            return removedValue;
        }

        Node current = head;

        for (int i = 0; i < index - 1; i++) {
            current = current.next;
            metrics.addStep();
        }

        int removedValue = current.next.value;

        current.next = current.next.next;
        metrics.addMove();

        size--;
        return removedValue;
    }

    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        Node current = head;

        for (int i = 0; i < index; i++) {
            current = current.next;
            metrics.addStep();
        }

        return current.value;
    }

    public boolean contains(int x) {
        Node current = head;

        while (current != null) {
            metrics.addComparison();

            if (current.value == x) {
                return true;
            }

            current = current.next;

            if (current != null) {
                metrics.addStep();
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