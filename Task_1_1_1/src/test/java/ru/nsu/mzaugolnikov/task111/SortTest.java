package ru.nsu.mzaugolnikov.task111;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

/**
 * Test class for Task111.
 */
class SortTests {
    @Test
    void testDefault() {
        int[] array = new int[]{1, 3, 2};
        int[] result = task111.heapsort(array);
        assertArrayEquals(new int[]{1, 2, 3}, result);
    }

    @Test
    void testDefault2() {
        int[] array = new int[]{10, 2, 3, 65, 123, 11, 11};
        int[] result = task111.heapsort(array);
        assertArrayEquals(new int[]{2, 3, 10, 11, 11, 65, 123}, result);
    }

    @Test
    void testReversedNMax() {
        int[] array = new int[]{10, 9, 8, 7, 6, 5, 11};
        int[] result = task111.heapsort(array);
        assertArrayEquals(new int[]{5, 6, 7, 8, 9, 10, 11}, result);
    }

    @Test
    void testAllSorted() {
        int[] array = new int[]{1, 2, 3, 4, 5};
        int[] result = task111.heapsort(array);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, result);
    }

    @Test
    void testEmpty() {
        int[] array = new int[]{};
        int[] result = task111.heapsort(array);
        assertArrayEquals(new int[]{}, result);
    }

    @Test
    void testSame() {
        int[] array = new int[]{5, 5, 5, 5};
        int[] result = task111.heapsort(array);
        assertArrayEquals(new int[]{5, 5, 5, 5}, result);
    }

    @Test
    void testNegative() {
        int[] array = new int[]{-2, 6, 123, -23, 0};
        int[] result = task111.heapsort(array);
        assertArrayEquals(new int[]{-23, -2, 0, 6, 123}, result);
    }
}