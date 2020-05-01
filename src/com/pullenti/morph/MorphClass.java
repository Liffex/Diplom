/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.morph;

/**
 * Часть речи
 */
public class MorphClass {

    public MorphClass(MorphClass val) {
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
     * [Get] Существительное
     */
    public boolean isNoun() {
        return this.getValue(0);
    }

    /**
     * [Set] Существительное
     */
    public boolean setNoun(boolean _value) {
        if (_value) 
            value = (short)0;
        this.setValue(0, _value);
        return _value;
    }


    public static boolean isNounInt(int val) {
        return ((val & 1)) != 0;
    }

    /**
     * [Get] Прилагательное
     */
    public boolean isAdjective() {
        return this.getValue(1);
    }

    /**
     * [Set] Прилагательное
     */
    public boolean setAdjective(boolean _value) {
        if (_value) 
            value = (short)0;
        this.setValue(1, _value);
        return _value;
    }


    public static boolean isAdjectiveInt(int val) {
        return ((val & 2)) != 0;
    }

    /**
     * [Get] Глагол
     */
    public boolean isVerb() {
        return this.getValue(2);
    }

    /**
     * [Set] Глагол
     */
    public boolean setVerb(boolean _value) {
        if (_value) 
            value = (short)0;
        this.setValue(2, _value);
        return _value;
    }


    public static boolean isVerbInt(int val) {
        return ((val & 4)) != 0;
    }

    /**
     * [Get] Наречие
     */
    public boolean isAdverb() {
        return this.getValue(3);
    }

    /**
     * [Set] Наречие
     */
    public boolean setAdverb(boolean _value) {
        if (_value) 
            value = (short)0;
        this.setValue(3, _value);
        return _value;
    }


    public static boolean isAdverbInt(int val) {
        return ((val & 8)) != 0;
    }

    /**
     * [Get] Местоимение
     */
    public boolean isPronoun() {
        return this.getValue(4);
    }

    /**
     * [Set] Местоимение
     */
    public boolean setPronoun(boolean _value) {
        if (_value) 
            value = (short)0;
        this.setValue(4, _value);
        return _value;
    }


    public static boolean isPronounInt(int val) {
        return ((val & 0x10)) != 0;
    }

    /**
     * [Get] Всякая ерунда (частицы, междометия)
     */
    public boolean isMisc() {
        return this.getValue(5);
    }

    /**
     * [Set] Всякая ерунда (частицы, междометия)
     */
    public boolean setMisc(boolean _value) {
        if (_value) 
            value = (short)0;
        this.setValue(5, _value);
        return _value;
    }


    public static boolean isMiscInt(int val) {
        return ((val & 0x20)) != 0;
    }

    /**
     * [Get] Предлог
     */
    public boolean isPreposition() {
        return this.getValue(6);
    }

    /**
     * [Set] Предлог
     */
    public boolean setPreposition(boolean _value) {
        this.setValue(6, _value);
        return _value;
    }


    public static boolean isPrepositionInt(int val) {
        return ((val & 0x40)) != 0;
    }

    /**
     * [Get] Союз
     */
    public boolean isConjunction() {
        return this.getValue(7);
    }

    /**
     * [Set] Союз
     */
    public boolean setConjunction(boolean _value) {
        this.setValue(7, _value);
        return _value;
    }


    public static boolean isConjunctionInt(int val) {
        return ((val & 0x80)) != 0;
    }

    /**
     * [Get] Собственное имя (фамилия, имя, отчество, геогр.название и др.)
     */
    public boolean isProper() {
        return this.getValue(8);
    }

    /**
     * [Set] Собственное имя (фамилия, имя, отчество, геогр.название и др.)
     */
    public boolean setProper(boolean _value) {
        this.setValue(8, _value);
        return _value;
    }


    public static boolean isProperInt(int val) {
        return ((val & 0x100)) != 0;
    }

    /**
     * [Get] Фамилия
     */
    public boolean isProperSurname() {
        return this.getValue(9);
    }

    /**
     * [Set] Фамилия
     */
    public boolean setProperSurname(boolean _value) {
        if (_value) 
            setProper(true);
        this.setValue(9, _value);
        return _value;
    }


    public static boolean isProperSurnameInt(int val) {
        return ((val & 0x200)) != 0;
    }

    /**
     * [Get] Фамилия
     */
    public boolean isProperName() {
        return this.getValue(10);
    }

    /**
     * [Set] Фамилия
     */
    public boolean setProperName(boolean _value) {
        if (_value) 
            setProper(true);
        this.setValue(10, _value);
        return _value;
    }


    public static boolean isProperNameInt(int val) {
        return ((val & 0x400)) != 0;
    }

    /**
     * [Get] Отчество
     */
    public boolean isProperSecname() {
        return this.getValue(11);
    }

    /**
     * [Set] Отчество
     */
    public boolean setProperSecname(boolean _value) {
        if (_value) 
            setProper(true);
        this.setValue(11, _value);
        return _value;
    }


    public static boolean isProperSecnameInt(int val) {
        return ((val & 0x800)) != 0;
    }

    /**
     * [Get] Географическое название
     */
    public boolean isProperGeo() {
        return this.getValue(12);
    }

    /**
     * [Set] Географическое название
     */
    public boolean setProperGeo(boolean _value) {
        if (_value) 
            setProper(true);
        this.setValue(12, _value);
        return _value;
    }


    public static boolean isProperGeoInt(int val) {
        return ((val & 0x1000)) != 0;
    }

    /**
     * [Get] Личное местоимение (я, мой, ты, он ...)
     */
    public boolean isPersonalPronoun() {
        return this.getValue(13);
    }

    /**
     * [Set] Личное местоимение (я, мой, ты, он ...)
     */
    public boolean setPersonalPronoun(boolean _value) {
        this.setValue(13, _value);
        return _value;
    }


    public static boolean isPersonalPronounInt(int val) {
        return ((val & 0x2000)) != 0;
    }

    private static String[] m_Names;

    @Override
    public String toString() {
        StringBuilder tmpStr = new StringBuilder();
        for (int i = 0; i < m_Names.length; i++) {
            if (this.getValue(i)) {
                if (i == 5) {
                    if (isConjunction() || isPreposition() || isProper()) 
                        continue;
                }
                if (tmpStr.length() > 0) 
                    tmpStr.append("|");
                tmpStr.append(m_Names[i]);
            }
        }
        return tmpStr.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!((obj instanceof MorphClass))) 
            return false;
        return value == (((MorphClass)obj)).value;
    }

