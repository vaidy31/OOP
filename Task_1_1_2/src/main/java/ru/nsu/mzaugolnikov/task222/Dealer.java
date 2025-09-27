package ru.nsu.mzaugolnikov.task222;

/**
 * Класс для дилера.
 * Наследует Hand
 */
public class Dealer extends Hand {
    private final String name;

    /**
     * Конструктор дилера
     *
     * @param name его имя лол
     */
    public Dealer(String name) {
        super();
        this.name = name;
    }

    /**
     * Возвращает строковое представление руки дилера с закрытой второй картой.
     * Используется в начале раунда, когда у дилера одна карта открыта, а вторая закрыта.
     *
     * @return строка с картами дилера, где вторая карта скрыта
     */
    public String startHiddenHand() {
        StringBuilder Sb = new StringBuilder("[");
        if (!getHand().isEmpty()) {
            Sb.append(getHand().get(0).toString()).append(", <закрытая крата>");
        }
        Sb.append("]");
        return  Sb.toString();
    }

    /**
     * Возвращает полное строковое представление всех карт дилера.
     *
     * @return строка со всеми картами дилера
     */
    public String showAllHand() {
        return showCards(false);
    }
}
