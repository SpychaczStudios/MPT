package com.lab.spych.mpt.dlaAndroida;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import com.lab.spych.mpt.uniwersalne.Util;
import com.lab.spych.mpt.uniwersalne.lista_zadan.PlikListyZadan;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Zaimplementowana klasa abstrakcyjna. Dostarcza warstwy fizycznej dla Androida.
 * Created by spych on 2017-04-14.
 */

public class PlikListyZadanAndroid extends PlikListyZadan {
    private final Context kontekst;
    private final Uri uri;

    public PlikListyZadanAndroid(Uri u, Context k){
        kontekst = k;
        uri = u;
    }

    /**
     * W razie niepowodzenia informuje uzytkownika o błędzie odczytu Toastem i zwraca manekina zadania , aby uniknąć dalszych błędów w aplikacji.
     * @return Strumien z pliku wskazanego przez Uri. W wypadku niepowodzenia strumien ze Stringa w którym zawarte jest dokładnie jedno zadanie, opisane jako błąd.
     */
    @Override
    public InputStream otworzInputStream() {
        try {
            return kontekst.getContentResolver().openInputStream(uri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(kontekst,"Błąd czytania pliku Listy Zadań.", Toast.LENGTH_SHORT).show();
            return Util.ZADANIE_DUMMY_STREAM;
        }
    }

    /**
     * W przypadku niepowodzenia zwraca strumień do nikąd i informuje użytkownika o niepowodzeniu zapisu do pliku Toastem.
     * @return Strumien do pliku wskazanego przez Uri. W przypadku niepowodzenia, strumien do nikad (aby uniknać dalszych błędów)
     */
    @Override
    public OutputStream otworzOutputStream() {
        try {
            return kontekst.getContentResolver().openOutputStream(uri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(kontekst,"Błąd zapisu do pliku Listy Zadań.", Toast.LENGTH_SHORT).show();
            return Util.STRUMIEN_DO_NIKAD;
        }
    }
}
