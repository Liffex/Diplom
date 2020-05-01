/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.morph;

/**
 * Язык(и)
 */
public class MorphLang {

    public MorphLang(MorphLang lng) {
        value = (short)0;
        if (lng != null) 
            value = lng.value;
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
     * [Get] Неопределённый язык
     */
    public boolean isUndefined() {
        return value == ((short)0);
    }

    /**
     * [Set] Неопределённый язык
     */
    public boolean setUndefined(boolean _value) {
        value = (short)0;
        return _value;
    }


    /**
     * [Get] Русский язык
     */
    public boolean isRu() {
        return this.getValue(0);
    }

    /**
     * [Set] Русский язык
     */
    public boolean setRu(boolean _value) {
        this.setValue(0, _value);
        return _value;
    }


    /**
     * [Get] Украинский язык
     */
    public boolean isUa() {
        return this.getValue(1);
    }

    /**
     * [Set] Украинский язык
     */
    public boolean setUa(boolean _value) {
        this.setValue(1, _value);
        return _value;
    }


    /**
     * [Get] Белорусский язык
     */
    public boolean isBy() {
        return this.getValue(2);
    }

    /**
     * [Set] Белорусский язык
     */
    public boolean setBy(boolean _value) {
        this.setValue(2, _value);
        return _value;
    }


    /**
     * [Get] Русский, украинский, белорусский или казахский язык
     */
    public boolean isCyrillic() {
        return (isRu() | isUa() | isBy()) | isKz();
    }


    /**
     * [Get] Английский язык
     */
    public boolean isEn() {
        return this.getValue(3);
    }

    /**
     * [Set] Английский язык
     */
    public boolean setEn(boolean _value) {
        this.setValue(3, _value);
        return _value;
    }


    /**
     * [Get] Итальянский язык
     */
    public boolean isIt() {
        return this.getValue(4);
    }

    /**
     * [Set] Итальянский язык
     */
    public boolean setIt(boolean _value) {
        this.setValue(4, _value);
        return _value;
    }


    /**
     * [Get] Казахский язык
     */
    public boolean isKz() {
        return this.getValue(5);
    }

    /**
     * [Set] Казахский язык
     */
    public boolean setKz(boolean _value) {
        this.setValue(5, _value);
        return _value;
    }


    private static String[] m_Names;

    @Override
    public String toString() {
        StringBuilder tmpStr = new StringBuilder();
        for (int i = 0; i < m_Names.length; i++) {
            if (this.getValue(i)) {
                if (tmpStr.length() > 0) 
                    tmpStr.append(";");
                tmpStr.append(m_Names[i]);
            }
        }
        return tmpStr.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!((obj instanceof MorphLang))) 
            return false;
        return value == (((MorphLang)obj)).value;
    }

    @Override
    public int hashCode() {
        return (int)value;
    }

    /**
     * Преобразовать из строки
     * @param str 
     * @param lang 
     * @return 
     */
    public static boolean tryParse(String str, com.pullenti.unisharp.Outargwrapper<MorphLang> lang) {
        lang.value = new MorphLang(null);
        while (!com.pullenti.unisharp.Utils.isNullOrEmpty(str)) {
            int i;
            for (i = 0; i < m_Names.length; i++) {
                if (com.pullenti.unisharp.Utils.startsWithString(str, m_Names[i], true)) 
                    break;
            }
            if (i >= m_Names.length) 
                break;
            lang.value.value |= ((short)((1 << i)));
            for (i = 2; i < str.length(); i++) {
                if (Character.isLetter(str.charAt(i))) 
                    break;
            }
            if (i >= str.length()) 
                break;
            str = str.substring(i);
        }
        if (lang.value.isUndefined()) 
            return false;
        return true;
    }

    public static MorphLang ooBitand(MorphLang arg1, MorphLang arg2) {
        short val1 = (short)0;
        short val2 = (short)0;
        if (arg1 != null) 
            val1 = arg1.value;
        if (arg2 != null) 
            val2 = arg2.value;
        return _new10((short)((((int)val1) & ((int)val2))));
    }

    public static MorphLang ooBitor(MorphLang arg1, MorphLang arg2) {
        short val1 = (short)0;
        short val2 = (short)0;
        if (arg1 != null) 
            val1 = arg1.value;
        if (arg2 != null) 
            val2 = arg2.value;
        return _new10((short)((((int)val1) | ((int)val2))));
    }

    public static boolean ooEq(MorphLang arg1, MorphLang arg2) {
        short val1 = (short)0;
        short val2 = (short)0;
        if (arg1 != null) 
            val1 = arg1.value;
        if (arg2 != null) 
            val2 = arg2.value;
        return val1 == val2;
    }

    public static boolean ooNoteq(MorphLang arg1, MorphLang arg2) {
        short val1 = (short)0;
        short val2 = (short)0;
        if (arg1 != null) 
            val1 = arg1.value;
        if (arg2 != null) 
            val2 = arg2.value;
        return val1 != val2;
    }

    public static MorphLang UNKNOWN;

    public static MorphLang RU;

    public static MorphLang UA;

    public static MorphLang BY;

    public static MorphLang EN;

    public static MorphLang IT;

    public static MorphLang KZ;

    public static MorphLang _new10(short _arg1) {
        MorphLang res = new MorphLang(null);
        res.value = _arg1;
        return res;
    }

    public static MorphLang _new93(boolean _arg1) {
        MorphLang res = new MorphLang(null);
        res.setRu(_arg1);
        return res;
    }

    public static MorphLang _new94(boolean _arg1) {
        MorphLang res = new MorphLang(null);
        res.setUa(_arg1);
        return res;
    }

    public static MorphLang _new95(boolean _arg1) {
        MorphLang res = new MorphLang(null);
        res.setBy(_arg1);
        return res;
    }

    public static MorphLang _new96(boolean _arg1) {
        MorphLang res = new MorphLang(null);
        res.setEn(_arg1);
        return res;
    }

    public static MorphLang _new97(boolean _arg1) {
        MorphLang res = new MorphLang(null);
        res.setIt(_arg1);
        return res;
    }

    public static MorphLang _new98(boolean _arg1) {
        MorphLang res = new MorphLang(null);
        res.setKz(_arg1);
        return res;
    }

    public MorphLang() {
        this(null);
    }
    
    static {
        m_Names = new String[] {"RU", "UA", "BY", "EN", "IT", "KZ"};
        UNKNOWN = new MorphLang(null);
        RU = _new93(true);
        UA = _new94(true);
        BY = _new95(true);
        EN = _new96(true);
        IT = _new97(true);
        KZ = _new98(true);
    }
}
