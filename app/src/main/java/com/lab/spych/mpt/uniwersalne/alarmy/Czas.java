package com.lab.spych.mpt.uniwersalne.alarmy;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Scanner;

/**
 * <p>Czas względem obecnie trwającej doby. Zawiera informacje o określonej minucie określonej godziny dnia.
 * W obiekcie istnieje również pole doby. Wskazuje ona na dobę do której obiekt się odnosi.</p>
 * Created by spych on 2017-04-13.
 * @see Czas#doba
 */

public class Czas implements Serializable, Comparable<Czas> {
    /**
     * Do ilu dób do przodu odnosi się obiekt czasu dnia. Zatem jeśli jest dzień 20 czerwca roku 2017 to obiekt czasu dnia
     * o wartościach doba = 2; godzina = 6; minuta = 34; wskazuje na godzinę 6:34 dnia 22 czerwca roku 2017. Obiekt taki ma swoją reprezentację w pliku ListyZadań w postaci: 2:6:34.
     * Obiekt doby wskazuje zatem na ilość dób do przodu (lub do tyłu w przypadku wartości ujemnych)
     */
    private int doba;
    private int godzina;
    private int minuta;

    ///KONSTRUKTORY

    /**
     * @param s String z którego zostanie skonstruowany obiekt string w formie doba:godzina:minuta
     */
    public Czas(String s){
        Scanner skan = new Scanner(s);
        skan.useDelimiter(":");
        doba = skan.nextInt();
        godzina = skan.nextInt();
        minuta = skan.nextInt();
    }

    /**
     * Konstruktor automatycznie ustawia dobę na wartość 0.
     * @param godzina godzina danej doby
     * @param minuta minuta danej godziny danej doby
     */
    public Czas(int godzina, int minuta){
        this.godzina = godzina;
        this.minuta = minuta;
        this.doba = 0;

    }

    /**
     * @param godzina godzina danej doby
     * @param minuta minuta danej godziny danej doby
     * @param doba jesli wartość dodatnia, to obiekt będzie odnosił się do odpowiedniej kolejnej doby (więcej w dokumentacji klasy)
     */
    public Czas(int godzina, int minuta, int doba){
        this.godzina = godzina;
        this.minuta = minuta;
        this.doba = doba;

    }

    public Czas(){}

    ///MODYFIKACJE PÓL

    /**
     * Metoda przesuwa "zegar" czasu dnia do przodu lub do tyłu o określoną ilość minut dodać określoną ilość godzin.
     * Ujemne argumrnty przesuwają zegar do tyłu. Przesunięcie jest sumą obydwu argumentów (zmienCzas(350,3) przesuwa zegar o 8 godzin i 10 minut do przodu).
     * Przesunięcie czasu poza granicę jednej doby zmienia parametr doby
     * @param minuty minuty o które nalezy przesunąć zegar - ujemne przesuwają zegar do tyłu (jeśli |x| > 59, egar zostaje przesunięty o odpowiednią ilość godzin)
     * @param godziny godziny o które należy przesunąć zegar - może (i często będzie) równe 0, ujemne wartości przesuwają zegar do tyłu
     * @param doba doby o które należy przesunąć czas dnia
     */
    public void zmienCzas(int minuty, int godziny, int doby){
        setDoba(getDoba()+doby);
        setGodzina(getGodzina()+godziny);
        setMinuta(getMinuta()+minuty);
    }

    ///INNE

    /**
     * Zwraca czytelną dla człowieka formę czasu. Dodaje opis doby (np. "23:00 jutro" lub "10:30 3 dni temu").
     *  @return opis czasu w czytelnej dla człowieka formie
     *  @see Czas#doba
     */
    public String wezFormeOpisowa(){
        String ret = godzina + ":" + minuta;
        if (doba<-1) return ret + " " + -1*doba + " dni temu";
        else if (doba==-1) return ret + "wczoraj";
        if(doba==0) return ret;
        else if (doba==1) return ret + " jutro";
        else return ret + " za " + doba + " dni";
    }

    /**
     * @return doba
     */
    ///GETTERY I SETTERY


    public int getDoba() {
        return doba;
    }

    public void setDoba(int doba) {
        this.doba = doba;
    }

    /**
     * @return godzina dnia
     */
    public int getGodzina() {
        return godzina;
    }

    /**
     * @return minuta godziny dnia
     */
    public int getMinuta() {
        return minuta;
    }

    /**
     * Ustawia wartość godziny w obiekcie,
     * @param g godzina dnia jeśli g >= 24 to wówczas następuje dodanie odpowiedniej ilości dób
     */
    public void setGodzina(int g){
        setDoba(getDoba()+g/24);
        godzina = g%24;
    }

    /**
     * Ustawia wartość minut w obiekcie. Jeśli ustawiana wartość minut przekracza 59, wówczas następnue zmiana godziny
     *@param m minuta godziny dnia
     */
    public void setMinuta (int m) {
        setGodzina(getGodzina()+m/60); /// dodaje liczbę odzin zawartych w m (możliwe, że zero), set godzina robi to samo z godzinami i dobami
        minuta = m%60;
    }
    ///UTIL

    /**
     * @return Czas w formacie: doba:godzina:minuta
     */
    public String toString(){
        String ret = String.valueOf(getDoba()) + ":" + String.valueOf(getGodzina()) + ":";
        if(getMinuta()<10)ret = ret + "0" +String.valueOf(getMinuta());
        else ret = ret + String.valueOf(getMinuta());
        return ret;
    }


    /**
     * Wcześniejsza godzina jest mniejsza niż godzina późniejsza.
     * 20:00 < 21:30
     * 20:00 < 20:10
     * 20:00 = 20:00
     * 20:00 > 19:00
     * @param o obiekt porównania
     * @return -1 jeśli obiekt jest mniejszy niż o, 0 jeśli obiekty równe, 1 jeśli obiekt większy od o
     */
    @Override
    public int compareTo(@NonNull Czas o) {
        ///TODO CHECK
        if(o.getGodzina() > this.getGodzina() || o.getGodzina() == this.getGodzina() && o.getMinuta() > this.getMinuta()) return -1;
        if(o.equals(this)) return 0;
        return 1;

    }

    public boolean equals(Object o){
        if(!o.getClass().equals(this.getClass())) return false; ///Jeśli rózne klasy TODO CHECK
        Czas o2 = (Czas) o;
        return o2.getGodzina() == this.getGodzina() && o2.getMinuta() == this.getMinuta();
    }

}
