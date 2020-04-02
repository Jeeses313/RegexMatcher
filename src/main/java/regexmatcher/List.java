package regexmatcher;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Lista tietorakenne.
 *
 * @param <T> Listaan talletettavan datan tyyppy.
 */
public class List<T> {

    private T[] list;
    private int size;

    /**
     * Luokan konstruktori, joka alustaa listan.
     */
    public List() {
        list = (T[]) new Object[20];
        size = 0;
    }

    /**
     * Lisää annetun objektin listan loppuun.
     *
     * @param t Listan loppuun asetettava objekti.
     */
    public void add(T t) {
        if (size == list.length) {
            growList();
        }
        list[size] = t;
        size++;
    }

    /**
     * Kasvattaa listan kapasiteettia.
     */
    private void growList() {
        T[] newList = (T[]) new Object[(int) (list.length * 1.5)];
        for (int i = 0; i < list.length; i++) {
            newList[i] = list[i];
        }
        list = newList;
    }

    /**
     * Palauttaa listassa olevien objektien määrän.
     *
     * @return Int, joka kertoo listassa olevien objektien määrän.
     */
    public int size() {
        return size;
    }

    /**
     * Palauttaa annetussa indeksissä olevan objektin.
     *
     * @param index int, jonka osoittamasta kohdasta palautetaan objekti.
     * @return Objekti, joka on annetun indeksin osoittamassa kohdassa.
     */
    public T get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        return list[index];
    }

    /**
     * Palauttaa totuusarvon, joka kertoo, onko lista tyhjä.
     *
     * @return Totuusarvo, joka kertoo, onko lista tyhjä.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Palauttaa totuusarvon, joka kertoo, onko annettu objekti listassa.
     *
     * @param t Objekti, jonka listassa olemista tarkistetaan.
     * @return Totuusarvo, joka kertoo, onko annettu objekti listassa.
     */
    public boolean contains(T t) {
        for (int i = 0; i < size; i++) {
            if (list[i].equals(t)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Palauttaa kokonaisluvun, joka kertoo annettun objektin indeksin.
     * Palauttaa -1, jos objektia ei ole listassa.
     *
     * @param t Objekti, jonka indeksiä haetaan.
     * @return int, joka on annetun objektin indeksi. Palauttaa -1, jos objektia
     * ei ole listassa.
     */
    public int indexOf(T t) {
        for (int i = 0; i < size; i++) {
            if (list[i].equals(t)) {
                return i;
            }
        }
        return -1;
    }
}
