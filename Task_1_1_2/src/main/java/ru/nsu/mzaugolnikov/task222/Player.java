package ru.nsu.mzaugolnikov.task222;

/**
 * Класс, представляющий пользователя (игрока).
 * Наследует Hand
 */
public class Player extends Hand{
    private final String name;

    /**
     * Конструктор
     *
     * @param name имя игрока
     */
    public Player(String name) {
        super();
        this.name = name;
    }
}
