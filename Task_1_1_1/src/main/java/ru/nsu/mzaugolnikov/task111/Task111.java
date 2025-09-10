package ru.nsu.mzaugolnikov.task111;

/**
 * Сортировка кучей (пирамидальная сортировка).
 * Сложность O(n) buildMaxheap * O(log n) heapify.
 */
public class Task111 {
    /**
     * Класс для выполнения условия родитель > ребёнок.
     *
     * @param obj массив -> в кучу
     * @param n количество элементов в массиве
     * @param i -ый элемент, который рассматриваем
     */
    private static void heapify(final int[] obj, final int n, final int i) {
        int largest = i;
        int leftEl = i * 2 + 1;
        int rightEl = i * 2 + 2;

        if (leftEl < n && obj[leftEl] > obj[largest]) { // если ребенок больше родителя
            largest = leftEl;
        }

        if (rightEl < n && obj[rightEl] > obj[largest]) {
            largest = rightEl;
        }

        if (largest != i) { // рекурсивный вызов (swap)
            int tempEl = obj[i];
            obj[i] = obj[largest];
            obj[largest] = tempEl;
            heapify(obj, n, largest);
        }
    }

    /**
     * Строит кучу, беря только узлы, а не листья.
     *
     * @param obj массив для построения кучи
     * @param n количество элементов в массиве
     */
    private static void buildMaxHeap(final int[] obj, final int n) {
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(obj, n, i);
        }
    }

    /**
     * Главный метод для сортировки, изменяет исходный массив.
     *
     * @param obj I/O массив для сортировки
     */
    public static void heapsort(int[] obj) {
        int n = obj.length;
        buildMaxHeap(obj, n);

        for (int i = n - 1; i > 0; i--) {
            int temp = obj[0];
            obj[0] = obj[i];
            obj[i] = temp;

            heapify(obj, i, 0);
        }
    }

    /**
     * Main method.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
    }
}