package structures;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class DynamicArrayTest {

    @Test
    void addAndGetElements() {
        DynamicArray array = new DynamicArray();

        array.add(10);
        array.add(20);
        array.add(30);

        assertEquals(3, array.size());
        assertEquals(10, array.get(0));
        assertEquals(20, array.get(1));
        assertEquals(30, array.get(2));
    }

    @Test
    void growArrayWhenCapacityIsFull() {
        DynamicArray array = new DynamicArray();

        for (int i = 0; i < 25; i++) {
            array.add(i);
        }

        assertEquals(25, array.size());

        for (int i = 0; i < 25; i++) {
            assertEquals(i, array.get(i));
        }
    }

    @Test
    void addAtIndex() {
        DynamicArray array = new DynamicArray();

        array.add(10);
        array.add(30);
        array.add(1, 20);

        assertEquals(3, array.size());
        assertEquals(10, array.get(0));
        assertEquals(20, array.get(1));
        assertEquals(30, array.get(2));
    }

    @Test
    void addAtFirstAndLastIndex() {
        DynamicArray array = new DynamicArray();

        array.add(10);
        array.add(20);

        array.add(0, 5);
        array.add(array.size(), 30);

        assertEquals(5, array.get(0));
        assertEquals(30, array.get(array.size() - 1));
    }

    @Test
    void removeElement() {
        DynamicArray array = new DynamicArray();

        array.add(10);
        array.add(20);
        array.add(30);

        int removed = array.remove(1);

        assertEquals(20, removed);
        assertEquals(2, array.size());
        assertEquals(10, array.get(0));
        assertEquals(30, array.get(1));
    }

    @Test
    void removeFirstAndLastElement() {
        DynamicArray array = new DynamicArray();

        array.add(10);
        array.add(20);
        array.add(30);

        assertEquals(10, array.remove(0));
        assertEquals(30, array.remove(array.size() - 1));
        assertEquals(1, array.size());
        assertEquals(20, array.get(0));
    }

    @Test
    void containsValues() {
        DynamicArray array = new DynamicArray();

        array.add(10);
        array.add(20);
        array.add(20);
        array.add(30);

        assertTrue(array.contains(20));
        assertTrue(array.contains(30));
        assertFalse(array.contains(100));
    }

    @Test
    void oneElementArray() {
        DynamicArray array = new DynamicArray();

        array.add(42);

        assertEquals(1, array.size());
        assertEquals(42, array.get(0));
        assertTrue(array.contains(42));
    }

    @Test
    void invalidGetIndex() {
        DynamicArray array = new DynamicArray();

        assertThrows(IndexOutOfBoundsException.class, () -> array.get(0));

        array.add(10);

        assertThrows(IndexOutOfBoundsException.class, () -> array.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> array.get(1));
    }

    @Test
    void invalidAddIndex() {
        DynamicArray array = new DynamicArray();

        assertThrows(IndexOutOfBoundsException.class, () -> array.add(-1, 10));
        assertThrows(IndexOutOfBoundsException.class, () -> array.add(1, 10));
    }

    @Test
    void invalidRemoveIndex() {
        DynamicArray array = new DynamicArray();

        assertThrows(IndexOutOfBoundsException.class, () -> array.remove(0));

        array.add(10);

        assertThrows(IndexOutOfBoundsException.class, () -> array.remove(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> array.remove(1));
    }

    @Test
    void compareWithArrayListOnRandomData() {
        DynamicArray array = new DynamicArray();
        ArrayList<Integer> expected = new ArrayList<>();
        Random random = new Random(42);

        for (int i = 0; i < 1000; i++) {
            int value = random.nextInt(10000);

            array.add(value);
            expected.add(value);
        }

        assertEquals(expected.size(), array.size());

        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), array.get(i));
        }
    }
}