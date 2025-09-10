package ru.nsu.mzaugolnikov.task111;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Random;
import org.junit.jupiter.api.Test;

/**
 * Test class for Task111.
 */
class SortTest {
    @Test
    void testDefault() {
        int[] array = new int[]{1, 3, 2};
        Task111.heapsort(array);
        assertArrayEquals(new int[]{1, 2, 3}, array);
    }

    @Test
    void testDefault2() {
        int[] array = new int[]{10, 2, 3, 65, 123, 11, 11};
        Task111.heapsort(array);
        assertArrayEquals(new int[]{2, 3, 10, 11, 11, 65, 123}, array);
    }

    @Test
    void testReversed() {
        int[] array = new int[]{10, 9, 8, 7, 6, 5, 11};
        Task111.heapsort(array);
        assertArrayEquals(new int[]{5, 6, 7, 8, 9, 10, 11}, array);
    }

    @Test
    void testAllSorted() {
        int[] array = new int[]{1, 2, 3, 4, 5};
        Task111.heapsort(array);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, array);
    }

    @Test
    void testEmpty() {
        int[] array = new int[]{};
        Task111.heapsort(array);
        assertArrayEquals(new int[]{}, array);
    }

    @Test
    void testSame() {
        int[] array = new int[]{5, 5, 5, 5};
        Task111.heapsort(array);
        assertArrayEquals(new int[]{5, 5, 5, 5}, array);
    }

    @Test
    void testNegative() {
        int[] array = new int[]{-2, 6, 123, -23, 0};
        Task111.heapsort(array);
        assertArrayEquals(new int[]{-23, -2, 0, 6, 123}, array);
    }

    @Test
    void testTimeBig() {
        int[] arrayOfSize = new int[]{250000, 500000, 750000, 1000000, 2500000, 5000000, 7500000, 10000000};
        Random random = new Random();
        for (int i = 0; i < arrayOfSize.length; i++) {
            int size = arrayOfSize[i];
            int[] arrayToSort = new int[size];
            for (int j = 0; j < size; j++) {
                arrayToSort[j] = random.nextInt();
            }
            long start = System.nanoTime();
            Task111.heapsort(arrayToSort);
            long end = System.nanoTime();
            long time = end - start;
            /*
            if (i == 0) {
                System.out.println("size: " + size + ", time: " + time  / 1000 + " mcs");
            }
            else {
                System.out.println("size: " + size + ", time: " + time / 1000000 + " ms");
            }
            */
            for (int k = 0; k < size - 1; k++) {
                if (arrayToSort[k] > arrayToSort[k + 1]) {
                    throw new AssertionError("GG, your code is trash :(");
                }
            }
        }

    }
}