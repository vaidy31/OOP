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
 * Реализация графа через список смежности
 */
public class AdjacencyListGraph implements Graph {
    /** Список смежности: вершина -> список соседей */
    private Map<Integer, List<Integer>> adjList = new HashMap<>();
    /** Множество всех вершин */
    private Set<Integer> vertexSet = new HashSet<>();
    /** Общее количество вершин в графе */

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
        addVertex(from);
        addVertex(to);
        List<Integer> neighbors = adjList.get(from);
        if (!neighbors.contains(to)) {
            neighbors.add(to);
            return 1;
        }
        return 0;
    }


    @Override
    public void deleteEdge(int from, int to) {
        if (adjList.containsKey(from)) {
            List<Integer> neighbors = adjList.get(from);
            neighbors.remove(Integer.valueOf(to));
        }
    }


    /**
     * Читает граф из файла
     * @param file путь к файлу
     * @throws IOException при ошибках чтения
     */
    @Override
    public void readGraphFromFile(String file) throws IOException {
        File newFile = new File(file);
        FileReader fr = new FileReader(newFile);
        String line;


        int countVertexGlobal = 0;
        int countVertexLocal = 0;
        int numString = 0;


        try (BufferedReader readerString = new BufferedReader(fr)) {
            String initLine = readerString.readLine();
            // основываясь на соглашении, что
            // первая строка содержит количество вершин

            // Указано ли число вершин в первой строке
            try {
                countVertexGlobal = Integer.parseInt(initLine);
            } catch (NumberFormatException e) {
                System.out.println("Первая строка не содержит число вершин — граф строится по факту появления вершин из ребер.");
                countVertexGlobal = 0;

                String[] verteciesInString = initLine.split("\\s+");
                if (verteciesInString.length == 2) {
                    try {
                        int fromVert = Integer.parseInt(verteciesInString[0]);
                        int toVert = Integer.parseInt(verteciesInString[1]);
                        addEdge(fromVert, toVert);
                    } catch (NumberFormatException ex) {
                        System.err.println("Ошибка формата в первой строке.");
                    }
                }
            }
            while ((line = readerString.readLine()) != null) {
                String[] verteciesInString = line.split("\\s+");
                numString++;

                if (verteciesInString.length != 2) {
                    System.err.println("Нестандартное количество вершин в строке " + numString);
                    continue;
                }

                int fromVert, toVert;
                try {
                    fromVert = Integer.parseInt(verteciesInString[0]);
                    toVert = Integer.parseInt(verteciesInString[1]);
                } catch (NumberFormatException e) {
                    System.err.println("Проблема формата в строке " + numString);
                    continue;
                }

                if (fromVert < 0 ||toVert < 0){
                    System.err.println("Нестандартный номер вершины в строке " + numString + "пропускаем...");
                    continue;
                }

                addEdge(fromVert, toVert);
            }

            if (vertexSet.size() > countVertexGlobal) {
                System.out.println("Количество вершин в начале файле было указано неверно, размеры были увеличены");
            }
        } catch (NumberFormatException e) {
            System.err.println("Проблема формата в строке " + numString);
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
