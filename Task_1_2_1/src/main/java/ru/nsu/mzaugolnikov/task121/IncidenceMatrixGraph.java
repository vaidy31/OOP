package ru.nsu.mzaugolnikov.task121;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Реализация графа через матрицу инцидентности.
 */
public class IncidenceMatrixGraph implements Graph {
    private int[][] matrix; // матрица индцедентности 1 ребро 0 нет
    Map<Integer, Integer> vertexMapForIndex; // вершина в индекс строки матрицы
    private int countVertexGlobal; // количество вершин
    private List<Edge> edges = new ArrayList<>(); // для ребер

    private static class Edge {
        int from;
        int to;

        Edge(int from, int to) {
            this.from = from;
            this.to = to;
        }
    }

    /**
     * Конструктор.
     */
    public IncidenceMatrixGraph() {
        vertexMapForIndex = new HashMap<>();
        matrix = new int[0][0];
        countVertexGlobal = 0;
    }

    @Override
    public void addVertex(Integer v) {
        if (!vertexMapForIndex.containsKey(v)) {
            vertexMapForIndex.put(v, countVertexGlobal);
            countVertexGlobal++;
            updateCapacity(countVertexGlobal);
        }
    }

    /**
     * Увлечивает размер матрицы.
     *
     * @param requiredSize размер для увелечения
     */
    public void updateCapacity(int requiredSize) {
        if (matrix == null) {
            matrix = new int[requiredSize][requiredSize];
            return;
        }
        if (requiredSize > matrix.length) {
            int newSize = Math.max(matrix.length * 2, requiredSize);
            int[][] newMatrix = new int[newSize][newSize];
            for (int i = 0; i < matrix.length; i++) {
                System.arraycopy(matrix[i], 0, newMatrix[i], 0, matrix[i].length);
            }
            matrix = newMatrix;
        }
    }

    @Override
    public void deleteVertex(int v) {
        if (!vertexMapForIndex.containsKey(v)) {
            return;
        }

        int indexToRemove = vertexMapForIndex.get(v);

        List<Edge> edgesToRemove = new ArrayList<>();
        for (Edge edge : edges) {
            if (edge.from == v || edge.to == v) {
                edgesToRemove.add(edge);
            }
        }

        for (int i = edgesToRemove.size() - 1; i >= 0; i--) {
            Edge edge = edgesToRemove.get(i);
            deleteEdge(edge.from, edge.to);
        }

        if (countVertexGlobal > 1) {
            int newRowCount = countVertexGlobal - 1;
            int colCount = matrix[0].length;
            int[][] newMatrix = new int[newRowCount][colCount];
            int newRow = 0;
            for (int oldRow = 0; oldRow < countVertexGlobal; oldRow++) {
                if (oldRow == indexToRemove) {
                    continue;
                }
                System.arraycopy(matrix[oldRow], 0, newMatrix[newRow], 0, colCount);
                newRow++;
            }

            matrix = newMatrix;
        } else {
            matrix = new int[0][0];
            edges.clear();
        }
        vertexMapForIndex.remove(v);
        Map<Integer, Integer> newVertexMap = new HashMap<>();
        int newIndex = 0;
        for (Integer vertex : vertexMapForIndex.keySet()) {
            newVertexMap.put(vertex, newIndex);
            newIndex++;
        }
        vertexMapForIndex = newVertexMap;
        countVertexGlobal--;
    }


