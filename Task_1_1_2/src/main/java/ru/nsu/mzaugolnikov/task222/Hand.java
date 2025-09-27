package ru.nsu.mzaugolnikov.task222;

import java.util.ArrayList;
import ru.nsu.mzaugolnikov.task222.Cards.Card;

public abstract class Hand {
    private final ArrayList<Cards.Card> hand;

    public Hand() {
        hand = new ArrayList<>();
    }

    public void addCard(Card card) {
        if (card != null) {
            hand.add(card);
        }
    }

    public int getTotal() {
        int total = 0;
        int aceCount = 0;
        for (Card card : hand) {
            total += card.getRank().getValue();

            if (card.getRank() == Cards.Rank.ACE){
                aceCount++;
            }
        }
        while (total > 21 && aceCount > 0) {
            total -= 10;
            aceCount--;
        }
        return total;
    }

    public boolean isBusted() {
        return  getTotal() > 21;
    }

    public boolean isBlackjack() {
        return hand.size() == 2 && getTotal() == 21;
    }

    public void clear() {
        hand.clear();
    }

    public String showCards(boolean hideFstCard){
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
    public ArrayList<Cards.Card> getHand() {
        return new ArrayList<>(hand); //копия чтобы не изменять исходный массив
    }
}
