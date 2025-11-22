package ru.nsu.mzaugolnikov.task122;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Реализация HashTable со всеми нужными методами для поддержки минимальной работы.
 *
 * @param <K> любой ключ
 * @param <V> любое значение
 */
public class HashTable<K, V> implements Iterable<HashTable.Node<K, V>> {

    private Node<K, V>[] hashTableObj;
    private final double resizeRatio = 0.8;
    private int size;
    private int capacity = 16;
    private int modCount = 0;

    @SuppressWarnings("unchecked")
    public HashTable() {
        hashTableObj = (Node<K, V>[]) new Node[capacity];
    }

    /**
     * Ноды для реализации коллизии с помощью цепочек.
     *
     * @param <K> ключ
     * @param <V> значение
     */
    public static class Node<K, V> {
        K key;
        V value;
        Node<K, V> next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    /** Возвращает количество элементов в таблице. */
    public int size() {
        return size;
    }

    /** Проверяет, пуста ли таблица. */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Конструктор для итератора.
     *
     * @return Итератор для внутренней таблицы класса
     */
    @Override
    public Iterator iterator() {
        return new Iterator(this);
    }

    /**
     * Рассчитывает хэш для ключа.
     *
     * @param key ключ
     * @return hash
     */
    private int hashForKey(Object key) {
        return (key == null) ? 0 : key.hashCode();
    }

    /**
     * Рассчитывает индекс по хэшу.
     *
     * @param hashFromKey hash
     * @return index
     */
    private int indexForKey(int hashFromKey) {
        return (hashFromKey & 0x7FFFFFFF) % capacity;
    }

    private int reHash(K key, int newCapacity) {
        int hash = hashForKey(key);
        return (hash & 0x7FFFFFFF) % newCapacity;
    }

    /**
     * Добавляет в цепочку новую Ноду со значением и ключом.
     * При необходимости увеличивает таблицу.
     *
     * @param key ключ
     * @param value значение
     */
    public void put(K key, V value) {
        int hash = hashForKey(key);
        int index = indexForKey(hash);

        if ((double) size / capacity >= resizeRatio) {
            resizeTable();
            index = indexForKey(hash);
        }

        Node<K, V> temp = hashTableObj[index];
        while (temp != null) {
            if (Objects.equals(temp.key, key)) {
                temp.value = value;
                return;
            }
            temp = temp.next;
        }

        Node<K, V> newNode = new Node<>(key, value);
        newNode.next = hashTableObj[index];
        hashTableObj[index] = newNode;
        size++;
        modCount++;
    }

    /**
     * Возвращает значение по ключу.
     *
     * @param key ключ
     * @return значение по ключу
     */
    public V get(K key) {
        int keyToFind = hashForKey(key);
        int indexToFind = indexForKey(keyToFind);

        Node<K, V> temp = hashTableObj[indexToFind];
        while (temp != null) {
            if (Objects.equals(temp.key, key)) {
                return temp.value;
            }
            temp = temp.next;
        }
        return null;
    }

    /**
     * Удаление.
     *
     * @param key ключ
     */
    public void remove(K key) {
        int keyToFind = hashForKey(key);
        int indexToFind = indexForKey(keyToFind);

        Node<K, V> temp = hashTableObj[indexToFind];
        Node<K, V> prev = null;

        while (temp != null) {
            if (Objects.equals(temp.key, key)) {
                if (prev == null) {
                    hashTableObj[indexToFind] = temp.next;
                } else {
                    prev.next = temp.next;
                }
                size--;
                modCount++;
                return;
            }
            prev = temp;
            temp = temp.next;
        }
    }

    /**
     * Обновляет значение по ключу. Возвращает boolean.
     *
     * @param key ключ
     * @param value значение
     * @return true, если ключ найден, иначе false
     */
    public boolean update(K key, V value) {
        int keyToFind = hashForKey(key);
        int indexToFind = indexForKey(keyToFind);

        Node<K, V> temp = hashTableObj[indexToFind];
        while (temp != null) {
            if (Objects.equals(temp.key, key)) {
                temp.value = value;
                return true;
            }
            temp = temp.next;
        }
        return false;
    }

    /**
     * Увеличивает таблицу вдвое при необходимости.
     */
    public void resizeTable() {
        int newCapacity = capacity * 2;
        Node<K, V>[] hashTableObjNew = (Node<K, V>[]) new Node[newCapacity];

        for (int i = 0; i < capacity; i++) {
            Node<K, V> temp = hashTableObj[i];
            while (temp != null) {
                Node<K, V> next = temp.next;
                int newIndex = reHash(temp.key, newCapacity);
                temp.next = hashTableObjNew[newIndex];
                hashTableObjNew[newIndex] = temp;
                temp = next;
            }
        }
        hashTableObj = hashTableObjNew;
        capacity = newCapacity;
        modCount++;
    }

    /**
     * Сравнивает две хэш-таблицы.
     *
     * @param obj объект для сравнения
     * @return true, если равны, иначе false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof HashTable<?, ?>)) {
            return false;
        }

        HashTable<K, ?> otherTable = (HashTable<K, ?>) obj;
        if (this.size != otherTable.size) {
            return false;
        }

        HashTable<K, V>.Iterator it = this.iterator();
        while (it.hasNext()) {
            Node<K, V> node = it.next();
            if (!Objects.equals(otherTable.get(node.key), node.value)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Строковое представление таблицы.
     *
     * @return таблица в виде строки
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        for (Node<K, V> n : this) {
            if (!first) {
                sb.append(", ");
            }
            sb.append(n.key).append("=").append(n.value);
            first = false;
        }
        sb.append("}");
        return sb.toString();
    }

    /**
     * Проверяет наличие ключа.
     *
     * @param key ключ для поиска
     * @return true, если ключ найден
     */
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /**
     * Итератор по всем нодам таблицы.
     */
    public class Iterator implements java.util.Iterator<Node<K, V>> {

        private int indexOfChumBucket;
        private Node<K, V> currNode;
        private final HashTable<K, V> sameTable;
        private final int modCountIter;

        public Iterator(HashTable<K, V> table) {
            this.indexOfChumBucket = 0;
            this.sameTable = table;
            this.modCountIter = table.modCount;

            while (indexOfChumBucket < table.capacity &&
                    table.hashTableObj[indexOfChumBucket] == null) {
                indexOfChumBucket++;
            }

            currNode = (indexOfChumBucket < table.capacity)
                    ? table.hashTableObj[indexOfChumBucket]
                    : null;
        }

        @Override
        public boolean hasNext() {
            return currNode != null;
        }

        @Override
        public Node<K, V> next() {
            if (modCountIter != sameTable.modCount) {
                throw new ConcurrentModificationException();
            }
            if (currNode == null) {
                throw new NoSuchElementException();
            }

            Node<K, V> res = currNode;

            if (currNode.next != null) {
                currNode = currNode.next;
            } else {
                indexOfChumBucket++;
                while (indexOfChumBucket < sameTable.capacity &&
                        sameTable.hashTableObj[indexOfChumBucket] == null) {
                    indexOfChumBucket++;
                }
                currNode = (indexOfChumBucket < sameTable.capacity)
                        ? sameTable.hashTableObj[indexOfChumBucket]
                        : null;
            }

            return res;
        }
    }
}
