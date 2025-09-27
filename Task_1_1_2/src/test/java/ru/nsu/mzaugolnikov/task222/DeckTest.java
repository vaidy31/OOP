package ru.nsu.mzaugolnikov.task222;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

class DeckTest {

    @Test
    void testDeckCreation() {
        Deck deck = new Deck();
        int count = 0;
        Cards.Suit[] suits = Cards.Suit.values();
        Cards.Rank[] ranks = Cards.Rank.values();

        for (Cards.Suit suit : suits) {
            for (Cards.Rank rank : ranks) {
                count++;
            }
        }
        assertEquals(count, 52);
    }

    @Test
    void testDealCardNotNull() {
        Deck deck = new Deck();
        Cards.Card card = deck.dealCard();
        assertNotNull(card);
    }

    @Test
    void testDeckSizeAfter() {
        Deck deck = new Deck();
        deck.dealCard();
        deck.dealCard();
        deck.dealCard();
        final int initialSize = 52;
        assertEquals(initialSize - 3, 49);
    }

    @Test
    void testDealAll() {
        Deck deck = new Deck();
        Cards.Card lastCard = null;
        for (int i = 0; i < 52; i++) {
            lastCard = deck.dealCard();
            assertNotNull(lastCard);
        }
        assertNull(deck.dealCard());
    }

    @Test
    void testShuffleChangesOrder() {
        Deck deck1 = new Deck();
        Deck deck2 = new Deck();
        deck2.shuffle();
        assertFalse(deck1.dealCard().equals(deck2.dealCard())); // Requires getCards()
    }

    @Test
    void testReset() {
        Deck deck = new Deck();
        deck.dealCard(); // Удаляем одну карту
        int cardsBeforeReset = 0;
        while (deck.dealCard() != null) {
            cardsBeforeReset++;
        }
        assertEquals(51, cardsBeforeReset, "После удаления должно остаться 51");
        deck.reset();
        int cardsAfterReset = 0;
        while (deck.dealCard() != null) {
            cardsAfterReset++;
        }
        assertEquals(52, cardsAfterReset, "После reset должно быть 52 карты");
    }
}