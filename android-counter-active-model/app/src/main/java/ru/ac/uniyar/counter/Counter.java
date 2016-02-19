package ru.ac.uniyar.counter;

public class Counter {
    private int value;
    public Counter() {
        value = 0;
    }
    public interface OnModificationListener {
        void onModification(Counter sender);
    }
    private OnModificationListener listener = null;
    public void setOnModificationListener(OnModificationListener listener) {
        this.listener = listener;
    }
    public int getValue() {
        return value;
    }
    public void increase() {
        value++;
        if (listener != null) { listener.onModification(this); }
    }
    public void reset() {
        value = 0;
        if (listener != null) { listener.onModification(this); }
    }
}
