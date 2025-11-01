package ru.nsu.mzaugolnikov.task121;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Тест для класса AdjListGraph.
 */
class AdjacencyListGraphTest {

    @Test
    void testReadGraphFromFile() throws IOException {
        File tempFile = File.createTempFile("graph_test", ".txt");

        try (FileWriter wr = new FileWriter(tempFile)) {
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

    @Test
    void testEquals_SameObject() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);

        assertTrue(graph.equals(graph));
    }

    @Test
    void testEquals_NotSameObject() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        AdjacencyListGraph graph2 = new AdjacencyListGraph();
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);

        graph2.addEdge(2, 5);
        graph2.addEdge(5, 8);

        assertFalse(graph2.equals(graph));
    }

    @Test
    void testEmpty() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
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
        AdjacencyListGraph graph = new AdjacencyListGraph();
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
    void test_HashCodeAndEquals() {
        AdjacencyListGraph graph1 = new AdjacencyListGraph();
        AdjacencyListGraph graph2 = new AdjacencyListGraph();

        graph1.addEdge(1, 2);
        graph1.addEdge(2, 3);
        graph2.addEdge(1, 2);
        graph2.addEdge(2, 3);
        AdjacencyListGraph graph3 = new AdjacencyListGraph();
        graph3.addEdge(1, 2);

        assertEquals(graph1, graph2);
        assertEquals(graph1.hashCode(), graph2.hashCode());
        assertNotEquals(graph1, graph3);
    }
}
