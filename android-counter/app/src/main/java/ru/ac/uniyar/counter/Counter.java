package ru.ac.uniyar.counter;

public class Counter {
    private int value;
    public Counter() {
        value = 0;
    }
    public int getValue() {
        return value;
    }
    public void increase() {
        value++;
    }
    public void reset() {
        value = 0;
    }
}