    @Override
    public int hashCode() {
        return (int)value;
    }

    public static MorphClass ooBitand(MorphClass arg1, MorphClass arg2) {
        short val1 = (short)0;
        short val2 = (short)0;
        if (arg1 != null) 
            val1 = arg1.value;
        if (arg2 != null) 
            val2 = arg2.value;
        return _new79((short)((((int)val1) & ((int)val2))));
    }

    public static MorphClass ooBitor(MorphClass arg1, MorphClass arg2) {
        short val1 = (short)0;
        short val2 = (short)0;
        if (arg1 != null) 
            val1 = arg1.value;
        if (arg2 != null) 
            val2 = arg2.value;
        return _new79((short)((((int)val1) | ((int)val2))));
    }

    public static MorphClass ooBitxor(MorphClass arg1, MorphClass arg2) {
        short val1 = (short)0;
        short val2 = (short)0;
        if (arg1 != null) 
            val1 = arg1.value;
        if (arg2 != null) 
            val2 = arg2.value;
        return _new79((short)((((int)val1) ^ ((int)val2))));
    }

    public static boolean ooEq(MorphClass arg1, MorphClass arg2) {
        short val1 = (short)0;
        short val2 = (short)0;
        if (arg1 != null) 
            val1 = arg1.value;
        if (arg2 != null) 
            val2 = arg2.value;
        return val1 == val2;
    }

    public static boolean ooNoteq(MorphClass arg1, MorphClass arg2) {
        short val1 = (short)0;
        short val2 = (short)0;
        if (arg1 != null) 
            val1 = arg1.value;
        if (arg2 != null) 
            val2 = arg2.value;
        return val1 != val2;
    }

    public static MorphClass UNDEFINED;

    public static MorphClass NOUN;

    public static MorphClass PRONOUN;

    public static MorphClass PERSONALPRONOUN;

    public static MorphClass VERB;

    public static MorphClass ADJECTIVE;

    public static MorphClass ADVERB;

    public static MorphClass PREPOSITION;

    public static MorphClass CONJUNCTION;

    public static MorphClass _new79(short _arg1) {
        MorphClass res = new MorphClass(null);
        res.value = _arg1;
        return res;
    }

    public static MorphClass _new82(boolean _arg1) {
        MorphClass res = new MorphClass(null);
        res.setUndefined(_arg1);
        return res;
    }

    public static MorphClass _new83(boolean _arg1) {
        MorphClass res = new MorphClass(null);
        res.setNoun(_arg1);
        return res;
    }

    public static MorphClass _new84(boolean _arg1) {
        MorphClass res = new MorphClass(null);
        res.setPronoun(_arg1);
        return res;
    }

    public static MorphClass _new85(boolean _arg1) {
        MorphClass res = new MorphClass(null);
        res.setPersonalPronoun(_arg1);
        return res;
    }

    public static MorphClass _new86(boolean _arg1) {
        MorphClass res = new MorphClass(null);
        res.setVerb(_arg1);
        return res;
    }

    public static MorphClass _new87(boolean _arg1) {
        MorphClass res = new MorphClass(null);
        res.setAdjective(_arg1);
        return res;
    }

    public static MorphClass _new88(boolean _arg1) {
        MorphClass res = new MorphClass(null);
        res.setAdverb(_arg1);
        return res;
    }

    public static MorphClass _new89(boolean _arg1) {
        MorphClass res = new MorphClass(null);
        res.setPreposition(_arg1);
        return res;
    }

    public static MorphClass _new90(boolean _arg1) {
        MorphClass res = new MorphClass(null);
        res.setConjunction(_arg1);
        return res;
    }

    public static MorphClass _new2579(boolean _arg1) {
        MorphClass res = new MorphClass(null);
        res.setProperSurname(_arg1);
        return res;
    }

    public MorphClass() {
        this(null);
    }
    
    static {
        m_Names = new String[] {"существ.", "прилаг.", "глагол", "наречие", "местоим.", "разное", "предлог", "союз", "собств.", "фамилия", "имя", "отч.", "геогр.", "личн.местоим."};
        UNDEFINED = _new82(true);
        NOUN = _new83(true);
        PRONOUN = _new84(true);
        PERSONALPRONOUN = _new85(true);
        VERB = _new86(true);
        ADJECTIVE = _new87(true);
        ADVERB = _new88(true);
        PREPOSITION = _new89(true);
        CONJUNCTION = _new90(true);
    }
}
