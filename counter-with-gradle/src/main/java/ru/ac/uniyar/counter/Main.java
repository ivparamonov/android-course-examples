package ru.ac.uniyar.counter;

public class Main {

    public static void main(String[] args) {
        Counter counter = new Counter();
        System.out.println("Counter's initial value: " + counter.getValue());
        counter.increase();
        System.out.println("Counter's value after increase: " + counter.getValue());
        counter.reset();
        System.out.println("Counter's value after reset: " + counter.getValue());
    }
}
