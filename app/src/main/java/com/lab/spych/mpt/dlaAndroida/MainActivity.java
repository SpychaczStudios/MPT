package com.lab.spych.mpt.dlaAndroida;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lab.spych.mpt.R;
import com.lab.spych.mpt.uniwersalne.lista_zadan.ListaZadan;
import com.lab.spych.mpt.uniwersalne.Util;
import com.lab.spych.mpt.uniwersalne.lista_zadan.Zadanie;

import java.io.IOException;

/**
 * Activity wczytuje listę z pliku (z Obiektu klasy PlikListyZadanAndroid). Jedyne co robi to tworzy z wybranego pliku ListView, dlatego Nie trzeba się martwić o onSavedInstanceState.
 */
public class MainActivity extends AppCompatActivity {
    private static final int WRITE_REQUEST_CODE = 43;
    private static final int READ_REQUEST_CODE = 42;
    private static final int NOWE_ZADANIE_REQUEST_CODE = 44;


    private ListView lista;
    private SharedPreferences preferencje;
    private PlikListyZadanAndroid plikListyZadanAndroid;
    private ListaZadan listaZadan;
    private AdapterListyZadan adapter;
    private TextView tytulListyZadan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), NoweZadanie.class);
                startActivityForResult(i, NOWE_ZADANIE_REQUEST_CODE);
            }
        });

        tytulListyZadan = (TextView) findViewById(R.id.tytulListy);
        lista = (ListView) findViewById(R.id.lista);
        preferencje = getSharedPreferences(Util.SHARED_PREFERENCES_PLIK, MODE_PRIVATE);
        String uri = preferencje.getString(Util.SHARED_PREFERENCES_LISTA_ZADAN, null); ///Tutaj wyciągam string uri z preferencji
        if(uri != (null)) zapelnijListe(Uri.parse(uri)); ///tutaj zapełniam listę pod warunkiem, że w sharedpreferences umieściłem wcześniej uri
