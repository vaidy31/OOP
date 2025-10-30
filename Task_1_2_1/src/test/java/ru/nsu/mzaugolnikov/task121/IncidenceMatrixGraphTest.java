package ru.nsu.mzaugolnikov.task121;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IncidenceMatrixGraphTest {

    @Test
    void testReadGraphFromFile() throws IOException {
        // Создаем временный файл
        File tempFile = File.createTempFile("graph_test", ".txt");

        try (FileWriter wr = new FileWriter(tempFile)) {
            wr.write("2\n"); // количество вершин
            wr.write("1 2\n");
            wr.write("2 3\n");
            wr.write("1 3\n");
        }

        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();
        graph.readGraphFromFile(tempFile.getAbsolutePath());

        List<Integer> adjVertices1 = graph.adjVertexList(1);
        assertEquals(2, adjVertices1.size());
        assertTrue(adjVertices1.contains(2));
        assertTrue(adjVertices1.contains(3));

        List<Integer> adjVertices2 = graph.adjVertexList(2);
        assertEquals(1, adjVertices2.size());
        assertTrue(adjVertices2.contains(3));
        tempFile.delete();
    }


}