package ru.nsu.mzaugolnikov.task121;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class TopSortTest {

    @Test
    void testTopSortSimpleGraph() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        // создаём граф: 1 -> 2 -> 3, 1 -> 3
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(1, 3);

        List<Integer> sorted = TopologicSort.topSort(graph);

        // Проверяем порядок: 1 перед 2 и 3, 2 перед 3
        int pos1 = sorted.indexOf(1);
        int pos2 = sorted.indexOf(2);
        int pos3 = sorted.indexOf(3);

        assertTrue(pos1 < pos2, "1 должно идти перед 2");
        assertTrue(pos1 < pos3, "1 должно идти перед 3");
        assertTrue(pos2 < pos3, "2 должно идти перед 3");
    }

    @Test
    void testTopSortEmptyGraph() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        List<Integer> sorted = TopologicSort.topSort(graph);
        assertTrue(sorted.isEmpty(), "Сортировка пустого графа должна возвращать пустой список");
    }

    @Test
    void testTopSortSingleVertex() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.addVertex(5);
        List<Integer> sorted = TopologicSort.topSort(graph);
        assertEquals(1, sorted.size());
        assertEquals(5, sorted.get(0));
    }

    @Test
    void testTopSortCycleGraph() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 1); // цикл

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            TopologicSort.topSort(graph);
        });
        assertTrue(exception.getMessage().contains("цикл"));
    }
}
