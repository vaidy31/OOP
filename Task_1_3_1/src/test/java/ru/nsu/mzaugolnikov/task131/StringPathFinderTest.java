package ru.nsu.mzaugolnikov.task131;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Тесты для поиска подстроки.
 */
class StringPathFinderTest {
    private StringPathFinder finder;

    @BeforeEach
    void start() {
        finder = new StringPathFinder();
    }

    @Test
    void testSimpleSearch(@TempDir Path tempDir) throws IOException {
        String text = "Здарова, ворлд.";
        File file = createTestFile(tempDir, "simple.txt", text);
        List<Long> result = finder.findSubstringInFile(file, "вор");
        assertEquals(1, result.size());
        assertEquals(9L, result.get(0));
    }

    @Test
    void testNullFile(@TempDir Path tempDir) {
        String text = "Здарова, ворлд.";
        assertThrows(IllegalStateException.class, () -> {
            finder.findSubstringInFile(null, text);
        });
    }

    @Test
    void testFileWithoutPattern(@TempDir Path tempDir) throws IOException {
        String text = "Здарова, ворлд.";
        File file = createTestFile(tempDir, "simple.txt", text);
        List<Long> result = finder.findSubstringInFile(file, "обама");
        assertEquals(0, result.size());
    }

    // Имя метода исправлено с testOVERLAP на testOverlap (требование Checkstyle)
    @Test
    void testOverlap(@TempDir Path tempDir) throws IOException {
        File file = createTestFile(tempDir, "test.txt", "nanana");
        List<Long> result = finder.findSubstringInFile(file, "nana");
        assertEquals(2, result.size());
        assertEquals(0L, result.get(0));
        assertEquals(2L, result.get(1));
    }

    @Test
    void testBoundary(@TempDir Path tempDir) throws IOException {
        String temp = "a".repeat(4094);
        String content = temp + "TEST" + "blablabla";
        File file = createTestFile(tempDir, "test.txt", content);

        List<Long> result = finder.findSubstringInFile(file, "TEST");

        assertEquals(1, result.size());
        assertEquals(4094L, result.get(0));
    }

    @Test
    void testBoundaryWithoutMatch(@TempDir Path tempDir) throws IOException {
        String temp = "a".repeat(4094);
        String content = temp + "TES" + "blablabla";
        File file = createTestFile(tempDir, "test.txt", content);

        List<Long> result = finder.findSubstringInFile(file, "TEST");

        assertEquals(0, result.size());
    }

    @Test
    void testLargeFileAndOffset(@TempDir Path tempDir) throws IOException {
        final String targetPattern = "GOIDAWORD";

        final long gigaChadSize = 2L * 1024 * 1024 * 1024;
        final long targetPos = gigaChadSize - 10;

        File file = createBigFile(tempDir, "huge.txt", targetPos, targetPattern);

        List<Long> result = finder.findSubstringInFile(file, targetPattern);
        assertEquals(1, result.size());
        assertEquals(2147483629L, result.get(0));
    }

    private File createTestFile(Path dir, String name, String content) throws IOException {
        Path filePath = dir.resolve(name);
        Files.write(filePath, content.getBytes(StandardCharsets.UTF_8));
        return filePath.toFile();
    }

    private File createBigFile(Path dir, String name, long sizeBytes, String content) throws IOException {
        Path filePath = dir.resolve(name);
        try (java.io.OutputStream os = Files.newOutputStream(filePath);
             java.io.BufferedOutputStream bos = new java.io.BufferedOutputStream(os)) {
            // пишем по 1мб
            byte[] chunk = "X".repeat(1024 * 1024).getBytes(StandardCharsets.UTF_8);

            // дл строки для поиска
            long patternLength = content.length();
            // сколько до нужной строки
            long dataBeforePattern = sizeBytes - patternLength;
            // заполняем до нужного места
            for (long written = 0; written < dataBeforePattern; written += chunk.length) {
                long remaining = dataBeforePattern - written;

                // если достаточно, вписываем еще
                if (remaining >= chunk.length) {
                    bos.write(chunk);
                } else {
                    // оставшаяся часть
                    bos.write(chunk, 0, (int) remaining);
                }
            }

            // запись искомой подстроки
            bos.write(content.getBytes(StandardCharsets.UTF_8));
        }
        return filePath.toFile();
    }
}