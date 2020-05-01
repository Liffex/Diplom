/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.morph;

/**
 * Лицо (1, 2, 3)
 */
public class MorphPerson implements Comparable<MorphPerson> {

    /**
     * Неопределено
     */
    public static final MorphPerson UNDEFINED; // 0

    /**
     * Первое
     */
    public static final MorphPerson FIRST; // 1

    /**
     * Второе
     */
    public static final MorphPerson SECOND; // 2

    /**
     * Третье
     */
    public static final MorphPerson THIRD; // 4


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private MorphPerson(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(MorphPerson v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, MorphPerson> mapIntToEnum; 
    private static java.util.HashMap<String, MorphPerson> mapStringToEnum; 
    private static MorphPerson[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static MorphPerson of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        MorphPerson item = new MorphPerson(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static MorphPerson of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static MorphPerson[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, MorphPerson>();
        mapStringToEnum = new java.util.HashMap<String, MorphPerson>();
        UNDEFINED = new MorphPerson(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        FIRST = new MorphPerson(1, "FIRST");
        mapIntToEnum.put(FIRST.value(), FIRST);
        mapStringToEnum.put(FIRST.m_str.toUpperCase(), FIRST);
        SECOND = new MorphPerson(2, "SECOND");
        mapIntToEnum.put(SECOND.value(), SECOND);
        mapStringToEnum.put(SECOND.m_str.toUpperCase(), SECOND);
        THIRD = new MorphPerson(4, "THIRD");
        mapIntToEnum.put(THIRD.value(), THIRD);
        mapStringToEnum.put(THIRD.m_str.toUpperCase(), THIRD);
        java.util.Collection<MorphPerson> col = mapIntToEnum.values();
        m_Values = new MorphPerson[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
