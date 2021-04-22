package com.example.rezistor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Класс activity главного экрана
 * @author Ignatovich
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Отображение журнала
     */
    public void btnJournalClick(View view) {
        Intent intent = new Intent(this, JournalActivity.class);
        startActivity(intent);
    }

    /**
     * Отображение экрана генерации маркировки по известному номиналу
     */
    public void btnMarkingByNominal(View view) {
        Intent intent = new Intent(this, MarkingGeneratorActivity.class);
        startActivity(intent);
    }

    /**
     * Отображение экрана распознавания маркировки резистора по 5 полосам
     */
    public void btnNominalBy5LinesClick(View view) {
        Intent intent = new Intent(this, NominalBy5LinesActivity.class);
        Nominal nominal = new Nominal(120, "кОм", 0.25);
        nominal.colors = 5;
        intent.putExtra("nominal", nominal);
        startActivity(intent);
    }

    /**
     * Отображение экрана распознавания маркировки резистора по 4 полосам
     */
    public void btnNominalBy4LinesClick(View view) {
        Intent intent = new Intent(this, NominalBy4LinesActivity.class);
        Nominal nominal = new Nominal(120, "кОм", 0.25);
        nominal.colors = 4;
        intent.putExtra("nominal", nominal);
        startActivity(intent);
    }
}