package com.lab.spych.mpt.uniwersalne;

import com.lab.spych.mpt.uniwersalne.alarmy.Czas;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * To i owo, głównie stałe i placeholdery wszystko statyczne.
 * Created by spych on 2017-04-13.
 */

    ///TODO! zapisywanie zadan do pliku tekstowego (modyfikowanie harmonogramu z poziomu aplikacji) (wartstyw idą tak: nadpisywanie - przesuwanie zadan itd)
    ///TODO operowanie na wielu plikach Listy Zadan (niski priorytet zadania)
    ///TODO! alarmy do harmonogramu i widoczny stoper (moze uzyc jakiegos odpowiednika daemona w systemie android (chron table?)
    ///TODO Kolejny krok: zrobienie widoku, w ktorym poszczegolne zadania maja rozne kolory (losowe) i czas, ktory mam im poswiecic jest odwzorowany przez wielkosc ich pol na diagramie (zebym od razu widzial co mi zajmuje ile czasu)
    ///TODO Kolejny krok: zapisywanie jak mi idzie realizacja własnych planów (analiza jakie zadania wchodzą w paradę oraz z którymi zadaniami nie wyrabiam się w wyznaczonym terminie)
    ///TODO ULTIMATE: 1. Porządek w projekcie 2. Modyfikacja planu z poziomu aplikacji (w sposób, który będzie przenośny na inne platformy!) 3. Alarmy, stopery, timery, caly czas, tak żeby to było stabilne (i najlepiej jedna metoda, która zostanie odpalona w osobnym wątku z zewnątrz silnika) 4. Lepsza reprezentacja graficzna 5.Przeniesienie na komputer i zdalna synchronizacja przez internet, bluetooth i inne rzeczy (jakie to jest świetne ćwiczenie!)
public class Util {

    /**
     * pod tym kluczem w SharedPreferences zapisywane są uwagi użytkonika co do aplikacji jako String w formacie: <br/>
     * TekstPierwszejUwagi{@value Util#SEPARATOR}TekstDrugiejUwagi{@value Util#SEPARATOR}
     */
    public static final String SHARED_PREFERENCES_BUGREPORTING = "Bugreporting";

    public static final String SHARED_PREFERENCES_PLIK = "PlikSharedPreferences";

    public static final String SHARED_PREFERENCES_LISTA_ZADAN = "PlikListyZadan";

    public static final String SHARED_PREFERENCES_TYTUL_LISTY = "PlikListyZadan";

    /**
     * Separator uzywany w pliku Listy Zadan.
     */
    public static final String SEPARATOR = "@separator@";

    public static final String PLACEHOLDER_OPIS = "Brak opisu.";

    public static final String PLACEHOLDER_TYTUL = "Brak tytułu.";

    public static final Czas PLACEHOLDER_GODZINA_RANO = new Czas("0:10:00");

    public static final Czas PLACEHOLDER_GODZINA_WIECZOR = new Czas("0:20:00");

    /**
     * Prosty manekin linijki z pliku Listy Zadan, symbolizujący błąd
     */
    private static final String ZADANIE_DUMMY = "Błąd" + SEPARATOR + PLACEHOLDER_GODZINA_RANO + SEPARATOR + PLACEHOLDER_GODZINA_WIECZOR + SEPARATOR + "To jest ZADANIE_DUMMY, najwyraźniej wystąpił błąd.";

    /**
     * Prosty manekin linijki z pliku Listy Zadan, symbolizujący błąd, przetworzony na stream.
     * ///TODO NEXT niech to zadziała
     */
    public static final InputStream ZADANIE_DUMMY_STREAM = new ByteArrayInputStream(ZADANIE_DUMMY.getBytes(StandardCharsets.UTF_8)); /// ta linijka zamienia string na instream

    /**
     * Ten strumień bierze wszystkie przepuszczone przez niego dane i sromotnie je ignoruje.
     */
    public static final OutputStream STRUMIEN_DO_NIKAD = new OutputStream() {
        @Override
        public void write(int b) throws IOException {

        }
    };
}
