package ru.nsu.mzaugolnikov.task222;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

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
    @Test
    void testTie() {
        // Ничья
        player.addCard(new Cards.Card(Cards.Rank.TEN, Cards.Suit.HEARTS));
        player.addCard(new Cards.Card(Cards.Rank.NINE, Cards.Suit.CLUBS)); // 19

        dealer.addCard(new Cards.Card(Cards.Rank.NINE, Cards.Suit.SPADES));
        dealer.addCard(new Cards.Card(Cards.Rank.TEN, Cards.Suit.DIAMONDS)); // 19

        int[] scores = {0, 0};
        game.whoIsWinner(player, dealer, scores);
        assertEquals(0, scores[0]);
        assertEquals(0, scores[1]);
    }

    @Test
    void testDealerTurnHits() {
        dealer.addCard(new Cards.Card(Cards.Rank.TEN, Cards.Suit.HEARTS));
        dealer.addCard(new Cards.Card(Cards.Rank.SIX, Cards.Suit.CLUBS)); // 16

        game.dealerTurn(dealer, deck);
        assertTrue(dealer.getTotal() >= 17 || dealer.isBusted());
    }

    @Test
    void testPlayerBusts() {
        player.addCard(new Cards.Card(Cards.Rank.KING, Cards.Suit.HEARTS));
        player.addCard(new Cards.Card(Cards.Rank.QUEEN, Cards.Suit.CLUBS));
        player.addCard(new Cards.Card(Cards.Rank.TWO, Cards.Suit.DIAMONDS)); // 22
        assertTrue(player.isBusted());
    }

    @Test
    void testPlayerTakesOneCardAndStands() {
        String simulatedInput = "1\n0\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        player.addCard(deck.dealCard());
        int initialTotal = player.getTotal();

        game.playerTurn(player, dealer, deck);

        assertEquals(initialTotal + player.getHand().get(1).getRank().getValue(), player.getTotal());
        assertEquals(2, player.getHand().size());
    }

    @Test
    void testPlayerBustsDuringTurn() {
        String simulatedInput = "1\n1\n1\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        player.addCard(new Cards.Card(Cards.Rank.KING, Cards.Suit.HEARTS));
        player.addCard(new Cards.Card(Cards.Rank.QUEEN, Cards.Suit.CLUBS));

        game.playerTurn(player, dealer, deck);

        assertTrue(player.isBusted());
    }
}