package ru.nsu.mzaugolnikov.task121;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;



import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;
/**
 * Тест для сортировки алгоритмом Кана.
 */
public class TopologicSortTest {

    @Test
    void testTopologicSort() throws IOException {
        // Создаем временный файл с графом
        File tempFile = File.createTempFile("graph_goidatest", ".txt");
        try (FileWriter wr = new FileWriter(tempFile)) {
            wr.write("1\n"); // количество вершин указываем неверно
            // для проверки расширяемости объектов хранения данных
            wr.write("1 2\n");
            wr.write("1 3\n");
            wr.write("2 3\n");
        }
        Graph[] graphs = new Graph[]{
            new AdjacencyListGraph(),
            new AdjacencyMatrixGraph(),
            new IncidenceMatrixGraph()
        };

        String expected = "1: 2 3 \n2: 3 \n3: \n";

        for (Graph graph : graphs) {
            graph.readGraphFromFile(tempFile.getAbsolutePath());
            assertEquals(expected, graph.toString());

            List<Integer> sorted = TopologicSort.sort(graph);
            assert sorted.indexOf(1) < sorted.indexOf(2);
            assert sorted.indexOf(1) < sorted.indexOf(3);
            assert sorted.indexOf(2) < sorted.indexOf(3);
        }

        tempFile.delete();
    }

    @Test
    void testTopologicSortWithCycle() throws IOException {
        // Создаем временный файл с графом
        File tempFile = File.createTempFile("graph_goidatest", ".txt");
        try (FileWriter wr = new FileWriter(tempFile)) {
            wr.write("1\n"); // количество вершин указываем неверно
            // для проверки расширяемости объектов хранения данных
            wr.write("1 2\n");
            wr.write("2 3\n");
            wr.write("3 1\n");
        }
        Graph[] graphs = new Graph[]{
            new AdjacencyListGraph(),
            new AdjacencyMatrixGraph(),
            new IncidenceMatrixGraph()
        };

        for (Graph graph : graphs) {
            graph.readGraphFromFile(tempFile.getAbsolutePath());
            assertThrows(IllegalArgumentException.class, () -> TopologicSort.sort(graph));
        }
        tempFile.delete();
    }

    @Test
    void testTopologicSortStrangeAndSpooky() throws IOException {
        // Создаем временный файл с графом
        File tempFile = File.createTempFile("graph_goidatest", ".txt");
        try (FileWriter wr = new FileWriter(tempFile)) {
            wr.write("1\n"); // количество вершин указываем неверно
            // для проверки расширяемости объектов хранения данных
            wr.write("a 2\n");
            wr.write("1 b\n");
            wr.write("2 3c\n");
        }
        Graph[] graphs = new Graph[]{
                new AdjacencyListGraph(),
                new AdjacencyMatrixGraph(),
                new IncidenceMatrixGraph()
        };

        for (Graph graph : graphs) {
            graph.readGraphFromFile(tempFile.getAbsolutePath());
            assertTrue(graph.vertexSet().isEmpty());
        }
        tempFile.delete();
    }
}