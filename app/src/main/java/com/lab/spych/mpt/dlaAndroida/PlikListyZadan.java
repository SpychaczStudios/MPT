//package com.lab.spych.mpt.naAndroida;
//
//import android.app.Activity;
//import android.content.ComponentName;
//import android.content.ContentResolver;
//import android.util.Log;
//
//import com.lab.spych.mpt.uniwersalne.ListaZadan;
//import com.lab.spych.mpt.uniwersalne.Util;
//import com.lab.spych.mpt.uniwersalne.lista_zadan.Zadanie;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.PrintWriter;
//import java.net.URI;
//import java.util.Scanner;
//
///**
// * Plik listy zadan, klasa pozwala zarzadzac plikami listy zadan i zapisywac
// * Created by spych on 2017-04-13.
// */
//
//public class PlikListyZadan extends File {
//    Activity activity;
//    public PlikListyZadan(String pathname, Activity a) {
//        super(pathname);
//        activity = a;
//        Log.i("NazwaPliku",this.getName());
//    }
//
//
//    public PlikListyZadan(Uri uri)
//
//    public PlikListyZadan(String parent, String child) {
//        super(parent, child);
//    }
//
//    public PlikListyZadan(File parent, String child) {
//        super(parent, child);
//    }
//
//    public PlikListyZadan(URI uri) {
//        super(uri);
//    }
//
//
//
//    /**
//     * @return Skaner tego pliku
//     */
//    public Scanner getSkaner() {
//        try {
//            return new Scanner(activity.getContentResolver().openInputStream());
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * Usuwa plik z ktorego powstal ten obiekt by nastepnie zapisac w jego miejsce inny plik, bedacy odzwierciedleniem lZ.
//     * @param lZ - obiekt listy zadan ktory zostanie przetworzony na plik i zapisany w miejsce starej listy zadan
//     */
//    void nadpisz(ListaZadan lZ){
//        try{
//            PrintWriter writer = new PrintWriter(getAbsolutePath(), "UTF-8");
//            for(Zadanie z : lZ){
//                writer.println(z.tytul + Util.SEPARATOR + z.start + Util.SEPARATOR + z.deadline + Util.SEPARATOR + z.opis);
//            }
//            writer.close();
//        } catch (IOException e) {
//            // do something
//        }
//
//    }
//
//
//
//}
