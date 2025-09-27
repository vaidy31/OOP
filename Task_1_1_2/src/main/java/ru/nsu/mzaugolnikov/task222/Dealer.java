package ru.nsu.mzaugolnikov.task111;

public class Dealer extends Hand{
    private final String name;

    public Dealer(String name) {
        super();
        this.name = name;
    }

    public void playTurn(Deck deck) {
        while (getTotal() < 17) {
            addCard(deck.dealCard());
        }
    }

    public String startHiddenHand() {
        StringBuilder Sb = new StringBuilder("[");
        if (!getHand().isEmpty()) {
            Sb.append(getHand().get(0).toString()).append(", <закрытая крата>");
        }
        Sb.append("]");
        return  Sb.toString();
    }

    public String showAllHand() {
        return showCards(false);
    }
}
