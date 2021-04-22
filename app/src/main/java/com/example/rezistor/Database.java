package com.example.rezistor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Реализация операций сохранения и чтения журнала распознавания номинала резисторов
 * @author Ignatovich
 */
public class Database extends SQLiteOpenHelper {

    public Database(Context context) {
        super(context, "database", null, 1);
    }

    /**
     * Обработчик события создания новой базы данных
     */
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE IF NOT EXISTS JOURNAL (RecordId INTEGER PRIMARY KEY AUTOINCREMENT, Nominal DOUBLE, Unit TEXT, Tolerance TEXT, Colors INT, Photo BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {  }

    /**
     * Сохранение новой записи в журнал
     * @param Nominal распознанный номинал резистора
     */
    public void createRecord(Nominal nominal) {
        ContentValues values = new ContentValues();
        values.put("Nominal", nominal.value);
        values.put("Unit", nominal.unit);
        values.put("Tolerance", nominal.tolerance);
        values.put("Colors", nominal.colors);
        values.put("Photo", nominal.photo);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("JOURNAL", null, values);
    }

    /**
     * Удаление записи из журнала
     * @param recordId ID записи в базе данных
     */
    public void deleteRecord(int recordId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("JOURNAL", "RecordId=?", new String[]{String.valueOf(recordId)});
    }

    /**
     * Чтение из базы данных журнала записей
     * @return Список записей журнала
     */
    public ArrayList<Nominal> getRecords() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select RecordId, Nominal, Unit, Tolerance, Colors, Photo from JOURNAL order by RecordId", new String[]{});
        ArrayList<Nominal> result = new ArrayList<Nominal>();
        while (cursor.moveToNext()) {
            Nominal item = new Nominal(cursor.getDouble(1), cursor.getString(2), cursor.getDouble(3));
            item.recordId = cursor.getInt(0);
            item.colors = cursor.getInt(4);
            item.photo = cursor.getBlob(5);
            result.add(item);
        }
        cursor.close();
        return result;
    }
}
