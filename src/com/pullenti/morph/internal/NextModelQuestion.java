/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.morph.internal;

/**
 * Основные вопросы модели управления
 */
public class NextModelQuestion implements Comparable<NextModelQuestion> {

    public static final NextModelQuestion UNDEFINED; // 0

    /**
     * Где
     */
    public static final NextModelQuestion WHERE; // 1

    /**
     * Откуда
     */
    public static final NextModelQuestion WHEREFROM; // 2

    /**
     * Куда
     */
    public static final NextModelQuestion WHERETO; // 4

    /**
     * Когда
     */
    public static final NextModelQuestion WHEN; // 8

    /**
     * Что делать (инфинитив за группой)
     */
    public static final NextModelQuestion WHATTODO; // 0x10


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private NextModelQuestion(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(NextModelQuestion v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, NextModelQuestion> mapIntToEnum; 
    private static java.util.HashMap<String, NextModelQuestion> mapStringToEnum; 
    private static NextModelQuestion[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static NextModelQuestion of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        NextModelQuestion item = new NextModelQuestion(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static NextModelQuestion of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static NextModelQuestion[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, NextModelQuestion>();
        mapStringToEnum = new java.util.HashMap<String, NextModelQuestion>();
        UNDEFINED = new NextModelQuestion(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        WHERE = new NextModelQuestion(1, "WHERE");
        mapIntToEnum.put(WHERE.value(), WHERE);
        mapStringToEnum.put(WHERE.m_str.toUpperCase(), WHERE);
        WHEREFROM = new NextModelQuestion(2, "WHEREFROM");
        mapIntToEnum.put(WHEREFROM.value(), WHEREFROM);
        mapStringToEnum.put(WHEREFROM.m_str.toUpperCase(), WHEREFROM);
        WHERETO = new NextModelQuestion(4, "WHERETO");
        mapIntToEnum.put(WHERETO.value(), WHERETO);
        mapStringToEnum.put(WHERETO.m_str.toUpperCase(), WHERETO);
        WHEN = new NextModelQuestion(8, "WHEN");
        mapIntToEnum.put(WHEN.value(), WHEN);
        mapStringToEnum.put(WHEN.m_str.toUpperCase(), WHEN);
        WHATTODO = new NextModelQuestion(0x10, "WHATTODO");
        mapIntToEnum.put(WHATTODO.value(), WHATTODO);
        mapStringToEnum.put(WHATTODO.m_str.toUpperCase(), WHATTODO);
        java.util.Collection<NextModelQuestion> col = mapIntToEnum.values();
        m_Values = new NextModelQuestion[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
