package ru.nsu.mzaugolnikov.task121;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Реализация графа через список смежности.
 */
public class AdjacencyListGraph implements Graph {
    /** Список смежности: вершина -> список соседей. */
    private Map<Integer, List<Integer>> adjList = new HashMap<>();
    /** Множество всех вершин. */
    private Set<Integer> vertexSet = new HashSet<>();

    @Override
    public void addVertex(Integer v) {
        if (v == null) {
            return;
        }
        if (!vertexSet.contains(v)) {
            vertexSet.add(v);
            adjList.put(v, new ArrayList<>());
        }
    }

    @Override
    public void deleteVertex(int v) {
        if (adjList.containsKey(v)) {
            for (List<Integer> neighbors : adjList.values()) {
                neighbors.remove((Integer) v);
            }
            adjList.remove(v);
            vertexSet.remove(v);
        }
    }


    @Override
    public int addEdge(int from, int to) {
        int count = 0;
        if (!vertexSet.contains(from)) {
            addVertex(from);
            count++;
        }
        if (!vertexSet.contains(to)) {
            addVertex(to);
            count++;
        }
        List<Integer> neighbors = adjList.get(from);
        if (!neighbors.contains(to)) {
            neighbors.add(to);
        }
        return count;
    }


    @Override
    public void deleteEdge(int from, int to) {
        if (adjList.containsKey(from)) {
            List<Integer> neighbors = adjList.get(from);
            neighbors.remove(Integer.valueOf(to));
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Integer v : vertexSet) {
            sb.append(v).append(": ");
            List<Integer> neighbors = adjList.get(v);
            if (neighbors != null) {
                for (Integer u : neighbors) {
                    sb.append(u).append(" ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertexSet, adjList);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AdjacencyListGraph other = (AdjacencyListGraph) obj;

        return Objects.equals(vertexSet, other.vertexSet)
                && Objects.equals(adjList, other.adjList);
    }

    @Override
    public Set<Integer> vertexSet() {
        return new HashSet<>(adjList.keySet());
    }

    @Override
    public List<Integer> adjVertexList(int v) {
        return adjList.getOrDefault(v, Collections.emptyList());
    }

}
