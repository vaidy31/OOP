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
    private static final int bufferSize = 4096;
    private char[] buffer = new char[bufferSize];
    private File newFile;
    private long offset = 0;

    /**
     * Проверяет существование файла и сохраняет его для дальнейшей работы.
     *
     * @param fileName файл
     * @return true/false
     */
    public boolean startForFileFuncs(File fileName) {
        if (fileName == null || !fileName.exists()) {
            return false;
        }
        this.newFile = fileName;
        return true;
    }


    /**
     * Геттер, который получается строку нужную для следующего обхода буфера.
     *
     * @param text предыдущий буфер
     * @param length длина
     * @return
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
     * @param pattern   паттерн
     * @return список с символами, если есть
     * @throws IOException исключение при открытии файла
     */
    public List<Long> findSubstringInFile(File fileName, String pattern) throws IOException {
        if (!startForFileFuncs(fileName)) {
            throw new IllegalStateException("File not exist. Try to check the directory");
        } else if (pattern.isEmpty()) {
            return new ArrayList<>();
        }

        List<Long> result = new ArrayList<>();
        int patternLength = pattern.length();
        String tailString = "";

        try (BufferedReader reader = Files.newBufferedReader(fileName.toPath(), StandardCharsets.UTF_8)) {
            int charsRead;
            while ((charsRead = reader.read(buffer)) != -1) {
                String chunk = new String(buffer, 0, charsRead);

                int found;
                int indexLocal = 0;
                while ((found = chunk.indexOf(pattern, indexLocal)) != -1) {
                    long findEntry = offset + found;
                    result.add(findEntry);
                    indexLocal = found + 1;
                }

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

                tailString = getTail(chunk, patternLength - 1);

                offset += charsRead;
                // System.out.printf("Processed %d characters, found %d matches so far.%n", offset, result.size());
                // debug
            }
        }
        return result;
    }
}
