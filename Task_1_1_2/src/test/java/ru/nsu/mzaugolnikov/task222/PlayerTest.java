package ru.nsu.mzaugolnikov.task222;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class PlayerTest {

    @Test
    void testAddCardAndTotal() {
        Player player = new Player("Игрок");
        player.addCard(new Cards.Card(Cards.Rank.TEN, Cards.Suit.HEARTS));
        player.addCard(new Cards.Card(Cards.Rank.SEVEN, Cards.Suit.SPADES));
        assertEquals(17, player.getTotal());
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
        player.addCard(new Cards.Card(Cards.Rank.TWO, Cards.Suit.DIAMONDS));
        assertTrue(player.isBusted());
    }

    @Test
    void testClearHand() {
        Player player = new Player("Игрок");
        player.addCard(new Cards.Card(Cards.Rank.TEN, Cards.Suit.HEARTS));
        player.clear();
        assertEquals(0, player.getTotal());
        assertFalse(player.isBusted());
    }

    @Test
    void testAceAdjustment() {
        Player player = new Player("Игрок");

        player.addCard(new Cards.Card(Cards.Rank.ACE, Cards.Suit.HEARTS));
        player.addCard(new Cards.Card(Cards.Rank.NINE, Cards.Suit.CLUBS));
        assertEquals(20, player.getTotal(), "Туз считается 11, общая сумма 20");

        player.addCard(new Cards.Card(Cards.Rank.THREE, Cards.Suit.SPADES));
        assertEquals(13, player.getTotal(), "Туз пересчитан как 1, общая сумма 13");

        player.addCard(new Cards.Card(Cards.Rank.KING, Cards.Suit.DIAMONDS));
        assertTrue(player.isBusted(), "Игрок перебрал");
    }
}