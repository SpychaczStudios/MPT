package com.lab.spych.mpt.uniwersalne.lista_zadan;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Scanner;

/**
 * Klase tą nalezy rozszerzyć w zalezności od używanego systemu i sposobu pozyskiwania pliku.
 * Wszystkie klasy dziedziczace po tej, mają pozwolić mi uzyskać niezawodny dostęp do pliku, nadpisywac, oraz czytac, a także TODO tworzyć nowy plik w razie, jeśli stary nie jest dostępny.
 * Ta klasa troszczy się o tępe zapisywanie obiektu Listy Zadań w pliku tekstowym
 * Created by spych on 2017-04-14.
 */

public abstract class PlikListyZadan {
    /**
     * Tworzy nową instancję klasy Scanner
     * @return Scanner ustawiony tak, by zczytywał kolejne linie z pliku ListyZadan.
     */
    protected Scanner getSkaner() {
        return new Scanner(otworzInputStream());
    }

    /**
     * Tą metodę należy zaimplementować system-dependent.
     * @return - strumien danych z kolejnymi linijkami reprezentującymi kolejne zadania
     */
    protected abstract InputStream otworzInputStream();

    /**
     * Nadpisuje plik, do ktorego odnosi sie ten Obiekt, podanym obiektem listy zadan.
     * W przypadku, gdy plik, do którego odwołuje się obiekt jeszcze nie istnieje, tworzy nowy plik.
     * Uwaga: zawartość pliku zostanie usunięta
     * @param listaZadan lita zadan, z której wzięte zostanie zadanie i kolejność
     */
    public void zapisz(ListaZadan listaZadan) throws IOException {
        OutputStreamWriter writer = stworzOutputStreamWriter();
        writer.write(String.format(listaZadan.getTytul() + "%n"));
        for(Zadanie z : listaZadan){
            writer.write(String.format(z.toString() + "%n"));
            Log.i("toString", z.toString());
        }
        writer.close();
    }

    private OutputStreamWriter stworzOutputStreamWriter(){
        return new OutputStreamWriter(otworzOutputStream());
    }
    /**
     * Tą metodę należy zaimplementować system-dependent. Wywołanie tej metody najczęściej będzie powodowało skasowanie całej zawartości pliku.
     * @return strumien pozwalajacy zapisac do pliku
     */
    protected abstract OutputStream otworzOutputStream();
}
