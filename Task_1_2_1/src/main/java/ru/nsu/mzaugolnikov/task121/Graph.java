package ru.nsu.mzaugolnikov.task121;


import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface Graph {

    void addVertex(Integer v);

    void deleteVertex(int v);

    int addEdge(int from, int to);

    void deleteEdge(int from, int to);

    List<Integer> adjVertexList(int v);

    void readGraphFromFile(String file) throws IOException;

    int hashCode();

    Set<Integer> vertexSet();
}
