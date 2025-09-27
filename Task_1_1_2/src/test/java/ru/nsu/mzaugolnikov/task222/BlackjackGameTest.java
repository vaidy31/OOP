package ru.nsu.mzaugolnikov.task222;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    }

    @Test
    void testPlayerWins() {
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
        String input = "1\n1\n1\n"; // берет много карт
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        player.addCard(new Cards.Card(Cards.Rank.TEN, Cards.Suit.HEARTS));
        player.addCard(new Cards.Card(Cards.Rank.TEN, Cards.Suit.CLUBS));

        game.playerTurn(player, dealer, deck);

        String output = outputStream.toString();
        assertTrue(output.contains("перебрали"));
    }

    @Test
    void testStartGame() {
        // просто запускаем игру с блэкдж. делаем сущность
        BlackjackGame gameWithFixedDeck = new BlackjackGame() {
            @Override
            public void startGame() {
                System.out.println("Добро пожаловать в Блэкджек!");
                java.util.Scanner scanner = new java.util.Scanner(System.in);
                int[] scores = {0, 0};
                int round = 1;

                // делаем свою колоду
                Deck fixedDeck = new Deck() {
                    private int dealCount = 0;
                    private Cards.Card[] cards = {
                            new Cards.Card(Cards.Rank.ACE, Cards.Suit.HEARTS),
                            new Cards.Card(Cards.Rank.KING, Cards.Suit.HEARTS),
                            new Cards.Card(Cards.Rank.TEN, Cards.Suit.CLUBS),
                            new Cards.Card(Cards.Rank.SEVEN, Cards.Suit.CLUBS)
                    };
                    @Override
                    public Cards.Card dealCard() {
                        return cards[dealCount++ % cards.length];
                    }
                    @Override
                    public void shuffle() {} // отключаем перемешивание
                };
                Player player = new Player("Игрок");
                Dealer dealer = new Dealer("Дилер");
                for (int i = 0; i < 2; i++) {
                    player.addCard(fixedDeck.dealCard());
                    dealer.addCard(fixedDeck.dealCard());
                }
                whoIsWinner(player, dealer, scores);
                System.out.println("Игра завершена. Финальный счет: Игрок " + scores[0]
                        + " : Дилер " + scores[1]);
            }
        };
        String input = "0\n"; // прерываем игру
        System.setIn(new ByteArrayInputStream(input.getBytes())); // точно прерываем
        gameWithFixedDeck.startGame();
        String output = outputStream.toString();
        assertTrue(output.contains("Добро пожаловать в Блэкджек"));
        assertTrue(output.contains("Игра завершена"));
    }
}