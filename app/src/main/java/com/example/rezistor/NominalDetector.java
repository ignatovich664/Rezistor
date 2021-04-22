package com.example.rezistor;

/**
 * Реализация логики распознавания номинала резистора по цветовой маркировке
 * и генерации цветовой маркировки по известному номиналу
 * @author Ignatovich
 */
public class NominalDetector {

    /**
     * Распознавание номинала резистора по 5 полосам
     * @param col1 цвет 1-й полосы (0...9)
     * @param col2 цвет 2-й полосы (0...9)
     * @param col3 цвет 3-й полосы (0...9)
     * @param col4 цвет 4-й полосы (0...9)
     * @param col5 цвет 5-й полосы (0...9)
     * @return номинал резистора
     */
    public static Nominal NominalBy5Lines(int col1, int col2, int col3, int col4, int col5) {
        double value = (col1 * 100 + col2 * 10 + col3);
        value *= Math.pow(10, col4);
        String unit;
        if (value >= 1E9) {
            value /= 1E9;
            unit = "ГОм";
        } else if (value >= 1E6) {
            value /= 1E6;
            unit = "МОм";
        } else if (value >= 1E3) {
            value /= 1E3;
            unit = "кОм";
        } else {
            unit = "Ом";
        }
        double tolerance = colorToTolerance(col5);
        return new Nominal(value, unit, tolerance);
    }

    /**
     * Распознавание номинала резистора по 4 полосам
     * @param col1 цвет 1-й полосы (0...9)
     * @param col2 цвет 2-й полосы (0...9)
     * @param col3 цвет 3-й полосы (0...9)
     * @param col4 цвет 4-й полосы (0...9)
     * @return номинал резистора
     */
    public static Nominal NominalBy4Lines(int col1, int col2, int col3, int col4) {
        double value = (col1 * 10 + col2);
        value *= Math.pow(10, col3);
        String unit;
        if (value >= 1E9) {
            value /= 1E9;
            unit = "ГОм";
        } else if (value >= 1E6) {
            value /= 1E6;
            unit = "МОм";
        } else if (value >= 1E3) {
            value /= 1E3;
            unit = "кОм";
        } else {
            unit = "Ом";
        }
        double tolerance = colorToTolerance(col4);
        return new Nominal(value, unit, tolerance);
    }

    /**
     * Генерация 4- полосной цветовой маркировки резистора по известному номиналу
     * @param nominal Номинал
     * @return Массив индексов цветов (0...9) цветовых полос маркировки
     */
    public static int[] Colors4ByNominal(Nominal nominal) {
        int[] result = new int[4];
        long value = getAbsoluteNominal(nominal);
        int[] digits = getDigits(value);
        result[0] = digits.length > 0 ? digits[0] : 0;
        result[1] = digits.length > 1 ? digits[1] : 0;
        result[2] = rangeToColor(value / 10.0);
        result[3] = toleranceToColor(nominal.tolerance);
        return result;
    }

    /**
     * Генерация 5- полосной цветовой маркировки резистора по известному номиналу
     * @param nominal Номинал
     * @return Массив индексов цветов (0...9) цветовых полос маркировки
     */
    public static int[] Colors5ByNominal(Nominal nominal) {
        int[] result = new int[5];
        long value = getAbsoluteNominal(nominal);
        int[] digits = getDigits(value);
        result[0] = digits.length > 0 ? digits[0] : 0;
        result[1] = digits.length > 1 ? digits[1] : 0;
        result[2] = digits.length > 2 ? digits[2] : 0;
        result[3] = rangeToColor(value / 100.0);
        result[4] = toleranceToColor(nominal.tolerance);
        return result;
    }

    static long getAbsoluteNominal(Nominal nominal){
        switch (nominal.unit) {
            case "кОм":
                return (long)(nominal.value * 1E3);
            case "МОм":
                return (long)(nominal.value * 1E6);
            case "ГОм":
                return (long)(nominal.value * 1E9);
            default:
                return (long)(nominal.value);
        }
    }

    static int[] getDigits(long value) {
        String str = String.valueOf(value);
        int[] digits = new int[str.length()];
        for (int i = 0; i < str.length(); i++) {
            digits[i] = (int) (str.charAt(i) - '0');
        }
        return digits;
    }

    static int rangeToColor(double value) {
        value = Math.floor(value);
        if (value <= 0) {
            return 0;
        }
        return (int) Math.floor(Math.log10(value));
    }

    static int toleranceToColor(double tolerance) {
        if (tolerance == 1)
            return 1;
        if (tolerance == 2)
            return 2;
        if (tolerance == 0.5)
            return 5;
        if (tolerance == 0.25)
            return 6;
        if (tolerance == 0.1)
            return 7;
        if (tolerance == 0.05)
            return 8;
        return 0;
    }

    static double colorToTolerance(int colorIndex){
        switch (colorIndex) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 5:
                return  0.5;
            case 6:
                return  0.25;
            case 7:
                return  0.1;
            case 8:
                return  0.05;
            default:
                return  0;
        }
    }
}
