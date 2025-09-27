package ru.nsu.mzaugolnikov.task222;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;




class BlackjackGameTest {
    private ByteArrayOutputStream outputStream;
    private BlackjackGame game;
    private Player player;
    private Dealer dealer;
    private Deck deck;

    @BeforeEach
    void setUp() {
        game = new BlackjackGame();
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        player = new Player("Игрок");
        dealer = new Dealer("Дилер");
        deck = new Deck();
    }

    //  playerTurn
    @Test
    void testPlayerTurnHitCard() {
        String input = "1\n0\n"; //
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        player.addCard(new Cards.Card(Cards.Rank.TEN, Cards.Suit.HEARTS));
        dealer.addCard(new Cards.Card(Cards.Rank.SEVEN, Cards.Suit.CLUBS));

        game.playerTurn(player, dealer, deck);

        String output = outputStream.toString();
        assertTrue(output.contains("Ваши карты:"));
        assertTrue(output.contains("Карты дилера:"));
        assertTrue(output.contains("взять карту"));
        assertTrue(output.contains("Вы открыли карту"));
    }

    @Test
    void testPlayerTurnStandImmediately() {
        String input = "0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        player.addCard(new Cards.Card(Cards.Rank.TEN, Cards.Suit.HEARTS));
        dealer.addCard(new Cards.Card(Cards.Rank.SEVEN, Cards.Suit.CLUBS));

        game.playerTurn(player, dealer, deck);

        String output = outputStream.toString();
        assertTrue(output.contains("Ваши карты:"));
        assertTrue(output.contains("Карты дилера:"));
        assertFalse(output.contains("Вы открыли карту")); // Не должно быть сообщения о взятии карты
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
    void testDealerTurnBusts() {
        Deck testDeck = new Deck() {
            @Override
            public Cards.Card dealCard() {
                return new Cards.Card(Cards.Rank.TEN, Cards.Suit.HEARTS);
            }
        };

        dealer.addCard(new Cards.Card(Cards.Rank.TEN, Cards.Suit.HEARTS));
        dealer.addCard(new Cards.Card(Cards.Rank.SIX, Cards.Suit.CLUBS)); // 16
        game.dealerTurn(dealer, testDeck);
        String output = outputStream.toString();
        assertTrue(output.contains("Дилер перебрал!"));
    }

    @Test
    void testDealerTurnUnder17() {
        dealer.addCard(new Cards.Card(Cards.Rank.TEN, Cards.Suit.HEARTS));
        dealer.addCard(new Cards.Card(Cards.Rank.SIX, Cards.Suit.CLUBS)); // 16

        game.dealerTurn(dealer, deck);

        String output = outputStream.toString();
        assertTrue(output.contains("Ход дилера:"));
        assertTrue(output.contains("Дилер открывает закрытую карту"));
        assertTrue(output.contains("Дилер открывает карту"));
        assertTrue(output.contains("Карты дилера:"));
    }

    @Test
    void testDealerTurnOver17() {
        dealer.addCard(new Cards.Card(Cards.Rank.TEN, Cards.Suit.HEARTS));
        dealer.addCard(new Cards.Card(Cards.Rank.SEVEN, Cards.Suit.CLUBS)); // 17

        game.dealerTurn(dealer, deck);

        String output = outputStream.toString();
        assertTrue(output.contains("Ход дилера:"));
        assertTrue(output.contains("Дилер открывает закрытую карту"));
        assertTrue(output.contains("Карты дилера:"));
        assertFalse(output.contains("Дилер открывает карту"));
    }

    @Test
    void testPlayerBusts() {
        player.addCard(new Cards.Card(Cards.Rank.KING, Cards.Suit.HEARTS));
        player.addCard(new Cards.Card(Cards.Rank.QUEEN, Cards.Suit.CLUBS));
        player.addCard(new Cards.Card(Cards.Rank.TWO, Cards.Suit.DIAMONDS)); // 22
        assertTrue(player.isBusted());
    }

    @Test
    void testPlayerTurnBusts() {
        // Тест когда игрок перебирает
        String input = "1\n1\n1\n"; // берет много карт
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Даем игроку карты с большим количеством очков
        player.addCard(new Cards.Card(Cards.Rank.TEN, Cards.Suit.HEARTS));
        player.addCard(new Cards.Card(Cards.Rank.TEN, Cards.Suit.CLUBS));

        game.playerTurn(player, dealer, deck);

        String output = outputStream.toString();
        assertTrue(output.contains("Перебор"));
    }

    @Test
    void testWhoIsWinnerScore() {
        int[] scores = {2, 3};

        player.addCard(new Cards.Card(Cards.Rank.TEN, Cards.Suit.HEARTS));
        player.addCard(new Cards.Card(Cards.Rank.NINE, Cards.Suit.CLUBS)); // 19
        dealer.addCard(new Cards.Card(Cards.Rank.TEN, Cards.Suit.SPADES));
        dealer.addCard(new Cards.Card(Cards.Rank.EIGHT, Cards.Suit.DIAMONDS)); // 18

        game.whoIsWinner(player, dealer, scores);
        assertEquals(3, scores[0]); // +1
        assertEquals(3, scores[1]);

        Player player2 = new Player("Игрок");
        Dealer dealer2 = new Dealer("Дилер");
        player2.addCard(new Cards.Card(Cards.Rank.TEN, Cards.Suit.HEARTS));
        player2.addCard(new Cards.Card(Cards.Rank.SEVEN, Cards.Suit.CLUBS)); // 17
        dealer2.addCard(new Cards.Card(Cards.Rank.TEN, Cards.Suit.SPADES));
        dealer2.addCard(new Cards.Card(Cards.Rank.NINE, Cards.Suit.DIAMONDS)); // 19

        game.whoIsWinner(player2, dealer2, scores);
        assertEquals(3, scores[0]); //
        assertEquals(4, scores[1]); // дилер +1
    }

}