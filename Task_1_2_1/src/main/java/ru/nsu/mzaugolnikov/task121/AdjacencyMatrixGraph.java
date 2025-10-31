package ru.nsu.mzaugolnikov.task121;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Реализация интерфейса через матрицу смежности.
 */
public class AdjacencyMatrixGraph implements Graph {
    /** Матрица смежности графа. */
    private int[][] graph;
    /** Сопоставление индексов массива с идентификаторами вершин. */
    private final List<Integer> indexToVertex = new ArrayList<>();
    /** Сопоставление идентификаторов вершин с индексами в массиве. */
    private final Map<Integer, Integer> vertexMap;

    public AdjacencyMatrixGraph() {
        vertexMap = new HashMap<>();
    }

    /**
     * Добавляет вершину в граф.
     *
     * @param v идентификатор добавляемой вершины
     */
    @Override
    public void addVertex(Integer v) {
        if (v == null) {
            return;
        }
        if (!vertexMap.containsKey(v)) {
            int newIndex = indexToVertex.size();
            if (graph == null || newIndex >= graph.length) {
                updateCapacity(newIndex + 1);
            }
            vertexMap.put(v, newIndex);
            indexToVertex.add(v);
        }
    }


    /**
     * Удаляет вершину из графа.
     *
     * @param v идентификатор удаляемой вершины
     */
    @Override
    public void deleteVertex(int v) {
        Integer index = vertexMap.remove(v);
        if (index == null) {
            return;
        }
        int lastIndex = indexToVertex.size() - 1;
        Integer lastVertex = indexToVertex.get(lastIndex);
        if (index != lastIndex) {
            for (int i = 0; i < graph.length; i++) {
                graph[index][i] = graph[lastIndex][i];
                graph[i][index] = graph[i][lastIndex];
            }
            vertexMap.put(lastVertex, index);
            indexToVertex.set(index, lastVertex);
        }

        for (int i = 0; i < graph.length; i++) {
            graph[lastIndex][i] = 0;
            graph[i][lastIndex] = 0;
        }
        indexToVertex.remove(lastIndex);
    }


    /**
     * Добавляет направленное ребро.
     *
     * @param from начальная вершина
     * @param to конечная вершина
     */
    @Override
    public int addEdge(int from, int to) {
        Integer fromIndex = vertexMap.get(from);
        Integer toIndex = vertexMap.get(to);
        int count = 0;
        if (fromIndex == null) {
            addVertex(from);
            fromIndex = vertexMap.get(from);
            count++;
        }
        if (toIndex == null) {
            addVertex(to);
            toIndex = vertexMap.get(to);
            count++;
        }
        graph[fromIndex][toIndex] = 1;
        return count;
    }

    /**
     * Удаляет ребро между вершинами.
     *
     * @param from начальная вершина
     * @param to конечная вершина
     */
    @Override
    public void deleteEdge(int from, int to) {
        Integer fromIndex = vertexMap.get(from);
        Integer toIndex = vertexMap.get(to);
        if (fromIndex == null || toIndex == null) {
            return;
        }
        graph[fromIndex][toIndex] = 0;
    }

    /**
     * Возвращает список смежных вершин.
     *
     * @param v исходная вершина
     * @return список смежных вершин
     */
    @Override
    public List<Integer> adjVertexList(int v) {
        List<Integer> neighbors = new ArrayList<>();
        Integer index = vertexMap.get(v);
        if (index == null) {
            return neighbors; // вершины нет — возвращаем пустой список
        }

        for (int i = 0; i < indexToVertex.size(); i++) {
            Integer u = indexToVertex.get(i);
            if (u != null && graph[index][i] != 0) {
                neighbors.add(u);
            }
        }
        return neighbors;
    }

    /**
     * Читает граф из файла.
     *
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
            // Проверяем, указано ли число вершин в первой строке
            try {
                countVertexGlobal = Integer.parseInt(initLine);
            } catch (NumberFormatException e) {
                System.out.println("Первая строка не содержит число вершин — "
                        + "граф строится по факту появления вершин из ребер.");
                countVertexGlobal = 0;

                // Добавляем обработку этой строки как ребра:
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

                int fromVert;
                int toVert;
                try {
                    fromVert = Integer.parseInt(verteciesInString[0]);
                    toVert = Integer.parseInt(verteciesInString[1]);
                } catch (NumberFormatException e) {
                    System.err.println("Проблема формата в строке " + numString);
                    continue;
                }

                if (fromVert < 0 || toVert < 0){
                    System.err.println("Нестандартный номер вершины в строке "
                            + numString + "пропускаем...");
                    continue;
                }

                countVertexLocal += addEdge(fromVert, toVert);
            }

            if (countVertexLocal > countVertexGlobal && countVertexGlobal > 0) {
                System.out.println("Количество вершин в начале файле было"
                        + "указано неверно, размеры были увеличены");
            }
        } catch (NumberFormatException e) {
            System.err.println("Проблема формата в строке " + numString);
        }
    }

    /**
     * Возвращает строковое представление графа.
     *
     * @return строковое представление
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indexToVertex.size(); i++) {
            Integer v = indexToVertex.get(i);
            if (v == null) {
                continue;
            }
            sb.append(v).append(": ");
            for (int j = 0; j < indexToVertex.size(); j++) {
                Integer u = indexToVertex.get(j);
                if (u != null && graph[i][j] != 0) {
                    sb.append(u).append(" ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Вычисляет хеш-код графа.
     *
     * @return хеш-код
     */
    @Override
    public int hashCode() {
        return Objects.hash(indexToVertex,
                    vertexMap, Arrays.deepHashCode(graph));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AdjacencyMatrixGraph other = (AdjacencyMatrixGraph) obj;
        return Objects.equals(indexToVertex, other.indexToVertex)
                && Objects.equals(vertexMap, other.vertexMap)
                && Arrays.deepEquals(this.graph, other.graph);
    }

    private void updateCapacity(int requiredSize) {
        if (graph == null) {
            graph = new int[requiredSize][requiredSize];
            return;
        }
        if (requiredSize > graph.length) {
            int newSize = Math.max(graph.length * 2, requiredSize);
            int[][] newGraph = new int[newSize][newSize];
            for (int i = 0; i < graph.length; i++) {
                System.arraycopy(graph[i], 0, newGraph[i], 0, graph[i].length);
            }
            graph = newGraph;
        }
    }


    @Override
    public Set<Integer> vertexSet() {
        return new HashSet<>(vertexMap.keySet());
    }

}
