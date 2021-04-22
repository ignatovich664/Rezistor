package com.example.rezistor;

import android.graphics.Color;

/**
 * Класс, представляющий линию цветовой маркировки резистора
 * @author Ignatovich
 */
public class LineColor {

    /**
     * Название цвета
     */
    public String name;

    /**
     * Значение цвета в формате RGB
     */
    public int color;

    /**
     * Возможные цвета линий маркировки резисторов
     */
    static final LineColor[] items = new LineColor[]{
            new LineColor("Черный", "#000000"),
            new LineColor("Коричневый", "#A05A2C"),
            new LineColor("Красный", "#FF0000"),
            new LineColor("Оранжевый", "#FF8000"),
            new LineColor("Желтый", "#FFFF00"),
            new LineColor("Зеленый", "#00FF00"),
            new LineColor("Голубой", "#0000FF"),
            new LineColor("Фиолетовый", "#C000FF"),
            new LineColor("Серый", "#808080"),
            new LineColor("Белый", "#FFFFFF")
    };

    /**
     * Конструктор класса
     * @param name имя цвета
     * @param color значение цвета в формате RGB
     */
    public LineColor(String name, String color){
        this.name =name;
        this.color = Color.parseColor(color);
    }

    /**
     * Получение списка возможных цветов маркировки резистора
     * @return массив LineColor[]
     */
    public static LineColor[] getList() {
        return items;
    }
}
