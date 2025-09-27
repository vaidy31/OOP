package ru.nsu.mzaugolnikov.task222;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


class HandTest {
    @Test
    void testGetTotalWithMultipleAces() {
        Player player = new Player("Игрок");
        // два туза + 9
        player.addCard(new Cards.Card(Cards.Rank.ACE, Cards.Suit.HEARTS));
        player.addCard(new Cards.Card(Cards.Rank.ACE, Cards.Suit.SPADES));
        player.addCard(new Cards.Card(Cards.Rank.NINE, Cards.Suit.CLUBS));
        // первый туз = 11, второй = 1
        assertEquals(21, player.getTotal());
    }

    @Test
    void testIsBustedEdgeCases() {
        Player player = new Player("Игрок");
        player.addCard(new Cards.Card(Cards.Rank.TEN, Cards.Suit.SPADES));
        player.addCard(new Cards.Card(Cards.Rank.TEN, Cards.Suit.HEARTS));
        player.addCard(new Cards.Card(Cards.Rank.ACE, Cards.Suit.HEARTS));
        assertFalse(player.isBusted()); //21

        player.addCard(new Cards.Card(Cards.Rank.TWO, Cards.Suit.CLUBS));
        assertTrue(player.isBusted()); //22
    }

    @Test
    void testIsBlackjack() {
        Player player = new Player("Игрок");
        player.addCard(new Cards.Card(Cards.Rank.ACE, Cards.Suit.HEARTS));
        player.addCard(new Cards.Card(Cards.Rank.KING, Cards.Suit.SPADES));
        assertTrue(player.isBlackjack());

        player.addCard(new Cards.Card(Cards.Rank.TWO, Cards.Suit.CLUBS));
        assertFalse(player.isBlackjack());
    }

    @Test
    void testGetHandReturnsCopy() {
        Player player = new Player("Игрок");
        player.addCard(new Cards.Card(Cards.Rank.TEN, Cards.Suit.HEARTS));
        player.addCard(new Cards.Card(Cards.Rank.KING, Cards.Suit.SPADES));

        ArrayList<Cards.Card> handCopy = player.getHand();
        assertEquals(2, handCopy.size());

        handCopy.clear();
        assertEquals(2, player.getHand().size());
    }

    @Test
    void testDealerShowAllHand() {
        Dealer dealer = new Dealer("Дилер");
        dealer.addCard(new Cards.Card(Cards.Rank.TEN, Cards.Suit.HEARTS));
        dealer.addCard(new Cards.Card(Cards.Rank.KING, Cards.Suit.SPADES));

        String str = dealer.showAllHand();
        assertTrue(str.contains("Десятка"));
        assertTrue(str.contains("Король"));
    }

    @Test
    void testClearHand() {
        Player player = new Player("Игрок");
        player.addCard(new Cards.Card(Cards.Rank.TEN, Cards.Suit.HEARTS));
        player.addCard(new Cards.Card(Cards.Rank.KING, Cards.Suit.SPADES));
        player.clear();
        assertEquals(0, player.getTotal());
        assertFalse(player.isBusted());
        assertFalse(player.isBlackjack());
        assertTrue(player.getHand().isEmpty());
    }

    @Test
    void testShownCards() {
        Player player = new Player("Игрок");
        player.addCard(new Cards.Card(Cards.Rank.TEN, Cards.Suit.HEARTS));
        player.addCard(new Cards.Card(Cards.Rank.KING, Cards.Suit.SPADES));

        String strHidden = player.showCards(true);
        assertTrue(strHidden.contains("<закрытая карта>"));
        assertTrue(strHidden.contains("Король"));

        String strVisible = player.showCards(false);
        assertTrue(strVisible.contains("Король"));
        assertTrue(strVisible.contains("Десятка"));
    }
}