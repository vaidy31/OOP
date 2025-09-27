package ru.nsu.mzaugolnikov.task222;

/**
 * Класс с сущностями для игры в Блэкджек.
 * Содержит перечисления мастей и достоинств карт, а также класс карты.
 */
public class Cards {

    /**
     * Перечисление достоинств карт и значений.
     */
    public enum Rank {
        /** Двойка со значением 2. */ TWO("Двойка", 2),
        /** Тройка со значением 3. */ THREE("Тройка", 3),
        /** Четверка со значением 4. */ FOUR("Четвёрка", 4),
        /** Пятерка со значением 5. */ FIVE("Пятёрка", 5),
        /** Шестерка со значением 6. */ SIX("Шестёрка", 6),
        /** Семерка со значением 7. */ SEVEN("Семёрка", 7),
        /** Восьмерка со значением 8. */ EIGHT("Восьмёрка", 8),
        /** Девятка со значением 9. */ NINE("Девятка", 9),
        /** Десятка со значением 10. */ TEN("Десятка", 10),
        /** Валет со значением 10. */ JACK("Валет", 10),
        /** Дама со значением 10. */ QUEEN("Дама", 10),
        /** Король со значением 10. */ KING("Король", 10),
        /** Туз со значением 11 (может быть 1). */ ACE("Туз", 11);

        private final String name;
        private final int value;

        /**
         * Конструктор достоинств карты.
         *
         * @param name название достоинства
         * @param value числовое значние
         */
        Rank(String name, int value) {
            this.name = name;
            this.value = value;
        }

        /**
         * Возвращает достоинство.
         *
         * @return имя достоинства
         */
        public String getName() {
            return name;
        }

        /**
         * Возвращает числовое значение.
         *
         * @return значение карты
         */
        public int getValue() {
            return value;
        }
    }

    /**
     * Перечисление мастей карт.
     */
    public enum Suit {
        /** Черви. */ HEARTS("Черви"),
        /** Крести. */ CLUBS("Крести"),
        /** Пики. */ SPADES("Пики"),
        /** Бубны. */ DIAMONDS("Бубны");

        private final String name;

        /**
         * Конструктор.
         *
         * @param name название масти
         */
        Suit(String name) {
            this.name = name;
        }

        /**
         * Возвращает название масти.
         *
         * @return название масти
         */
        public String getName() {
            return name;
        }
    }

    /**
     * Класс для игральной карты.
     * Карта состоит из достоинства и масти.
     */
    public static class Card {
        private final Rank rank;
        private final Suit suit;

        /**
         * Конструктор карты.
         *
         * @param rank достоинство карты
         * @param suit масть карты
         */
        public Card(Rank rank, Suit suit) {
            this.rank = rank;
            this.suit = suit;
        }

        /**
         * Возвращает достоинство карты.
         *
         * @return достоинство карты
         */
        public Rank getRank() {
            return rank;
        }

        /**
         * Возвращает масть карты.
         *
         * @return масть карты
         */
        public Suit getSuit() {
            return suit;
        }

        /**
         * Возвращает строковое представление карты в формате: "Достоинство Масть (Значение)".
         *
         * @return строковое представление карты
         */
        @Override
        public String toString() {
            return rank.getName() + " " + suit.getName() + " (" + rank.getValue() + ")";
        }
    }
}