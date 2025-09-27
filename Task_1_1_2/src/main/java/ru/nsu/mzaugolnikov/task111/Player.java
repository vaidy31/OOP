package ru.nsu.mzaugolnikov.task111;

public class Player extends Hand{
    private final String name;

    public Player(String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
