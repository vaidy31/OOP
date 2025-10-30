package ru.nsu.mzaugolnikov.task121;

import java.util.*;

public class TopologicSort {
    public static List<Integer> topSort(Graph g) {
        Set<Integer> allVertices = g.vertexSet();

        if (allVertices.isEmpty()) return new ArrayList<>();

        Map<Integer, Integer> indeg = new HashMap<>();
        for (Integer v : allVertices) indeg.put(v, 0);

        for (Integer v : allVertices) {
            for (Integer u : g.adjVertexList(v)) {
                indeg.put(u, indeg.get(u) + 1);
            }
        }

        LinkedList<Integer> zeroIn = new LinkedList<>();
        for (Map.Entry<Integer, Integer> entry : indeg.entrySet()) {
            if (entry.getValue() == 0) zeroIn.add(entry.getKey());
        }

        List<Integer> result = new ArrayList<>();
        while (!zeroIn.isEmpty()) {
            int current = zeroIn.removeFirst();
            result.add(current);
            for (Integer neighbor : g.adjVertexList(current)) {
                indeg.put(neighbor, indeg.get(neighbor) - 1);
                if (indeg.get(neighbor) == 0) zeroIn.add(neighbor);
            }
        }

        if (result.size() < allVertices.size()) {
            throw new IllegalArgumentException("Граф содержит цикл, сортировка невозможна");
        }

        return result;
    }

    private static Set<Integer> collectVertices(AdjacencyListGraph g) {
        Set<Integer> allVertices = new HashSet<>();
        for (Integer v : g.vertexSet()) {      // <-- здесь реальные вершины графа
            allVertices.add(v);
            allVertices.addAll(g.adjVertexList(v));
        }
        return allVertices;
    }


}
