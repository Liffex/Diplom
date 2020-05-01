/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.sentiment;

/**
 * Тип сентимента
 */
public class SentimentKind implements Comparable<SentimentKind> {

    public static final SentimentKind UNDEFINED; // 0

    /**
     * Положительный
     */
    public static final SentimentKind POSITIVE; // 1

    /**
     * Отрицательный
     */
    public static final SentimentKind NEGATIVE; // 2


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private SentimentKind(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(SentimentKind v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, SentimentKind> mapIntToEnum; 
    private static java.util.HashMap<String, SentimentKind> mapStringToEnum; 
    private static SentimentKind[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static SentimentKind of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        SentimentKind item = new SentimentKind(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static SentimentKind of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static SentimentKind[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, SentimentKind>();
        mapStringToEnum = new java.util.HashMap<String, SentimentKind>();
        UNDEFINED = new SentimentKind(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        POSITIVE = new SentimentKind(1, "POSITIVE");
        mapIntToEnum.put(POSITIVE.value(), POSITIVE);
        mapStringToEnum.put(POSITIVE.m_str.toUpperCase(), POSITIVE);
        NEGATIVE = new SentimentKind(2, "NEGATIVE");
        mapIntToEnum.put(NEGATIVE.value(), NEGATIVE);
        mapStringToEnum.put(NEGATIVE.m_str.toUpperCase(), NEGATIVE);
        java.util.Collection<SentimentKind> col = mapIntToEnum.values();
        m_Values = new SentimentKind[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
