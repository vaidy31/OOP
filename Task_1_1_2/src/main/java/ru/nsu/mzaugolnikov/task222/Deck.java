package ru.nsu.mzaugolnikov.task222;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Класс, представляющий колоду карт
 * 52 карты
 */
public class Deck {
    private final ArrayList<Cards.Card> cards;
    /**
     * Конструктор колоды. Создает и перемешивает колоду из 52 карт
     */
    public Deck() {
        cards = new ArrayList<>();
        init();
    }

    /**
     * Инициализирует колоду - создает все 52 карты
     */
    public void init() {
        for (Cards.Suit suit : Cards.Suit.values()) {
            for (Cards.Rank rank : Cards.Rank.values()) {
                cards.add(new Cards.Card(rank, suit));
            }
        }
    }

    /**
     * Сбрасывает колоду и создает новую перемешанную колоду.
     */
    public void reset() {
        cards.clear();
        init();
        shuffle();
    }

    /**
     * Перемешка колоды
     */
    public void shuffle() {
        Collections.shuffle(cards);
        // custom shuffle
    }

    /**
     * Раздача одной карты из колоды
     *
     * @return карта из начала или null если колода пуста
     */
    public Cards.Card dealCard() {
        if (cards.isEmpty()) {
            return null;
        }
        return cards.remove(0);
    }
}
