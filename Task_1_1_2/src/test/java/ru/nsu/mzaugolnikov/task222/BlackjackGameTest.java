package ru.nsu.mzaugolnikov.task222;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class BlackjackTest {

    @Test
    void testCardValue() {
        Cards.Card card = new Cards.Card(Cards.Rank.KING, Cards.Suit.HEARTS);
        assertEquals(10, card.getRank().getValue());
        assertEquals("Король", card.getRank().getName());
    }

    @Test
    void testAceCounting() {
        Player player = new Player("Игрок");
        player.addCard(new Cards.Card(Cards.Rank.ACE, Cards.Suit.HEARTS));
        player.addCard(new Cards.Card(Cards.Rank.NINE, Cards.Suit.CLUBS));
        assertEquals(20, player.getTotal()); // туз считается как 11
        player.addCard(new Cards.Card(Cards.Rank.THREE, Cards.Suit.SPADES));
        assertEquals(13, player.getTotal()); // туз 1
    }

    @Test
    void testBlackjack() {
        Player player = new Player("Игрок");
        player.addCard(new Cards.Card(Cards.Rank.ACE, Cards.Suit.HEARTS));
        player.addCard(new Cards.Card(Cards.Rank.KING, Cards.Suit.SPADES));
        assertTrue(player.isBlackjack());
    }

    @Test
    void testBusted() {
        Player player = new Player("Игрок");
        player.addCard(new Cards.Card(Cards.Rank.KING, Cards.Suit.HEARTS));
        player.addCard(new Cards.Card(Cards.Rank.QUEEN, Cards.Suit.CLUBS));
        player.addCard(new Cards.Card(Cards.Rank.TWO, Cards.Suit.SPADES));
        assertTrue(player.isBusted());
    }

    @Test
    void testDeckDealsCards() {
        Deck deck = new Deck();
        int sizeBefore = deck.size();
        Cards.Card card = deck.dealCard();
        assertNotNull(card);
        assertEquals(sizeBefore - 1, deck.size()); // проверка уменьшения колоды
    }
}
