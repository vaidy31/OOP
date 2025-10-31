package ru.nsu.mzaugolnikov.task121;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Топологическая сортировка методом Кана через методы графа.
 */
public class TopologicSort {

    /**
     * Выполняет топологическую сортировку графа.
     *
     * @param g граф
     * @return список вершин в топологическом порядке
     * @throws IllegalArgumentException если граф содержит цикл
     */
    public static List<Integer> sort(Graph g) {
        List<Integer> sorted = new ArrayList<>();
        Set<Integer> vertices = g.vertexSet();

        while (!vertices.isEmpty()) {
            Integer nextVertex = null;

            // ищем вершину без входящих рёбер
            for (Integer v : vertices) {
                boolean hasIncoming = false;
                for (Integer u : vertices) {
                    if (!u.equals(v) && g.adjVertexList(u).contains(v)) {
                        hasIncoming = true;
                        break;
                    }
                }
                if (!hasIncoming) {
                    nextVertex = v;
                    break;
                }
            }

            if (nextVertex == null) {
                throw new IllegalArgumentException("Граф содержит цикл");
            }

            sorted.add(nextVertex);

            // удаляем все исходящие рёбра
            List<Integer> neighbors = new ArrayList<>(g.adjVertexList(nextVertex));
            for (Integer neighbor : neighbors) {
                g.deleteEdge(nextVertex, neighbor);
            }

            // удаляем саму вершину
            g.deleteVertex(nextVertex);

            // обновляем множество вершин
            vertices = g.vertexSet();
        }
        return sorted;
    }
}
