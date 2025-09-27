package ru.nsu.mzaugolnikov.task222;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardsTest {

    @Test
    void testCardCreation() {
        Cards.Card card = new Cards.Card(Cards.Rank.ACE, Cards.Suit.HEARTS);
        assertNotNull(card);
        assertEquals(Cards.Suit.HEARTS, card.getSuit());
        assertEquals(Cards.Rank.ACE, card.getRank());
    }

    @Test
    void testToString() {
        Cards.Card card = new Cards.Card(Cards.Rank.KING, Cards.Suit.SPADES);
        String str = card.toString();
        assertTrue(str.contains("Король"));
        assertTrue(str.contains("Пики"));
    }
}