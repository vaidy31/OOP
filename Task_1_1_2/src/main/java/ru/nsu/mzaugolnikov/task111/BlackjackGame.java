package ru.nsu.mzaugolnikov.task111;

import java.util.Scanner;

public class BlackjackGame {
    public static void main(String[] args) {
        BlackjackGame game = new BlackjackGame();
        game.startGame();
    }


    public void playerTurn(Player player, Dealer dealer, Deck deck) {
        Scanner scanner = new Scanner(System.in);
        boolean playerStands = false;

        while (!playerStands && !player.isBusted()) {
            System.out.println("Ваши карты: " + player.showCards(false) + " ==> " + player.getTotal());
            System.out.println("Карты дилера: " + dealer.startHiddenHand());
            System.out.print("Введите 1, чтобы взять карту, 0 чтобы остановиться: ");
            int choice = scanner.nextInt();

            if (choice == 1) {
                Cards.Card newCard = deck.dealCard();
                player.addCard(newCard);
                System.out.println("Вы открыли карту " + newCard);
                if (player.isBusted()) {
                    System.out.println("Вы перебрали! Ваши карты: " + player.showCards(false) + " > " + player.getTotal());
                }
            } else {
                playerStands = true;
            }
        }
    }

    public void dealerTurn(Dealer dealer, Deck deck) {
        System.out.println("Ход дилера:");
        System.out.println("-------------");

        System.out.println("Дилер открывает закрытую карту");
        System.out.println("Карты дилера: " + dealer.showAllHand() + " > " + dealer.getTotal());

        while (dealer.getTotal() < 17) {
            Cards.Card newCard = deck.dealCard();
            dealer.addCard(newCard);
            System.out.println("Дилер открывает карту " + newCard);
            System.out.println("Карты дилера: " + dealer.showAllHand() + " > " + dealer.getTotal());
        }

        if (dealer.isBusted()) {
            System.out.println("Дилер перебрал!");
        }
    }



    public void whoIsWinner(Player player, Dealer dealer, int[] scores) {
        int playerTotal = player.getTotal();
        int dealerTotal = dealer.getTotal();

        int playerWins = 0;
        int flag = 0;

        if (player.isBusted()) {
            System.out.println("Вы проиграли! Перебор.");
        } else if (dealer.isBusted()) {
            System.out.println("Вы выиграли! Дилер перебрал.");
            playerWins++;
        } else if (playerTotal > dealerTotal) {
            System.out.println("Вы выиграли! Ваши очки: " + playerTotal + ", очки дилера: " + dealerTotal);
            playerWins++;
        } else if (playerTotal < dealerTotal) {
            System.out.println("Вы проиграли! Ваши очки: " + playerTotal + ", очки дилера: " + dealerTotal);
        } else {
            System.out.println("Ничья! Оба набрали " + playerTotal + " очков.");
            flag++;
        }
        if (playerWins == 1) {
            scores[0]++;
        } else if (flag == 0) {
            scores[1]++;
        }
        System.out.println("Счет " + scores[0] + ":" + scores[1] + " в вашу пользу.");
    }

    public void startGame() {
        System.out.println("Добро пожаловать в Блэкджек!");
        Scanner scanner = new Scanner(System.in);
        int[] scores = {0, 0}; // [playerScore, dealerScore]
        int round = 1;

        boolean continuePlaying = true;

        while (continuePlaying) {
            System.out.println("\n------------");
            System.out.println("Раунд " + round);
            System.out.println("-------------");

            Deck deck = new Deck();
            deck.shuffle();
            Player player = new Player("Игрок");
            Dealer dealer = new Dealer("Дилер");

            for (int i = 0; i < 2; i++) {
                player.addCard(deck.dealCard());
                dealer.addCard(deck.dealCard());
            }

            System.out.println("Дилер раздал карты");
            System.out.println("Ваши карты: " + player.showCards(false) + " > " + player.getTotal());
            System.out.println("Карты дилера: " + dealer.startHiddenHand());

            if (player.isBlackjack() || dealer.isBlackjack()) {
                whoIsWinner(player, dealer, scores);
            } else {
                System.out.println("\nВАш ход");
                playerTurn(player, dealer, deck);
                if (!player.isBusted()) {
                    dealerTurn(dealer, deck);
                }
                whoIsWinner(player, dealer, scores);
            }

            // Спрашиваем, продолжать ли играть
            System.out.print("Хотите сыграть еще один раунд? (1 - да, 0 - нет): ");
            int choice = scanner.nextInt();
            continuePlaying = choice == 1;
            round++;
        }

        System.out.println("Игра завершена. Финальный счет: Игрок " + scores[0] + " : Дилер " + scores[1]);
    }
}