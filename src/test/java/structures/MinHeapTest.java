package structures;

import org.junit.jupiter.api.Test;

import java.util.PriorityQueue;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class MinHeapTest {

    @Test
    void insertAndPeekMin() {
        MinHeap heap = new MinHeap();

        heap.insert(30);
        heap.insert(10);
        heap.insert(20);

        assertEquals(3, heap.size());
        assertEquals(10, heap.peekMin());
    }

    @Test
    void extractMinReturnsSmallestElement() {
        MinHeap heap = new MinHeap();

        heap.insert(30);
        heap.insert(10);
        heap.insert(20);

        assertEquals(10, heap.extractMin());
        assertEquals(2, heap.size());
        assertEquals(20, heap.peekMin());
    }

    @Test
    void duplicateValues() {
        MinHeap heap = new MinHeap();

        heap.insert(10);
        heap.insert(10);
        heap.insert(5);
        heap.insert(5);

        assertEquals(5, heap.extractMin());
        assertEquals(5, heap.extractMin());
        assertEquals(10, heap.extractMin());
        assertEquals(10, heap.extractMin());
    }

    @Test
    void oneElementHeap() {
        MinHeap heap = new MinHeap();

        heap.insert(42);

        assertEquals(42, heap.peekMin());
        assertEquals(42, heap.extractMin());
        assertEquals(0, heap.size());
    }

    @Test
    void emptyHeapThrowsException() {
        MinHeap heap = new MinHeap();

        assertThrows(IllegalStateException.class, heap::peekMin);
        assertThrows(IllegalStateException.class, heap::extractMin);
    }

    @Test
    void growsWhenCapacityIsFull() {
        MinHeap heap = new MinHeap();

        for (int i = 100; i >= 0; i--) {
            heap.insert(i);
        }

        assertEquals(101, heap.size());
        assertEquals(0, heap.peekMin());
    }

    @Test
    void extractedValuesAreNonDecreasing() {
        MinHeap heap = new MinHeap();
        Random random = new Random(42);

        for (int i = 0; i < 1000; i++) {
            heap.insert(random.nextInt(10000));
        }

        int previous = heap.extractMin();

        while (heap.size() > 0) {
            int current = heap.extractMin();

            assertTrue(previous <= current);

            previous = current;
        }
    }

    @Test
    void compareWithPriorityQueueOnRandomData() {
        MinHeap heap = new MinHeap();
        PriorityQueue<Integer> expected = new PriorityQueue<>();
        Random random = new Random(42);

        for (int i = 0; i < 1000; i++) {
            int value = random.nextInt(10000);

            heap.insert(value);
            expected.add(value);

            assertEquals(expected.peek().intValue(), heap.peekMin());
        }

        while (!expected.isEmpty()) {
            assertEquals(expected.poll().intValue(), heap.extractMin());
        }

        assertEquals(0, heap.size());
    }

    @Test
    void heapWorksWithNegativeValues() {
        MinHeap heap = new MinHeap();

        heap.insert(-5);
        heap.insert(10);
        heap.insert(-20);
        heap.insert(0);

        assertEquals(-20, heap.extractMin());
        assertEquals(-5, heap.extractMin());
        assertEquals(0, heap.extractMin());
        assertEquals(10, heap.extractMin());
    }
}