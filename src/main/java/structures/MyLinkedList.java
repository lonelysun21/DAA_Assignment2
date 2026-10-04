package structures;

public class MyLinkedList {
    private Node head;
    private int size;

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
    }

    public void add(int x) {
        Node newNode = new Node(x);

        if (head == null) {
            head = newNode;
            size++;
            return;
        }

        Node current = head;

        while (current.next != null) {
            current = current.next;
        }

        current.next = newNode;
        size++;
    }

    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        Node current = head;

        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        return current.value;
    }

    public int size() {
        return size;
    }
}