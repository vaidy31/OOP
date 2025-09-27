package ru.nsu.mzaugolnikov.task111;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private final ArrayList<Cards.Card> cards;
    public Deck() {
        cards = new ArrayList<>();
        init();
    }

    public void init() {
        for (Cards.Suit suit : Cards.Suit.values()) {
            for (Cards.Rank rank : Cards.Rank.values()) {
                cards.add(new Cards.Card(rank, suit));
            }
        }
    }

    public void reset() {
        init();
        shuffle();
    }

    public void shuffle() {
        Collections.shuffle(cards);
        // custom shuffle
    }

    public Cards.Card dealCard() {
        if (cards.isEmpty()) {
            return null;
        }
        return cards.remove(0);
    }

    public ArrayList<Cards.Card> dealHand(int n) {
        if (n > cards.size()) {
            throw new IllegalStateException("Количество карт в колоде недостаточно");
        }
        ArrayList<Cards.Card> hand = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Cards.Card card = dealCard();
            hand.add(card);
        }
        return hand;
    }
}
