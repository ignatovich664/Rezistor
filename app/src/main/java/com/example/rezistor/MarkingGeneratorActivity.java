package com.example.rezistor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Класс activity экрана генерации цветовой маркировки резистора по известному номиналу
 */
public class MarkingGeneratorActivity extends AppCompatActivity {

    /**
     * Набор значений для списка точности
     */
    String[] tolerances = new String[]{
            "(нет)",
            "±10%",
            "±5%",
            "±1%",
            "±2%",
            "±0.5%",
            "±0.25%",
            "±0.10%",
            "±0.05%"
    };

    /**
     * Набор значений для списка точности
     */
    double[] toleranceValues= new double[]{
            0,
            10,
            5,
            1,
            2,
            0.5,
            0.25,
            0.10,
            0.05
    };

    /**
     * Набор значений для списка кратности
     */
    String[] units = new String[]{
            "Ом",
            "кОм",
            "МОм",
            "ГОм"
    };

    LineColor[] lineColors = LineColor.getList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marking_generator);
        setTitle("Генератор цветовой маркировки");

        Spinner selUnit = findViewById(R.id.selUnit);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, units);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selUnit.setAdapter(adapter);

        Spinner selTolerance = findViewById(R.id.selTolerance);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tolerances);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selTolerance.setAdapter(adapter);

        TextView txbNominal = (EditText) findViewById(R.id.txbNominal);
        txbNominal.setText("100");

        AdapterView.OnItemSelectedListener spinnerChangedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                refreshData();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        };

        selTolerance.setOnItemSelectedListener(spinnerChangedListener);
        selUnit.setOnItemSelectedListener(spinnerChangedListener);
        txbNominal.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                refreshData();
            }
        });
    }

    /**
     * Обновление данных на экране
     */
    void refreshData() {
        Nominal nominal = getNominal();
        int[] mark = NominalDetector.Colors4ByNominal(nominal);
        setColor(R.id.col4_1, mark[0]);
        setColor(R.id.col4_2, mark[1]);
        setColor(R.id.col4_3, mark[2]);
        setColor(R.id.col4_4, mark[3]);
        mark = NominalDetector.Colors5ByNominal(nominal);
        setColor(R.id.col5_1, mark[0]);
        setColor(R.id.col5_2, mark[1]);
        setColor(R.id.col5_3, mark[2]);
        setColor(R.id.col5_4, mark[3]);
        setColor(R.id.col5_5, mark[4]);
    }

    /**
     * Получение введённого номинала резистора
     * @return объект, представляющий номинал резистора
     */
    Nominal getNominal() {
        String strValue = ((EditText) findViewById(R.id.txbNominal)).getText().toString();
        double value = Double.parseDouble(strValue);
        Spinner selUnit = findViewById(R.id.selUnit);
        String unit = units[selUnit.getSelectedItemPosition()];
        Spinner selTolerance = findViewById(R.id.selTolerance);
        double tolerance = toleranceValues[selTolerance.getSelectedItemPosition()];
        return new Nominal(value, unit, tolerance);
    }

    void setColor(int viewId, int colorIndex) {
        findViewById(viewId).setBackgroundColor(lineColors[colorIndex].color);
    }
}