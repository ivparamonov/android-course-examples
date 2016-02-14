package ru.ac.uniyar.counter;

public class Counter {

    private int value = 0;

    public Counter() { }

    public void increase() { ++value; }

    public void reset() { value = 0; }

    public int getValue() { return value; }

}
