package ru.nsu.mzaugolnikov.task222;

import java.util.ArrayList;
import ru.nsu.mzaugolnikov.task222.Cards.Card;

/**
 * Абстрактный класс руки.
 * Общая логика игрока для игрока и дилера.
 */
public abstract class Hand {
    private final ArrayList<Cards.Card> hand;
    int basedScore = 21;

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
        while (total > basedScore && aceCount > 0) {
            total -= 10;
            aceCount--;
        }
        return total;
    }

    /**
     * Перебор или нет (больше basedscore).
     *
     * @return true/false
     */
    public boolean isBusted() {
        return  getTotal() > basedScore;
    }

    /**
     * БлэкДжек или нет.
     *
     * @return true/false
     */
    public boolean isBlackjack() {
        return hand.size() == 2 && getTotal() == basedScore;
    }

    /**
     * Очистка руки.
     */
    public void clear() {
        hand.clear();
    }

    /**
     * Строковое представление карт в руке.
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
        sb.append("]");
        return sb.toString();
    }

    /**
     * геттер для получения размера руки.
     *
     * @return размер руки
     */
    public int getHandSize() {
        return hand.size();
    }

    /**
     * получение карты по индексу.
     */
    public Cards.Card getCard(int index) {
        if (index < 0 || index >= hand.size()) {
            return null;
        }
        return hand.get(index);
    }


}
