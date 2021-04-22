package com.example.rezistor;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Модульные тесты класса NominalDetector
 * @author Ignatovich
 */
public class NominalDetectorUnitTest {

    /**
     * Тест определения номинала резистора по 4 полосам
     */
    @Test
    public void NominalBy4Lines() {
        int[] colors = new int[]{
                1, //коричневый
                2, //красный
                3, //оранжевый
                5  //зеленый
        };
        Nominal nominal = NominalDetector.NominalBy4Lines(colors[0], colors[1], colors[2], colors[3]);
        assertEquals(12, nominal.value, 1E-5);
        assertEquals("кОм", nominal.unit);
        assertEquals(0.5, nominal.tolerance, 1E-5);
    }

    /**
     * Тест определения номинала резистора по 5 полосам
     */
    @Test
    public void NominalBy5Lines() {
        int[] colors = new int[]{
                1, //коричневый
                2, //красный
                3, //оранжевый
                4, //желтый
                5  //зеленый
        };
        Nominal nominal = NominalDetector.NominalBy5Lines(colors[0], colors[1], colors[2], colors[3], colors[4]);
        assertEquals(1.23, nominal.value, 1E-5);
        assertEquals("МОм", nominal.unit);
        assertEquals(0.5, nominal.tolerance, 1E-5);
    }

    /**
     * Тест генерации 4-полосной маркировки рестистора по номиналу
     */
    @Test
    public void Colors4ByNominal(){
        Nominal nominal = new Nominal(12, "кОм", 0.5);
        int[] colors = NominalDetector.Colors4ByNominal(nominal);
        assertEquals(4, colors.length);
        assertEquals(colors[0], 1); //коричневый
        assertEquals(colors[1], 2); //красный
        assertEquals(colors[2], 3); //оранжевый
        assertEquals(colors[3], 5); //зеленый
    }

    /**
     * Тест генерации 5-полосной маркировки рестистора по номиналу
     */
    @Test
    public void Colors5ByNominal(){
        Nominal nominal = new Nominal(1.23, "МОм", 0.5);
        int[] colors = NominalDetector.Colors5ByNominal(nominal);
        assertEquals(5, colors.length);
        assertEquals(colors[0], 1); //коричневый
        assertEquals(colors[1], 2); //красный
        assertEquals(colors[2], 3); //оранжевый
        assertEquals(colors[3], 4); //желтый
        assertEquals(colors[4], 5); //зеленый
    }
}