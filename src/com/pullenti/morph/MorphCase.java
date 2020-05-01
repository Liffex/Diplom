/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.morph;

/**
 * Падеж
 */
public class MorphCase {

    public MorphCase(MorphCase val) {
        value = (short)0;
        if (val != null) 
            value = val.value;
    }

    public short value;

    public boolean isUndefined() {
        return value == ((short)0);
    }

    public boolean setUndefined(boolean _value) {
        value = (short)0;
        return _value;
    }


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
     * [Get] Количество падежей
     */
    public int getCount() {
        if (value == ((short)0)) 
            return 0;
        int cou = 0;
        for (int i = 0; i < 12; i++) {
            if (((((int)value) & ((1 << i)))) != 0) 
                cou++;
        }
        return cou;
    }


    public static MorphCase UNDEFINED;

    public static MorphCase NOMINATIVE;

    public static MorphCase GENITIVE;

    public static MorphCase DATIVE;

    public static MorphCase ACCUSATIVE;

    public static MorphCase INSTRUMENTAL;

    public static MorphCase PREPOSITIONAL;

    public static MorphCase VOCATIVE;

    public static MorphCase PARTIAL;

    public static MorphCase COMMON;

    public static MorphCase POSSESSIVE;

    public static MorphCase ALLCASES;

    /**
     * [Get] Именительный
     */
    public boolean isNominative() {
        return this.getValue(0);
    }

    /**
     * [Set] Именительный
     */
    public boolean setNominative(boolean _value) {
        this.setValue(0, _value);
        return _value;
    }


    /**
     * [Get] Родительный
     */
    public boolean isGenitive() {
        return this.getValue(1);
    }

    /**
     * [Set] Родительный
     */
    public boolean setGenitive(boolean _value) {
        this.setValue(1, _value);
        return _value;
    }


    /**
     * [Get] Дательный
     */
    public boolean isDative() {
        return this.getValue(2);
    }

    /**
     * [Set] Дательный
     */
    public boolean setDative(boolean _value) {
        this.setValue(2, _value);
        return _value;
    }


    /**
     * [Get] Винительный
     */
    public boolean isAccusative() {
        return this.getValue(3);
    }

    /**
     * [Set] Винительный
     */
    public boolean setAccusative(boolean _value) {
        this.setValue(3, _value);
        return _value;
    }


    /**
     * [Get] Творительный
     */
    public boolean isInstrumental() {
        return this.getValue(4);
    }

    /**
     * [Set] Творительный
     */
    public boolean setInstrumental(boolean _value) {
        this.setValue(4, _value);
        return _value;
    }


    /**
     * [Get] Предложный
     */
    public boolean isPrepositional() {
        return this.getValue(5);
    }

    /**
     * [Set] Предложный
     */
    public boolean setPrepositional(boolean _value) {
        this.setValue(5, _value);
        return _value;
    }


    /**
     * [Get] Звательный
     */
    public boolean isVocative() {
        return this.getValue(6);
    }

    /**
     * [Set] Звательный
     */
    public boolean setVocative(boolean _value) {
        this.setValue(6, _value);
        return _value;
    }


    /**
     * [Get] Частичный
     */
    public boolean isPartial() {
        return this.getValue(7);
    }

    /**
     * [Set] Частичный
     */
    public boolean setPartial(boolean _value) {
        this.setValue(7, _value);
        return _value;
    }


    /**
     * [Get] Общий (для английского)
     */
    public boolean isCommon() {
        return this.getValue(8);
    }

    /**
     * [Set] Общий (для английского)
     */
    public boolean setCommon(boolean _value) {
        this.setValue(8, _value);
        return _value;
    }


    /**
     * [Get] Притяжательный (для английского)
     */
    public boolean isPossessive() {
        return this.getValue(9);
    }

    /**
     * [Set] Притяжательный (для английского)
     */
    public boolean setPossessive(boolean _value) {
        this.setValue(9, _value);
        return _value;
    }


    private static String[] m_Names;

    @Override
    public String toString() {
        StringBuilder tmpStr = new StringBuilder();
        for (int i = 0; i < m_Names.length; i++) {
            if (this.getValue(i)) {
                if (tmpStr.length() > 0) 
                    tmpStr.append("|");
                tmpStr.append(m_Names[i]);
            }
        }
        return tmpStr.toString();
    }

    /**
     * Восстановить падежи из строки, полученной ToString
     * @param str 
     * @return 
     */
    public static MorphCase parse(String str) {
        MorphCase res = new MorphCase(null);
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(str)) 
            return res;
        for (String s : com.pullenti.unisharp.Utils.split(str, String.valueOf('|'), false)) {
            for (int i = 0; i < m_Names.length; i++) {
                if (com.pullenti.unisharp.Utils.stringsEq(s, m_Names[i])) {
                    res.setValue(i, true);
                    break;
                }
            }
        }
        return res;
    }

    @Override
    public boolean equals(Object obj) {
        if (!((obj instanceof MorphCase))) 
            return false;
        return value == (((MorphCase)obj)).value;
    }

    @Override
    public int hashCode() {
        return (int)value;
    }

    public static MorphCase ooBitand(MorphCase arg1, MorphCase arg2) {
        short val1 = (short)0;
        short val2 = (short)0;
        if (arg1 != null) 
            val1 = arg1.value;
        if (arg2 != null) 
            val2 = arg2.value;
        return _new64((short)((((int)val1) & ((int)val2))));
    }

    public static MorphCase ooBitor(MorphCase arg1, MorphCase arg2) {
        short val1 = (short)0;
        short val2 = (short)0;
        if (arg1 != null) 
            val1 = arg1.value;
        if (arg2 != null) 
            val2 = arg2.value;
        return _new64((short)((((int)val1) | ((int)val2))));
    }

    public static MorphCase ooBitxor(MorphCase arg1, MorphCase arg2) {
        short val1 = (short)0;
        short val2 = (short)0;
        if (arg1 != null) 
            val1 = arg1.value;
        if (arg2 != null) 
            val2 = arg2.value;
        return _new64((short)((((int)val1) ^ ((int)val2))));
    }

    public static boolean ooEq(MorphCase arg1, MorphCase arg2) {
        short val1 = (short)0;
        short val2 = (short)0;
        if (arg1 != null) 
            val1 = arg1.value;
        if (arg2 != null) 
            val2 = arg2.value;
        return val1 == val2;
    }

    public static boolean ooNoteq(MorphCase arg1, MorphCase arg2) {
        short val1 = (short)0;
        short val2 = (short)0;
        if (arg1 != null) 
            val1 = arg1.value;
        if (arg2 != null) 
            val2 = arg2.value;
        return val1 != val2;
    }

    public static MorphCase _new64(short _arg1) {
        MorphCase res = new MorphCase(null);
        res.value = _arg1;
        return res;
    }

    public MorphCase() {
        this(null);
    }
    
    static {
        UNDEFINED = _new64((short)0);
        NOMINATIVE = _new64((short)1);
        GENITIVE = _new64((short)2);
        DATIVE = _new64((short)4);
        ACCUSATIVE = _new64((short)8);
        INSTRUMENTAL = _new64((short)0x10);
        PREPOSITIONAL = _new64((short)0x20);
        VOCATIVE = _new64((short)0x40);
        PARTIAL = _new64((short)0x80);
        COMMON = _new64((short)0x100);
        POSSESSIVE = _new64((short)0x200);
        ALLCASES = _new64((short)0x3FF);
        m_Names = new String[] {"именит.", "родит.", "дател.", "винит.", "творит.", "предлож.", "зват.", "частич.", "общ.", "притяж."};
    }
}
