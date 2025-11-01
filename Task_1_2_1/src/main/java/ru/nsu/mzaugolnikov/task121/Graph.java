package ru.nsu.mzaugolnikov.task121;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Интерфейс для графа.
 */
public interface Graph {

    /**
     * Добавление вершины в граф.
     *
     * @param v номер вершины
     */
    void addVertex(Integer v);

    /**
     * Удаляет вершину из графа.
     *
     * @param v номер вершины
     */
    void deleteVertex(int v);

    /**
     * Добавляет ребро в граф.
     *
     * @param from откуда
     * @param to куда
     * @return количество вершин
     */
    int addEdge(int from, int to);

    /**
     * Удаляет ребро из графа.
     *
     * @param from откуда
     * @param to куда
     */
    void deleteEdge(int from, int to);

    /**
     * Список соседей для вершины.
     *
     * @param v вершина
     * @return список вершин
     */
    List<Integer> adjVertexList(int v);

    /**
     * Читает ребра и вершины из файла.
     *
     * @param file какой-то файл
     * @throws IOException кидает исключение
     */
    public default void readGraphFromFile(String file) throws IOException {
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
                if (countVertexGlobal < 0) {
                    System.err.println("Первая строка имеет отрицательное количество вершин — "
                        + "граф строится по факту появления вершин из ребер.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Первая строка не содержит число вершин — "
                        + "граф строится по факту появления вершин из ребер.");
                // Добавляем обработку этой строки как ребра:
                String[] verteciesInString = initLine.split("\\s+");
                if (verteciesInString.length == 2) {
                    try {
                        int fromVert = Integer.parseInt(verteciesInString[0]);
                        int toVert = Integer.parseInt(verteciesInString[1]);
                        if (fromVert < 0 || toVert < 0) {
                            System.err.println("Вершина в первой строке представляется в "
                                    + "отрицательном виде, пропускаем...");
                        }
                        else {
                            addEdge(fromVert, toVert);
                        }
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

                if (fromVert < 0 || toVert < 0) {
                    System.err.println("Отрицательный номер вершины в строке "
                            + numString + "пропускаем...");
                    continue;
                }

                countVertexLocal += addEdge(fromVert, toVert);
            }

            if (countVertexLocal > countVertexGlobal && countVertexGlobal > 0) {
                System.err.println("Количество вершин в начале файле было"
                        + "указано неверно, размеры были увеличены");
            }
        } catch (NumberFormatException e) {
            System.err.println("Проблема формата в строке " + numString);
        }
    }

    /**
     * Hash.
     *
     * @return возвращает хэш
     */
    int hashCode();

    /**
     * Сет из вершин.
     *
     * @return неповторяющиеся вершины из сета
     */
    Set<Integer> vertexSet();
}
