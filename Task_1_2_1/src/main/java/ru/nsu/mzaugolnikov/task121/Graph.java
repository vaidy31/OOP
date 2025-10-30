package ru.nsu.mzaugolnikov.task121;


import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface Graph {

    /**
     * Добавление вершины в граф.
     *
     * @param v номер вершины
     */
    void addVertex(Integer v);

    /**
     * Удаляет вершину из графа.
     *
     * @param v номер вершины
     */
    void deleteVertex(int v);

    /**
     * Добавляет ребро в граф.
     *
     * @param from откуда
     * @param to куда
     * @return количество вершин
     */
    int addEdge(int from, int to);

    /**
     * Удаляет ребро из графа.
     *
     * @param from откуда
     * @param to куда
     */
    void deleteEdge(int from, int to);

    /**
     * Список соседей для вершины.
     *
     * @param v вершина
     * @return список вершин
     */
    List<Integer> adjVertexList(int v);

    /**
     * Читает ребра из файла.
     *
     * @param file какой-то файл
     * @throws IOException кидает исключение
     */
    void readGraphFromFile(String file) throws IOException;

    /**
     * Hash.
     *
     * @return возвращает хэш
     */
    int hashCode();

    /**
     * Сет из вершин.
     *
     * @return неповторяющиеся вершины из сета
     */
    Set<Integer> vertexSet();
}
