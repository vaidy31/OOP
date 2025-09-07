package ru.nsu.mzaugolnikov.Task_1_1_1;

/**
 * сортировка кучей (пирамидальная сортировка (минадльная хаха))
 * сложность  O(n) buildMaxheap * O(log n) heapify
 */
public class Task_1_1_1 {
    /**
     * класс для выполнения условия родитель > ребёнок
     * @param obj массив -> в кучу
     * @param n количество элементов в массиве
     * @param i -ый элемент, который рассматриваем
     */
    public void heapify(int[] obj, int n, int i){
        int largest = i;
        int left_el = i * 2 + 1;
        int right_el = i * 2 + 2;

        if (left_el < n && obj[left_el] > obj[largest]){ // если ребенок больше родителя
            largest = left_el;
        }

        if ( right_el < n && obj[right_el] > obj[largest]) {
            largest = right_el;
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
    public void buildMaxHeap(int[] obj, int n){
        for (int i = n / 2 - 1; i >= 0; i--){
            heapify(obj, n, i);
        }
    }

    /**
     * главный метод для сортировки, изменяет исходный массив
     * @param obj исходный массив для сортировки
     * @return возвращает отсортированный массив
     */
    public int[] heapsort(int[] obj) {
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
}
