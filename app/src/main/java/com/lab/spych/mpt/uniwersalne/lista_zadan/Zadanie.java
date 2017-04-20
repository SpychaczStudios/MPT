package com.lab.spych.mpt.uniwersalne.lista_zadan;

import android.support.annotation.Nullable;

import com.lab.spych.mpt.uniwersalne.Util;
import com.lab.spych.mpt.uniwersalne.alarmy.Czas;

import java.io.Serializable;
import java.util.Scanner;

/**
 * Klasa pojedynczego Zadania.
 * Created by spych on 2017-04-13.
 */

public class Zadanie implements Serializable {
    private String tytul;
    private Czas start;
    private Czas deadline;
    private String opis;

    ///KONSTRUKTORY i toString()

    /**
     * Konstruktor, tworzący Zadanie ze Scannera jednej linijki pliku Listy Zadan.
     * @param skan skaner ma linijkę odpowiadającą jednemu Zadaniu
     */
    public Zadanie(Scanner skan){
        this(skan.next(),skan.next(),skan.next(),skan.next());
        skan.close();
    }

    /**
     * Konstruktor tworzy Zadanie z serii stringów odpowiadających polom.
     * @param tytul
     * @param start
     * @param deadline
     * @param opis
     */
    private Zadanie(String tytul, String start, String deadline, String opis){
        this(tytul, new Czas(start), new Czas(deadline), opis);
    }

    /**
     * Konstruuje zadanie z elementów odpowiedniego typu.
     * @param tytul
     * @param start
     * @param deadline
     * @param opis
     */
    private Zadanie(String tytul, Czas start, Czas deadline, String opis){
        this(); ///Tutja umieszczamy placeholdery
        this.setTytul(tytul);
        this.setStart(start);
        this.setDeadline(deadline);
        this.setOpis(opis);
    }

    /**
     * Metoda używa placeholderów, dlatego, że zapis bez któregoś elementu może powodować błedy.
     */
    public Zadanie(){
        this.tytul = Util.PLACEHOLDER_TYTUL;
        this.opis = Util.PLACEHOLDER_OPIS;
        this.start = Util.PLACEHOLDER_GODZINA_RANO;
        this.deadline = Util.PLACEHOLDER_GODZINA_WIECZOR;
    }

    ///METODY EDYCJI

    /**
     * Przesuwa w czasie start i deadline zadania o równą ilość minut i/lub godzin i/lub dób
     */
    public void opoznij(int minuty, int godziny, int doby){
        getStart().zmienCzas(minuty, godziny, doby);
        getDeadline().zmienCzas(minuty, godziny, doby);
    }

    ///GETTERY I SETTERY

    public String getTytul() {
        return tytul;
    }

    /**
     * @param tytul jeśli tytul.isEmpty(), wówczas metoda nic nie zmienia
     */
    public void setTytul(String tytul) {
        this.tytul = (tytul.isEmpty()) ? this.tytul : tytul;
    }

    /**
     * Czas planowanego rozpoczęcia zadania.
     */
    public Czas getStart() {
        return start;
    }

    /**
     * @param start jeśli Null metoda nic nie robi
     */
    public void setStart(@Nullable Czas start) {
        this.start = (start == null) ? this.start : start;
    }

    /**
     * Czas planowanego akończenia zadania.
     */
    public Czas getDeadline() {
        return deadline;
    }

    /**
     * @param deadline jeśli Null, metoda nic nie robi
     */
    public void setDeadline(@Nullable Czas deadline) {
        this.deadline = (deadline == null) ? this.deadline : deadline;
    }


    /**
     * @return opis Zadania
     */
    public String getOpis() {
        return opis;
    }

    /**
     * @param opis jeśli opis.isEmpty(), wówczas metoda nic nie zmienia
     */
    public void setOpis(String opis) {
        this.opis = (opis.isEmpty()) ? this.opis : opis;
    }

    ///UTIL

    /**
     * @return String odpowiadający formatowi pliku Listy Zadan.
     */
    public String toString () {
        return getTytul() + Util.SEPARATOR + getStart().toString() + Util.SEPARATOR + getDeadline().toString() + Util.SEPARATOR + getOpis();
    }

}
