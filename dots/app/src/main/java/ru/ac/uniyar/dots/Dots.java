package ru.ac.uniyar.dots;

import java.util.LinkedList;
import java.util.List;

import ru.ac.uniyar.dots.Dot;

/** A list of dots. */
public class Dots {

    /** DotChangeListener. */
    public interface DotsChangeListener {
        /** @param dots the dots that changed. */
        void onDotsChange(Dots dots);
    }

    private final LinkedList<Dot> dots = new LinkedList<Dot>();

    private DotsChangeListener dotsChangeListener;

    /** @param listener set the change listener. */
    public void setDotsChangeListener(DotsChangeListener listener) {
        dotsChangeListener = listener;
    }

    /** @return the most recently added dot. */
    public Dot getLastDot() {
        return (dots.size() <= 0) ? null : dots.getLast();
    }

    /**
     * @param x dot horizontal coordinate.
     * @param y dot vertical coordinate.
     * @param color dot color.
     * @param diameter dot size.
      */
    public void addDot(Dot dot) {
        dots.add(dot);
        notifyListener();
    }

    /** @return list of dots */
    public List<Dot> getDots() {
        return dots;
    }

    /** Remove all dots. */
    public void clearDots() {
        dots.clear();
        notifyListener();
    }

    private void notifyListener() {
        if (null != dotsChangeListener) {
            dotsChangeListener.onDotsChange(this);
        }
    }
}
