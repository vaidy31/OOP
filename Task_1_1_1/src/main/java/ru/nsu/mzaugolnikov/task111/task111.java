package ru.nsu.mzaugolnikov.task111;

/**
 * сортировка кучей (пирамидальная сортировка (минадльная хаха))
 * сложность  O(n) buildMaxheap * O(log n) heapify
 */
public class task111 {
    /**
     * класс для выполнения условия родитель > ребёнок
     * @param obj массив -> в кучу
     * @param n количество элементов в массиве
     * @param i -ый элемент, который рассматриваем
     */
    private static void heapify(int[] obj, int n, int i) {
        int largest = i;
        int leftel = i * 2 + 1;
        int rightel = i * 2 + 2;

        if (leftel < n && obj[leftel] > obj[largest]) { // если ребенок больше родителя
            largest = leftel;
        }

        if (rightel < n && obj[rightel] > obj[largest]) {
            largest = rightel;
        }

        if (largest != i){ // рекурсивный вызов (swap)
            int temp_el = obj[i];
            obj[i] = obj[largest];
            obj[largest] = temp_el;
            heapify(obj, n, largest);
        }
    }

    /**
     * строит кучу, беря только узлы, а не листья
     * @param obj массив для построения кучи
     * @param n количество элементов в массиве
     */
    private static void buildMaxHeap(int[] obj, int n) {
        for (int i = n / 2 - 1; i >= 0; i--){
            heapify(obj, n, i);
        }
    }

    /**
     * главный метод для сортировки, изменяет исходный массив
     * @param obj исходный массив для сортировки
     * @return возвращает отсортированный массив
     */
    public static int[] heapsort(int[] obj) {
        int n = obj.length;
        buildMaxHeap(obj, n);

        for (int i = n - 1; i > 0; i--) {
            int temp = obj[0];
            obj[0] = obj[i];
            obj[i] = temp;

            heapify(obj, i, 0);
        }
        return obj;
    }

    /**
     * main
     * @param args 0
     */
    public static void main(String[] args){
    }
}
