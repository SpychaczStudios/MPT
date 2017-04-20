package com.lab.spych.mpt.uniwersalne.lista_zadan;

import com.lab.spych.mpt.dlaAndroida.PlikListyZadanAndroid;
import com.lab.spych.mpt.uniwersalne.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

/**
 * Klasa pozwala na wszelkie operacje zwiazane z listą zadań TODO NEXT przesuwanie, anulowanie zadań, dodawanie zadań, wszystko w formie prostego interfejsu
 * Klasa ta zostaje potem łatwo zapisana do pamięci w zależności od systemu oraz używanego medium zapisu (SQL, FTP, txt itd) przez implementacje abstrakcyjnej klasy PlikListyZadan (implementacja zalezy od tych własnie wymienionych czynników (system-dependent))
 * Created by spych on 2017-04-13.
 */

public class ListaZadan extends ArrayList<Zadanie> {
    private String tytul;

    ///KONSTRUKTORY

    /**
     * Prosty konstruktor, uzyskuje liste zadan z pliku Listy Zadan. Pierwsza linijka zawiera tytul listy zadan.
     *
     * @param plik - zbudowany w odpowiedni sposob plik, z ktorego przeprasowywane sa zadania.
     */
    public ListaZadan(PlikListyZadanAndroid plik) throws IOException {

        Scanner skan = plik.getSkaner();
        if(skan.hasNextLine()) tytul = skan.nextLine();
        while (skan.hasNextLine()) {
            String tmp = skan.nextLine();
            Scanner skan2 = new Scanner(tmp);
            skan2.useDelimiter(Util.SEPARATOR);
            Zadanie zadanie = new Zadanie(skan2);
            add(zadanie); ///dodaje zadanie do listy zadan
        }
        skan.close();
    }

    /**
     * Konstruuje pustą listę zadań, do której następnie można dodać nieograniczoną liczbę zadań metodą add()
     */
    public ListaZadan(){

    }

    ///GETTERY I SETTERY

    public String getTytul() {
        return tytul;
    }

    public void setTytul(String tytul) {
        this.tytul = tytul;
    }

    ///SORTOWANIE

    public void dodajAndPosortujWgCzasuRozpoczecia(Zadanie z){
        this.add(z);
        this.sortujCzasRozpoczecia();
    }

    /**
     * Sortuje listę według czasu rozpoczęcia poszczególnych zadań. POrządek chronologiczny.
     */
    private void sortujCzasRozpoczecia(){
        Collections.sort(this, new ZadaniaWedlugPoczatkuZadania());
    }
}

/**
 * Ten komparator porównuje zadania porównując ich czasy rozpoczecia. Porządek chronologiczny.
 */
class ZadaniaWedlugPoczatkuZadania implements Comparator<Zadanie>{

    @Override
    public int compare(Zadanie o1, Zadanie o2) {
        return o1.getStart().compareTo(o2.getStart());
    }
}