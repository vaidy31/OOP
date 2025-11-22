package ru.nsu.mzaugolnikov.task122;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ConcurrentModificationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Тесты для того чтобы все было надежно.
 */
class HashTableTest {

    private HashTable<String, Number> table;

    @BeforeEach
    void start() {
        table = new HashTable<>();
    }

    @Test
    void test1pg() {
        table.put("shest`", 6);
        table.put("dva", 2);

        assertEquals(6, table.get("shest`"));
        assertEquals(2, table.get("dva"));
        assertNull(table.get("glorb"));
    }

    @Test
    void testUpdate() {
        table.put("один", 1);
        assertTrue(table.update("один", 1.0));
        assertEquals(1.0, table.get("один"));
        assertFalse(table.update("два", 2.0));
    }

    @Test
    void testContainsKey() {
        table.put("one", 1);
        assertTrue(table.containsKey("one"));
    }

    @Test
    void testRemove() {
        table.put("1", 1);
        table.put("2", 2);
        table.remove("1");
        assertNull(table.get("1"));
        assertEquals(2, table.get("2"));

        // удаляем несуществующий ключ
        table.remove("three");
    }

    @Test
    void testSizeAndIsEmpty() {
        assertEquals(0, table.size());
        assertTrue(table.isEmpty());

        table.put("one", 1);
        assertEquals(1, table.size());
        assertFalse(table.isEmpty());
    }

    @Test
    void testEquals() {
        HashTable<String, Number> other = new HashTable<>();
        table.put("one", 1);
        table.put("two", 2);
        other.put("one", 1);
        other.put("two", 2);
        assertEquals(table, other);
        other.put("three", 3);
        assertNotEquals(table, other);
    }

    @Test
    void testIterator() {
        table.put("one", 1);
        table.put("two", 2);

        int count = 0;
        for (HashTable.Node<String, Number> node : table) {
            assertNotNull(node.key);
            assertNotNull(node.value);
            count++;
        }
        assertEquals(table.size(), count);
    }

    @Test
    void testConcurrentModificationException() {
        table.put("one", 1);
        table.put("two", 2);

        assertThrows(ConcurrentModificationException.class, () -> {
            for (HashTable.Node<String, Number> node : table) {
                table.put("three", 3); // изменение во время итерации
            }
        });
    }

    @Test
    void testResize() {
        for (int i = 0; i < 20; i++) {
            table.put("kluch`" + i, i);
        }

        for (int i = 0; i < 20; i++) {
            assertEquals(i, table.get("kluch`" + i));
        }

        assertEquals(20, table.size());

        table.put("novikluch", 123333);
        assertEquals(21, table.size());
        assertEquals(123333, table.get("novikluch"));
    }

    @Test
    void testToString() {
        assertEquals("{}", table.toString());

        for (int i = 0; i < 20; i++) {
            table.put("kluch`" + i, i);
        }
        String result = table.toString();
        for (int i = 0; i < 20; i++) {
            assertTrue(result.contains("kluch`" + i + "=" + i));
        }
    }
}
