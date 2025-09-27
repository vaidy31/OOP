package ru.nsu.mzaugolnikov.task222;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BlackjackGameTest {

    @Test
    public void testCardCreation() {
        Cards.Card card = new Cards.Card(Cards.Rank.ACE, Cards.Suit.HEARTS);
        assertNotNull(card);
        assertEquals(Cards.Rank.ACE, card.getRank());
    }

    @Test
    public void testDeckInitialization() {
        Deck deck = new Deck();
        assertNotNull(deck);
    }
}