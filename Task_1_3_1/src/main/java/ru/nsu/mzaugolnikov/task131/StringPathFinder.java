package ru.nsu.mzaugolnikov.task131;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Реализация метода поиска подстроки в файле.
 * Работает с UTF-8.
 */
public class StringPathFinder {
    private static final int BUFFER_SIZE = 4096;
    private final char[] buffer = new char[BUFFER_SIZE];
    private File newFile;
    private long offset = 0;

    /**
     * Проверяет существование f файла и сохраняет его для дальнейшей работы.
     *
     * @param fileName файл
     * @return true, если файл существует и доступен; false иначе
     */
    public boolean startForFileFuncs(File fileName) {
        if (fileName == null || !fileName.exists()) {
            return false;
        }
        this.newFile = fileName;
        return true;
    }

    /**
     * Получает хвост предыдущего буфера, чтобы корректно обработать границы паттерна.
     *
     * @param text   предыдущий буфер
     * @param length количество символов хвоста
     * @return хвост строки для следующего буфера
     */
    private String getTail(String text, int length) {
        if (text.length() <= length) {
            return text;
        }
        return text.substring(text.length() - length);
    }

    /**
     * Находит все позиции вхождений подстроки в файле.
     * Использует раздельный поиск: сначала внутри буфера, затем на границе.
     *
     * @param fileName имя файла
     * @param pattern  паттерн
     * @return список индексов всех найденных вхождений
     * @throws IOException если произошла ошибка при чтении файла
     */
    public List<Long> findSubstringInFile(File fileName, String pattern) throws IOException {
        if (!startForFileFuncs(fileName)) {
            throw new IllegalStateException(
                    "File not exist. Try to check the directory"
            );
        } else if (pattern.isEmpty()) {
            return new ArrayList<>();
        }

        List<Long> result = new ArrayList<>();
        final int patternLength = pattern.length();
        String tailString = "";

        try (BufferedReader reader = Files.newBufferedReader(
                fileName.toPath(), StandardCharsets.UTF_8)) {
            int charsRead;
            while ((charsRead = reader.read(buffer)) != -1) {
                String chunk = new String(buffer, 0, charsRead);

                // Поиск внутри буфера
                int indexLocal = 0;
                int found;
                while ((found = chunk.indexOf(pattern, indexLocal)) != -1) {
                    long findEntry = offset + found;
                    result.add(findEntry);
                    indexLocal = found + 1;
                }

                // Поиск на границе буферов
                if (!tailString.isEmpty()) {
                    int takeLength = Math.min(patternLength - 1, chunk.length());
                    String borderRegion = tailString + chunk.substring(0, takeLength);

                    int borderIndex = 0;
                    while ((found = borderRegion.indexOf(pattern, borderIndex)) != -1) {
                        if (found < tailString.length()) {
                            long position = (offset - tailString.length()) + found;
                            result.add(position);
                        }
                        borderIndex = found + 1;
                    }
                }

                // Сохраняем хвост для следующей итерации
                tailString = getTail(chunk, patternLength - 1);
                offset += charsRead;
            }
        }
        return result;
    }
}
