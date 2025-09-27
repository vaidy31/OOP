package ru.nsu.mzaugolnikov.task111;
// Коля машкин не играет
public class Cards {
    public enum Rank {
        TWO("Двойка", 2),
        THREE("Тройка", 3),
        FOUR("Четвёрка", 4),
        FIVE("Пятёрка", 5),
        SIX("Шестёрка", 6),
        SEVEN("Семёрка", 7),
        EIGHT("Восьмёрка", 8),
        NINE("Девятка", 9),
        TEN("Десятка", 10),
        JACK("Валет", 10),
        QUEEN("Дама", 10),
        KING("Король", 10),
        ACE("Туз", 11);

        private final String name;
        private final int value;

        Rank(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public int getValue() {
            return value;
        }
    }

    public enum Suit {
        HEARTS("Черви"),
        CLUBS("Крести"),
        SPADES("Пики"),
        DIAMONDS("Бубны");

        private final String name;

        Suit(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static class Card {
        private final Rank rank;
        private final Suit suit;

        public Card(Rank rank, Suit suit) {
            this.rank = rank;
            this.suit = suit;
        }

        public Rank getRank() {
            return rank;
        }

        public Suit getSuit() {
            return suit;
        }

        @Override
        public String toString() {
            return rank.getName() + " " + suit.getName() + " (" + rank.getValue() + ")";
        }
    }
}