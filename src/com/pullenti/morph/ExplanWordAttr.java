/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.morph;

/**
 * Дополнительные характеристики слова
 */
public class ExplanWordAttr {

    public ExplanWordAttr(ExplanWordAttr val) {
        value = (short)0;
        if (val != null) 
            value = val.value;
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
     * [Get] Неопределённый тип
     */
    public boolean isUndefined() {
        return value == ((short)0);
    }

    /**
     * [Set] Неопределённый тип
     */
    public boolean setUndefined(boolean _value) {
        value = (short)0;
        return _value;
    }


    /**
     * [Get] Одушевлённое
     */
    public boolean isAnimated() {
        return this.getValue(0);
    }

    /**
     * [Set] Одушевлённое
     */
    public boolean setAnimated(boolean _value) {
        this.setValue(0, _value);
        return _value;
    }


    /**
     * [Get] Может иметь собственное имя
     */
    public boolean isNamed() {
        return this.getValue(1);
    }

    /**
     * [Set] Может иметь собственное имя
     */
    public boolean setNamed(boolean _value) {
        this.setValue(1, _value);
        return _value;
    }


    /**
     * [Get] Может иметь номер (например, Олимпиада 80)
     */
    public boolean isNumbered() {
        return this.getValue(2);
    }

    /**
     * [Set] Может иметь номер (например, Олимпиада 80)
     */
    public boolean setNumbered(boolean _value) {
        this.setValue(2, _value);
        return _value;
    }


    /**
     * [Get] Может ли иметь числовую характеристику (длина, количество, деньги ...)
     */
    public boolean isMeasured() {
        return this.getValue(3);
    }

    /**
     * [Set] Может ли иметь числовую характеристику (длина, количество, деньги ...)
     */
    public boolean setMeasured(boolean _value) {
        this.setValue(3, _value);
        return _value;
    }


    /**
     * [Get] Позитивная окраска
     */
    public boolean isEmoPositive() {
        return this.getValue(4);
    }

    /**
     * [Set] Позитивная окраска
     */
    public boolean setEmoPositive(boolean _value) {
        this.setValue(4, _value);
        return _value;
    }


    /**
     * [Get] Негативная окраска
     */
    public boolean isEmoNegative() {
        return this.getValue(5);
    }

    /**
     * [Set] Негативная окраска
     */
    public boolean setEmoNegative(boolean _value) {
        this.setValue(5, _value);
        return _value;
    }


    /**
     * [Get] Это животное, а не человек (для IsAnimated = true)
     */
    public boolean isAnimal() {
        return this.getValue(6);
    }

    /**
     * [Set] Это животное, а не человек (для IsAnimated = true)
     */
    public boolean setAnimal(boolean _value) {
        this.setValue(6, _value);
        return _value;
    }


    /**
     * [Get] Это человек, а не животное (для IsAnimated = true)
     */
    public boolean isMan() {
        return this.getValue(7);
    }

    /**
     * [Set] Это человек, а не животное (для IsAnimated = true)
     */
    public boolean setMan(boolean _value) {
        this.setValue(7, _value);
        return _value;
    }


    /**
     * [Get] За словом может быть персона в родительном падеже (слуга Хозяина, отец Ивана ...)
     */
    public boolean isCanPersonAfter() {
        return this.getValue(8);
    }

    /**
     * [Set] За словом может быть персона в родительном падеже (слуга Хозяина, отец Ивана ...)
     */
    public boolean setPersonAfter(boolean _value) {
        this.setValue(8, _value);
        return _value;
    }


    /**
     * [Get] Пространственный объект
     */
    public boolean isSpaceObject() {
        return this.getValue(9);
    }

    /**
     * [Set] Пространственный объект
     */
    public boolean setSpaceObject(boolean _value) {
        this.setValue(9, _value);
        return _value;
    }


    /**
     * [Get] Временной объект
     */
    public boolean isTimeObject() {
        return this.getValue(10);
    }

    /**
     * [Set] Временной объект
     */
    public boolean setTimeObject(boolean _value) {
        this.setValue(10, _value);
        return _value;
    }


    @Override
    public String toString() {
        StringBuilder tmpStr = new StringBuilder();
        if (isAnimated()) 
            tmpStr.append("одуш.");
        if (isAnimal()) 
            tmpStr.append("животн.");
        if (isMan()) 
            tmpStr.append("чел.");
        if (isSpaceObject()) 
            tmpStr.append("простр.");
        if (isTimeObject()) 
            tmpStr.append("времен.");
        if (isNamed()) 
            tmpStr.append("именов.");
        if (isNumbered()) 
            tmpStr.append("нумеруем.");
        if (isMeasured()) 
            tmpStr.append("измеряем.");
        if (isEmoPositive()) 
            tmpStr.append("позитив.");
        if (isEmoNegative()) 
            tmpStr.append("негатив.");
        if (isCanPersonAfter()) 
            tmpStr.append("персона_за_родит.");
        return tmpStr.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!((obj instanceof ExplanWordAttr))) 
            return false;
        return value == (((ExplanWordAttr)obj)).value;
    }

    @Override
    public int hashCode() {
        return (int)value;
    }

    public static ExplanWordAttr ooBitand(ExplanWordAttr arg1, ExplanWordAttr arg2) {
        short val1 = (short)0;
        short val2 = (short)0;
        if (arg1 != null) 
            val1 = arg1.value;
        if (arg2 != null) 
            val2 = arg2.value;
        return _new58((short)((((int)val1) & ((int)val2))));
    }

    public static ExplanWordAttr ooBitor(ExplanWordAttr arg1, ExplanWordAttr arg2) {
        short val1 = (short)0;
        short val2 = (short)0;
        if (arg1 != null) 
            val1 = arg1.value;
        if (arg2 != null) 
            val2 = arg2.value;
        return _new58((short)((((int)val1) | ((int)val2))));
    }

    public static boolean ooEq(ExplanWordAttr arg1, ExplanWordAttr arg2) {
        short val1 = (short)0;
        short val2 = (short)0;
        if (arg1 != null) 
            val1 = arg1.value;
        if (arg2 != null) 
            val2 = arg2.value;
        return val1 == val2;
    }

    public static boolean ooNoteq(ExplanWordAttr arg1, ExplanWordAttr arg2) {
        short val1 = (short)0;
        short val2 = (short)0;
        if (arg1 != null) 
            val1 = arg1.value;
        if (arg2 != null) 
            val2 = arg2.value;
        return val1 != val2;
    }

    public static ExplanWordAttr UNDEFINED;

    public static ExplanWordAttr _new58(short _arg1) {
        ExplanWordAttr res = new ExplanWordAttr(null);
        res.value = _arg1;
        return res;
    }

    public ExplanWordAttr() {
        this(null);
    }
    
    static {
        UNDEFINED = new ExplanWordAttr(null);
    }
}