// TODO NOW NOW NOW Tytuł pliku w MainActivity
// TODO NEXT Dodatkowy przycisk na toolbarze w ActivityNoweZadanie

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        ///tutaj ustawiam, zeby przycisk zaladuj na pasku górnym w menu trzech kropek otwieral menu wyboru pliku tekstowego (uzytkownik ma tutaj wybrac plik z grafikiem)
        ///Rezultaty tego co tutaj ustawilem ukaza sie w formie callbacku (on Activity result)
        if (id == R.id.zaladuj) {
//            klikniecie na przycisk odpowiadający załadowaniu istniejacego planu dnia
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("text/*");
            startActivityForResult(intent, READ_REQUEST_CODE); ///Rozpoczyna nowe Activity
        } else if (id == R.id.nowy){
//            kliknięcie na pzycisk menu odpowiadający za stworzenie nowego planu dnia
            nowyPlanDnia();
        } else if (id == R.id.bugreporting){
//            kliknięcie na przycisk odpowiadający funkcji zapisywania komenarzy użytkownika dotyczących działania aplikacji
            AlertDialog.Builder b = new AlertDialog.Builder(this);
            final EditText tV = (EditText) LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog_tytul_nowej_listy_zadan, null);
            tV.setText(preferencje.getString(Util.SHARED_PREFERENCES_BUGREPORTING, "Domyślne"));
            b.setView(tV);
            b.setPositiveButton("Gotowe", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences.Editor e = preferencje.edit();
                    e.putString(Util.SHARED_PREFERENCES_BUGREPORTING, preferencje.getString(Util.SHARED_PREFERENCES_BUGREPORTING, null) + tV.getText().toString() + Util.SEPARATOR);
                    e.apply();
                }
            });
            b.show();

        }
        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if(requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            ///w tej opcji ifa odbieram Uri z activity w którym użytkownik wybierał plik listy zadań
            if (resultData != null) {
                Uri uri = resultData.getData(); //to jest uri pliku, wybranego przez użytkownika
                Log.i("MAM_URI", uri.toString());
                SharedPreferences.Editor edytor = preferencje.edit();
                edytor.putString(Util.SHARED_PREFERENCES_LISTA_ZADAN, uri.toString());///Tutaj wkladam Uri wybranego przez uzytkownika pliku do Shared Preferences, aby pozniej onCreate mozna bylo przywrocic odpowiednia liste
                ///[TODO ULEPSZENIE uporać się z awariami bezpieczeństwa, po restarcie urządzenia, związanymi z persistent persmissions] (obecnie po restarcie zwyczajnie trzeba na nowo zaladowac plik)
                ///getContentResolver().takePersistableUriPermission(uri, resultData.getFlags() & (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION)); ///Tutaj daje nać systemowi, żeby na zawsze zapamiętał, że chcę uzywać pliku o tym Uri
                edytor.apply();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        } else if (requestCode == NOWE_ZADANIE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            ///w tej opcji ifa odbieram informację o nowym zadaniu użytkownika z activity w którym użytkownik wprowadzał informację dotyczące nowego zadania
            if(resultData != null) {
                Zadanie z = (Zadanie) resultData.getSerializableExtra(NoweZadanie.ZADANIE_KEY);
                listaZadan.dodajAndPosortujWgCzasuRozpoczecia(z);
                adapter.notifyDataSetChanged();
                try {
                    plikListyZadanAndroid.zapisz(listaZadan);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Błąd w trakcie zapisywania do pliku", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == WRITE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            ///w tej opcji ifa odbieram Uri nowego pliku listy zadan, który użytkownik właśnie stworzył w activity wywołanym przez dialog
            SharedPreferences.Editor edytor = preferencje.edit();
            edytor.putString(Util.SHARED_PREFERENCES_LISTA_ZADAN, String.valueOf(resultData.getData()));///Tutaj wkladam Uri wybranego przez uzytkownika pliku do Shared Preferences, aby pozniej onCreate mozna bylo przywrocic odpowiednia liste
            edytor.apply();
            startActivity(new Intent(this, MainActivity.class)); ///Po prostu rozpoczynam nową instancję MainActivity, czepire ona z pliku wskazanetego przez SharedPreferences
        }

    }

    /**
     * Zapelnia listę tworząc plikListyZadan z Uri, a nastepnie wyciągając z niego listę zadań i przekładając ją do Adaptera, następnie wypełniając według niego listView.
     * @param uri - Uri pliku, z którego będa pochodzić zadania na Liście Zadań
     */
    private void zapelnijListe(Uri uri) {

        try {
            ///Najpierw łączymy się z fizycznym plikiem z androida
            plikListyZadanAndroid = new PlikListyZadanAndroid(uri, this);
            ///Teraz tworzymy liste zadan jako obiekt
            listaZadan = new ListaZadan(plikListyZadanAndroid);
            if(listaZadan.getTytul()==null) listaZadan.setTytul(Util.tytulNowejListyRyzykownaOperacjaPrzyUzyciuZmiennejGlobalnej);
            ///Teraz zapełniamy listę
            adapter = new AdapterListyZadan(getBaseContext(), R.layout.zadanie, listaZadan);
            lista.setAdapter(adapter);
            tytulListyZadan.setText(listaZadan.getTytul());
        } catch (SecurityException e){
            /// TODO ULEPSZENIE Spodziewam się tutaj błędu bezpieczeństwa. Pojawia się ona za każdym razem gdy resetuję urządzenie. Jego naprawa wymaga pracy i jest skomplikowana - chyba nie warto. Na razie po prostu zostawie jak jest: obecnie po restarcie zwyczajnie trzeba na nowo zaladowac plik
            e.printStackTrace();
        }
        catch (Exception e){
            ///W razie gdyby pojawił się jakikolwiek inny problem, musze łapać wszystkie wyjątki, bo funkcja przypominania sobie ostatnio otwartego pliku jest przeciez tylko dodatkowa, zawsze mozna zaladowac plik recznie, a w razie bledu nie mozna paralizowac aplikacji
            e.printStackTrace();
        }
    }

    private void nowyPlanDnia() {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        final EditText tytulNowejListyZadan = (EditText) LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog_tytul_nowej_listy_zadan, null);
        b.setView(tytulNowejListyZadan);
        b.setPositiveButton("Stwórz i otwórz", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nazwaPliku =  tytulNowejListyZadan.getText().toString();
                if(!nazwaPliku.isEmpty()) {
                    ///Tutaj tworzę intent, który stworzy mój dokument, potem w onActivityResult() odbiorę Uri tego dokumentu i wsadzę je do Shared Preferences. A będzie on [dokumnet] pusty.
                    Intent i = new Intent(Intent.ACTION_CREATE_DOCUMENT);
                    i.addCategory(Intent.CATEGORY_OPENABLE);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_TITLE, nazwaPliku);
                    Util.tytulNowejListyRyzykownaOperacjaPrzyUzyciuZmiennejGlobalnej = nazwaPliku;
                    startActivityForResult(i, WRITE_REQUEST_CODE);
                }
            }
        });
        AlertDialog d = b.create();
        d.show();
    }
}

/**
 * Adapter przekształcający ListeZadan w View dla ListView w MainActivity. Adapter bierze ListeZadan i sam wywoluje notifyDataSetChanged() żeby listView dodał nowy element (ALE NIE DOTYCZY TO ZMIAN W OBIEKTACH ZADAŃ)
 */
class AdapterListyZadan extends ArrayAdapter<Zadanie>{
    private final ListaZadan obiekty;
    private final int layoutPojedynczegoView;
    private final Context kontekst;

    AdapterListyZadan(Context context, int resource, ListaZadan objects) {
        super(context, resource, objects); ///zamiast resource moglbym dac -1, i zbudowac View dynamicznie, ale poco, lepiej zbudowac pojedynczy View w XMLu
        obiekty = objects;
        layoutPojedynczegoView = resource;
        kontekst = context;
    }
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent){
        Zadanie z = obiekty.get(position);
        RelativeLayout ret;
        if(convertView==null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ret = (RelativeLayout) inflater.inflate(layoutPojedynczegoView, parent, false);
        } else ret = (RelativeLayout) convertView;
        ((TextView)ret.findViewById(R.id.tytul)).setText(z.getTytul());
        ((TextView)ret.findViewById(R.id.czas)).setText(z.getStart().wezFormeOpisowa() + " - " + z.getDeadline().wezFormeOpisowa());
        ((TextView)ret.findViewById(R.id.opis)).setText(z.getOpis());
        return ret;
    }
}
