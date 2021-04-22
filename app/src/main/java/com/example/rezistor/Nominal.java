package com.example.rezistor;

import java.io.Serializable;

/**
 * Класс, предствляющий распознанный номинал резистора
 * @author Ignatovich
 */
public class Nominal implements Serializable {

    /**
     * ID записи в базе данных
     */
    public int recordId;

    /**
     * Значение номинала
     */
    public double value;

    /**
     * Количество цветов маркировки (4/5)
     */
    public int colors;

    /**
     * Единица измерения / кратность
     */
    public String unit;

    /**
     * Допуск, %
     */
    public double tolerance;

    /**
     * Фото
     */
    public byte[] photo;


    public Nominal(double value, String unit, double tolerance) {
        this.value = value;
        this.unit = unit;
        this.tolerance = tolerance;
    }

    /**
     * Формирование строкового представления номинала
     */
    @Override
    public String toString() {
        String val = String.valueOf(value) + " " + unit;
        if (tolerance > 0) {
            val += " ±" + String.valueOf(tolerance) + "%";
        }
        return val;
    }
}
