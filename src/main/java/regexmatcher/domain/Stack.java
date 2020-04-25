package regexmatcher.domain;

import java.util.NoSuchElementException;

/**
 * Pino tietorakenne.
 *
 * @param <T> Pinoon talletettavan datan tyyppy.
 */
public class Stack<T> {

    private T[] stack;
    private int size;

    /**
     * Luokan konstruktori, joka alustaa pinon.
     */
    public Stack() {
        stack = (T[]) new Object[20];
        size = 0;
    }

    /**
     * Lisää annetun objektin pinon päälimmäiseksi.
     *
     * @param t Pinon päälimmäiseksi asetettava objekti.
     */
    public void push(T t) {
        if (size == stack.length) {
            growStack();
        }
        stack[size] = t;
        size++;
    }

    /**
     * Kasvattaa pinon kapasiteettia.
     */
    private void growStack() {
        T[] newStack = (T[]) new Object[(int) (stack.length * 1.5)];
        for (int i = 0; i < stack.length; i++) {
            newStack[i] = stack[i];
        }
        stack = newStack;
    }

    /**
     * Palauttaa pinossa olevien objektien määrän.
     *
     * @return Int, joka kertoo pinossa olevien objektien määrän.
     */
    public int size() {
        return size;
    }

    /**
     * Palauttaa pinon pääillmäisen objektin poistamatta sitä.
     *
     * @return Objekti, joka on pinon päälimmäisenä.
     */
    public T peek() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return stack[size - 1];
    }

    /**
     * Palauttaa pinon pääillmäisen objektin samalla poistaen sen pinosta.
     *
     * @return Objekti, joka on pinon päälimmäisenä.
     */
    public T pop() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        T t = stack[size - 1];
        stack[size - 1] = null;
        size--;
        return t;
    }

    /**
     * Palauttaa totuusarvon, joka kertoo, onko pino tyhjä.
     *
     * @return Totuusarvo, joka kertoo, onko pino tyhjä.
     */
    public boolean isEmpty() {
        return size == 0;
    }

}
