/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.morph;

/**
 * Информация о символах токена
 */
public class CharsInfo {

    public CharsInfo(CharsInfo ci) {
        value = (short)0;
        if (ci != null) 
            value = ci.value;
    }

    public short value;

    private boolean getValue(int i) {
        return ((((((int)value) >> i)) & 1)) != 0;
    }

    private void setValue(int i, boolean val) {
        if (val) 
            value |= ((short)((1 << i)));
        else 
            value &= ((short)(~((1 << i))));
    }

    /**
     * [Get] Все символы в верхнем регистре
     */
    public boolean isAllUpper() {
        return this.getValue(0);
    }

    /**
     * [Set] Все символы в верхнем регистре
     */
    public boolean setAllUpper(boolean _value) {
        this.setValue(0, _value);
        return _value;
    }


    /**
     * [Get] Все символы в нижнем регистре
     */
    public boolean isAllLower() {
        return this.getValue(1);
    }

    /**
     * [Set] Все символы в нижнем регистре
     */
    public boolean setAllLower(boolean _value) {
        this.setValue(1, _value);
        return _value;
    }


    /**
     * [Get] ПЕрвый символ в верхнем регистре, остальные в нижнем. 
     *  Для однобуквенной комбинации false.
     */
    public boolean isCapitalUpper() {
        return this.getValue(2);
    }

    /**
     * [Set] ПЕрвый символ в верхнем регистре, остальные в нижнем. 
     *  Для однобуквенной комбинации false.
     */
    public boolean setCapitalUpper(boolean _value) {
        this.setValue(2, _value);
        return _value;
    }


    /**
     * [Get] Все символы в верхнеи регистре, кроме последнего (длина >= 3)
     */
    public boolean isLastLower() {
        return this.getValue(3);
    }

    /**
     * [Set] Все символы в верхнеи регистре, кроме последнего (длина >= 3)
     */
    public boolean setLastLower(boolean _value) {
        this.setValue(3, _value);
        return _value;
    }


    /**
     * [Get] Это буквы
     */
    public boolean isLetter() {
        return this.getValue(4);
    }

    /**
     * [Set] Это буквы
     */
    public boolean setLetter(boolean _value) {
        this.setValue(4, _value);
        return _value;
    }


    /**
     * [Get] Это латиница
     */
    public boolean isLatinLetter() {
        return this.getValue(5);
    }

    /**
     * [Set] Это латиница
     */
    public boolean setLatinLetter(boolean _value) {
        this.setValue(5, _value);
        return _value;
    }


    /**
     * [Get] Это кириллица
     */
    public boolean isCyrillicLetter() {
        return this.getValue(6);
    }

    /**
     * [Set] Это кириллица
     */
    public boolean setCyrillicLetter(boolean _value) {
        this.setValue(6, _value);
        return _value;
    }


    @Override
    public String toString() {
        if (!isLetter()) 
            return "Nonletter";
        StringBuilder tmpStr = new StringBuilder();
        if (isAllUpper()) 
            tmpStr.append("AllUpper");
        else if (isAllLower()) 
            tmpStr.append("AllLower");
        else if (isCapitalUpper()) 
            tmpStr.append("CapitalUpper");
        else if (isLastLower()) 
            tmpStr.append("LastLower");
        else 
            tmpStr.append("Nonstandard");
        if (isLatinLetter()) 
            tmpStr.append(" Latin");
        else if (isCyrillicLetter()) 
            tmpStr.append(" Cyrillic");
        else if (isLetter()) 
            tmpStr.append(" Letter");
        return tmpStr.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!((obj instanceof MorphClass))) 
            return false;
        return value == (((MorphClass)obj)).value;
    }

    public static boolean ooEq(CharsInfo arg1, CharsInfo arg2) {
        short val1 = (short)0;
        short val2 = (short)0;
        if (arg1 != null) 
            val1 = arg1.value;
        if (arg2 != null) 
            val2 = arg2.value;
        return val1 == val2;
    }

    public static boolean ooNoteq(CharsInfo arg1, CharsInfo arg2) {
        short val1 = (short)0;
        short val2 = (short)0;
        if (arg1 != null) 
            val1 = arg1.value;
        if (arg2 != null) 
            val2 = arg2.value;
        return val1 != val2;
    }

    public static CharsInfo _new2378(boolean _arg1) {
        CharsInfo res = new CharsInfo(null);
        res.setCapitalUpper(_arg1);
        return res;
    }

    public static CharsInfo _new2553(boolean _arg1) {
        CharsInfo res = new CharsInfo(null);
        res.setCyrillicLetter(_arg1);
        return res;
    }

    public static CharsInfo _new2559(boolean _arg1, boolean _arg2) {
        CharsInfo res = new CharsInfo(null);
        res.setCyrillicLetter(_arg1);
        res.setCapitalUpper(_arg2);
        return res;
    }

    public static CharsInfo _new2564(boolean _arg1, boolean _arg2, boolean _arg3, boolean _arg4) {
        CharsInfo res = new CharsInfo(null);
        res.setCapitalUpper(_arg1);
        res.setCyrillicLetter(_arg2);
        res.setLatinLetter(_arg3);
        res.setLetter(_arg4);
        return res;
    }

    public static CharsInfo _new2587(boolean _arg1) {
        CharsInfo res = new CharsInfo(null);
        res.setLatinLetter(_arg1);
        return res;
    }

    public static CharsInfo _new2866(short _arg1) {
        CharsInfo res = new CharsInfo(null);
        res.value = _arg1;
        return res;
    }
    public CharsInfo() {
        this(null);
    }
}
