package com.lab.spych.mpt.dlaAndroida;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import com.lab.spych.mpt.R;
import com.lab.spych.mpt.uniwersalne.alarmy.Czas;
import com.lab.spych.mpt.uniwersalne.lista_zadan.Zadanie;

public class NoweZadanie extends AppCompatActivity {

    public static final String ZADANIE_KEY = "kluczDoZadania";
    private TimePicker start;
    private TimePicker deadline;
    private TextView tytul;
    private TextView opis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nowe_zadanie);
        start = (TimePicker) findViewById(R.id.startNowegoZadania);
        deadline = (TimePicker) findViewById(R.id.deadlineNowegoZadania);
        tytul = (TextView) findViewById(R.id.tytulNowegoZadania);
        opis = (TextView) findViewById(R.id.opisNowegoZadania);
        start.setIs24HourView(true);
        deadline.setIs24HourView(true);
    }


    public void dodajNoweZadanie(View v){
        Intent i = new Intent();
        Zadanie zadanie = new Zadanie();
        zadanie.setTytul(tytul.getText().toString());
        zadanie.setOpis(opis.getText().toString());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            zadanie.setStart(new Czas(start.getHour(),start.getMinute()));
            zadanie.setDeadline(new Czas(deadline.getHour(), deadline.getMinute()));
        } else {
            zadanie.setStart(new Czas(start.getCurrentHour(),start.getCurrentMinute()));
            zadanie.setDeadline(new Czas(deadline.getCurrentHour(), deadline.getCurrentMinute()));
        }

        i.putExtra(ZADANIE_KEY, zadanie);
        setResult(Activity.RESULT_OK, i );
        finish();
    }
}
