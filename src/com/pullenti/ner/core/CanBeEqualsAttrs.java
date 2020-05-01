/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.core;

/**
 * Атрибуты функции CanBeEqualsEx
 */
public class CanBeEqualsAttrs implements Comparable<CanBeEqualsAttrs> {

    public static final CanBeEqualsAttrs NO; // 0

    /**
     * Игнорировать небуквенные символы (они как бы выбрасываются)
     */
    public static final CanBeEqualsAttrs IGNORENONLETTERS; // 1

    /**
     * Игнорировать регистр символов
     */
    public static final CanBeEqualsAttrs IGNOREUPPERCASE; // 2

    /**
     * После первого существительного слова должны полностью совпадать 
     *  (иначе совпадение с точностью до морфологии)
     */
    public static final CanBeEqualsAttrs CHECKMORPHEQUAFTERFIRSTNOUN; // 4

    /**
     * Даже если указано IgnoreNonletters, кавычки проверять!
     */
    public static final CanBeEqualsAttrs USEBRACKETS; // 8

    /**
     * Игнорировать регистр символов только первого слова
     */
    public static final CanBeEqualsAttrs IGNOREUPPERCASEFIRSTWORD; // 0x10

    /**
     * Первое слово может быть короче (то есть второе должно начинаться на первое слово)
     */
    public static final CanBeEqualsAttrs FIRSTCANBESHORTER; // 0x20


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private CanBeEqualsAttrs(int val, String str) { m_val = val; m_str = str; }
    @Override
    public String toString() {
        if(m_str != null) return m_str;
        return ((Integer)m_val).toString();
    }
    @Override
    public int hashCode() {
        return (int)m_val;
    }
    @Override
    public int compareTo(CanBeEqualsAttrs v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, CanBeEqualsAttrs> mapIntToEnum; 
    private static java.util.HashMap<String, CanBeEqualsAttrs> mapStringToEnum; 
    private static CanBeEqualsAttrs[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static CanBeEqualsAttrs of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        CanBeEqualsAttrs item = new CanBeEqualsAttrs(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static CanBeEqualsAttrs of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static CanBeEqualsAttrs[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, CanBeEqualsAttrs>();
        mapStringToEnum = new java.util.HashMap<String, CanBeEqualsAttrs>();
        NO = new CanBeEqualsAttrs(0, "NO");
        mapIntToEnum.put(NO.value(), NO);
        mapStringToEnum.put(NO.m_str.toUpperCase(), NO);
        IGNORENONLETTERS = new CanBeEqualsAttrs(1, "IGNORENONLETTERS");
        mapIntToEnum.put(IGNORENONLETTERS.value(), IGNORENONLETTERS);
        mapStringToEnum.put(IGNORENONLETTERS.m_str.toUpperCase(), IGNORENONLETTERS);
        IGNOREUPPERCASE = new CanBeEqualsAttrs(2, "IGNOREUPPERCASE");
        mapIntToEnum.put(IGNOREUPPERCASE.value(), IGNOREUPPERCASE);
        mapStringToEnum.put(IGNOREUPPERCASE.m_str.toUpperCase(), IGNOREUPPERCASE);
        CHECKMORPHEQUAFTERFIRSTNOUN = new CanBeEqualsAttrs(4, "CHECKMORPHEQUAFTERFIRSTNOUN");
        mapIntToEnum.put(CHECKMORPHEQUAFTERFIRSTNOUN.value(), CHECKMORPHEQUAFTERFIRSTNOUN);
        mapStringToEnum.put(CHECKMORPHEQUAFTERFIRSTNOUN.m_str.toUpperCase(), CHECKMORPHEQUAFTERFIRSTNOUN);
        USEBRACKETS = new CanBeEqualsAttrs(8, "USEBRACKETS");
        mapIntToEnum.put(USEBRACKETS.value(), USEBRACKETS);
        mapStringToEnum.put(USEBRACKETS.m_str.toUpperCase(), USEBRACKETS);
        IGNOREUPPERCASEFIRSTWORD = new CanBeEqualsAttrs(0x10, "IGNOREUPPERCASEFIRSTWORD");
        mapIntToEnum.put(IGNOREUPPERCASEFIRSTWORD.value(), IGNOREUPPERCASEFIRSTWORD);
        mapStringToEnum.put(IGNOREUPPERCASEFIRSTWORD.m_str.toUpperCase(), IGNOREUPPERCASEFIRSTWORD);
        FIRSTCANBESHORTER = new CanBeEqualsAttrs(0x20, "FIRSTCANBESHORTER");
        mapIntToEnum.put(FIRSTCANBESHORTER.value(), FIRSTCANBESHORTER);
        mapStringToEnum.put(FIRSTCANBESHORTER.m_str.toUpperCase(), FIRSTCANBESHORTER);
        java.util.Collection<CanBeEqualsAttrs> col = mapIntToEnum.values();
        m_Values = new CanBeEqualsAttrs[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
