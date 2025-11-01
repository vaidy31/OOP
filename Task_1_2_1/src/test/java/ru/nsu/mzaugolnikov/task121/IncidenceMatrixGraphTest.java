package ru.nsu.mzaugolnikov.task121;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

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

    @Test
    void testEquals_SameObject() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);

        assertTrue(graph.equals(graph));
    }

    @Test
    void testEquals_NotSameObject() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();
        IncidenceMatrixGraph graph2 = new IncidenceMatrixGraph();
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);

        graph2.addEdge(2, 5);
        graph2.addEdge(5, 8);

        assertFalse(graph2.equals(graph));
    }

    @Test
    void testEmpty() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.deleteEdge(1, 2);
        graph.deleteEdge(2, 3);

        for (int v : graph.vertexSet()) {
            assertTrue(graph.adjVertexList(v).isEmpty());
        }
    }


    @Test
    void test_DeleteVertex() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addEdge(2, 3);

        assertTrue(graph.vertexSet().contains(2));
        assertTrue(graph.vertexSet().contains(3));

        graph.deleteVertex(2);

        assertFalse(graph.vertexSet().contains(2));

        assertTrue(graph.adjVertexList(1).isEmpty());
    }

    @Test
    void test_Equals() {
        IncidenceMatrixGraph graph1 = new IncidenceMatrixGraph();
        IncidenceMatrixGraph graph2 = new IncidenceMatrixGraph();

        graph1.addEdge(1, 2);
        graph1.addEdge(2, 3);
        graph2.addEdge(1, 2);
        graph2.addEdge(2, 3);
        IncidenceMatrixGraph graph3 = new IncidenceMatrixGraph();
        graph3.addEdge(1, 2);

        assertEquals(graph1, graph2);
        assertNotEquals(graph1, graph3);
    }
}
