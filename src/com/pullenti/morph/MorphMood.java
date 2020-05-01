/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.morph;

/**
 * Наклонение (для глаголов)
 */
public class MorphMood implements Comparable<MorphMood> {

    /**
     * Неопределено
     */
    public static final MorphMood UNDEFINED; // 0

    /**
     * Изъявительное
     */
    public static final MorphMood INDICATIVE; // 1

    /**
     * Условное
     */
    public static final MorphMood SUBJUNCTIVE; // 2

    /**
     * Повелительное
     */
    public static final MorphMood IMPERATIVE; // 4


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private MorphMood(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(MorphMood v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, MorphMood> mapIntToEnum; 
    private static java.util.HashMap<String, MorphMood> mapStringToEnum; 
    private static MorphMood[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static MorphMood of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        MorphMood item = new MorphMood(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static MorphMood of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static MorphMood[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, MorphMood>();
        mapStringToEnum = new java.util.HashMap<String, MorphMood>();
        UNDEFINED = new MorphMood(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        INDICATIVE = new MorphMood(1, "INDICATIVE");
        mapIntToEnum.put(INDICATIVE.value(), INDICATIVE);
        mapStringToEnum.put(INDICATIVE.m_str.toUpperCase(), INDICATIVE);
        SUBJUNCTIVE = new MorphMood(2, "SUBJUNCTIVE");
        mapIntToEnum.put(SUBJUNCTIVE.value(), SUBJUNCTIVE);
        mapStringToEnum.put(SUBJUNCTIVE.m_str.toUpperCase(), SUBJUNCTIVE);
        IMPERATIVE = new MorphMood(4, "IMPERATIVE");
        mapIntToEnum.put(IMPERATIVE.value(), IMPERATIVE);
        mapStringToEnum.put(IMPERATIVE.m_str.toUpperCase(), IMPERATIVE);
        java.util.Collection<MorphMood> col = mapIntToEnum.values();
        m_Values = new MorphMood[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
