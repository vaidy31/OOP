package ru.nsu.mzaugolnikov.task121;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Тест для класса AdjListGraph.
 */
class AdjacencyListGraphTest {

    @Test
    void testReadGraphFromFile() throws IOException {
        File tempFile = File.createTempFile("graph_test", ".txt");

        try (FileWriter wr = new FileWriter(tempFile)) {
            wr.write("1\n");
            wr.write("10 20\n");
            wr.write("20 30\n");
            wr.write("10 30\n");
        }

        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.readGraphFromFile(tempFile.getAbsolutePath());

        List<Integer> adjVertices10 = graph.adjVertexList(10);
        assertEquals(2, adjVertices10.size());
        assertTrue(adjVertices10.contains(20));
        assertTrue(adjVertices10.contains(30));

        List<Integer> adjVertices20 = graph.adjVertexList(20);
        assertEquals(1, adjVertices20.size());
        assertTrue(adjVertices20.contains(30));

        tempFile.delete();
    }
}
