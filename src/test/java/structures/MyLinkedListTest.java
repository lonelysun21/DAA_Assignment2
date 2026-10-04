package structures;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class MyLinkedListTest {

    @Test
    void addAndGetElements() {
        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(20);
        list.add(30);

        assertEquals(3, list.size());
        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
        assertEquals(30, list.get(2));
    }

    @Test
    void addAtIndex() {
        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(30);
        list.add(1, 20);

        assertEquals(3, list.size());
        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
        assertEquals(30, list.get(2));
    }

    @Test
    void addAtFirstAndLastIndex() {
        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(20);

        list.add(0, 5);
        list.add(list.size(), 30);

        assertEquals(5, list.get(0));
        assertEquals(30, list.get(list.size() - 1));
    }

    @Test
    void removeElement() {
        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(20);
        list.add(30);

        int removed = list.remove(1);

        assertEquals(20, removed);
        assertEquals(2, list.size());
        assertEquals(10, list.get(0));
        assertEquals(30, list.get(1));
    }

    @Test
    void removeFirstAndLastElement() {
        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(20);
        list.add(30);

        assertEquals(10, list.remove(0));
        assertEquals(30, list.remove(list.size() - 1));
        assertEquals(1, list.size());
        assertEquals(20, list.get(0));
    }

    @Test
    void containsValues() {
        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(20);
        list.add(20);
        list.add(30);

        assertTrue(list.contains(20));
        assertTrue(list.contains(30));
        assertFalse(list.contains(100));
    }

    @Test
    void oneElementList() {
        MyLinkedList list = new MyLinkedList();

        list.add(42);

        assertEquals(1, list.size());
        assertEquals(42, list.get(0));
        assertTrue(list.contains(42));
    }

    @Test
    void invalidGetIndex() {
        MyLinkedList list = new MyLinkedList();

        assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));

        list.add(10);

        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(1));
    }

    @Test
    void invalidAddIndex() {
        MyLinkedList list = new MyLinkedList();

        assertThrows(IndexOutOfBoundsException.class, () -> list.add(-1, 10));
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(1, 10));
    }

    @Test
    void invalidRemoveIndex() {
        MyLinkedList list = new MyLinkedList();

        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(0));

        list.add(10);

        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(1));
    }

    @Test
    void compareWithLinkedListOnRandomData() {
        MyLinkedList list = new MyLinkedList();
        LinkedList<Integer> expected = new LinkedList<>();
        Random random = new Random(42);

        for (int i = 0; i < 1000; i++) {
            int value = random.nextInt(10000);

            list.add(value);
            expected.add(value);
        }

        assertEquals(expected.size(), list.size());

        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), list.get(i));
        }
    }
}