package ru.ac.uniyar.counter;

import org.junit.Test;
import static org.junit.Assert.*;

public class CounterTest {

    private Counter counter = new Counter();

    @Test
    public void counterIsInitiallyReset() {
        assertEquals(0, counter.getValue());
    }

    @Test
    public void increaseIncreasesTheCounter() {
        counter.increase();
        assertEquals(1, counter.getValue());
    }

    @Test
    public void resetResetsTheCounter() {
        counter.increase();
        assertTrue(counter.getValue() != 0);
        counter.reset();
        assertEquals(0, counter.getValue());
    }

}
