package ru.nsu.mzaugolnikov.task222;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


class DealerTest {

    @Test
    void testAddCardAndTotal() {
        Dealer dealer = new Dealer("Дилер");
        dealer.addCard(new Cards.Card(Cards.Rank.TEN, Cards.Suit.HEARTS));
        dealer.addCard(new Cards.Card(Cards.Rank.SEVEN, Cards.Suit.SPADES));
        assertEquals(17, dealer.getTotal());
    }

    @Test
    void testBusted() {
        Dealer dealer = new Dealer("Дилер");
        dealer.addCard(new Cards.Card(Cards.Rank.KING, Cards.Suit.HEARTS));
        dealer.addCard(new Cards.Card(Cards.Rank.QUEEN, Cards.Suit.CLUBS));
        dealer.addCard(new Cards.Card(Cards.Rank.TWO, Cards.Suit.DIAMONDS));
        assertTrue(dealer.isBusted());
    }

    @Test
    void testBlackjack() {
        Dealer dealer = new Dealer("Дилер");
        dealer.addCard(new Cards.Card(Cards.Rank.ACE, Cards.Suit.HEARTS));
        dealer.addCard(new Cards.Card(Cards.Rank.KING, Cards.Suit.SPADES));
        assertTrue(dealer.isBlackjack());

        dealer.addCard(new Cards.Card(Cards.Rank.TWO, Cards.Suit.CLUBS));
        assertFalse(dealer.isBlackjack());
    }

    @Test
    void testClearHand() {
        Dealer dealer = new Dealer("Дилер");
        dealer.addCard(new Cards.Card(Cards.Rank.TEN, Cards.Suit.HEARTS));
        dealer.addCard(new Cards.Card(Cards.Rank.KING, Cards.Suit.SPADES));

        dealer.clear();
        assertEquals(0, dealer.getTotal());
        assertFalse(dealer.isBusted());
        assertFalse(dealer.isBlackjack());
    }
    
    @Test
    void testShowAllHand() {
        Dealer dealer = new Dealer("Дилер");
        dealer.addCard(new Cards.Card(Cards.Rank.TEN, Cards.Suit.HEARTS));
        dealer.addCard(new Cards.Card(Cards.Rank.KING, Cards.Suit.SPADES));

        String handStr = dealer.showAllHand();
        assertTrue(handStr.contains("Десятка"));
        assertTrue(handStr.contains("Король"));
    }


}
