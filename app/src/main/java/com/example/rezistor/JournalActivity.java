package com.example.rezistor;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * Activity экрана журнала распознавания номинала резисторов
 */
public class JournalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);
        setTitle("Журнал");

        ListView listView = (ListView) findViewById(R.id.journalListView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Nominal item = (Nominal) listView.getItemAtPosition(position);
                showItemActivity(item);
            }
        });

        refreshData();
    }

    /**
     * Обновление данных в списке
     */
    void refreshData(){
        Database database = new Database(getApplicationContext());
        List<Nominal> results = database.getRecords();
        ListView listView = (ListView) findViewById(R.id.journalListView);
        ArrayAdapter<Nominal> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, results);
        listView.setAdapter(adapter);
    }

    /**
     * Отображение activity распознанного номинала резистора
     * @param nominal
     */
    void showItemActivity(Nominal nominal) {
        Intent intent = new Intent(this, nominal.colors == 4 ? NominalBy4LinesActivity.class : NominalBy5LinesActivity.class);
        intent.putExtra("nominal", nominal);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        refreshData();
    }
}