package com.example.rezistor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/**
 * Класс activity распознавания номинала резистора по 4 полосам
 * @author Ignatovich
 */
public class NominalBy4LinesActivity extends NominalActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setContentView(){
        setContentView(R.layout.activity_nominal_by4_lines);
    }

    @Override
    protected void initializeSpinners() {
        CustomArrayAdapter adapter = new CustomArrayAdapter(this, android.R.layout.simple_spinner_item, LineColor.getList());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        int[] cols = NominalDetector.Colors4ByNominal(nominal);
        initializeSpinner(R.id.spinner1, cols[0]);
        initializeSpinner(R.id.spinner2, cols[1]);
        initializeSpinner(R.id.spinner3, cols[2]);
        initializeSpinner(R.id.spinner4, cols[3]);
    }

    @Override
    protected Nominal getNominal() {
        int c1 = getSpinnerItem(R.id.spinner1);
        int c2 = getSpinnerItem(R.id.spinner2);
        int c3 = getSpinnerItem(R.id.spinner3);
        int c4 = getSpinnerItem(R.id.spinner4);
        Nominal newNominal = NominalDetector.NominalBy4Lines(c1, c2, c3, c4);
        newNominal.recordId = nominal.recordId;
        newNominal.colors = nominal.colors;
        newNominal.photo = nominal.photo;
        return newNominal;
    }
}