package ru.nsu.mzaugolnikov.task121;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Тест для класса IncMatGraph.
 */
class IncidenceMatrixGraphTest {

    @Test
    void testReadGraphFromFile() throws IOException {
        // Создаем временный файл с графом
        File tempFile = File.createTempFile("graph_test", ".txt");

        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("-1\n"); // количество вершин
            writer.write("10 20\n");
            writer.write("20 30\n");
            writer.write("10 30\n");
        }

        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();
        graph.readGraphFromFile(tempFile.getAbsolutePath());

        List<Integer> neighbors10 = graph.adjVertexList(10);
        assertEquals(2, neighbors10.size());
        assertTrue(neighbors10.contains(20));
        assertTrue(neighbors10.contains(30));

        List<Integer> neighbors20 = graph.adjVertexList(20);
        assertEquals(1, neighbors20.size());
        assertTrue(neighbors20.contains(30));

        List<Integer> neighbors30 = graph.adjVertexList(30);
        assertEquals(0, neighbors30.size());

        tempFile.delete();
    }
}
