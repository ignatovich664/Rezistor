package com.example.rezistor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Базовый абстрактный класс activity экранной формы распознавания номинала резистора
 * @author Ignatovich
 */
public abstract class NominalActivityBase extends AppCompatActivity {

    protected Nominal nominal;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    String currentPhotoPath;
    byte[] currentPhotoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        setTitle("Определение номинала резистора");

        nominal = (Nominal)getIntent().getSerializableExtra("nominal");

        if(nominal.photo!=null) {
            refreshPhoto();
        }

        initializeSpinners();
        refreshData();
    }

    /**
     * Установка layout
     */
    protected abstract void setContentView();

    /**
     * Инициализация списков выбора цветов маркировки
     */
    protected abstract void initializeSpinners();

    void initializeSpinner(int spinnerId, int position) {
        CustomArrayAdapter adapter = new CustomArrayAdapter(this, android.R.layout.simple_spinner_item, LineColor.getList());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) findViewById(spinnerId);
        spinner.setAdapter(adapter);
        spinner.setSelection(position);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                refreshData();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    int getSpinnerItem(int spinnerId) {
        Spinner spinner = (Spinner) findViewById(spinnerId);
        return spinner.getSelectedItemPosition();
    }

    void refreshData() {
        Nominal nominal = getNominal();
        ((TextView) findViewById(R.id.txbResult)).setText(nominal.toString());
    }

    void refreshPhoto(){
        Bitmap bmp = BitmapFactory.decodeByteArray(nominal.photo, 0, nominal.photo.length);
        ImageView image = (ImageView) findViewById(R.id.imageView);
        image.setImageBitmap(bmp);
    }

    /**
     * Получение распознанного номинала резистора
     */
    protected abstract Nominal getNominal();

    /**
     * Сохранение распознанного номинала в журнал
     */
    public void btnSaveClick(View view) {
        Nominal nom = getNominal();
        Database db = new Database(this);
        if (nom.recordId > 0) {
            db.deleteRecord(nom.recordId);
        }
        db.createRecord(nom);
        finish();
    }

    File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(imageFileName, ".jpg", storageDir);
        currentPhotoPath = imageFile.getAbsolutePath();
        currentPhotoData = null;
        return imageFile;
    }

    /**
     * Обработка нажатия кнопки "Сделать фото"
     */
    public void btnPhotoClick(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            File photoFile = createImageFile();
            Uri photoURI = FileProvider.getUriForFile(this, "com.example.android.fileprovider", photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (IOException ex) {
        }
    }

    /**
     * Обработчик получения результата из запущенной activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == -1) {
            // результат получения фото с камеры
            File file = new File(currentPhotoPath);
            int size = (int) file.length();
            byte[] imageBytes = new byte[size];
            try {
                BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
                buf.read(imageBytes, 0, imageBytes.length);
                buf.close();
                file.delete();
                nominal.photo = imageBytes;
                refreshPhoto();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
