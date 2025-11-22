package ru.nsu.mzaugolnikov.task131;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

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
    void testNullFile(@TempDir Path tempDir){
        String text = "Здарова, ворлд.";
        assertThrows(IllegalStateException.class, () -> {
            finder.findSubstringInFile(null, text); });
    }

    @Test
    void testFileWithoutPattern(@TempDir Path tempDir) throws IOException {
        String text = "Здарова, ворлд.";
        File file = createTestFile(tempDir, "simple.txt", text);
        List<Long> result = finder.findSubstringInFile(file, "обама");
        assertEquals(0, result.size());
    }

    @Test
    void testOVERLAP(@TempDir Path tempDir) throws IOException {
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
        String temp1 = "X".repeat(100000);
        String temp2 = "Y".repeat(100000);

        String content = temp1 + targetPattern + temp2;
        File file = createTestFile(tempDir, "soso.txt", content);
        List<Long> result = finder.findSubstringInFile(file, targetPattern);

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals((long) 100000, result.get(0));
    }

    private File createTestFile(Path dir, String name, String content) throws IOException {
        Path filePath = dir.resolve(name);
        Files.write(filePath, content.getBytes(StandardCharsets.UTF_8));
        return filePath.toFile();
    }
}