package ru.nsu.mzaugolnikov.task222;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlackjackGameTest {

    private BlackjackGame game;
    private Player player;
    private Dealer dealer;
    private Deck deck;

    @BeforeEach
    void setUp() {
        game = new BlackjackGame();
        player = new Player("Игрок");
        dealer = new Dealer("Дилер");
        deck = new Deck();
    }

    @Test
    void testPlayerWins() {
        // Игрок выигрывает
        player.addCard(new Cards.Card(Cards.Rank.ACE, Cards.Suit.HEARTS));
        player.addCard(new Cards.Card(Cards.Rank.KING, Cards.Suit.SPADES)); // 21

        dealer.addCard(new Cards.Card(Cards.Rank.TEN, Cards.Suit.CLUBS));
        dealer.addCard(new Cards.Card(Cards.Rank.NINE, Cards.Suit.DIAMONDS)); // 19

        int[] scores = {0, 0};
        game.whoIsWinner(player, dealer, scores);
        assertEquals(1, scores[0]);
        assertEquals(0, scores[1]);
    }

    @Test
    void testDealerWins() {
        // Дилер выигрывает
        player.addCard(new Cards.Card(Cards.Rank.TEN, Cards.Suit.HEARTS));
        player.addCard(new Cards.Card(Cards.Rank.SIX, Cards.Suit.CLUBS)); // 16

        dealer.addCard(new Cards.Card(Cards.Rank.TEN, Cards.Suit.SPADES));
        dealer.addCard(new Cards.Card(Cards.Rank.NINE, Cards.Suit.DIAMONDS)); // 19

        int[] scores = {0, 0};
        game.whoIsWinner(player, dealer, scores);
        assertEquals(0, scores[0]);
        assertEquals(1, scores[1]);
    }
}