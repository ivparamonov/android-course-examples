package ru.ac.uniyar.counter;

import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

public class CounterTest {

    private Counter counter = new Counter();

    @Test
    public void counterIsInitiallyReset() {
        assertThat(counter.getValue()).isEqualTo(0);
    }

    @Test
    public void increaseIncreasesTheCounter() {
        counter.increase();
        assertThat(counter.getValue()).isEqualTo(1);
    }

    @Test
    public void resetResetsTheCounter() {
        counter.increase();
        assertThat(counter.getValue()).isNotEqualTo(0);
        counter.reset();
        assertThat(counter.getValue()).isEqualTo(0);
    }

}