    @Override
    public int addEdge(int from, int to) {
        int addedVertices = 0;
        if (!vertexMapForIndex.containsKey(from)) {
            addVertex(from);
            addedVertices++;
        }
        if (!vertexMapForIndex.containsKey(to)) {
            addVertex(to);
            addedVertices++;
        }

        int maxIndex = Math.max(vertexMapForIndex.get(from), vertexMapForIndex.get(to));
        if (matrix.length <= maxIndex) {
            updateCapacity(maxIndex + 1);
        }

        int oldCols = (matrix.length > 0) ? matrix[0].length : 0;
        int newCols = oldCols + 1;

        int[][] newMatrix = new int[matrix.length][newCols];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                newMatrix[i][j] = matrix[i][j];
            }
        }
        int fromIndex = vertexMapForIndex.get(from);
        int toIndex = vertexMapForIndex.get(to);
        newMatrix[fromIndex][newCols - 1] = -1;
        newMatrix[toIndex][newCols - 1] = 1;

        matrix = newMatrix;
        edges.add(new Edge(from, to));
        return addedVertices;
    }

    @Override
    public void deleteEdge(int from, int to) {
        int tempInd = -1;
        for (int i = 0; i < edges.size(); i++) {
            Edge e = edges.get(i);
            if (e.from == from && e.to == to) {
                tempInd = i;
                break;
            }
        }
        if (tempInd == -1) {
            System.out.println("Нет такого ребра для удаления : " + from + " до " + to);
            return;
        }

        int tempColsOld = matrix[0].length;
        int tempColsNew = tempColsOld - 1;

        int[][] newMatrix = new int[countVertexGlobal][tempColsNew];

        for (int i = 0; i < countVertexGlobal; i++) {
            int temp = 0;
            for (int j = 0; j < tempColsOld; j++) {
                if (j == tempInd) {
                    continue;
                }
                newMatrix[i][temp++] = matrix[i][j];
            }
        }
        matrix = newMatrix;
        edges.remove(tempInd);
    }


    @Override
    public List<Integer> adjVertexList(int v) {
        List<Integer> neighbors = new ArrayList<>();
        Integer vertexIndex = vertexMapForIndex.get(v);
        if (vertexIndex == null) {
            return neighbors;
        }
        for (int j = 0; j < edges.size(); j++) {
            Edge e = edges.get(j);
            if (e.from == v) {
                neighbors.add(e.to);
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
                int declaredVertexCount = Integer.parseInt(initLine);
                updateCapacity(declaredVertexCount);

                while ((line = readerString.readLine()) != null) {
                    String[] verteciesInString = line.split("\\s+");
                    numString++;

                    if (verteciesInString.length != 2) {
                        System.err.println("Нестандартное количество вершин в строке "
                                + numString);
                        continue;
                    }

                    int fromVert;
                    int toVert;
                    try {
                        fromVert = Integer.parseInt(verteciesInString[0]);
                        toVert = Integer.parseInt(verteciesInString[1]);
                    } catch (NumberFormatException e) {
                        System.err.println("Проблема формата в строке "
                                + numString);
                        continue;
                    }

                    if (fromVert < 0 || toVert < 0) {
                        System.err.println("Нестандартный номер вершины в строке "
                                + numString + "пропускаем...");
                        continue;
                    }

                    addEdge(fromVert, toVert);
                }

                if (this.countVertexGlobal > declaredVertexCount) {
                    System.out.println("Количество вершин в начале файле было"
                            + "указано неверно, размеры были увеличены");
                }

            } catch (NumberFormatException e) {
                System.err.println("Первая строка не содержит число вершин —"
                        + "граф строится по факту появления вершин из ребер.");
            }

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
        for (Integer v : vertexMapForIndex.keySet()) {
            sb.append(v).append(": ");
            List<Integer> neighbors = adjVertexList(v);
            for (Integer u : neighbors) {
                sb.append(u).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertexMapForIndex.keySet(), edges);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        IncidenceMatrixGraph other = (IncidenceMatrixGraph) obj;

        Set<String> thisEdges = new HashSet<>();
        for (Edge e : edges) {
            thisEdges.add(e.from + "->" + e.to);
        }
        Set<String> otherEdges = new HashSet<>();
        for (Edge e : other.edges) {
            otherEdges.add(e.from + "->" + e.to);
        }

        return Objects.equals(vertexMapForIndex.keySet(), other.vertexMapForIndex.keySet())
                && Objects.equals(thisEdges, otherEdges);
    }

    @Override
    public Set<Integer> vertexSet() {
        return new HashSet<>(vertexMapForIndex.keySet());
    }

}
