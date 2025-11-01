package ru.nsu.mzaugolnikov.task121;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import org.junit.jupiter.api.Test;

class GraphTest {
    @Test
    void testReadFileException() {
        Graph graph = new AdjacencyListGraph();
        assertThrows(IOException.class, () -> graph.readGraphFromFile("golda.txt"));
    }
}