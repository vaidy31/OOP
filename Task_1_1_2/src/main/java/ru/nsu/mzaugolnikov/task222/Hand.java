package ru.nsu.mzaugolnikov.task222;

import java.util.ArrayList;
import ru.nsu.mzaugolnikov.task222.Cards.Card;

/**
 * Абстрактный класс руки
 * Общая логика игрока для игрока и дилера
 */
public abstract class Hand {
    private final ArrayList<Cards.Card> hand;

    /**
     * Конструктор руки. Создает пустую руку.
     */
    public Hand() {
        hand = new ArrayList<>();
    }

    /**
     * Добавляет карту в руку.
     *
     * @param card карта
     */
    public void addCard(Card card) {
        if (card != null) {
            hand.add(card);
        }
    }

    /**
     * Вычисляет сумму очков на руке с учетом особых правил для тузов.
     *
     * @return сумма очков
     */
    public int getTotal() {
        int total = 0;
        int aceCount = 0;
        for (Card card : hand) {
            total += card.getRank().getValue();

            if (card.getRank() == Cards.Rank.ACE) {
                aceCount++;
            }
        }
        while (total > 21 && aceCount > 0) {
            total -= 10;
            aceCount--;
        }
        return total;
    }

    /**
     * Перебор или нет (> 21)
     *
     * @return true/false
     */
    public boolean isBusted() {
        return  getTotal() > 21;
    }

    /**
     * Блэкджек?
     *
     * @return true/false
     */
    public boolean isBlackjack() {
        return hand.size() == 2 && getTotal() == 21;
    }

    /**
     * Очистка руки
     */
    public void clear() {
        hand.clear();
    }

    /**
     * Строковое представление карт в руке
     *
     * @param hideFstCard (для дилера) скрытие первой карты
     * @return строчка
     */
    public String showCards(boolean hideFstCard) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < hand.size(); i++) {
            if (i == 0 && hideFstCard) {
                sb.append("<закрытая карта>");
            } else {
                sb.append(hand.get(i).toString());
            }

            if (i < hand.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append( "]");
        return sb.toString();
    }
    /**
     * Возвращает копию списка карт на руке.
     *
     * @return копия списка карт
     */
    public ArrayList<Cards.Card> getHand() {
        return new ArrayList<>(hand); //копия чтобы не изменять исходный массив
    }
}
